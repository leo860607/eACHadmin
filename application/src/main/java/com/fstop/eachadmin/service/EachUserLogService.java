package com.fstop.eachadmin.service;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
//import org.apache.struts.util.LabelValueBean;

import com.fstop.eachadmin.GenerieAop;
import com.fstop.eachadmin.repository.EachUserLogRepository;
import com.fstop.eachadmin.repository.EachUserRepository;
import com.fstop.infra.entity.EACH_USER;
import com.fstop.infra.entity.EACH_USERLOG;
import com.fstop.infra.entity.EACH_USERLOG_PK;
import com.fstop.infra.entity.EACH_USER_PK;
import util.DateTimeUtils;
import util.JSONUtils;
import util.SpringAppCtxHelper;
import util.StrUtils;
import util.WebServletUtils;
import util.zDateHandler;
@Slf4j
public class EachUserLogService extends GenerieAop {

	private EachUserRepository each_user_Dao;
	private EachUserLogRepository userLog_Dao;
//	private Logger log = Logger.getLogger(EachUserLogService.class.getName());
	/**
	 * 
	 * @param op_type
	 * @param msgMap
	 * @param oldmap
	 * @param newmap
	 * @param pkmap
	 */
	public void writeFailLog(String op_type,Map msgMap , Map oldmap , Map newmap , Map pkmap){
		String adexcode = "";
		String str = "失敗，";
		try {
			if(StrUtils.isEmpty(op_type)){
				log.debug(" writeLog op_type is null  do nothing");
				return;
			}
			oldmap = each_user_Dao.mapremove(oldmap);
			newmap = each_user_Dao.mapremove(newmap);
			pkmap = each_user_Dao.mapremove(pkmap);
			Map<String,String> mapTypes = SpringAppCtxHelper.getBean("opt_type");
			log.debug(" writeFailLog.mapTypes>>"+mapTypes);
			adexcode = mapTypes.get(op_type);
			if(op_type.equals("A")){
				adexcode =  str + msgMap.get("msg").toString();
			}
			if(op_type.equals("B")){
				adexcode =  str + msgMap.get("msg").toString();
			}
			if(op_type.equals("C") || op_type.equals("E") || op_type.equals("F") || op_type.equals("K") || op_type.equals("U")){
				adexcode = str + msgMap.get("msg").toString();
			}
			if(op_type.equals("D")){
				adexcode = str + msgMap.get("msg").toString();
			}
			
			geFailLog(op_type, adexcode, oldmap, newmap, pkmap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void geFailLog(String op_type ,String adexcode, Map oldmap , Map newmap , Map pkmap){
		String serchStrs = "{}";
		String action = "";
		Map tmpmap = new HashMap<String,String>();
		
		if(op_type.equals("C") || op_type.equals("E") || op_type.equals("F") || op_type.equals("K")){
			serchStrs = (String) (StrUtils.isEmpty(String.valueOf(pkmap.get("serchStrs")) ) ? "{}" :pkmap.get("serchStrs")) ;
			log.debug("geFailLog.serchStrs>>"+serchStrs);
			tmpmap =  JSONUtils.json2map(serchStrs) ;
			action = (String) (tmpmap.get("action") == null ? "" :tmpmap.get("action")) ;
		}
		EACH_USERLOG userlog_po = getUSERLOG(op_type ,action );
		if(op_type.equals("C") || op_type.equals("E") || op_type.equals("F")){
			userlog_po.setAFCHCON("查詢條件 : "+restMapKey2CH(tmpmap));
		}
		System.out.println("oldmap>>"+oldmap);
		System.out.println("restMapKey2CH>>"+restMapKey2CH(oldmap));
		userlog_po.setBFCHCON(restMapKey2CH(oldmap)+"");
		userlog_po.setAFCHCON(restMapKey2CH(newmap)+"");
		userlog_po.setADEXCODE(adexcode);
		userLog_Dao.aop_save(userlog_po);
	}

	public void addLog(String op_type  , String adexcode, Map newmap , Map pkmap ){
		try {
			log.debug(" addLog.newmap >>"+newmap);
			log.debug(" addLog.op_type >>"+op_type);
			EACH_USERLOG userlog_po = getUSERLOG(op_type , "");
			if(newmap != null ){
				userlog_po.setAFCHCON(String.valueOf(restMapKey2CH(newmap)));
			}
			userlog_po.setADEXCODE(adexcode);
			userLog_Dao.aop_save(userlog_po);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.debug(" addLog.Exception>>"+e);
		}
	}

	public EachUserRepository getEach_user_Dao() {
		return each_user_Dao;
	}

	public void setEach_user_Dao(EachUserRepository each_user_Dao) {
		this.each_user_Dao = each_user_Dao;
	}
	
	public EachUserLogRepository getUserLog_Dao() {
		return userLog_Dao;
	}

	public void setUserLog_Dao(EachUserLogRepository userLog_Dao) {
		this.userLog_Dao = userLog_Dao;
	}

	
	
}
