package com.fstop.eachadmin;

import java.util.LinkedList;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;

import com.fstop.infra.dao.OnPendingTabRepository;
import com.fstop.infra.entity.onpendingtab;
import com.fstop.infra.entity.onpendingtabPK;

import java.util.HashMap;

import util.StrUtils;
import util.socketPackage;
import util.socketPackage.Body;
import util.socketPackage.Header;
import util.zDateHandler;
import util.messageConverter;
import util.DateTimeUtils;

public class NTRService {
	
	@Autowired
	OnPendingTabRepository onpendingtabR;
	
	//operation
	public List<LabelValueBean> getOpbkList(){
		List<BANK_OPBK> list = bank_opbk_bo.getAllOpbkList();
		List<LabelValueBean> beanList = new LinkedList<LabelValueBean>();
		LabelValueBean bean = null;
		for(BANK_OPBK po : list){
			bean = new LabelValueBean(po.getOPBK_ID() + " - " + po.getOPBK_NAME(), po.getOPBK_ID());
			beanList.add(bean);
		}
		return beanList;
	}
	
	
	
	//businessLabel
	public List<LabelValueBean> getBsTypeIdList(){
		//List<BUSINESS_TYPE> list = business_type_Dao.getAll();
		List<BUSINESS_TYPE> list = business_type_Dao.find("FROM tw.org.twntch.po.BUSINESS_TYPE ORDER BY BUSINESS_TYPE_ID");
		List<LabelValueBean> beanList = new LinkedList<LabelValueBean>();
		LabelValueBean bean = null;
		new LabelValueBean("===請選擇===", "");
//		beanList.add(bean);
		for(BUSINESS_TYPE po :list){
			po.getBUSINESS_TYPE_ID();
			bean = new LabelValueBean(po.getBUSINESS_TYPE_ID()+" - "+po.getBUSINESS_TYPE_NAME(), po.getBUSINESS_TYPE_ID());
			beanList.add(bean);
		}
		System.out.println("beanList>>"+beanList);
		return beanList;
		
	}
	
	
	
