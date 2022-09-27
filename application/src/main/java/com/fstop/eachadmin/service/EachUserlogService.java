package com.fstop.eachadmin.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.repository.EachFuncListRepository;
import com.fstop.eachadmin.repository.EachUserRepository;
import com.fstop.fcore.util.StrUtils;
import com.fstop.infra.entity.EACH_FUNC_LIST;
import com.fstop.infra.entity.EACH_USER;
import com.fstop.infra.entity.EACH_USER_PK;

@Service
public class EachUserlogService {
	
	@Autowired
	private EachUserRepository eachUserRepository;
	
	@Autowired
	private EachFuncListRepository eachFuncListRepository;

	//用戶代號--------------------------------------------------------------------------------
	public List<Map<String, String>> getUserIdListByComId(String comId){
		List<EACH_USER> userIdList = null;
		List<Map<String, String>> list = null;
		try{
			if(StrUtils.isNotEmpty(comId)){
				userIdList = eachUserRepository.getDataByComId(comId);
			}else{
				//TOASK: HibernateEntity裡的如何正確使用?
				userIdList = eachUserRepository.getAll();
			}
			System.out.println("userIdList"+userIdList);
			if(userIdList != null && userIdList.size() > 0){
				list = new ArrayList<Map<String, String>>();
				for(int i = 0; i < userIdList.size(); i++){
					list.add(new Map<String, String>(userIdList.get(i).getUSER_ID(), userIdList.get(i).getUSER_ID()));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		list = list == null? null : list.size() == 0? null : list;
		System.out.println("list>>" + list);
		return list;
	}
	
	////用戶所屬單位-------------------------------------------------------------------------------------------
	//找出所有USER_COMPANY清單
	public List<Map<String, String>> getUserCompanyList(){
		List<EACH_USER_PK> usercompanyList = null;
		List<Map<String, String>> list = null;
		try{
			usercompanyList = eachUserRepository.getUserCompanyList();
			if(usercompanyList != null && usercompanyList.size() > 0){
				list = new ArrayList<Map<String, String>>();
				for(int i = 0; i < usercompanyList.size(); i++){
					list.add(new Map<String, String>(usercompanyList.get(i).getUSER_COMPANY(), usercompanyList.get(i).getUSER_COMPANY()));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		list = list == null? null : list.size() == 0? null : list;
		System.out.println("list>>" + list);
		return list;
	}	
	
	//根據user_type找出關聯的USER_COMPANY清單
	public List<Map<String, String>> getUserCompanyList(String user_type){
		List<EACH_USER_PK> usercompanyList = null;
		List<Map<String, String>> list = null;
		try{
			usercompanyList = eachUserRepository.getUserCompanyList(user_type);
			if(usercompanyList != null && usercompanyList.size() > 0){
				list = new ArrayList<Map<String, String>>();
				for(int i = 0; i < usercompanyList.size(); i++){
					list.add(new Map<String, String>(usercompanyList.get(i).getUSER_COMPANY(), usercompanyList.get(i).getUSER_COMPANY()));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		list = list == null? null : list.size() == 0? null : list;
		System.out.println("list>>" + list);
		return list;
	}	
	//功能名稱---------------------------------------------------------------------------------
	//找出所有功能清單
//		public List getFuncList(){
//			List<EACH_FUNC_LIST> menuList = null;
//			List<EACH_FUNC_LIST> subList = null;
//			Map menuItem = null;
//			Map subItem = null;
//			List menuList_N = new ArrayList();
//			List subList_N = new ArrayList();
//			try {
//				//找作業模組
//				menuList = func_list_Dao.getFuncItemByType("1");
//				if(menuList != null){
//					menuList_N = new ArrayList();
//					for(int i = 0; i < menuList.size(); i++){
//						menuItem = new HashMap();
//						menuItem.put("FUNC_NAME", menuList.get(i).getFUNC_NAME());
//						menuItem.put("FUNC_URL", menuList.get(i).getFUNC_URL());
//						menuItem.put("FUNC_TYPE", menuList.get(i).getFUNC_TYPE());
//						menuItem.put("FUNC_ID", menuList.get(i).getFUNC_ID());
//						subList = func_list_Dao.getNextSubItem(menuList.get(i).getFUNC_ID());
//						if(subList != null){
//							subList_N = new ArrayList();
//							for(int j = 0; j < subList.size(); j++){
//								subItem = new HashMap();
//								subItem.put("FUNC_NAME", subList.get(j).getFUNC_NAME());
//								subItem.put("FUNC_URL", subList.get(j).getFUNC_URL());
//								subItem.put("FUNC_TYPE", subList.get(j).getFUNC_TYPE());
//								subItem.put("FUNC_ID", subList.get(j).getFUNC_ID());
//								subList_N.add(subItem);
//							}
//							menuItem.put("SUB_LIST", subList_N);
//						}
//						menuList_N.add(menuItem);
//					}
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			System.out.println("menu>>" + menuList_N);
//			return menuList_N;
//		}
//		
//		//找出所有功能清單
//		public List getFuncList(String user_type){
//			List<EACH_FUNC_LIST> menuList = null;
//			List<EACH_FUNC_LIST> subList = null;
//			Map menuItem = null;
//			Map subItem = null;
////			List menuList_N = new ArrayList();
//			List menuList_N = new LinkedList();
//			List subList_N = new ArrayList();
//			try {
//				//找作業模組
//				StringBuffer pathSql = new StringBuffer();
//				if(user_type.equals("A")){
//					pathSql.append(" AND TCH_FUNC != 'N' ");
//				}
//				if(user_type.equals("B")){
//					pathSql.append(" AND BANK_FUNC != 'N' ");
//				}
//				if(user_type.equals("C")){
//					pathSql.append(" AND COMPANY_FUNC != 'N' ");
//				}
//				
//				menuItem = getLoginItem(menuItem, subItem);
//				menuList_N.add(menuItem);
//				menuList = func_list_Dao.getFuncItemByTypes("1" ,pathSql.toString() );
//				if(menuList != null){
////					menuList_N = new ArrayList();
//					for(int i = 0; i < menuList.size(); i++){
//						menuItem = new HashMap();
//						menuItem.put("FUNC_NAME", menuList.get(i).getFUNC_NAME());
//						menuItem.put("FUNC_URL", menuList.get(i).getFUNC_URL());
//						menuItem.put("FUNC_TYPE", menuList.get(i).getFUNC_TYPE());
//						menuItem.put("FUNC_ID", menuList.get(i).getFUNC_ID());
//						subList = func_list_Dao.getNextSubItem(menuList.get(i).getFUNC_ID());
//						subList = func_list_Dao.getNextSubItemII(menuList.get(i).getFUNC_ID() , user_type);
//						if(subList != null){
//							subList_N = new ArrayList();
//							for(int j = 0; j < subList.size(); j++){
//								subItem = new HashMap();
//								subItem.put("FUNC_NAME", subList.get(j).getFUNC_NAME());
//								subItem.put("FUNC_URL", subList.get(j).getFUNC_URL());
//								subItem.put("FUNC_TYPE", subList.get(j).getFUNC_TYPE());
//								subItem.put("FUNC_ID", subList.get(j).getFUNC_ID());
//								subList_N.add(subItem);
//							}
//							menuItem.put("SUB_LIST", subList_N);
//						}//if end
//						menuList_N.add(menuItem);
//					}//for end
////						menuList_N.add(0,menuItem);
//				}
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//			System.out.println("menu>>" + menuList_N);
//			return menuList_N;
//		}
	
	
	//群組類型----------------------------------------------------------------------------------
	public List getFuncListByRole_Type(String role_type){
		List<Map> beanList = new LinkedList<Map>();
		List<Map> subList = new LinkedList<Map>();
		StringBuffer pathSql = new StringBuffer();
		try {
			role_type= StrUtils.isEmpty(role_type) ? "" :role_type;
			//票交所
			if(role_type.equals("A")){
//				pathSql.append(" WHERE (S.BANK_FUNC='Y' AND S.TCH_FUNC='Y') OR S.TCH_FUNC='Y' ");
				pathSql.append(" WHERE S.TCH_FUNC !='N' ");
			//銀行端
			}else if(role_type.equals("B")){
//				pathSql.append(" WHERE (S.BANK_FUNC='Y' AND S.TCH_FUNC='Y') OR S.BANK_FUNC='Y' ");
				pathSql.append(" WHERE S.BANK_FUNC !='N' ");
			}else if(role_type.equals("C")){
				pathSql.append(" WHERE S.COMPANY_FUNC !='N' ");
			}
			List<EACH_FUNC_LIST> list = eachFuncListRepository.getAllSubItem(pathSql.toString());
			String tmp = "";
			String tmpName = "";
			Map map = null;
			Map keymap = null;
			keymap = getLoginItem_userTypB(null, null);
			subList.add(keymap);
			int i =0;
			for(EACH_FUNC_LIST po :list){
				map = new HashMap<>();
				System.out.println("tmp"+tmp+",UP_FUNC_ID>>"+po.getUP_FUNC_ID());
				if(i==0){
					tmp = po.getUP_FUNC_ID();
					tmpName = po.getUP_FUNC_NAME();
				}
				
				if(!tmp.equals(po.getUP_FUNC_ID())){
					keymap = new HashMap<>();
					keymap.put(tmpName, beanList);
					subList.add(keymap);
					tmp = po.getUP_FUNC_ID();
					tmpName = po.getUP_FUNC_NAME();
					beanList = new LinkedList<Map>();
				}
				map.put("label", po.getFUNC_NAME());
				map.put("value", po.getFUNC_ID());
				beanList.add(map);
				if(i==list.size()-1){
					keymap = new HashMap<>();
					keymap.put(tmpName, beanList);
					subList.add(keymap);
				}
				i++;	
				
			}
			System.out.println("subList>>"+subList);
			return subList;
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("subList>>" + subList);
		return subList;
	}
	
	//查詢按鈕---------------------------------------------------------------------------------
	
	
	
	//檢視明細按鈕-------------------------------------------------------------------------------
	
}
