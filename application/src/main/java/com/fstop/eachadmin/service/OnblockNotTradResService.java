package com.fstop.eachadmin.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.xml.bind.JAXBException;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.repository.BankGroupRepository;
import com.fstop.eachadmin.repository.BusinessTypeRepository;
import com.fstop.eachadmin.repository.OnPendingTabRepository;
import com.fstop.infra.entity.BANK_BRANCH;
import com.fstop.infra.entity.BANK_GROUP;
import com.fstop.infra.entity.BANK_OPBK;

import com.fstop.infra.entity.BUSINESS_TYPE;
import com.fstop.infra.entity.ONBLOCKTAB;
import com.fstop.infra.entity.ONBLOCKTAB_FORM;
import com.fstop.infra.entity.ONPENDINGTAB;
import com.fstop.infra.entity.ONPENDINGTAB_PK;
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
import com.fstop.eachadmin.dto.UndoneSendRq;
import com.fstop.eachadmin.dto.UndoneSendRs;

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

}
