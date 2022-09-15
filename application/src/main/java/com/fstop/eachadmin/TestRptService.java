package com.fstop.eachadmin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.JRException;
import util.ReportUtil;

@Service
public class TestRptService {
	public byte[] exportPdf2() throws FileNotFoundException, JRException {
//        String templatePath = "classpath:templates/contract.jrxml";
		File file = null;
		try {
			file = new ClassPathResource("templates/contract.jrxml").getFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ContractEntity contract = new ContractEntity();
		contract.setContractCode("CON11123445567778888");
		contract.setContractName("马尔代夫海景房转让合同");
		contract.setContractOriginalCode("ORI555444333222111");
		contract.setOriginalCcyTaxIncludedAmt(new BigDecimal(123456789.12345666666).setScale(6, BigDecimal.ROUND_DOWN));
		contract.setLocalCcyTaxIncludedAmt(new BigDecimal(987654321.123456));
		contract.setContractType("租赁合同");
		contract.setContractDetailType("房屋租赁合同");
		contract.setSupplierName("太平洋租房股份有限公司");
		contract.setOperatorName("德玛西亚");
		contract.setOperatorOrgName("稀里糊涂银行总行");
		contract.setOperatorDeptName("马尔代夫总行财务部");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		contract.setEffectiveDate(df.format(new Date()));
		contract.setExpiredDate(df.format(new Date()));
		ObjectMapper oMapper = new ObjectMapper();
		return ReportUtil.exportPdfFromXml(file.getPath(), oMapper.convertValue(contract, Map.class));
	}
	
	public byte[] exportxls() throws FileNotFoundException, JRException {
//      String templatePath = "classpath:templates/contract.jrxml";
		File file = null;
		try {
			file = new ClassPathResource("templates/contract.jrxml").getFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ContractEntity contract = new ContractEntity();
		contract.setContractCode("CON11123445567778888");
		contract.setContractName("马尔代夫海景房转让合同");
		contract.setContractOriginalCode("ORI555444333222111");
		contract.setOriginalCcyTaxIncludedAmt(new BigDecimal(123456789.12345666666).setScale(6, BigDecimal.ROUND_DOWN));
		contract.setLocalCcyTaxIncludedAmt(new BigDecimal(987654321.123456));
		contract.setContractType("租赁合同");
		contract.setContractDetailType("房屋租赁合同");
		contract.setSupplierName("太平洋租房股份有限公司");
		contract.setOperatorName("德玛西亚");
		contract.setOperatorOrgName("稀里糊涂银行总行");
		contract.setOperatorDeptName("马尔代夫总行财务部");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		contract.setEffectiveDate(df.format(new Date()));
		contract.setExpiredDate(df.format(new Date()));
		ObjectMapper oMapper = new ObjectMapper();
		return ReportUtil.exportXlsFromXml(file.getPath(), oMapper.convertValue(contract, Map.class));
	}
	
//	//業務行
//	public List getBsTypeIdList(){
//		//List<BUSINESS_TYPE> list = business_type_Dao.getAll();
//		List list = business_type_Dao.find("FROM tw.org.twntch.po.BUSINESS_TYPE ORDER BY BUSINESS_TYPE_ID");
//		List beanList = new LinkedList<LabelValueBean>();
//		LabelValueBean bean = null;
//		new LabelValueBean("===請選擇===", "");
////		beanList.add(bean);
//		for(BUSINESS_TYPE po :list){
//			po.getBUSINESS_TYPE_ID();
//			bean = new LabelValueBean(po.getBUSINESS_TYPE_ID()+" - "+po.getBUSINESS_TYPE_NAME(), po.getBUSINESS_TYPE_ID());
//			beanList.add(bean);
//		}
//		System.out.println("beanList>>"+beanList);
//		return beanList;
//		
//	}
	
	
	//表單查詢產出
//	public String pageSearch(Map<String, String> param){
//		List<String> conditions_1 = new ArrayList<String>();
//		List<String> conditions_2 = new ArrayList<String>();
//		//是否包含整批資料("N"表示不過濾)
//		String filter_bat = param.get("FILTER_BAT")==null?"N":"Y";
//		
//		/* 20150210 HUANGPU 改以營業日(BIZDATE)查詢資料，非交易日期(TXDATE) */
//		String startDate = "";
//		if(StrUtils.isNotEmpty(param.get("START_DATE").trim())){
//			startDate = param.get("START_DATE").trim();
//			conditions_1.add(" BIZDATE >= '" + DateTimeUtils.convertDate(startDate, "yyyyMMdd", "yyyyMMdd") + "' ");
//		}
//				
//		String endDate = "";
//		if(StrUtils.isNotEmpty(param.get("END_DATE").trim())){
//			endDate = param.get("END_DATE").trim();
//			conditions_1.add(" BIZDATE <= '" + DateTimeUtils.convertDate(endDate, "yyyyMMdd", "yyyyMMdd") + "' ");
//		}
//		
//		String clearingphase = "";
//		if(StrUtils.isNotEmpty(param.get("CLEARINGPHASE").trim()) && !param.get("CLEARINGPHASE").trim().equals("all")){
//			clearingphase = param.get("CLEARINGPHASE").trim();
//			conditions_1.add(" CLEARINGPHASE = '" + clearingphase + "' ");
//		}
//		
//		
//		
//		String bgbkId = "";
//		if(StrUtils.isNotEmpty(param.get("BGBK_ID").trim()) && !param.get("BGBK_ID").trim().equals("all")){
//			bgbkId = param.get("BGBK_ID").trim();
//			conditions_1.add(" (SENDERHEAD = '" + bgbkId + "' OR OUTHEAD = '" + bgbkId + "' OR INHEAD = '" + bgbkId + "') ");
//		}
//		
//		String businessTypeId = "";
//		if(StrUtils.isNotEmpty(param.get("BUSINESS_TYPE_ID").trim()) && !param.get("BUSINESS_TYPE_ID").trim().equals("all")){
//			businessTypeId = param.get("BUSINESS_TYPE_ID").trim();
//			conditions_1.add(" BUSINESS_TYPE_ID = '" + businessTypeId + "' ");
//		}
//		
//		String ostan = "";
//		if(StrUtils.isNotEmpty(param.get("OSTAN")) && !param.get("OSTAN").equals("all")){
//			ostan = param.get("OSTAN");
//			conditions_1.add(" STAN = '" + ostan + "' ");
//		}
//		
//		if(StrUtils.isNotEmpty(param.get("RESULTCODE")) && !param.get("RESULTCODE").equals("all")){
//			if("A".equals(param.get("RESULTCODE"))){
//				conditions_1.add(" RESULTCODE IS NOT NULL ");
//			}else if("P".equals(param.get("RESULTCODE"))){
//				conditions_1.add(" RESULTCODE IS NULL ");
//			}
//		}
//		
//		String opbkId = "";
//		if(StrUtils.isNotEmpty(param.get("OPBK_ID").trim()) && !param.get("OPBK_ID").trim().equals("all")){
//			opbkId = param.get("OPBK_ID").trim();
//			if(filter_bat.equals("Y")){
//				conditions_2.addAll(conditions_1);
//				conditions_2.add(" COALESCE(FLBIZDATE,'') <> ''  ");
//				conditions_2.add(" ((INACQUIRE = '" + opbkId + "' AND substr(COALESCE(PCODE,''),4) = '2' ) OR (OUTACQUIRE = '" + opbkId + "' AND substr(COALESCE(PCODE,''),4) = '1')) ");
//				conditions_1.add(" COALESCE(FLBIZDATE,'') = ''  ");
//				conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId + "' OR INACQUIRE = '" + opbkId + "') ");
//			}else{
//				conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId + "' OR INACQUIRE = '" + opbkId + "') ");
//			}
//		}else{
//			if(filter_bat.equals("Y")){
//				conditions_2.addAll(conditions_1);
//				conditions_2.add(" COALESCE(FLBIZDATE,'') <> ''  ");
//				conditions_1.add(" COALESCE(FLBIZDATE,'') = ''  ");
//			}
//		}
//		
//		
//		
//		int pageNo = StrUtils.isEmpty(param.get("page")) ?0:Integer.valueOf(param.get("page"));
//		int pageSize = StrUtils.isEmpty(param.get("rows")) ?Integer.valueOf(Arguments.getStringArg("PAGE.SIZE")):Integer.valueOf(param.get("rows"));
//		
//		Map rtnMap = new HashMap();
//		
//		List<UNDONE_TXDATA> list = null;
//		Page page = null;
//		try {
//			list = new ArrayList<UNDONE_TXDATA>();
//			String condition_1 = "",condition_2 = "" ;
//			condition_1 = combine(conditions_1);
//			condition_2 = combine(conditions_2);
//			String sord =StrUtils.isNotEmpty(param.get("sord"))? param.get("sord"):"";
//			String sidx =StrUtils.isNotEmpty(param.get("sidx"))? param.get("sidx"):"";
//			String orderSQL = StrUtils.isNotEmpty(sidx)? " ORDER BY "+sidx+" "+sord:"";
//			StringBuffer tmpSQL = new StringBuffer();
//			StringBuffer cntSQL = new StringBuffer();
//			StringBuffer sumSQL = new StringBuffer();
//			StringBuffer sql= new StringBuffer();
//			if(StrUtils.isNotEmpty(param.get("sidx"))){
////				if("TXDT".equalsIgnoreCase(param.get("sidx"))){
////					orderSQL = " ORDER BY NEWTXDT "+param.get("sord");
////				}
////				if("TXAMT".equalsIgnoreCase(param.get("sidx"))){
////					orderSQL = " ORDER BY NEWTXAMT "+param.get("sord");
////				}
//				if("RESULTCODE".equalsIgnoreCase(param.get("sidx"))){
//					orderSQL = " ORDER BY NEWRESULT "+param.get("sord");
//				}
//			}
//			tmpSQL.append(" WITH TEMP AS ");
//			tmpSQL.append(" ( ");
//			tmpSQL.append(" SELECT   COALESCE(NEWTXDT ,'') AS TXDT, TXDATE, BIZDATE, STAN, OUTACCTNO, INACCTNO, COALESCE(NEWTXAMT,0) AS TXAMT , NEWRESULT ,SENDERACQUIRE, SENDERHEAD ");
//			
//			tmpSQL.append("  , SENDERID, EACH_TXN_NAME ");
//			tmpSQL.append(" , COALESCE(TXID,'') ||'-'|| COALESCE((SELECT TC.TXN_NAME FROM TXN_CODE TC WHERE TC.TXN_ID = TXID ),'') AS TXID ");
//			tmpSQL.append(" , COALESCE(SENDERBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=SENDERBANKID),'') SENDERBANKID "); 
//			tmpSQL.append(" , COALESCE(OUTBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=OUTBANKID),'') OUTBANKID "); 
//			tmpSQL.append(" , COALESCE(INBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=INBANKID),'') INBANKID ");
//			tmpSQL.append(" , COALESCE(PCODE,'') || '-' || COALESCE(EACH_TXN_NAME,'') AS PCODE ");
////			20151127 add by hugo req by UAT-20151126-01
//			tmpSQL.append(" , BUSINESS_TYPE_ID ");
//			tmpSQL.append(" FROM VW_ONBLOCKTAB ");
//			tmpSQL.append(" LEFT JOIN (  SELECT EACH_TXN_ID, EACH_TXN_NAME FROM EACH_TXN_CODE  ) ON PCODE = EACH_TXN_ID   ");
//			tmpSQL.append(" LEFT JOIN (  SELECT OTXDate, OSTAN, RESULTCODE FROM ONPENDINGTAB  ) ON TXDATE = OTXDate AND STAN = OSTAN   ");
//			//20150302 BY 李建利(與陳淑華開會討論)：只要是曾經是未完成交易(不包含處理中)，無論是否有回應結果，都可在此功能中查詢出來
//			tmpSQL.append(" WHERE RESULTSTATUS = 'P' AND SENDERSTATUS <> '1' ");
//			tmpSQL.append((StrUtils.isEmpty(condition_1)?"" : "AND " + condition_1));
//			if(filter_bat.equals("Y")){
//				tmpSQL.append(" UNION ALL ");
//				tmpSQL.append(" SELECT   COALESCE(NEWTXDT ,'') AS TXDT, TXDATE, BIZDATE, STAN, OUTACCTNO, INACCTNO, COALESCE(NEWTXAMT,0) AS TXAMT, NEWRESULT,SENDERACQUIRE, SENDERHEAD ");
//				
//				tmpSQL.append("  , SENDERID, EACH_TXN_NAME ");
//				tmpSQL.append(" , COALESCE(TXID,'') ||'-'|| COALESCE((SELECT TC.TXN_NAME FROM TXN_CODE TC WHERE TC.TXN_ID = TXID ),'') AS TXID ");
//				tmpSQL.append(" , COALESCE(SENDERBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=SENDERBANKID),'') SENDERBANKID "); 
//				tmpSQL.append(" , COALESCE(OUTBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=OUTBANKID),'') OUTBANKID "); 
//				tmpSQL.append(" , COALESCE(INBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=INBANKID),'') INBANKID ");
//				tmpSQL.append(" , COALESCE(PCODE,'') || '-' || COALESCE(EACH_TXN_NAME,'') AS PCODE ");
////				20151127 add by hugo req by UAT-20151126-01
//				tmpSQL.append(" , BUSINESS_TYPE_ID ");
//				tmpSQL.append(" FROM VW_ONBLOCKTAB ");
//				tmpSQL.append(" LEFT JOIN (  SELECT EACH_TXN_ID, EACH_TXN_NAME FROM EACH_TXN_CODE  ) ON PCODE = EACH_TXN_ID   ");
//				tmpSQL.append(" WHERE RESULTSTATUS = 'P' AND SENDERSTATUS <> '1' ");
//				tmpSQL.append((StrUtils.isEmpty(condition_2)?"" : "AND " + condition_2));
//			}
//			tmpSQL.append(" ) ");
//			
//			sql.append(" SELECT * FROM ( ");
//			sql.append("  	SELECT  ROWNUMBER() OVER( "+orderSQL+") AS ROWNUMBER , TEMP.*  ");
//			sql.append(" FROM TEMP ");
//			sql.append(" ) AS A    ");
//			sql.append("  WHERE ROWNUMBER >= " + (Page.getStartOfPage(pageNo, pageSize) + 1) + " AND ROWNUMBER <= " + (pageNo * pageSize) + " ");
//			sql.insert(0, tmpSQL.toString());
//			sql.append(orderSQL);
//			
//			cntSQL.append(" SELECT COALESCE(COUNT(*),0) AS NUM FROM TEMP ");
//			cntSQL.insert(0, tmpSQL.toString());
//			sumSQL.append(" SELECT COALESCE( SUM(TXAMT) ,0) AS TXAMT FROM TEMP ");
//			sumSQL.insert(0, tmpSQL.toString());
//			
//			//先算出總計的欄位值，放到rtnMap裡面，到頁面再放到下面的總計Grid裡面
//			System.out.println("sumSQL="+sumSQL);
////			List dataSumList = commonSpringDao.list(sumSQL.toString(),null);
////			跟資料庫相關的先註解掉 20220914
//			rtnMap.put("dataSumList",dataSumList);
//			
////			String cols[] = {"PCODE","NEWTXDT","TXDATE","STAN","SENDERBANKID","OUTBANKID","INBANKID","OUTACCTNO","INACCTNO","NEWTXAMT","TXID","SENDERID"};
//			String cols[] = {"PCODE","TXDT","TXDATE","STAN","SENDERBANKID","OUTBANKID","INBANKID","OUTACCTNO","INACCTNO","TXAMT","TXID","SENDERID"};
//		System.out.println("cntSQL==>"+cntSQL.toString().toUpperCase());
//		System.out.println("sql==>"+sql.toString().toUpperCase());
////			page = vw_onblocktab_Dao.getDataIII(pageNo, pageSize, cntSQL.toString(), sql.toString(), cols, UNDONE_TXDATA.class);
//// 因為還沒有寫資料庫的串法,所以把跟資料庫相關的Dao都先註解,只留方法 20220914
//			list = (List<UNDONE_TXDATA>) page.getResult();
//			System.out.println("UNDONE_TXDATA.list>>"+list);
//			list = list!=null&& list.size() ==0 ?null:list;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		if(page == null){
//			rtnMap.put("total", "0");
//			rtnMap.put("page", "0");
//			rtnMap.put("records", "0");
//			rtnMap.put("rows", new ArrayList());
//		}else{
//			rtnMap.put("total", page.getTotalPageCount());
//			rtnMap.put("page", String.valueOf(page.getCurrentPageNo()));
//			rtnMap.put("records", page.getTotalCount());
//			rtnMap.put("rows", list);
//		}
//		
//		String json = JSONUtils.map2json(rtnMap) ;
//		return json;
//	}
	
//	1406
//	Controller
//	public String send_1406(Map<String, String> param){
//
//		String json = "{}";
//		String stan = StrUtils.isNotEmpty(param.get("STAN"))?param.get("STAN"):"" ;
//		String txdate = StrUtils.isNotEmpty(param.get("TXDATE"))?param.get("TXDATE"):"" ;
//		txdate = DateTimeUtils.convertDate(2, txdate, "yyyy-MM-dd", "yyyyMMdd");
//		String resultCode = "";
//		Map rtnMap = new HashMap();
//		try {
//			//先檢查onpendingtab中是否有該筆資料存在
//			ONPENDINGTAB_PK id = new ONPENDINGTAB_PK(txdate, stan);
//			ONPENDINGTAB po = onpendingtab_Dao.get(id);
//			if(po == null){
//				rtnMap.put("result", "FALSE");
//				rtnMap.put("msg", "失敗，資料尚未轉移，PK={STAN:"+stan+",TXDATE:"+txdate+"}");
//			}else{
////				20150529 add by hugo req by UAT-20150526-06
//				if(po.getBIZDATE() !=null){//表示已有處理結果
//					rtnMap.put("result", "FALSE");
//					rtnMap.put("msg", "已有未完成交易處理結果，營業日="+po.getBIZDATE());
//					json = JSONUtils.map2json(rtnMap);
//					return json;
//				}
//				Header msgHeader = new Header();
//				msgHeader.setSystemHeader("eACH01");
//				msgHeader.setMsgType("0100");
//				msgHeader.setPrsCode("1406");
//				msgHeader.setStan("");//此案例未使用
//				msgHeader.setInBank("0000000");
//				msgHeader.setOutBank("9990000");	//20150109 FANNY說 票交發動的電文，「OUTBANK」必須固定為「9990000」
//				msgHeader.setDateTime(zDateHandler.getDateNum()+zDateHandler.getTheTime().replaceAll(":", ""));
//				msgHeader.setRspCode("0000");
//				Message msg = new Message();
//				msg.setHeader(msgHeader);
//				Body body = new Body();
//				body.setOSTAN(stan);
//				body.setOTxDate(txdate);
////				body.setResultCode(resultCode);
//				msg.setBody(body);
//				String telegram = MessageConverter.marshalling(msg);
//				rtnMap = socketClient.send(telegram);
//			}
//		} catch ( JAXBException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			rtnMap.put("result", "FALSE");
//			rtnMap.put("msg", "失敗，電文發送異常");
//		}catch(Exception ee){
//			ee.printStackTrace();
//			rtnMap.put("result", "FALSE");
//			rtnMap.put("msg", "失敗，系統異常");
//		}
//		
//		json = JSONUtils.map2json(rtnMap);
//		return json;
//	}
//	
//	
}


