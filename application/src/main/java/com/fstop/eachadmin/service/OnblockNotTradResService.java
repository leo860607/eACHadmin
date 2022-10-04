package com.fstop.eachadmin.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.repository.BankGroupRepository;
import com.fstop.eachadmin.repository.BusinessTypeRepository;
import com.fstop.eachadmin.repository.OnBlockTabRepository;
import com.fstop.eachadmin.repository.OnPendingTabRepository;
import com.fstop.fcore.util.Page;
import com.fstop.infra.entity.BANK_BRANCH;
import com.fstop.infra.entity.BANK_GROUP;
import com.fstop.infra.entity.BANK_OPBK;

import com.fstop.infra.entity.BUSINESS_TYPE;
import com.fstop.infra.entity.ONBLOCKTAB;
import com.fstop.infra.entity.ONBLOCKTAB_FORM;
import com.fstop.infra.entity.ONPENDINGTAB;
import com.fstop.infra.entity.ONPENDINGTAB_PK;
import com.fstop.infra.entity.UNDONE_TXDATA;

import lombok.extern.slf4j.Slf4j;

import com.fstop.infra.entity.ONBLOCKTAB_NOTTRAD_RESF;

import util.messageConverter;

import util.DateTimeUtils;

import util.StrUtils;
import util.socketPackage;
import util.zDateHandler;
import util.socketPackage.Body;
import util.socketPackage.Header;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstop.eachadmin.dto.DetailRs;
import com.fstop.eachadmin.dto.LoginFormDto;
import com.fstop.eachadmin.dto.ObtkNtrRq;
import com.fstop.eachadmin.dto.ObtkNtrRs;
import com.fstop.eachadmin.dto.PageSearchRq;
import com.fstop.eachadmin.dto.PageSearchRs;
import com.fstop.eachadmin.dto.UndoneSendRq;
import com.fstop.eachadmin.dto.UndoneSendRs;

@Slf4j
@Service
public class OnblockNotTradResService {

//	@Autowired
//	private BUSINESS_TYPE_Dao business_type_Dao ;

//	@Autowired
//	OnPendingTabRepository OnPendingTabR;
	ObtkNtrRs obtkNtRRs;
	@Autowired
	UndoneService undoneService;
	
	@Autowired
	private BankGroupRepository bankGroupRepository;

	@Autowired
	private BusinessTypeRepository businessTypeRepository;
	
	@Autowired 
	private OnblocktabService onblocktabService;
	
	@Autowired
	private OnBlockTabRepository onblocktabRepository;
	
	@Autowired
	private EachUserlogService eachUserlogService;

	
	// 暫時測試用
	public void testFunction() {
		System.out.println("哭哭");
	}

	// 操作行下拉選單
	public List<Map<String, String>> getOpbkList() {

		// String sql = "SELECT COALESCE( OP.OPBK_ID,'' ) AS OPBK_ID , COALESCE(
		// BG.BGBK_NAME ,'' ) AS OPBK_NAME FROM ( SELECT DISTINCT OPBK_ID FROM
		// EACHUSER.BANK_OPBK ) AS OP JOIN ( SELECT BGBK_ID, BGBK_NAME FROM BANK_GROUP
		// WHERE BGBK_ATTR <> '6' ) AS BG ON OP.OPBK_ID = BG.BGBK_ID ORDER BY OP.OPBK_ID
		// ";
		// List<BANK_OPBK> list = business_type_Dao.getAllOpbkList(sql);
		// TODO:
		// jdbc 還沒有好, 暫時先用
		List<BANK_OPBK> list = (List<BANK_OPBK>) businessTypeRepository.testFunction();
		List<Map<String, String>> beanList = new LinkedList<Map<String, String>>();

		Map<String, String> bean = null;

		for (BANK_OPBK po : list) {
			po.getOPBK_ID();
			String k = (String) po.getOPBK_ID() + " - " + po.getOPBK_NAME();
			String v = (String) po.getOPBK_ID();
			bean = new HashMap<String, String>();
			bean.put(k, v);
			beanList.add(bean);
		}
		System.out.println("beanList>>" + beanList);
		return beanList;
	}