	//search
    public String getNotTradResList(Map<String, String> params){
		
		String startDate = StrUtils.isEmpty(params.get("START_DATE"))?"":params.get("START_DATE");//交易日期
		String endDate = StrUtils.isEmpty(params.get("END_DATE"))?"":params.get("END_DATE");//交易日期
		
		String opbkId = StrUtils.isEmpty(params.get("OPBK_ID"))?"all":params.get("OPBK_ID");//操作行代號
		String bgbkId = StrUtils.isEmpty(params.get("BGBK_ID"))?"all":params.get("BGBK_ID");//總行代號
		String clearingphase = StrUtils.isEmpty(params.get("CLEARINGPHASE"))?"all":params.get("CLEARINGPHASE");//清算階段代號
		String bus_Type_Id = StrUtils.isEmpty(params.get("BUSINESS_TYPE_ID"))?"all":params.get("BUSINESS_TYPE_ID");//業務類別代號
		String resultStatus = StrUtils.isEmpty(params.get("RESULTSTATUS"))?"all":params.get("RESULTSTATUS");//業務類別代號
		String ostan = StrUtils.isEmpty(params.get("OSTAN"))?"all":params.get("OSTAN");//系統追蹤序號
		
		int pageNo = StrUtils.isEmpty(params.get("page")) ?0:Integer.valueOf(params.get("page"));
		int pageSize = StrUtils.isEmpty(params.get("rows")) ?Integer.valueOf(Arguments.getStringArg("PAGE.SIZE")):Integer.valueOf(params.get("rows"));
		String isSearch = StrUtils.isEmpty(params.get("isSearch")) ?"Y":params.get("isSearch");
		//是否包含整批資料("N"表示不過濾)
		String filter_bat = params.get("FILTER_BAT")==null?"N":"Y";
		List<ONBLOCKTAB> list = null;
		Map rtnMap = new HashMap();
		Page page = null;
		String condition_1 = "";
		String condition_2 = "";
		try {
			list = new ArrayList<ONBLOCKTAB>();
			List<String> conditions_1 = new ArrayList<String>();
			List<String> conditions_2 = new ArrayList<String>();
			/* 20150210 HUANGPU 改以清算階段後的營業日(BIZDATE)查詢資料，非原交易日期(OTXDATE) */
			if(StrUtils.isNotEmpty(startDate)){//交易日期
				conditions_1.add(" BIZDATE >= '"+send1406DateTimeUtil.convertDate(startDate, "yyyyMMdd", "yyyyMMdd")+"' ");
			}
			if(StrUtils.isNotEmpty(endDate)){//交易日期
				conditions_1.add(" BIZDATE <= '"+send1406DateTimeUtil.convertDate(endDate, "yyyyMMdd", "yyyyMMdd")+"' ");
			}
					
			if(!bgbkId.equals("all")){//發動行所屬總行、入賬行所屬總行、扣款行所屬總行
				conditions_1.add(" (SENDERHEAD = '"+bgbkId+"' OR INHEAD = '"+bgbkId+"' OR OUTHEAD = '"+bgbkId+"') ");
			}
			if(!clearingphase.equals("all")){//清算階段代號
				conditions_1.add(" CLEARINGPHASE= '"+clearingphase+"' ");
			}
			if(!resultStatus.equals("all")){
				conditions_1.add(" COALESCE(RESULTCODE,'00') = '" + resultStatus + "' ");
			}
			if(!bus_Type_Id.equals("all")){
				conditions_1.add(" ETC.BUSINESS_TYPE_ID IN ('"+bus_Type_Id+"') ");
			}
			if(!ostan.equals("all")){
				conditions_1.add(" A.OSTAN = '"+ostan+"' ");
			}
			if(!opbkId.equals("all")){//發動行所屬操作行、入賬行所屬操作行、扣款行所屬操作行
				if(filter_bat.equals("Y")){
					conditions_2.addAll(conditions_1);
					conditions_2.add(" COALESCE(FLBIZDATE,'') <> ''  ");
					conditions_2.add(" ((INACQUIRE = '" + opbkId + "' AND substr(COALESCE(PCODE,''),4) = '2' ) OR (OUTACQUIRE = '" + opbkId + "' AND substr(COALESCE(PCODE,''),4) = '1')) ");
					conditions_1.add(" COALESCE(FLBIZDATE,'') = ''  ");
					conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId + "' OR INACQUIRE = '" + opbkId + "') ");
				}else{
					conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId + "' OR INACQUIRE = '" + opbkId + "') ");
				}
//					conditions_1.add(" (SENDERACQUIRE = '"+opbkId+"' OR INACQUIRE = '"+opbkId+"' OR OUTACQUIRE = '"+opbkId+"') ");
			}else{
				if(filter_bat.equals("Y")){
					conditions_2.addAll(conditions_1);
					conditions_2.add(" COALESCE(FLBIZDATE,'') <> ''  ");
					conditions_1.add(" COALESCE(FLBIZDATE,'') = ''  ");
				}
			}	
			condition_1 = combine(conditions_1);
			condition_2 = combine(conditions_2);
			StringBuffer sql = new StringBuffer();
			StringBuffer tmpSQL = new StringBuffer();
			StringBuffer sumSQL = new StringBuffer();
			StringBuffer cntSQL = new StringBuffer();
			String sord =StrUtils.isNotEmpty(params.get("sord"))? params.get("sord"):"";
			String sidx =StrUtils.isNotEmpty(params.get("sidx"))? params.get("sidx"):"";
			String orderSQL = "";
			if(StrUtils.isNotEmpty(params.get("sidx"))){
//					20160329 edit by hugo 修正未完成交易結果 grid 按部分欄位排序時(ex:交易日期、金額)，查無資料問題
//					if("STAN".equalsIgnoreCase(params.get("sidx"))){
//						orderSQL = " ORDER BY A.OSTAN "+params.get("sord");
//					}
//					if("TXDT".equalsIgnoreCase(params.get("sidx"))){
//						if("AAA".equalsIgnoreCase(params.get("sidx"))){
//						orderSQL = " ORDER BY (SELECT NEWTXDT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) "+params.get("sord");
//					}
//					else if("TXAMT".equalsIgnoreCase(params.get("sidx"))){
//						else if("BBB".equalsIgnoreCase(params.get("sidx"))){
//						orderSQL = " ORDER BY (SELECT NEWTXAMT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) "+params.get("sord");
//					}
//					else if("RESULTCODE".equalsIgnoreCase(params.get("sidx"))){
//						else if("CCC".equalsIgnoreCase(params.get("sidx"))){
//						orderSQL = " ORDER BY (CASE (SELECT NEWRESULT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) WHEN 'R' THEN '失敗' WHEN 'A' THEN '成功' ELSE '未完成' END) "+params.get("sord");
//					}
//					else if("ACCTCODE".equalsIgnoreCase(params.get("sidx"))){
//						orderSQL = " ORDER BY (SELECT ACCTCODE FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) "+params.get("sord");
//					}
//					else if("CONRESULTCODE".equalsIgnoreCase(params.get("sidx"))){
//						orderSQL = " ORDER BY (GETRESPDESC(A.OTXDATE, A.OSTAN)) "+params.get("sord");
//					}
//					else{
//						orderSQL = StrUtils.isNotEmpty(sidx)? " ORDER BY "+sidx+" "+sord:"";
//					}
					orderSQL = StrUtils.isNotEmpty(sidx)? " ORDER BY "+sidx+" "+sord:"";
			}
			tmpSQL.append("WITH TEMP AS ( ");
			tmpSQL.append(" SELECT OUTACCT, INACCT, OBIZDATE, OCLEARINGPHASE, BIZDATE, CLEARINGPHASE, SENDERACQUIRE, SENDERHEAD , ETC.EACH_TXN_NAME, '' AS ACCTCODE  , COALESCE(A.OSTAN,'') AS OSTAN");
			tmpSQL.append(" , COALESCE(A.OTXDATE, '') AS TXDATE , COALESCE(A.OSTAN,'') AS STAN , (COALESCE(RESULTCODE, 'eACH')) AS CONRESULTCODE ");
			tmpSQL.append(" , A.SENDERBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.SENDERBANKID),'') AS SENDERBANKID ");
			tmpSQL.append(" , A.OUTBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.OUTBANKID),'') AS OUTBANKID ");
			tmpSQL.append(" , A.INBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.INBANKID),'') AS INBANKID ");
			tmpSQL.append(" , (CASE A.RESULTCODE WHEN '01' THEN '失敗' ELSE '成功' END) AS RESULTCODE,COALESCE(A.ACHFLAG,'') AS ACHFLAG  ");
			tmpSQL.append(" , (SELECT NEWTXAMT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) AS TXAMT ");
			tmpSQL.append(" , A.PCODE || '-' || COALESCE(ETC.EACH_TXN_NAME,'') AS PCODE ");
			tmpSQL.append(" , VARCHAR_FORMAT((SELECT NEWTXDT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN),'YYYY-MM-DD HH24:MI:SS') AS TXDT ");
			tmpSQL.append(" FROM ONPENDINGTAB A ");
			tmpSQL.append(" LEFT JOIN EACH_TXN_CODE ETC ON A.PCODE = ETC.EACH_TXN_ID  ");
			tmpSQL.append(" WHERE COALESCE(BIZDATE,'') <> '' AND COALESCE(CLEARINGPHASE,'') <> ''  ");
			tmpSQL.append("    " + (StrUtils.isEmpty(condition_1)? "" : "AND " + condition_1));
			if(filter_bat.equals("Y")){
				tmpSQL.append("  UNION ALL  ");
				tmpSQL.append(" SELECT OUTACCT, INACCT, OBIZDATE, OCLEARINGPHASE, BIZDATE, CLEARINGPHASE, SENDERACQUIRE, SENDERHEAD , ETC.EACH_TXN_NAME, '' AS ACCTCODE  , COALESCE(A.OSTAN,'') AS OSTAN");
				tmpSQL.append(" , COALESCE(A.OTXDATE, '') AS TXDATE , COALESCE(A.OSTAN,'') AS STAN , (COALESCE(RESULTCODE, 'eACH')) AS CONRESULTCODE ");
				tmpSQL.append(" , A.SENDERBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.SENDERBANKID),'') AS SENDERBANKID ");
				tmpSQL.append(" , A.OUTBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.OUTBANKID),'') AS OUTBANKID ");
				tmpSQL.append(" , A.INBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.INBANKID),'') AS INBANKID ");
				tmpSQL.append(" , (CASE A.RESULTCODE WHEN '01' THEN '失敗' ELSE '成功' END) AS RESULTCODE,COALESCE(A.ACHFLAG,'') AS ACHFLAG  ");
				tmpSQL.append(" , (SELECT NEWTXAMT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) AS TXAMT ");
				tmpSQL.append(" , A.PCODE || '-' || COALESCE(ETC.EACH_TXN_NAME,'') AS PCODE ");
				tmpSQL.append(" , VARCHAR_FORMAT((SELECT NEWTXDT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN),'YYYY-MM-DD HH24:MI:SS') AS TXDT ");
				tmpSQL.append(" FROM ONPENDINGTAB A ");
				tmpSQL.append(" LEFT JOIN EACH_TXN_CODE ETC ON A.PCODE = ETC.EACH_TXN_ID  ");
				tmpSQL.append(" WHERE COALESCE(BIZDATE,'') <> '' AND COALESCE(CLEARINGPHASE,'') <> ''  ");
				tmpSQL.append("    " + (StrUtils.isEmpty(condition_2)? "" : "AND " + condition_2));
			}
			tmpSQL.append(" ) ");
			sql.append(" SELECT * FROM ( ");
			sql.append("  	SELECT  ROWNUMBER() OVER( "+orderSQL+") AS ROWNUMBER , TEMP.*  ");
			sql.append(" FROM TEMP ");
			sql.append(" ) AS A    ");
			sql.append("  WHERE ROWNUMBER >= " + (Page.getStartOfPage(pageNo, pageSize) + 1) + " AND ROWNUMBER <= " + (pageNo * pageSize) + " ");
			sql.insert(0, tmpSQL.toString());
			sql.append(orderSQL);
			//先算出總計的欄位值，放到rtnMap裡面，到頁面再放到下面的總計Grid裡面
			sumSQL.append("SELECT SUM(TXAMT) AS TXAMT  FROM TEMP ");
			sumSQL.insert(0, tmpSQL.toString());
			System.out.println("sumSQL="+sumSQL);
			String dataSumCols[] = {"TXAMT"};
			list = onblocktab_Dao.dataSum(sumSQL.toString(),dataSumCols,ONBLOCKTAB.class);
			
