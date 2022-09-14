package com.fstop.eachadmin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
//-------------------------業務行------------------------------------------------
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
//-------------------------查詢------------------------------------------------
	public String pageSearch(Map<String, String> param){
		List<String> conditions_1 = new ArrayList<String>();
		List<String> conditions_2 = new ArrayList<String>();
		//是否包含整批資料("N"表示不過濾)
		String filter_bat = param.get("FILTER_BAT")==null?"N":"Y";
		
		/* 20150210 HUANGPU 改以營業日(BIZDATE)查詢資料，非交易日期(TXDATE) */
		String startDate = "";
		if(StrUtils.isNotEmpty(param.get("START_DATE").trim())){
			startDate = param.get("START_DATE").trim();
			conditions_1.add(" BIZDATE >= '" + DateTimeUtils.convertDate(startDate, "yyyyMMdd", "yyyyMMdd") + "' ");
		}
				
		String endDate = "";
		if(StrUtils.isNotEmpty(param.get("END_DATE").trim())){
			endDate = param.get("END_DATE").trim();
			conditions_1.add(" BIZDATE <= '" + DateTimeUtils.convertDate(endDate, "yyyyMMdd", "yyyyMMdd") + "' ");
		}
		
		String clearingphase = "";
		if(StrUtils.isNotEmpty(param.get("CLEARINGPHASE").trim()) && !param.get("CLEARINGPHASE").trim().equals("all")){
			clearingphase = param.get("CLEARINGPHASE").trim();
			conditions_1.add(" CLEARINGPHASE = '" + clearingphase + "' ");
		}
		
		
		
		String bgbkId = "";
		if(StrUtils.isNotEmpty(param.get("BGBK_ID").trim()) && !param.get("BGBK_ID").trim().equals("all")){
			bgbkId = param.get("BGBK_ID").trim();
			conditions_1.add(" (SENDERHEAD = '" + bgbkId + "' OR OUTHEAD = '" + bgbkId + "' OR INHEAD = '" + bgbkId + "') ");
		}
		
		String businessTypeId = "";
		if(StrUtils.isNotEmpty(param.get("BUSINESS_TYPE_ID").trim()) && !param.get("BUSINESS_TYPE_ID").trim().equals("all")){
			businessTypeId = param.get("BUSINESS_TYPE_ID").trim();
			conditions_1.add(" BUSINESS_TYPE_ID = '" + businessTypeId + "' ");
		}
		
		String ostan = "";
		if(StrUtils.isNotEmpty(param.get("OSTAN")) && !param.get("OSTAN").equals("all")){
			ostan = param.get("OSTAN");
			conditions_1.add(" STAN = '" + ostan + "' ");
		}
		
		if(StrUtils.isNotEmpty(param.get("RESULTCODE")) && !param.get("RESULTCODE").equals("all")){
			if("A".equals(param.get("RESULTCODE"))){
				conditions_1.add(" RESULTCODE IS NOT NULL ");
			}else if("P".equals(param.get("RESULTCODE"))){
				conditions_1.add(" RESULTCODE IS NULL ");
			}
		}
		
		String opbkId = "";
		if(StrUtils.isNotEmpty(param.get("OPBK_ID").trim()) && !param.get("OPBK_ID").trim().equals("all")){
			opbkId = param.get("OPBK_ID").trim();
			if(filter_bat.equals("Y")){
				conditions_2.addAll(conditions_1);
				conditions_2.add(" COALESCE(FLBIZDATE,'') <> ''  ");
				conditions_2.add(" ((INACQUIRE = '" + opbkId + "' AND substr(COALESCE(PCODE,''),4) = '2' ) OR (OUTACQUIRE = '" + opbkId + "' AND substr(COALESCE(PCODE,''),4) = '1')) ");
				conditions_1.add(" COALESCE(FLBIZDATE,'') = ''  ");
				conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId + "' OR INACQUIRE = '" + opbkId + "') ");
			}else{
				conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId + "' OR INACQUIRE = '" + opbkId + "') ");
			}
		}else{
			if(filter_bat.equals("Y")){
				conditions_2.addAll(conditions_1);
				conditions_2.add(" COALESCE(FLBIZDATE,'') <> ''  ");
				conditions_1.add(" COALESCE(FLBIZDATE,'') = ''  ");
			}
		}
		
		
		
		int pageNo = StrUtils.isEmpty(param.get("page")) ?0:Integer.valueOf(param.get("page"));
		int pageSize = StrUtils.isEmpty(param.get("rows")) ?Integer.valueOf(Arguments.getStringArg("PAGE.SIZE")):Integer.valueOf(param.get("rows"));
		
		Map rtnMap = new HashMap();
		
		List<UNDONE_TXDATA> list = null;
		Page page = null;
		try {
			list = new ArrayList<UNDONE_TXDATA>();
			String condition_1 = "",condition_2 = "" ;
			condition_1 = combine(conditions_1);
			condition_2 = combine(conditions_2);
			String sord =StrUtils.isNotEmpty(param.get("sord"))? param.get("sord"):"";
			String sidx =StrUtils.isNotEmpty(param.get("sidx"))? param.get("sidx"):"";
			String orderSQL = StrUtils.isNotEmpty(sidx)? " ORDER BY "+sidx+" "+sord:"";
			StringBuffer tmpSQL = new StringBuffer();
			StringBuffer cntSQL = new StringBuffer();
			StringBuffer sumSQL = new StringBuffer();
			StringBuffer sql= new StringBuffer();
			if(StrUtils.isNotEmpty(param.get("sidx"))){
//				if("TXDT".equalsIgnoreCase(param.get("sidx"))){
//					orderSQL = " ORDER BY NEWTXDT "+param.get("sord");
//				}
//				if("TXAMT".equalsIgnoreCase(param.get("sidx"))){
//					orderSQL = " ORDER BY NEWTXAMT "+param.get("sord");
//				}
				if("RESULTCODE".equalsIgnoreCase(param.get("sidx"))){
					orderSQL = " ORDER BY NEWRESULT "+param.get("sord");
				}
			}
			tmpSQL.append(" WITH TEMP AS ");
			tmpSQL.append(" ( ");
			tmpSQL.append(" SELECT   COALESCE(NEWTXDT ,'') AS TXDT, TXDATE, BIZDATE, STAN, OUTACCTNO, INACCTNO, COALESCE(NEWTXAMT,0) AS TXAMT , NEWRESULT ,SENDERACQUIRE, SENDERHEAD ");
			
			tmpSQL.append("  , SENDERID, EACH_TXN_NAME ");
			tmpSQL.append(" , COALESCE(TXID,'') ||'-'|| COALESCE((SELECT TC.TXN_NAME FROM TXN_CODE TC WHERE TC.TXN_ID = TXID ),'') AS TXID ");
			tmpSQL.append(" , COALESCE(SENDERBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=SENDERBANKID),'') SENDERBANKID "); 
			tmpSQL.append(" , COALESCE(OUTBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=OUTBANKID),'') OUTBANKID "); 
			tmpSQL.append(" , COALESCE(INBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=INBANKID),'') INBANKID ");
			tmpSQL.append(" , COALESCE(PCODE,'') || '-' || COALESCE(EACH_TXN_NAME,'') AS PCODE ");
//			20151127 add by hugo req by UAT-20151126-01
			tmpSQL.append(" , BUSINESS_TYPE_ID ");
			tmpSQL.append(" FROM VW_ONBLOCKTAB ");
			tmpSQL.append(" LEFT JOIN (  SELECT EACH_TXN_ID, EACH_TXN_NAME FROM EACH_TXN_CODE  ) ON PCODE = EACH_TXN_ID   ");
			tmpSQL.append(" LEFT JOIN (  SELECT OTXDate, OSTAN, RESULTCODE FROM ONPENDINGTAB  ) ON TXDATE = OTXDate AND STAN = OSTAN   ");
			//20150302 BY 李建利(與陳淑華開會討論)：只要是曾經是未完成交易(不包含處理中)，無論是否有回應結果，都可在此功能中查詢出來
			tmpSQL.append(" WHERE RESULTSTATUS = 'P' AND SENDERSTATUS <> '1' ");
			tmpSQL.append((StrUtils.isEmpty(condition_1)?"" : "AND " + condition_1));
			if(filter_bat.equals("Y")){
				tmpSQL.append(" UNION ALL ");
				tmpSQL.append(" SELECT   COALESCE(NEWTXDT ,'') AS TXDT, TXDATE, BIZDATE, STAN, OUTACCTNO, INACCTNO, COALESCE(NEWTXAMT,0) AS TXAMT, NEWRESULT,SENDERACQUIRE, SENDERHEAD ");
				
				tmpSQL.append("  , SENDERID, EACH_TXN_NAME ");
				tmpSQL.append(" , COALESCE(TXID,'') ||'-'|| COALESCE((SELECT TC.TXN_NAME FROM TXN_CODE TC WHERE TC.TXN_ID = TXID ),'') AS TXID ");
				tmpSQL.append(" , COALESCE(SENDERBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=SENDERBANKID),'') SENDERBANKID "); 
				tmpSQL.append(" , COALESCE(OUTBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=OUTBANKID),'') OUTBANKID "); 
				tmpSQL.append(" , COALESCE(INBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=INBANKID),'') INBANKID ");
				tmpSQL.append(" , COALESCE(PCODE,'') || '-' || COALESCE(EACH_TXN_NAME,'') AS PCODE ");
//				20151127 add by hugo req by UAT-20151126-01
				tmpSQL.append(" , BUSINESS_TYPE_ID ");
				tmpSQL.append(" FROM VW_ONBLOCKTAB ");
				tmpSQL.append(" LEFT JOIN (  SELECT EACH_TXN_ID, EACH_TXN_NAME FROM EACH_TXN_CODE  ) ON PCODE = EACH_TXN_ID   ");
				tmpSQL.append(" WHERE RESULTSTATUS = 'P' AND SENDERSTATUS <> '1' ");
				tmpSQL.append((StrUtils.isEmpty(condition_2)?"" : "AND " + condition_2));
			}
			tmpSQL.append(" ) ");
			
			sql.append(" SELECT * FROM ( ");
			sql.append("  	SELECT  ROWNUMBER() OVER( "+orderSQL+") AS ROWNUMBER , TEMP.*  ");
			sql.append(" FROM TEMP ");
			sql.append(" ) AS A    ");
			sql.append("  WHERE ROWNUMBER >= " + (Page.getStartOfPage(pageNo, pageSize) + 1) + " AND ROWNUMBER <= " + (pageNo * pageSize) + " ");
			sql.insert(0, tmpSQL.toString());
			sql.append(orderSQL);
			
			cntSQL.append(" SELECT COALESCE(COUNT(*),0) AS NUM FROM TEMP ");
			cntSQL.insert(0, tmpSQL.toString());
			sumSQL.append(" SELECT COALESCE( SUM(TXAMT) ,0) AS TXAMT FROM TEMP ");
			sumSQL.insert(0, tmpSQL.toString());
			
			//先算出總計的欄位值，放到rtnMap裡面，到頁面再放到下面的總計Grid裡面
			System.out.println("sumSQL="+sumSQL);
			List dataSumList = commonSpringDao.list(sumSQL.toString(),null);
			rtnMap.put("dataSumList",dataSumList);
			
//			String cols[] = {"PCODE","NEWTXDT","TXDATE","STAN","SENDERBANKID","OUTBANKID","INBANKID","OUTACCTNO","INACCTNO","NEWTXAMT","TXID","SENDERID"};
			String cols[] = {"PCODE","TXDT","TXDATE","STAN","SENDERBANKID","OUTBANKID","INBANKID","OUTACCTNO","INACCTNO","TXAMT","TXID","SENDERID"};
		System.out.println("cntSQL==>"+cntSQL.toString().toUpperCase());
		System.out.println("sql==>"+sql.toString().toUpperCase());
			page = vw_onblocktab_Dao.getDataIII(pageNo, pageSize, cntSQL.toString(), sql.toString(), cols, UNDONE_TXDATA.class);
			list = (List<UNDONE_TXDATA>) page.getResult();
			System.out.println("UNDONE_TXDATA.list>>"+list);
			list = list!=null&& list.size() ==0 ?null:list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
		
		String json = JSONUtils.map2json(rtnMap) ;
		return json;
	}

	public List getBgbkIdList(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	
//	// 操作行
//	public List<LabelValueBean> getOpbkList(){
//		List<BANK_OPBK> list = bank_opbk_bo.getAllOpbkList();
//		List<LabelValueBean> beanList = new LinkedList<LabelValueBean>();
//		LabelValueBean bean = null;
//		for(BANK_OPBK po : list){
//			bean = new LabelValueBean(po.getOPBK_ID() + " - " + po.getOPBK_NAME(), po.getOPBK_ID());
//			beanList.add(bean);
//		}
//		return beanList;
//	}
//	
//	// 業務類別
//	public List<LabelValueBean> getBsTypeIdList(){
//		//List<BUSINESS_TYPE> list = business_type_Dao.getAll();
//		List<BUSINESS_TYPE> list = business_type_Dao.find("FROM tw.org.twntch.po.BUSINESS_TYPE ORDER BY BUSINESS_TYPE_ID");
//		List<LabelValueBean> beanList = new LinkedList<LabelValueBean>();
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
//	
//	// 查詢按鈕
//		public String getNotTradResList(Map<String, String> params){
//			
//			String startDate = StrUtils.isEmpty(params.get("START_DATE"))?"":params.get("START_DATE");//交易日期
//			String endDate = StrUtils.isEmpty(params.get("END_DATE"))?"":params.get("END_DATE");//交易日期
//			
//			String opbkId = StrUtils.isEmpty(params.get("OPBK_ID"))?"all":params.get("OPBK_ID");//操作行代號
//			String bgbkId = StrUtils.isEmpty(params.get("BGBK_ID"))?"all":params.get("BGBK_ID");//總行代號
//			String clearingphase = StrUtils.isEmpty(params.get("CLEARINGPHASE"))?"all":params.get("CLEARINGPHASE");//清算階段代號
//			String bus_Type_Id = StrUtils.isEmpty(params.get("BUSINESS_TYPE_ID"))?"all":params.get("BUSINESS_TYPE_ID");//業務類別代號
//			String resultStatus = StrUtils.isEmpty(params.get("RESULTSTATUS"))?"all":params.get("RESULTSTATUS");//業務類別代號
//			String ostan = StrUtils.isEmpty(params.get("OSTAN"))?"all":params.get("OSTAN");//系統追蹤序號
//			
//			int pageNo = StrUtils.isEmpty(params.get("page")) ?0:Integer.valueOf(params.get("page"));
//			int pageSize = StrUtils.isEmpty(params.get("rows")) ?Integer.valueOf(Arguments.getStringArg("PAGE.SIZE")):Integer.valueOf(params.get("rows"));
//			String isSearch = StrUtils.isEmpty(params.get("isSearch")) ?"Y":params.get("isSearch");
//			//是否包含整批資料("N"表示不過濾)
//			String filter_bat = params.get("FILTER_BAT")==null?"N":"Y";
//			List<ONBLOCKTAB> list = null;
//			Map rtnMap = new HashMap();
//			Page page = null;
//			String condition_1 = "";
//			String condition_2 = "";
//			try {
//				list = new ArrayList<ONBLOCKTAB>();
//				List<String> conditions_1 = new ArrayList<String>();
//				List<String> conditions_2 = new ArrayList<String>();
//				/* 20150210 HUANGPU 改以清算階段後的營業日(BIZDATE)查詢資料，非原交易日期(OTXDATE) */
//				if(StrUtils.isNotEmpty(startDate)){//交易日期
//					conditions_1.add(" BIZDATE >= '"+DateTimeUtils.convertDate(startDate, "yyyyMMdd", "yyyyMMdd")+"' ");
//				}
//				if(StrUtils.isNotEmpty(endDate)){//交易日期
//					conditions_1.add(" BIZDATE <= '"+DateTimeUtils.convertDate(endDate, "yyyyMMdd", "yyyyMMdd")+"' ");
//				}
//						
//				if(!bgbkId.equals("all")){//發動行所屬總行、入賬行所屬總行、扣款行所屬總行
//					conditions_1.add(" (SENDERHEAD = '"+bgbkId+"' OR INHEAD = '"+bgbkId+"' OR OUTHEAD = '"+bgbkId+"') ");
//				}
//				if(!clearingphase.equals("all")){//清算階段代號
//					conditions_1.add(" CLEARINGPHASE= '"+clearingphase+"' ");
//				}
//				if(!resultStatus.equals("all")){
//					conditions_1.add(" COALESCE(RESULTCODE,'00') = '" + resultStatus + "' ");
//				}
//				if(!bus_Type_Id.equals("all")){
//					conditions_1.add(" ETC.BUSINESS_TYPE_ID IN ('"+bus_Type_Id+"') ");
//				}
//				if(!ostan.equals("all")){
//					conditions_1.add(" A.OSTAN = '"+ostan+"' ");
//				}
//				if(!opbkId.equals("all")){//發動行所屬操作行、入賬行所屬操作行、扣款行所屬操作行
//					if(filter_bat.equals("Y")){
//						conditions_2.addAll(conditions_1);
//						conditions_2.add(" COALESCE(FLBIZDATE,'') <> ''  ");
//						conditions_2.add(" ((INACQUIRE = '" + opbkId + "' AND substr(COALESCE(PCODE,''),4) = '2' ) OR (OUTACQUIRE = '" + opbkId + "' AND substr(COALESCE(PCODE,''),4) = '1')) ");
//						conditions_1.add(" COALESCE(FLBIZDATE,'') = ''  ");
//						conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId + "' OR INACQUIRE = '" + opbkId + "') ");
//					}else{
//						conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId + "' OR INACQUIRE = '" + opbkId + "') ");
//					}
////						conditions_1.add(" (SENDERACQUIRE = '"+opbkId+"' OR INACQUIRE = '"+opbkId+"' OR OUTACQUIRE = '"+opbkId+"') ");
//				}else{
//					if(filter_bat.equals("Y")){
//						conditions_2.addAll(conditions_1);
//						conditions_2.add(" COALESCE(FLBIZDATE,'') <> ''  ");
//						conditions_1.add(" COALESCE(FLBIZDATE,'') = ''  ");
//					}
//				}	
//				condition_1 = combine(conditions_1);
//				condition_2 = combine(conditions_2);
//				StringBuffer sql = new StringBuffer();
//				StringBuffer tmpSQL = new StringBuffer();
//				StringBuffer sumSQL = new StringBuffer();
//				StringBuffer cntSQL = new StringBuffer();
//				String sord =StrUtils.isNotEmpty(params.get("sord"))? params.get("sord"):"";
//				String sidx =StrUtils.isNotEmpty(params.get("sidx"))? params.get("sidx"):"";
//				String orderSQL = "";
//				if(StrUtils.isNotEmpty(params.get("sidx"))){
////						20160329 edit by hugo 修正未完成交易結果 grid 按部分欄位排序時(ex:交易日期、金額)，查無資料問題
////						if("STAN".equalsIgnoreCase(params.get("sidx"))){
////							orderSQL = " ORDER BY A.OSTAN "+params.get("sord");
////						}
////						if("TXDT".equalsIgnoreCase(params.get("sidx"))){
////							if("AAA".equalsIgnoreCase(params.get("sidx"))){
////							orderSQL = " ORDER BY (SELECT NEWTXDT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) "+params.get("sord");
////						}
////						else if("TXAMT".equalsIgnoreCase(params.get("sidx"))){
////							else if("BBB".equalsIgnoreCase(params.get("sidx"))){
////							orderSQL = " ORDER BY (SELECT NEWTXAMT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) "+params.get("sord");
////						}
////						else if("RESULTCODE".equalsIgnoreCase(params.get("sidx"))){
////							else if("CCC".equalsIgnoreCase(params.get("sidx"))){
////							orderSQL = " ORDER BY (CASE (SELECT NEWRESULT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) WHEN 'R' THEN '失敗' WHEN 'A' THEN '成功' ELSE '未完成' END) "+params.get("sord");
////						}
////						else if("ACCTCODE".equalsIgnoreCase(params.get("sidx"))){
////							orderSQL = " ORDER BY (SELECT ACCTCODE FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) "+params.get("sord");
////						}
////						else if("CONRESULTCODE".equalsIgnoreCase(params.get("sidx"))){
////							orderSQL = " ORDER BY (GETRESPDESC(A.OTXDATE, A.OSTAN)) "+params.get("sord");
////						}
////						else{
////							orderSQL = StrUtils.isNotEmpty(sidx)? " ORDER BY "+sidx+" "+sord:"";
////						}
//						orderSQL = StrUtils.isNotEmpty(sidx)? " ORDER BY "+sidx+" "+sord:"";
//				}
//				tmpSQL.append("WITH TEMP AS ( ");
//				tmpSQL.append(" SELECT OUTACCT, INACCT, OBIZDATE, OCLEARINGPHASE, BIZDATE, CLEARINGPHASE, SENDERACQUIRE, SENDERHEAD , ETC.EACH_TXN_NAME, '' AS ACCTCODE  , COALESCE(A.OSTAN,'') AS OSTAN");
//				tmpSQL.append(" , COALESCE(A.OTXDATE, '') AS TXDATE , COALESCE(A.OSTAN,'') AS STAN , (COALESCE(RESULTCODE, 'eACH')) AS CONRESULTCODE ");
//				tmpSQL.append(" , A.SENDERBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.SENDERBANKID),'') AS SENDERBANKID ");
//				tmpSQL.append(" , A.OUTBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.OUTBANKID),'') AS OUTBANKID ");
//				tmpSQL.append(" , A.INBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.INBANKID),'') AS INBANKID ");
//				tmpSQL.append(" , (CASE A.RESULTCODE WHEN '01' THEN '失敗' ELSE '成功' END) AS RESULTCODE,COALESCE(A.ACHFLAG,'') AS ACHFLAG  ");
//				tmpSQL.append(" , (SELECT NEWTXAMT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) AS TXAMT ");
//				tmpSQL.append(" , A.PCODE || '-' || COALESCE(ETC.EACH_TXN_NAME,'') AS PCODE ");
//				tmpSQL.append(" , VARCHAR_FORMAT((SELECT NEWTXDT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN),'YYYY-MM-DD HH24:MI:SS') AS TXDT ");
//				tmpSQL.append(" FROM ONPENDINGTAB A ");
//				tmpSQL.append(" LEFT JOIN EACH_TXN_CODE ETC ON A.PCODE = ETC.EACH_TXN_ID  ");
//				tmpSQL.append(" WHERE COALESCE(BIZDATE,'') <> '' AND COALESCE(CLEARINGPHASE,'') <> ''  ");
//				tmpSQL.append("    " + (StrUtils.isEmpty(condition_1)? "" : "AND " + condition_1));
//				if(filter_bat.equals("Y")){
//					tmpSQL.append("  UNION ALL  ");
//					tmpSQL.append(" SELECT OUTACCT, INACCT, OBIZDATE, OCLEARINGPHASE, BIZDATE, CLEARINGPHASE, SENDERACQUIRE, SENDERHEAD , ETC.EACH_TXN_NAME, '' AS ACCTCODE  , COALESCE(A.OSTAN,'') AS OSTAN");
//					tmpSQL.append(" , COALESCE(A.OTXDATE, '') AS TXDATE , COALESCE(A.OSTAN,'') AS STAN , (COALESCE(RESULTCODE, 'eACH')) AS CONRESULTCODE ");
//					tmpSQL.append(" , A.SENDERBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.SENDERBANKID),'') AS SENDERBANKID ");
//					tmpSQL.append(" , A.OUTBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.OUTBANKID),'') AS OUTBANKID ");
//					tmpSQL.append(" , A.INBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.INBANKID),'') AS INBANKID ");
//					tmpSQL.append(" , (CASE A.RESULTCODE WHEN '01' THEN '失敗' ELSE '成功' END) AS RESULTCODE,COALESCE(A.ACHFLAG,'') AS ACHFLAG  ");
//					tmpSQL.append(" , (SELECT NEWTXAMT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) AS TXAMT ");
//					tmpSQL.append(" , A.PCODE || '-' || COALESCE(ETC.EACH_TXN_NAME,'') AS PCODE ");
//					tmpSQL.append(" , VARCHAR_FORMAT((SELECT NEWTXDT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN),'YYYY-MM-DD HH24:MI:SS') AS TXDT ");
//					tmpSQL.append(" FROM ONPENDINGTAB A ");
//					tmpSQL.append(" LEFT JOIN EACH_TXN_CODE ETC ON A.PCODE = ETC.EACH_TXN_ID  ");
//					tmpSQL.append(" WHERE COALESCE(BIZDATE,'') <> '' AND COALESCE(CLEARINGPHASE,'') <> ''  ");
//					tmpSQL.append("    " + (StrUtils.isEmpty(condition_2)? "" : "AND " + condition_2));
//				}
//				tmpSQL.append(" ) ");
//				sql.append(" SELECT * FROM ( ");
//				sql.append("  	SELECT  ROWNUMBER() OVER( "+orderSQL+") AS ROWNUMBER , TEMP.*  ");
//				sql.append(" FROM TEMP ");
//				sql.append(" ) AS A    ");
//				sql.append("  WHERE ROWNUMBER >= " + (Page.getStartOfPage(pageNo, pageSize) + 1) + " AND ROWNUMBER <= " + (pageNo * pageSize) + " ");
//				sql.insert(0, tmpSQL.toString());
//				sql.append(orderSQL);
//				//先算出總計的欄位值，放到rtnMap裡面，到頁面再放到下面的總計Grid裡面
//				sumSQL.append("SELECT SUM(TXAMT) AS TXAMT  FROM TEMP ");
//				sumSQL.insert(0, tmpSQL.toString());
//				System.out.println("sumSQL="+sumSQL);
//				String dataSumCols[] = {"TXAMT"};
//				list = onblocktab_Dao.dataSum(sumSQL.toString(),dataSumCols,ONBLOCKTAB.class);
//				
//				/*
//				for(ONBLOCKTAB po:list){
//					System.out.println(String.format("SUM(X.TXAMT)=%s", po.getTXAMT()));
//				}
//				*/
//				rtnMap.put("dataSumList", list);
//				cntSQL.append("SELECT COUNT(*) AS NUM FROM TEMP");
//				cntSQL.insert(0, tmpSQL.toString());
//				String cols[] = {"PCODE","TXDT","STAN","TXDATE","SENDERBANKID", "OUTBANKID", "INBANKID","OUTACCT","INACCT", 
//								 "TXAMT", "CONRESULTCODE","OBIZDATE","OCLEARINGPHASE","RESULTCODE", "ACCTCODE" ,"ACHFLAG"  };
//				System.out.println("cntSQL===>"+cntSQL.toString());
//				System.out.println("sql===>"+sql.toString());
//				page= onblocktab_Dao.getDataIIII(Integer.valueOf(pageNo), Integer.valueOf(pageSize), cntSQL.toString(), sql.toString(), cols, ONBLOCKTABbean.class);
//				list = (List<ONBLOCKTAB>) page.getResult();
//				System.out.println("ONBLOCKTAB.list>>"+list);
//				list = list!=null&& list.size() ==0 ?null:list;
//				if(page == null){
//					rtnMap.put("total", "0");
//					rtnMap.put("page", "0");
//					rtnMap.put("records", "0");
//					rtnMap.put("rows", new ArrayList());
//				}else{
//					rtnMap.put("total", page.getTotalPageCount());
//					rtnMap.put("page", String.valueOf(page.getCurrentPageNo()));
//					rtnMap.put("records", page.getTotalCount());
//					rtnMap.put("rows", list);
//				}
//				
//				String newParams = params.get("serchStrs");
//				System.out.println("serchStrs ="+newParams);
//				if(resultStatus.equals("00")){
//					newParams = newParams.replace("\"RESULTSTATUS\":\"00\"", "\"RESULTSTATUS\":\"成功\"");
//				    params.put("serchStrs", newParams);
//				}else if(resultStatus.equals("01")){
//					newParams = newParams.replace("\"RESULTSTATUS\":\"01\"", "\"RESULTSTATUS\":\"失敗\"");
//				    params.put("serchStrs", newParams);
//				}
////					必須是按下UI的查詢才紀錄
//				if(isSearch.equals("Y")){
//					userlog_bo.writeLog("C", null, null, params);
//				}
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//				rtnMap.put("total", 1);
//				rtnMap.put("page", 1);
//				rtnMap.put("records", 0);
//				rtnMap.put("rows", new ArrayList<>());
//				rtnMap.put("msg", "查詢失敗");
//				userlog_bo.writeFailLog("C", rtnMap, null, null, params);
//			}
//			String json = JSONUtils.map2json(rtnMap) ;
//			System.out.println("json===>"+json+"<===");
//			return json;
//	}
	
}