	// businessLabel
	public List<Map<String, String>> getBsTypeIdList() {

		// String sql = "FROM tw.org.twntch.po.BUSINESS_TYPE ORDER BY BUSINESS_TYPE_ID";
		// List<BUSINESS_TYPE> list = business_type_Dao.find(sql);
		// TODO:
		// jdbc 還沒有好, 暫時先用
		List<BUSINESS_TYPE> list = (List<BUSINESS_TYPE>) businessTypeRepository.testFunction();
		List<Map<String, String>> beanList = new LinkedList<Map<String, String>>();

		Map<String, String> bean = null;

		for (BUSINESS_TYPE po : list) {
			po.getBUSINESS_TYPE_ID();
			String k = (String) po.getBUSINESS_TYPE_ID() + " - " + po.getBUSINESS_TYPE_NAME();
			String v = (String) po.getBUSINESS_TYPE_ID();
			bean = new HashMap<String, String>();
			bean.put(k, v);
			beanList.add(bean);
		}
		System.out.println("beanList>>" + beanList);
		return beanList;
	}
	
	
	//將list內的元素串接成一個字串
	public String combine(List<String> conditions){
		String conStr = "";
		for(int i = 0 ; i < conditions.size(); i++){
			conStr += conditions.get(i);
			if(i < conditions.size() - 1){
				conStr += " AND ";
			}
		}
		return conStr;
	}
	