			/*
			for(ONBLOCKTAB po:list){
				System.out.println(String.format("SUM(X.TXAMT)=%s", po.getTXAMT()));
			}
			*/
			rtnMap.put("dataSumList", list);
			cntSQL.append("SELECT COUNT(*) AS NUM FROM TEMP");
			cntSQL.insert(0, tmpSQL.toString());
			String cols[] = {"PCODE","TXDT","STAN","TXDATE","SENDERBANKID", "OUTBANKID", "INBANKID","OUTACCT","INACCT", 
							 "TXAMT", "CONRESULTCODE","OBIZDATE","OCLEARINGPHASE","RESULTCODE", "ACCTCODE" ,"ACHFLAG"  };
			System.out.println("cntSQL===>"+cntSQL.toString());
			System.out.println("sql===>"+sql.toString());
			page= onblocktab_Dao.getDataIIII(Integer.valueOf(pageNo), Integer.valueOf(pageSize), cntSQL.toString(), sql.toString(), cols, ONBLOCKTABbean.class);
			list = (List<ONBLOCKTAB>) page.getResult();
			System.out.println("ONBLOCKTAB.list>>"+list);
			list = list!=null&& list.size() ==0 ?null:list;
			if(page == null){
				rtnMap.put("total", "0");
				rtnMap.put("page", "0");
				rtnMap.put("records", "0");
				rtnMap.put("rows", new ArrayList());
			}else{
				rtnMap.put("total", page.getTotalPageCount());
				rtnMap.put("page", String.valueOf(page.getCurrentPageNo()));
				rtnMap.put("records", page.getTotalCount());
				rtnMap.put("rows", list);
			}
			
