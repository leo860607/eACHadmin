//package com.fstop.eachadmin;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.UUID;
//
//import org.aspectj.lang.annotation.Aspect;
//
//import com.fstop.eachadmin.service.FieldsMapService;
//import com.fstop.eachadmin.repository.EachUserLogRepository;
//import com.fstop.infra.entity.EACH_FUNC_LIST;
//import com.fstop.infra.entity.EACH_USERLOG;
//import util.SpringAppCtxHelper;
//import util.StrUtils;
//
//public class GenerieAop {
//private EachUserLogRepository userLog_Dao ;
//private FieldsMapService fieldsmap;
//
//public Map com_Dif_Val(Map<String,String> newmap , Map<String,String> oldmap){
//	System.out.println(" super.compareDif_Val is start");
//	newmap = newmap != null ?newmap :new HashMap<String,String>();
//	oldmap = oldmap != null ?oldmap :new HashMap<String,String>();
//	String oldvalue = "";
//	String newvalue = "";
//	Map<String ,Map> map = new HashMap<String, Map>();
//	Map<String ,String> oldtmp = new HashMap<String, String>();
//	Map<String ,String> newtmp = new HashMap<String, String>();
//	for(String key : newmap.keySet()){
////		20150627 edit by hugo 用containsValue 不適合
////		if( ! oldmap.containsValue(newmap.get(key))){
//		oldvalue = StrUtils.isNotEmpty(oldmap.get(key)) ? oldmap.get(key):"";
//		newvalue = StrUtils.isNotEmpty(newmap.get(key)) ? newmap.get(key):"";
//			if( ! oldvalue.equals(newvalue)){
////			oldtmp = new HashMap<String, String>();
////			newtmp = new HashMap<String, String>();
//			oldtmp.put(key, oldmap.get(key));
//			newtmp.put(key, newmap.get(key));
////			map.put(fieldMap.get(key).toString(), newmap.get(key));
////			map.put("oldmap", JSONUtils.json2map("{"+key+"="+oldmap.get(key)+"}") );
////			map.put("newmap", JSONUtils.json2map("{"+key+"="+newmap.get(key)+"}") );
//		}
//	}
//	map.put("oldmap", oldtmp );
//	map.put("newmap", newtmp );
//	System.out.println("compareDif_Val.map>>"+map);
//	return map;
//}
//	
//	/**
//	 * 把map中的key(field name)轉成對應的中文名稱
//	 * @param newmap
//	 * @return
//	 */
//	public Map restMapKey2CH(Map<String,String> newmap){
//		System.out.println(" super.restMapKey2CH is start");
//		newmap = newmap != null ?newmap :new HashMap<String,String>();
//		fieldsmap = (FieldsMapService) (fieldsmap== null ?SpringAppCtxHelper.getBean("fieldsmap"):fieldsmap);
//		Map<String, Object> fieldMap = fieldsmap.getArgs();
//		Map map = new HashMap<String, String>();
//		for(String key : newmap.keySet()){
//			if(fieldMap.containsKey(key)){
//				map.put( String.valueOf(fieldMap.get(key)), String.valueOf(newmap.get(key)));
////				newmap.put(fieldMap.get(key).toString(), newmap.get(key));
////				newmap.remove(key); 會出錯
//			}
//		}
//		return map;
//	}
//	
//	
//	public EACH_USERLOG getUSERLOG(String op_type , String uri){
//		EACH_USERLOG userlog_po = null;
//		EACH_FUNC_LIST func_list_po = null;
////		func_list_po = getUsed_Func(op_type , uri);
////		userlog_po = getEACH_USERLOG(op_type);
////		System.out.println("func_list_po>>"+func_list_po+", userlog_po>>"+userlog_po);
//		if(func_list_po != null ){
//			userlog_po.setFUNC_ID(func_list_po.getFUNC_ID());
//			userlog_po.setUP_FUNC_ID(func_list_po.getUP_FUNC_ID());
//			userlog_po.setFUNC_TYPE(func_list_po.getFUNC_TYPE());
//		}
//		return userlog_po;
//	}
//	
//
//	public Map mapremove(Map map){
//		if(map !=null){
//			map.remove("hibernateLazyInitializer");
//			map.remove("class");
//			map.remove("handler");
//			map.remove("multipartRequestHandler");
//			map.remove("pagesize");
//			map.remove("result");
//			map.remove("servletWrapper");
//			map.remove("scaseary");
//			map.remove("target");
//			map.remove("msg");
//			map.remove("ac_key");
//		}
//		
//		return map;
//	}
//	
//	public EachUserLogRepository getUserLog_Dao() {
//		return userLog_Dao;
//	}
//	public void setUserLog_Dao(EachUserLogRepository userLog_Dao) {
//		this.userLog_Dao = userLog_Dao;
//	}
//
//	public FieldsMapService getFieldsmap() {
//		return fieldsmap;
//	}
//
//	public void setFieldsmap(FieldsMapService fieldsmap) {
//		this.fieldsmap = fieldsmap;
//	}
//	
//	
//	
//	
//}
