package com.fstop.eachadmin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import util.DateTimeUtils;
import util.StrUtils;

import com.fstop.eachadmin.dto.UserlogTchDetailRq;
import com.fstop.eachadmin.dto.UserlogTchFunRs;
import com.fstop.eachadmin.dto.UserlogTchFunRs.FunSubList;
import com.fstop.eachadmin.dto.UserlogTchSearchRq;
import com.fstop.eachadmin.dto.UserlogTchSearchRs;
import com.fstop.eachadmin.dto.UserlogTchUserIdRs;
import com.fstop.eachadmin.repository.EachUserRepository;
import com.fstop.infra.entity.EACH_USER;

@Service
public class UserlogTchService {
	
	
	@Autowired
	private EachUserRepository eachUserRepository;
	
	// 用戶代號行------------------------------------------------------
	public List<UserlogTchUserIdRs> getUserIdListByComId(String comId){
		
		List<UserlogTchUserIdRs> beanList = new LinkedList<>();
		List<EACH_USER> userIdList = eachUserRepository.getDataByComId(comId);
		
		if(userIdList != null && userIdList.size() > 0){
			
			for(int i = 0; i < userIdList.size(); i++){
				
				UserlogTchUserIdRs bean = new UserlogTchUserIdRs();
				bean.setUserId(userIdList.get(i).getUSER_ID());
				beanList.add(bean);
			}
		}
		System.out.println("list>>" + beanList);
		return beanList == null? null : beanList.size() == 0? null : beanList;
	}
	
	// 功能名稱行------------------------------------------------------
	public List<UserlogTchFunRs> getFuncList(){
		
		List<UserlogTchFunRs> menuList = eachUserRepository.getFuncItemByType("1");
		
		if(menuList != null){
			for(int i = 0; i < menuList.size(); i++){
				
				List<UserlogTchFunRs> subList = eachUserRepository.getNextSubItem(menuList.get(i).getFUNC_ID());
				if(subList != null){
					
					List<FunSubList> funSubList = new ArrayList<FunSubList>();

					for(int j = 0; j < subList.size(); j++){
						UserlogTchFunRs userlogTchFunRs = new UserlogTchFunRs();
						FunSubList funSub = userlogTchFunRs.new FunSubList();
						
						funSub.setFUNC_ID(subList.get(j).getFUNC_ID());
						funSub.setFUNC_NAME(subList.get(j).getFUNC_NAME());
						funSub.setFUNC_URL(subList.get(j).getFUNC_URL());
						funSub.setFUNC_TYPE(subList.get(j).getFUNC_TYPE());
						funSubList.add(funSub);
					}
					menuList.get(i).setSUB_LIST(funSubList);
				}
			}
		}
		System.out.println("menu>>" + menuList);
		return menuList;
	}
	
	// 查詢------------------------------------------------------
	public List<UserlogTchSearchRs> pageSearch(UserlogTchSearchRq param){
		
		String opt_date = StrUtils.isNotEmpty(param.getTXTIME_1())?param.getTXTIME_1():"";
		String opt_date2 = StrUtils.isNotEmpty(param.getTXTIME_2())?param.getTXTIME_2():"";
		String user_company = StrUtils.isNotEmpty(param.getUSER_COMPANY()) && param.getUSER_COMPANY().trim().equals("all") ?"":param.getUSER_COMPANY();
		String sUser_id = StrUtils.isNotEmpty(param.getUSERID()) && param.getUSERID().trim().equals("all") ?"":param.getUSERID();
		String func_id = StrUtils.isNotEmpty(param.getFUNC_ID()) && param.getFUNC_ID().trim().equals("all") ?"":param.getFUNC_ID();
		String user_type = StrUtils.isNotEmpty(param.getUSER_TYPE()) ?param.getUSER_TYPE():"";
		//String pageNo = StrUtils.isEmpty(param.get("page")) ?"0":param.get("page");
		
		//改從參數拿取
		//String pageSize = StrUtils.isEmpty(param.get("rows")) ?Arguments.getStringArg("PAGE.SIZE"):param.get("rows");
		//判斷群組類型
		String role_type = StrUtils.isNotEmpty(param.getROLE_TYPE())?param.getROLE_TYPE():"";
		
		//Map rtnMap = new HashMap();
		List<UserlogTchSearchRs> list = null;
		//Page page = null;

		list = new ArrayList<UserlogTchSearchRs>();
		StringBuffer orderbysql = new StringBuffer();
		//List<String> strList = new LinkedList<String>();
		Map<String,String> map = new HashMap<String,String>();
		
		map.put("TXTIME_1", DateTimeUtils.convertDate(opt_date.trim(), "yyyyMMdd", "yyyy-MM-dd"));
		map.put("TXTIME_2", DateTimeUtils.convertDate(opt_date2.trim(), "yyyyMMdd", "yyyy-MM-dd"));
		map.put("USER_COMPANY", user_company.trim());
		map.put("USERID", sUser_id.trim());
		map.put("FUNC_ID", func_id.trim());
		map.put("USER_TYPE", user_type.trim());
		map.put("ROLE_TYPE", role_type.trim());
		
		orderbysql.append( eachUserRepository.getOrderBySql(param.getSidx(), param.getSord()));
		Map<String,Object> condition_Map =  eachUserRepository.getConditionData(map);
		
		String sql = eachUserRepository.getSql(condition_Map.get("sqlPath").toString(), orderbysql.toString(), user_type);
		//String countSql = getCountSql(condition_Map.get("sqlPath").toString(), user_type);
		System.out.println("sql.toString()==>"+sql.toString());
		
		// page = userLog_Dao.pagedSQLQuery(sql, countSql, Integer.valueOf(pageNo), Integer.valueOf(pageSize), (List<String>) condition_Map.get("values") , EACH_USERLOG.class );
		//list = (List<EACH_USERLOG>) page.getResult();
		
		list=eachUserRepository.getPage(sql);
		
		list = list!=null&& list.size() ==0 ?null:list;
		return list;
	}
	
	// 檢視明細------------------------------------------------------
	public List<UserlogTchSearchRs> getDetail(UserlogTchDetailRq param){
		String ser_no = StrUtils.isEmpty(param.getSERNO())?"":param.getSERNO().trim();
		String user_id = StrUtils.isEmpty(param.getUSERID())?"":param.getUSERID().trim();
		String com_id = StrUtils.isEmpty(param.getUSER_COMPANY())?"":param.getUSER_COMPANY().trim();
		List<UserlogTchSearchRs> list  = eachUserRepository.getDetail(ser_no, user_id, com_id);
		System.out.println("list>>"+list);
		return list;
	}
}