			String newParams = params.get("serchStrs");
			System.out.println("serchStrs ="+newParams);
			if(resultStatus.equals("00")){
				newParams = newParams.replace("\"RESULTSTATUS\":\"00\"", "\"RESULTSTATUS\":\"成功\"");
			    params.put("serchStrs", newParams);
			}else if(resultStatus.equals("01")){
				newParams = newParams.replace("\"RESULTSTATUS\":\"01\"", "\"RESULTSTATUS\":\"失敗\"");
			    params.put("serchStrs", newParams);
			}
//				必須是按下UI的查詢才紀錄
			if(isSearch.equals("Y")){
				userlog_bo.writeLog("C", null, null, params);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnMap.put("total", 1);
			rtnMap.put("page", 1);
			rtnMap.put("records", 0);
			rtnMap.put("rows", new ArrayList<>());
			rtnMap.put("msg", "查詢失敗");
			userlog_bo.writeFailLog("C", rtnMap, null, null, params);
		}
		String json = JSONUtils.map2json(rtnMap) ;
		System.out.println("json===>"+json+"<===");
		return json;
}
    
    
    
    
    
    
    //===============================
    //details----請求傳送未完成交易結果(1406)
    public String send_1406(Map<String, String> param){
		/* 查詢未完成交易處理結果 
			<?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
			<msg> 
			    <header> 
			        <SystemHeader>eACH01</SystemHeader> 
			        <MsgType>0100</MsgType> 
			        <PrsCode>1406</PrsCode> 
			        <Stan>XXXXXXX</Stan> 
			        <InBank>0000000</InBank> 
			        <OutBank>9990000</OutBank> 
			        <DateTime>YYYYMMDDHHMMSS</DateTime> 
			        <RspCode>0000</RspCode> 
			    </header> 
			    <body> 
			         <OTxDate></OTXDate> 
			         <OSTAN></OSTAN> 
			    </body> 
			</msg> 

		 */
		String json = "{}";
		//String stan = StrUtils.isNotEmpty(param.get("STAN"))?param.get("STAN"):"" ;
		String stan = StrUtils.isNotEmpty(param.get("STAN"))?param.get("STAN"):"" ;
		String txdate = StrUtils.isNotEmpty(param.get("TXDATE"))?param.get("TXDATE"):"" ;
		txdate = DateTimeUtils.convertDate(2, txdate, "yyyy-MM-dd", "yyyyMMdd");
		String resultCode = "";
		Map rtnMap = new HashMap();
		try {
			//先檢查onpendingtab中是否有該筆資料存在
			onpendingtabPK id = new onpendingtabPK(txdate, stan);
			Optional<onpendingtab> po = onpendingtabR.findById(id);
			if(po == null){
				rtnMap.put("result", "FALSE");
				rtnMap.put("msg", "失敗，資料尚未轉移，PK={STAN:"+stan+",TXDATE:"+txdate+"}");
			}else{
//				20150529 add by hugo req by UAT-20150526-06
				if(po.get().getBIZDATE() !=null){//表示已有處理結果
					rtnMap.put("result", "FALSE");
					rtnMap.put("msg", "已有未完成交易處理結果，營業日="+po.get().getBIZDATE());
				//TODO
				//	json = JSONUtils.map2json(rtnMap);
					return json;
				}
				Header msgHeader = new Header();
				msgHeader.setSystemHeader("eACH01");//11
				msgHeader.setMsgType("0100");//11
				msgHeader.setPrsCode("1406");//11
				msgHeader.setStan("");//此案例未使用//EachSyssStatusTabDto
				msgHeader.setInBank("0000000");//11
				msgHeader.setOutBank("9990000");	//20150109 FANNY說 票交發動的電文，「OUTBANK」必須固定為「9990000」 //11
				msgHeader.setDateTime(zDateHandler.getDateNum()+zDateHandler.getTheTime().replaceAll(":", ""));
				msgHeader.setRspCode("0000");
				socketPackage msg = new socketPackage();
				msg.setHeader(msgHeader);
				Body body = new Body();
				body.setOSTAN(stan);
				body.setOTxDate(txdate);
//				body.setResultCode(resultCode);
				msg.setBody(body);
				String telegram = messageConverter.marshalling(msg);
				//TODO
//				rtnMap = socketClient.send(telegram);//socket先註解
			}
		} catch ( JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnMap.put("result", "FALSE");
			rtnMap.put("msg", "失敗，電文發送異常");
		}catch(Exception ee){
			ee.printStackTrace();
			rtnMap.put("result", "FALSE");
			rtnMap.put("msg", "失敗，系統異常");
		}
		//TODO
//		json = JSONUtils.map2json(rtnMap);//json先不搬
		return json;
	}
    
    
    
    
	//===========================================================
	//details----請求傳送確認訊息(1400)
    public String send_1400(Map<String, String> param){
		/* 請求傳送確認訊息			
<?xml version="1.0" encoding="UTF-8" standalone="yes"?> 
<msg> 
    <header> 
        <SystemHeader>eACH01</SystemHeader> 
        <MsgType>0100</MsgType> 
        <PrsCode>1400</PrsCode> 
        <Stan>XXXXXXX</Stan> 
        <InBank>0000000</InBank> 
        <OutBank>9990000</OutBank> 
        <DateTime>YYYYMMDDHHMMSS</DateTime> 
        <RspCode>0000</RspCode> 
    </header> 
    <body> 
         <OTxDate></OTxDate> 
         <OSTAN></OSTAN> 
    </body> 
</msg> 



		 */
		String json = "{}";
		String stan = StrUtils.isNotEmpty(param.get("STAN"))?param.get("STAN"):"" ;
		String txdate = StrUtils.isNotEmpty(param.get("TXDATE"))?param.get("TXDATE"):"" ;
		txdate = DateTimeUtils.convertDate(2, txdate, "yyyy-MM-dd", "yyyyMMdd");
		String resultCode = "";
		Map rtnMap = new HashMap();
		try {
			//先檢查onpendingtab中是否有該筆資料存在
			onpendingtabPK id = new onpendingtabPK(txdate, stan);
			Optional<onpendingtab> po = onpendingtabR.findById(id);
			if(po != null){
				rtnMap.put("result", "FALSE");
				rtnMap.put("msg", "失敗，資料已轉移，PK={STAN:"+stan+",TXDATE:"+txdate+"}");
			}else{
				Header msgHeader = new Header();
				msgHeader.setSystemHeader("eACH01");
				msgHeader.setMsgType("0100");
				msgHeader.setPrsCode("1400");
				msgHeader.setStan("");//此案例未使用
				msgHeader.setInBank("0000000");
				msgHeader.setOutBank("9990000");	//20150109 FANNY說 票交發動的電文，「OUTBANK」必須固定為「9990000」
				msgHeader.setDateTime(zDateHandler.getDateNum()+zDateHandler.getTheTime().replaceAll(":", ""));
				msgHeader.setRspCode("0000");
				socketPackage msg = new socketPackage();
				msg.setHeader(msgHeader);
				Body body = new Body();
				body.setOSTAN(stan);
				body.setOTxDate(txdate);
//				body.setResultCode(resultCode);
				msg.setBody(body);
				String telegram = messageConverter.marshalling(msg);
				//TODO
//				rtnMap = socketClient.send(telegram);
			}
		} catch ( JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnMap.put("result", "FALSE");
			rtnMap.put("msg", "失敗，電文發送異常");
		}catch(Exception ee){
			ee.printStackTrace();
			rtnMap.put("result", "FALSE");
			rtnMap.put("msg", "失敗，系統異常");
		}
		
//		json = JSONUtils.map2json(rtnMap);
		return json;
	}
	
	
	

}
