package com.fstop.eachadmin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.dto.PageSearchRq;
import com.fstop.eachadmin.dto.PageSearchRs;
import com.fstop.eachadmin.repository.EachFuncListRepository;
import com.fstop.eachadmin.repository.EachUserRepository;
import com.fstop.fcore.util.Page;
import com.fstop.fcore.util.StrUtils;
import com.fstop.infra.entity.EACH_FUNC_LIST;
import com.fstop.infra.entity.EACH_USER;
import com.fstop.infra.entity.EACH_USERLOG;
import com.fstop.infra.entity.EACH_USER_PK;
import com.fstop.infra.entity.UNDONE_TXDATA;

import util.DateTimeUtils;

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
	
	public Map getLoginItem_userTypB(Map menuItem , Map subItem){
		List<Map> subList_N = new LinkedList<Map>();
		menuItem = new HashMap();
		subItem = new HashMap();
		
		subItem.put("label", "登入作業");
		subItem.put("value", "eACH9999");
		subList_N.add(subItem);
		
		subItem = new HashMap();
		subItem.put("label", "登出作業");
		subItem.put("value", "eACH9998");
		subList_N.add(subItem);
		
		menuItem.put("登入登出作業", subList_N);
		
		return menuItem;
	}
	
	//查詢按鈕---------------------------------------------------------------------------------
	//分頁版本
		public PageSearchRs<UNDONE_TXDATA> pageSearch(PageSearchRq param){
//			String opt_date = StrUtils.isNotEmpty(param.get("TXTIME_1"))?param.get("TXTIME_1"):"";
//			String opt_date2 = StrUtils.isNotEmpty(param.get("TXTIME_2"))?param.get("TXTIME_2"):"";
//			String user_company = StrUtils.isNotEmpty(param.get("USER_COMPANY")) && param.get("USER_COMPANY").trim().equals("all") ?"":param.get("USER_COMPANY");
//			String sUser_id = StrUtils.isNotEmpty(param.get("USERID")) && param.get("USERID").trim().equals("all") ?"":param.get("USERID");
//			String func_id = StrUtils.isNotEmpty(param.get("FUNC_ID")) && param.get("FUNC_ID").trim().equals("all") ?"":param.get("FUNC_ID");
//			String user_type = StrUtils.isNotEmpty(param.get("USER_TYPE")) ?param.get("USER_TYPE"):"";
//			//String pageNo = StrUtils.isEmpty(param.get("pageNo")) ?"0":param.get("pageNo");
//			String pageNo = StrUtils.isEmpty(param.get("page")) ?"0":param.get("page");
//			//改從參數拿取
//			String pageSize = StrUtils.isEmpty(param.get("rows")) ?Arguments.getStringArg("PAGE.SIZE"):param.get("rows");
//			//判斷群組類型
//			String role_type = StrUtils.isNotEmpty(param.get("ROLE_TYPE"))?param.get("ROLE_TYPE"):"";
			
			String opt_date = StrUtils.isNotEmpty(param.getOpt_date())?param.getOpt_date():"";
			String opt_date2 = StrUtils.isNotEmpty(param.getOpt_date_2())?param.getOpt_date_2():"";
			String user_company = StrUtils.isNotEmpty(param.getUser_company()) && param.getUser_company().trim().equals("all") ?"":param.getUser_company();
			String sUser_id = StrUtils.isNotEmpty(param.getSUser_id()) && param.getSUser_id().trim().equals("all") ?"":param.getSUser_id();
			String func_id = StrUtils.isNotEmpty(param.getFunc_id()) && param.getFunc_id().trim().equals("all") ?"":param.getFunc_id();
			String user_type = StrUtils.isNotEmpty(param.getUser_type()) ?param.getUser_type():"";
			//String pageNo = StrUtils.isEmpty(param.get("pageNo")) ?"0":param.get("pageNo");
			String pageNo = StrUtils.isEmpty(param.getPage()) ?"0":param.getPage();
			//改從參數拿取
			String pageSize = StrUtils.isEmpty(param.getRow()) ?Arguments.getStringArg("PAGE.SIZE"):param.getRow();
			//判斷群組類型
			String role_type = StrUtils.isNotEmpty(param.getRole_type())?param.getRole_type():"";
			
			Map rtnMap = new HashMap();
			List<EACH_USERLOG> list = null;
			Page page = null;
			try {
				list = new ArrayList<EACH_USERLOG>();
				StringBuffer orderbysql = new StringBuffer();
				List<String> strList = new LinkedList<String>();
				Map<String,String> map = new HashMap<String,String>();
				map.put("TXTIME_1", DateTimeUtils.convertDate(opt_date.trim(), "yyyyMMdd", "yyyy-MM-dd"));
				map.put("TXTIME_2", DateTimeUtils.convertDate(opt_date2.trim(), "yyyyMMdd", "yyyy-MM-dd"));
				map.put("USER_COMPANY", user_company.trim());
				map.put("USERID", sUser_id.trim());
				map.put("FUNC_ID", func_id.trim());
				map.put("USER_TYPE", user_type.trim());
				map.put("ROLE_TYPE", role_type.trim());
				System.out.println("user_type>>"+user_type);
				System.out.println("strList>>"+strList);
				System.out.println("role_type>>"+role_type);
//				if(StrUtils.isNotEmpty(param.get("sidx"))){
//					orderbysql.append(" ORDER BY "+param.get("sidx")+" "+param.get("sord"));
					orderbysql.append( getOrderBySql(param.get("sidx"), param.get("sord")));
//				}
				Map condition_Map =  getConditionData(map);
				String sql = getSql(condition_Map.get("sqlPath").toString(), orderbysql.toString(), user_type);
				String countSql = getCountSql(condition_Map.get("sqlPath").toString(), user_type);
				System.out.println("sql.toString()==>"+sql.toString());
				page = userLog_Dao.pagedSQLQuery(sql, countSql, Integer.valueOf(pageNo), Integer.valueOf(pageSize), (List<String>) condition_Map.get("values") , EACH_USERLOG.class );
//				page = userLog_Dao.pagedQuery(sql.toString(),Integer.valueOf(pageNo), Integer.valueOf(pageSize), strList.toArray());
				list = (List<EACH_USERLOG>) page.getResult();
//				list = (List<EACH_USERLOG>) userLog_Dao.getAllDataByParm(Integer.valueOf(pageNo),Integer.valueOf(pageSize));
				System.out.println("EACH_USERLOG.list>>"+list);
				list = list!=null&& list.size() ==0 ?null:list;
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
			//TODO:
			String json = JSONUtils.map2json(rtnMap) ;
			return json;
		}
	
	
	//檢視明細按鈕-------------------------------------------------------------------------------
		public List<Map<String, String>>showDetail() {
			// TODO Auto-generated method stub
				Each_Userlog_Form userlog_form = (Each_Userlog_Form) form ;
				String target = StrUtils.isEmpty(userlog_form.getTarget())?"":userlog_form.getTarget();
				String ac_key = StrUtils.isEmpty(userlog_form.getAc_key())?"":userlog_form.getAc_key();
				System.out.println("ADMUSERLOG_Action is start >> " + ac_key);
				Login_Form login_form = (Login_Form) WebServletUtils.getRequest().getSession().getAttribute("login_form");
				String fc_type = WebServletUtils.getRequest().getParameter("USER_TYPE");
				if(!ac_key.equals("back")){
					userlog_form.setFc_type(fc_type);
				}
				System.out.println("fc_type>>>"+fc_type);
				if(StrUtils.isNotEmpty(ac_key)){
					if(ac_key.equals("search")){
					}else if(ac_key.equals("update")){
					}else if(ac_key.equals("back")){
						BeanUtils.populate(userlog_form, JSONUtils.json2map(userlog_form.getSerchStrs()));
//									Map<String ,String> tmpMap = JSONUtils.json2map(userlog_form.getSerchStrs());
//									System.out.println("USER_TYPE_1>>"+(tmpMap.get("action").split("=")[1].replace("&", "")));
//									System.out.println("USER_TYPE_2>>"+login_form.getUserData().getUSER_TYPE());
//									System.out.println("target>>"+target);
//									userlog_form.setUSER_TYPE((tmpMap.get("action").split("=")[1].replace("&", "")));
						System.out.println("userlog_form.getFc_type>>"+userlog_form.getFc_type());
						if(userlog_form.getFc_type().equals("A")){
							userlog_form.setUSER_TYPE((login_form.getUserData().getUSER_TYPE()));
							userlog_form.setUserIdList(userlog_bo.getUserIdListByComId(""));
							userlog_form.setUserCompanyList(userlog_bo.getUserCompanyList());
							userlog_form.setFuncList(userlog_bo.getFuncList());
						}else if(userlog_form.getFc_type().equals("B")) {
							System.out.println("Fc_type ="+userlog_form.getFc_type()+" ,ROLE_TYPE ="+userlog_form.getROLE_TYPE()+" ,USER_COMPANY ="+userlog_form.getUSER_COMPANY());
							userlog_form.setUSER_TYPE("B");
							
							if(userlog_form.getROLE_TYPE().equals("B")){
								setDropdownList4back(userlog_form , login_form);
							}else{
								setDropdownList4back2(userlog_form , login_form);
							}
							
						}
					}else if(ac_key.equals("add")){
					}else if(ac_key.equals("edit")){
						BeanUtils.populate(userlog_form, JSONUtils.json2map(userlog_form.getEdit_params()));
						System.out.println("pk>>"+userlog_form.getSERNO()+","+ userlog_form.getUSERID()+" , "+ userlog_form.getUSER_COMPANY());
						BeanUtils.copyProperties(userlog_form, userlog_bo.getDetail(userlog_form.getSERNO(), userlog_form.getUSERID(), userlog_form.getUSER_COMPANY()));
					}else if(ac_key.equals("save")){
					}else if(ac_key.equals("delete")){
					}
				}else{
					userlog_form.setUSER_COMPANY(login_form.getUserData().getUSER_COMPANY());
					setDropdownList(userlog_form , login_form);
				}
				
				if(StrUtils.isEmpty(target)){
					target = "search";
				}
				
				System.out.println("forward to >> " + target);
				return (mapping.findForward(target));
			}
}