	//明細
		public List<BANK_GROUP> search(String bgbkId){
			List<BANK_GROUP> list = null ;
			if(StrUtils.isEmpty(bgbkId)){
//				list = bank_group_Dao.getAll();
				list = bankGroupRepository.getAllData();
			}else{
//				list = new ArrayList<BANK_GROUP>();
//				BANK_GROUP po = bank_group_Dao.get(bgbkId);
//				if(po != null){
//					list.add(po);
//				}
				list = bankGroupRepository.getDataByBgbkId(bgbkId);
			}
			System.out.println("list>>"+list);
			list = list == null? null : list.size() == 0? null:list;
			
			//測試
			//bank_group_Dao.creatWK();
			
			return list;
		}
	
	
	//帳單明細
	//只需要用到產生檢視明細資料的部分
	public ObtkNtrRs NTRDetail(ObtkNtrRq param){
//		ONBLOCKTAB_NOTTRAD_RESF obktNtR_form = (ONBLOCKTAB_NOTTRAD_RESF) form ;		
//		String target = "";
//		String ac_key = StrUtils.isEmpty(obktNtR_form.getAc_key())?"":obktNtR_form.getAc_key();
//		target = StrUtils.isEmpty(obktNtR_form.getTarget())?"search":obktNtR_form.getTarget();
//		obktNtR_form.setTarget(target);
//		List<BANK_BRANCH> list = null;
//		List<ONBLOCKTAB> onblist = null;
//		//String onblist ="";
//		System.out.println("ONBLOCKTAB_NotTradRes_Action is start target>> " + target);
//		System.out.println("ONBLOCKTAB_NotTradRes_Action is start ac_key>> " + ac_key);
//		//方法裡面沒有用到先註解
////		loginFormDto login_form = (loginFormDto) WebServletUtils.getRequest().getSession().getAttribute("login_form");
//		Map<String,String> m = new HashMap<String,String>();
		
		String ac_key = StrUtils.isEmpty(param.getAc_key())?"":param.getAc_key();
		if(StrUtils.isNotEmpty(ac_key) && !ac_key.equals("back")){			
			if(ac_key.equals("search")){				
				
			}else if(ac_key.equals("edit")){
				try {
					BeanUtils.populate(param, null );// TODO  JSONUtils.json2map(param.getEdit_params()));
				} catch (IllegalAccessException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (InvocationTargetException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String txDate = param.getTXDATE();
				String stan = param.getSTAN();
				System.out.println("TXDATE>>"+txDate);
				System.out.println("STAN>>"+stan);
				ONBLOCKTAB_FORM onblocktab_form = new ONBLOCKTAB_FORM();
				Map detailDataMap=onblocktabService.showNotTradResDetail(txDate,stan);
				//要用舊的營業日去查手續費
				String obizdate = param.getOLDBIZDATE();
				
				//20220321新增FOR EXTENDFEE 位數轉換
				if(detailDataMap.get("EXTENDFEE")!=null) {
				  BigDecimal orgNewExtendFee = (BigDecimal) detailDataMap.get("EXTENDFEE");
				   //去逗號除100 1,000 > 1000/100 = 10
				  String strOrgNewExtendFee = orgNewExtendFee.toString();
				   double realNewExtendFee = Double.parseDouble(strOrgNewExtendFee.replace(",", ""))/100;
				   detailDataMap.put("NEWEXTENDFEE", String.valueOf(realNewExtendFee));
				}else {
					//如果是null 顯示空字串
					detailDataMap.put("NEWEXTENDFEE", "");
				}
				
				//如果FEE_TYPE有值 且結果為成功或未完成  此功能都拿新的值
				if(StrUtils.isNotEmpty((String)detailDataMap.get("FEE_TYPE")) && 
				   ("成功".equals((String)detailDataMap.get("RESP"))||"未完成".equals((String)detailDataMap.get("RESP")))) {
					switch ((String)detailDataMap.get("FEE_TYPE")){
					case "A":
						detailDataMap.put("TXN_TYPE","固定");
						break;
					case "B":
						detailDataMap.put("TXN_TYPE","外加");
						break;
					case "C":
						detailDataMap.put("TXN_TYPE","百分比");
						break;
					case "D":
						detailDataMap.put("TXN_TYPE","級距");
						break;
					}
					
				//如果FEE_TYPE有值 且結果為失敗或處理中 此功能都拿新的值
				}else if (StrUtils.isNotEmpty((String)detailDataMap.get("FEE_TYPE")) && 
						   ("失敗".equals((String)detailDataMap.get("RESP"))||"處理中".equals((String)detailDataMap.get("RESP")))) {
					switch ((String)detailDataMap.get("FEE_TYPE")){
					case "A":
						detailDataMap.put("TXN_TYPE","固定");
						break;
					case "B":
						detailDataMap.put("TXN_TYPE","外加");
						break;
					case "C":
						detailDataMap.put("TXN_TYPE","百分比");
						break;
					case "D":
						detailDataMap.put("TXN_TYPE","級距");
						break;
					}
					
//					detailDataMap.put("NEWSENDERFEE_NW", detailDataMap.get("NEWSENDERFEE")!=null?detailDataMap.get("NEWSENDERFEE"):"0");
//					detailDataMap.put("NEWINFEE_NW", detailDataMap.get("NEWINFEE")!=null?detailDataMap.get("NEWINFEE"):"0");
//					detailDataMap.put("NEWOUTFEE_NW", detailDataMap.get("NEWOUTFEE")!=null?detailDataMap.get("NEWOUTFEE"):"0");
//					detailDataMap.put("NEWWOFEE_NW", detailDataMap.get("NEWWOFEE")!=null?detailDataMap.get("NEWWOFEE"):"0");
//					detailDataMap.put("NEWEACHFEE_NW", detailDataMap.get("NEWEACHFEE")!=null?detailDataMap.get("NEWEACHFEE"):"0");
//					detailDataMap.put("NEWFEE_NW",detailDataMap.get("NEWFEE")!=null?detailDataMap.get("NEWFEE"):"0");
					
				//如果FEE_TYPE為空 且結果為成功或未完成 新版手續call sp  此功能都拿新的值
				}else if (StrUtils.isEmpty((String)detailDataMap.get("FEE_TYPE")) && 
						   ("成功".equals((String)detailDataMap.get("RESP"))||"未完成".equals((String)detailDataMap.get("RESP")))) {
					Map newFeeDtailMap = onblocktabService.getNewFeeDetail(obizdate,(String) detailDataMap.get("TXN_NAME"),(String) detailDataMap.get("SENDERID")
							,(String) detailDataMap.get("SENDERBANKID_NAME"),(String)detailDataMap.get("NEWTXAMT"));
					detailDataMap.put("TXN_TYPE", newFeeDtailMap.get("TXN_TYPE"));
					detailDataMap.put("NEWSENDERFEE_NW", newFeeDtailMap.get("NEWSENDERFEE_NW"));
					detailDataMap.put("NEWINFEE_NW", newFeeDtailMap.get("NEWINFEE_NW"));
					detailDataMap.put("NEWOUTFEE_NW", newFeeDtailMap.get("NEWOUTFEE_NW"));
					detailDataMap.put("NEWWOFEE_NW", newFeeDtailMap.get("NEWWOFEE_NW"));
					detailDataMap.put("NEWEACHFEE_NW", newFeeDtailMap.get("NEWEACHFEE_NW"));
					detailDataMap.put("NEWFEE_NW", newFeeDtailMap.get("NEWFEE_NW"));
					
				//如果FEE_TYPE為空 且結果為失敗或處理中 call sp 拿FEE_TYPE	  此功能都拿新的值
				}else if (StrUtils.isEmpty((String)detailDataMap.get("FEE_TYPE")) && 
						   ("失敗".equals((String)detailDataMap.get("RESP"))||"處理中".equals((String)detailDataMap.get("RESP")))) {
					Map newFeeDtailMap = onblocktabService.getNewFeeDetail(obizdate,(String) detailDataMap.get("TXN_NAME"),(String) detailDataMap.get("SENDERID")
							,(String) detailDataMap.get("SENDERBANKID_NAME"),(String)detailDataMap.get("NEWTXAMT"));
					
					detailDataMap.put("TXN_TYPE", newFeeDtailMap.get("TXN_TYPE"));
					detailDataMap.put("NEWSENDERFEE_NW", newFeeDtailMap.get("NEWSENDERFEE")!=null?newFeeDtailMap.get("NEWSENDERFEE"):"0");
					detailDataMap.put("NEWINFEE_NW", newFeeDtailMap.get("NEWINFEE")!=null?newFeeDtailMap.get("NEWINFEE"):"0");
					detailDataMap.put("NEWOUTFEE_NW", newFeeDtailMap.get("NEWOUTFEE")!=null?newFeeDtailMap.get("NEWOUTFEE"):"0");
					detailDataMap.put("NEWWOFEE_NW", newFeeDtailMap.get("NEWWOFEE")!=null?newFeeDtailMap.get("NEWWOFEE"):"0");
					detailDataMap.put("NEWEACHFEE_NW", newFeeDtailMap.get("NEWEACHFEE")!=null?newFeeDtailMap.get("NEWEACHFEE"):"0");
					detailDataMap.put("NEWFEE_NW",newFeeDtailMap.get("NEWFEE")!=null?newFeeDtailMap.get("NEWFEE"):"0");
				
				}
//				onblocktab_form.setDetailData(detailDataMap);
//				onblocktab_form.setIsUndoneRes("Y");
////				BeanUtils.copyProperties(onblocktab_form, obktNtR_form);
//				BeanUtils.copyProperties(onblocktab_form, param);

				
				ObtkNtrRs obtkNtRRs = new ObtkNtrRs();
				obtkNtRRs.setDetailData(detailDataMap);
				try {
					BeanUtils.copyProperties(obtkNtRRs, param);
				} catch (IllegalAccessException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (InvocationTargetException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				obtkNtRRs.setIsUndone("Y");
//				BeanUtils.copyProperties(onblocktab_form, obktNtR_form);
				
				
				Map userData = null;
				try {
					userData = BeanUtils.describe(obtkNtRRs.getUserData());
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				ObtkNtrRs.setOPBK_ID("onblocktab_form", onblocktab_form);
//				ObtkNtrRs.setOPBK_ID(OnblockNotTradResService.search((String) userData.get("USER_COMPANY")).get(0).getOPBK_ID());
//				obtkNtRRs.setOPBK_ID(search((String) userData.get("USER_COMPANY"));
				obtkNtRRs.setOpbkIdList(getOpbkList());;
			}
		}
		System.out.println("forward to >> " + param.getFILTER_BAT());
		return obtkNtRRs;
	}

	
	//未完成交易結果查詢
//	public String getNotTradResList(Map<String, String> params) {
	public PageSearchRs<UNDONE_TXDATA> getNotTradResList(PageSearchRq params) {

//		String startDate = StrUtils.isEmpty(params.get("START_DATE")) ? "" : params.get("START_DATE");// 交易日期
		String startDate = StrUtils.isEmpty(params.getStartDate()) ? "" : params.getStartDate();// 交易日期
//		String endDate = StrUtils.isEmpty(params.get("END_DATE")) ? "" : params.get("END_DATE");// 交易日期
		String endDate = StrUtils.isEmpty(params.getEndDate()) ? "" : params.getEndDate();// 交易日期
//		String opbkId = StrUtils.isEmpty(params.get("OPBK_ID")) ? "all" : params.get("OPBK_ID");// 操作行代號
		String opbkId = StrUtils.isEmpty(params.getOpbkId()) ? "all" : params.getOpbkId();// 操作行代號
//		String bgbkId = StrUtils.isEmpty(params.get("BGBK_ID")) ? "all" : params.get("BGBK_ID");// 總行代號
		String bgbkId = StrUtils.isEmpty(params.getBgbkId()) ? "all" : params.getBgbkId();// 總行代號
//		String clearingphase = StrUtils.isEmpty(params.get("CLEARINGPHASE")) ? "all" : params.get("CLEARINGPHASE");// 清算階段代號
		String clearingphase = StrUtils.isEmpty(params.getClearingphase()) ? "all" : params.getClearingphase();// 清算階段代號
//		String bus_Type_Id = StrUtils.isEmpty(params.get("BUSINESS_TYPE_ID")) ? "all" : params.get("BUSINESS_TYPE_ID");// 業務類別代號
		String bus_Type_Id = StrUtils.isEmpty(params.getBusinessTypeId()) ? "all" : params.getBusinessTypeId();// 業務類別代號
//		String resultStatus = StrUtils.isEmpty(params.get("RESULTSTATUS")) ? "all" : params.get("RESULTSTATUS");// 業務類別代號
		String resultStatus = StrUtils.isEmpty(params.getResultStatus()) ? "all" : params.getResultStatus();// 業務類別代號
//		String ostan = StrUtils.isEmpty(params.get("OSTAN")) ? "all" : params.get("OSTAN");// 系統追蹤序號
		String ostan = StrUtils.isEmpty(params.getOstan()) ? "all" : params.getOstan();// 系統追蹤序號

//		int pageNo = StrUtils.isEmpty(params.get("page")) ? 0 : Integer.valueOf(params.get("page"));
//		int pageSize = StrUtils.isEmpty(params.get("rows")) ? Integer.valueOf(Arguments.getStringArg("PAGE.SIZE"))
//				: Integer.valueOf(params.get("rows"));
		int pageNo = 1;
		int pageSize = 1;
		
//		String isSearch = StrUtils.isEmpty(params.get("isSearch")) ? "Y" : params.get("isSearch");
		String isSearch = StrUtils.isEmpty(params.getIsSearch()) ? "Y" : params.getIsSearch();
		// 是否包含整批資料("N"表示不過濾)
//		String filter_bat = params.get("FILTER_BAT") == null ? "N" : "Y";
		String filter_bat = params.getFilter_bat() == null ? "N" : "Y";
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
			if (StrUtils.isNotEmpty(startDate)) {// 交易日期
				conditions_1.add(" BIZDATE >= '" + DateTimeUtils.convertDate(startDate, "yyyyMMdd", "yyyyMMdd") + "' ");
			}
			if (StrUtils.isNotEmpty(endDate)) {// 交易日期
				conditions_1.add(" BIZDATE <= '" + DateTimeUtils.convertDate(endDate, "yyyyMMdd", "yyyyMMdd") + "' ");
			}

			if (!bgbkId.equals("all")) {// 發動行所屬總行、入賬行所屬總行、扣款行所屬總行
				conditions_1.add(
						" (SENDERHEAD = '" + bgbkId + "' OR INHEAD = '" + bgbkId + "' OR OUTHEAD = '" + bgbkId + "') ");
			}
			if (!clearingphase.equals("all")) {// 清算階段代號
				conditions_1.add(" CLEARINGPHASE= '" + clearingphase + "' ");
			}
			if (!resultStatus.equals("all")) {
				conditions_1.add(" COALESCE(RESULTCODE,'00') = '" + resultStatus + "' ");
			}
			if (!bus_Type_Id.equals("all")) {
				conditions_1.add(" ETC.BUSINESS_TYPE_ID IN ('" + bus_Type_Id + "') ");
			}
			if (!ostan.equals("all")) {
				conditions_1.add(" A.OSTAN = '" + ostan + "' ");
			}
			if (!opbkId.equals("all")) {// 發動行所屬操作行、入賬行所屬操作行、扣款行所屬操作行
				if (filter_bat.equals("Y")) {
					conditions_2.addAll(conditions_1);
					conditions_2.add(" COALESCE(FLBIZDATE,'') <> ''  ");
					conditions_2.add(" ((INACQUIRE = '" + opbkId
							+ "' AND substr(COALESCE(PCODE,''),4) = '2' ) OR (OUTACQUIRE = '" + opbkId
							+ "' AND substr(COALESCE(PCODE,''),4) = '1')) ");
					conditions_1.add(" COALESCE(FLBIZDATE,'') = ''  ");
					conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId
							+ "' OR INACQUIRE = '" + opbkId + "') ");
				} else {
					conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId
							+ "' OR INACQUIRE = '" + opbkId + "') ");
				}
//					conditions_1.add(" (SENDERACQUIRE = '"+opbkId+"' OR INACQUIRE = '"+opbkId+"' OR OUTACQUIRE = '"+opbkId+"') ");
			} else {
				if (filter_bat.equals("Y")) {
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
//			String sord = StrUtils.isNotEmpty(params.get("sord")) ? params.get("sord") : "";
			String sord = StrUtils.isNotEmpty(params.getSord()) ? params.getSord() : "";
//			String sidx = StrUtils.isNotEmpty(params.get("sidx")) ? params.get("sidx") : "";
			String sidx = StrUtils.isNotEmpty(params.getSidx()) ? params.getSidx() : "";
			String orderSQL = "";
//			if (StrUtils.isNotEmpty(params.get("sidx"))) {
			if (StrUtils.isNotEmpty(params.getSidx())) {
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
				orderSQL = StrUtils.isNotEmpty(sidx) ? " ORDER BY " + sidx + " " + sord : "";
			}
			tmpSQL.append("WITH TEMP AS ( ");
			tmpSQL.append(
					" SELECT OUTACCT, INACCT, OBIZDATE, OCLEARINGPHASE, BIZDATE, CLEARINGPHASE, SENDERACQUIRE, SENDERHEAD , ETC.EACH_TXN_NAME, '' AS ACCTCODE  , COALESCE(A.OSTAN,'') AS OSTAN");
			tmpSQL.append(
					" , COALESCE(A.OTXDATE, '') AS TXDATE , COALESCE(A.OSTAN,'') AS STAN , (COALESCE(RESULTCODE, 'eACH')) AS CONRESULTCODE ");
			tmpSQL.append(
					" , A.SENDERBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.SENDERBANKID),'') AS SENDERBANKID ");
			tmpSQL.append(
					" , A.OUTBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.OUTBANKID),'') AS OUTBANKID ");
			tmpSQL.append(
					" , A.INBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.INBANKID),'') AS INBANKID ");
			tmpSQL.append(
					" , (CASE A.RESULTCODE WHEN '01' THEN '失敗' ELSE '成功' END) AS RESULTCODE,COALESCE(A.ACHFLAG,'') AS ACHFLAG  ");
			tmpSQL.append(
					" , (SELECT NEWTXAMT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) AS TXAMT ");
			tmpSQL.append(" , A.PCODE || '-' || COALESCE(ETC.EACH_TXN_NAME,'') AS PCODE ");
			tmpSQL.append(
					" , VARCHAR_FORMAT((SELECT NEWTXDT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN),'YYYY-MM-DD HH24:MI:SS') AS TXDT ");
			tmpSQL.append(" FROM ONPENDINGTAB A ");
			tmpSQL.append(" LEFT JOIN EACH_TXN_CODE ETC ON A.PCODE = ETC.EACH_TXN_ID  ");
			tmpSQL.append(" WHERE COALESCE(BIZDATE,'') <> '' AND COALESCE(CLEARINGPHASE,'') <> ''  ");
			tmpSQL.append("    " + (StrUtils.isEmpty(condition_1) ? "" : "AND " + condition_1));
			if (filter_bat.equals("Y")) {
				tmpSQL.append("  UNION ALL  ");
				tmpSQL.append(
						" SELECT OUTACCT, INACCT, OBIZDATE, OCLEARINGPHASE, BIZDATE, CLEARINGPHASE, SENDERACQUIRE, SENDERHEAD , ETC.EACH_TXN_NAME, '' AS ACCTCODE  , COALESCE(A.OSTAN,'') AS OSTAN");
				tmpSQL.append(
						" , COALESCE(A.OTXDATE, '') AS TXDATE , COALESCE(A.OSTAN,'') AS STAN , (COALESCE(RESULTCODE, 'eACH')) AS CONRESULTCODE ");
				tmpSQL.append(
						" , A.SENDERBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.SENDERBANKID),'') AS SENDERBANKID ");
				tmpSQL.append(
						" , A.OUTBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.OUTBANKID),'') AS OUTBANKID ");
				tmpSQL.append(
						" , A.INBANKID || '-' || COALESCE((SELECT BRBK_NAME from BANK_BRANCH WHERE BRBK_ID = A.INBANKID),'') AS INBANKID ");
				tmpSQL.append(
						" , (CASE A.RESULTCODE WHEN '01' THEN '失敗' ELSE '成功' END) AS RESULTCODE,COALESCE(A.ACHFLAG,'') AS ACHFLAG  ");
				tmpSQL.append(
						" , (SELECT NEWTXAMT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN) AS TXAMT ");
				tmpSQL.append(" , A.PCODE || '-' || COALESCE(ETC.EACH_TXN_NAME,'') AS PCODE ");
				tmpSQL.append(
						" , VARCHAR_FORMAT((SELECT NEWTXDT FROM VW_ONBLOCKTAB B WHERE A.OTXDATE = B.TXDATE AND A.OSTAN = B.STAN),'YYYY-MM-DD HH24:MI:SS') AS TXDT ");
				tmpSQL.append(" FROM ONPENDINGTAB A ");
				tmpSQL.append(" LEFT JOIN EACH_TXN_CODE ETC ON A.PCODE = ETC.EACH_TXN_ID  ");
				tmpSQL.append(" WHERE COALESCE(BIZDATE,'') <> '' AND COALESCE(CLEARINGPHASE,'') <> ''  ");
				tmpSQL.append("    " + (StrUtils.isEmpty(condition_2) ? "" : "AND " + condition_2));
			}
			tmpSQL.append(" ) ");
			sql.append(" SELECT * FROM ( ");
			sql.append("  	SELECT  ROWNUMBER() OVER( " + orderSQL + ") AS ROWNUMBER , TEMP.*  ");
			sql.append(" FROM TEMP ");
			sql.append(" ) AS A    ");
			sql.append("  WHERE ROWNUMBER >= " + (Page.getStartOfPage(pageNo, pageSize) + 1) + " AND ROWNUMBER <= "
					+ (pageNo * pageSize) + " ");
			sql.insert(0, tmpSQL.toString());
			sql.append(orderSQL);
			// 先算出總計的欄位值，放到rtnMap裡面，到頁面再放到下面的總計Grid裡面
			sumSQL.append("SELECT SUM(TXAMT) AS TXAMT  FROM TEMP ");
			sumSQL.insert(0, tmpSQL.toString());
			System.out.println("sumSQL=" + sumSQL);
			String dataSumCols[] = { "TXAMT" };
//			list = onblocktab_Dao.dataSum(sumSQL.toString(), dataSumCols, ONBLOCKTAB.class);
//			list = OnBlockTabRepository.dataSum(sumSQL.toString(), dataSumCols, ONBLOCKTAB.class);
			list = onblocktabRepository.dataSum(sumSQL.toString(), dataSumCols, ONBLOCKTAB.class);

			/*
			 * for(ONBLOCKTAB po:list){ System.out.println(String.format("SUM(X.TXAMT)=%s",
			 * po.getTXAMT())); }
			 */
			rtnMap.put("dataSumList", list);
			cntSQL.append("SELECT COUNT(*) AS NUM FROM TEMP");
			cntSQL.insert(0, tmpSQL.toString());
			String cols[] = { "PCODE", "TXDT", "STAN", "TXDATE", "SENDERBANKID", "OUTBANKID", "INBANKID", "OUTACCT",
					"INACCT", "TXAMT", "CONRESULTCODE", "OBIZDATE", "OCLEARINGPHASE", "RESULTCODE", "ACCTCODE",
					"ACHFLAG" };
			System.out.println("cntSQL===>" + cntSQL.toString());
			System.out.println("sql===>" + sql.toString());
			page = onblocktabRepository.getDataIIII(Integer.valueOf(pageNo), Integer.valueOf(pageSize), cntSQL.toString(),
					sql.toString(), cols, ONBLOCKTABbean.class);
			list = (List<ONBLOCKTAB>) page.getResult();
			System.out.println("ONBLOCKTAB.list>>" + list);
			list = list != null && list.size() == 0 ? null : list;
			if (page == null) {
				rtnMap.put("total", "0");
				rtnMap.put("page", "0");
				rtnMap.put("records", "0");
				rtnMap.put("rows", new ArrayList());
			} else {
				rtnMap.put("total", page.getTotalPageCount());
				rtnMap.put("page", String.valueOf(page.getCurrentPageNo()));
				rtnMap.put("records", page.getTotalCount());
				rtnMap.put("rows", list);
			}

//			String newParams = params.get("serchStrs");
			String newParams = params.getSerchStrs();
			System.out.println("serchStrs =" + newParams);
			if (resultStatus.equals("00")) {
				newParams = newParams.replace("\"RESULTSTATUS\":\"00\"", "\"RESULTSTATUS\":\"成功\"");
				params.put("serchStrs", newParams);
			} else if (resultStatus.equals("01")) {
				newParams = newParams.replace("\"RESULTSTATUS\":\"01\"", "\"RESULTSTATUS\":\"失敗\"");
				params.put("serchStrs", newParams);
			}
//				必須是按下UI的查詢才紀錄
			if (isSearch.equals("Y")) {
//				userlog_bo.writeLog("C", null, null, params);
				eachUserlogService.writeLog("C", null, null, params);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnMap.put("total", 1);
			rtnMap.put("page", 1);
			rtnMap.put("records", 0);
			rtnMap.put("rows", new ArrayList<>());
			rtnMap.put("msg", "查詢失敗");
//			userlog_bo.writeFailLog("C", rtnMap, null, null, params);
			eachUserlogService.writeFailLog("C", rtnMap, null, null, params);
		}
		String json = JSONUtils.map2json(rtnMap);
		System.out.println("json===>" + json + "<===");
		return json;
	}
}
