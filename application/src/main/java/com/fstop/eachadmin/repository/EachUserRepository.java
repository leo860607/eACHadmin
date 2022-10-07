package com.fstop.eachadmin.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.fstop.eachadmin.dto.UserlogTchFunRs;
import com.fstop.eachadmin.dto.UserlogTchFunRs.FunSubList;
import com.fstop.eachadmin.dto.UserlogTchSearchRs;
import com.fstop.fcore.util.AutoAddScalar;
import com.fstop.infra.entity.EACH_FUNC_LIST;
import com.fstop.infra.entity.EACH_USER;
import com.fstop.infra.entity.EACH_USERLOG;
import com.fstop.infra.entity.EACH_USER_PK;

import util.StrUtils;
import util.zDateHandler;
@Repository
public class EachUserRepository{
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EACH_USER> getAllData(String orderSQL){
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT A.USER_COMPANY ||'-'|| B.BGBK_NAME COM_NAME , R.ROLE_ID||'-'||R.ROLE_NAME ROLE_ID ,A.* FROM EACH_USER A ");
		sql.append(" JOIN EACH_ROLE_LIST R ON A.ROLE_ID = R.ROLE_ID  ");
		sql.append(" JOIN BANK_GROUP B  ON A.USER_COMPANY = B.BGBK_ID ");
		sql.append(orderSQL);
		AutoAddScalar addScalr = new AutoAddScalar();
		String cols = "COM_NAME,USER_COMPANY,USER_ID,USER_TYPE,USER_STATUS,USER_DESC,USE_IKEY,ROLE_ID,NOLOGIN_EXPIRE_DAY,IP,IDLE_TIMEOUT,LAST_LOGIN_DATE,LAST_LOGIN_IP,CDATE,UDATE ";
		List<EACH_USER> list = new ArrayList<EACH_USER>();
		list=jdbcTemplate.query(cols,new BeanPropertyRowMapper(EACH_USER.class));
//		addScalr.addScalar(query, EACH_USER.class, cols.split(","));
//		query.addEntity(EACH_USER.class);
//		query.setResultTransformer(Transformers.aliasToBean(EACH_USER.class));
//		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
//		List<EACH_USER> list = query.list();
		return list;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EACH_USER> getData(String sql ,List<String> values ){
		int i =0;
		for(String value :values){
			jdbcTemplate.execute(value);
			i++;
		}
		AutoAddScalar addScalr = new AutoAddScalar();
		String cols = "COM_NAME,USER_COMPANY,USER_ID,USER_TYPE,USER_STATUS,USER_DESC,USE_IKEY,ROLE_ID,NOLOGIN_EXPIRE_DAY,IP,IDLE_TIMEOUT,LAST_LOGIN_DATE,LAST_LOGIN_IP,CDATE,UDATE ";
		List<EACH_USER> list = new ArrayList<EACH_USER>();
		list=jdbcTemplate.query(cols,new BeanPropertyRowMapper(EACH_USER.class));
//		addScalr.addScalar(query, EACH_USER.class, cols.split(","));
//		query.setResultTransformer(Transformers.aliasToBean(EACH_USER.class));
//		List list = query.list();
		return list;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EACH_USER_PK> getUserCompanyList(){
		List<EACH_USER_PK> list = null;
		String sql = "SELECT DISTINCT USER_COMPANY AS USER_COMPANY FROM EACH_USER";
		try{
			list=jdbcTemplate.query(sql,new BeanPropertyRowMapper(EACH_USER_PK.class));
//			query.addScalar("USER_COMPANY", Hibernate.STRING);
//			query.setResultTransformer(Transformers.aliasToBean(EACH_USER_PK.class));
//			list = query.list();
		}catch(HibernateException e){
			e.printStackTrace();
		}
		return list;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EACH_USER_PK> getUserCompanyList(String user_type){
		List<EACH_USER_PK> list = null;
		String sql = "SELECT DISTINCT USER_COMPANY AS USER_COMPANY FROM EACH_USER WHERE USER_TYPE =:user_type";
		try{		
			list=jdbcTemplate.query(sql,new BeanPropertyRowMapper(EACH_USER_PK.class));
//			query.setParameter("user_type", user_type);
//			query.addScalar("USER_COMPANY", Hibernate.STRING);
//			query.setResultTransformer(Transformers.aliasToBean(EACH_USER_PK.class));
		}catch(HibernateException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public  EACH_USER getUserData(String userId){
		StringBuffer sql = new  StringBuffer();
		sql.append(" SELECT * FROM EACH_USER WHERE USER_ID =:userId ");
		sql.append(" AND USER_STATUS = 'Y' ");
		List<EACH_USER> list = null;
		list=jdbcTemplate.query(userId,new BeanPropertyRowMapper(EACH_USER.class));
//		query.setParameter("userId", userId);
//		query.addEntity(EACH_USER.class);
		EACH_USER po = null;
//		List<EACH_USER> list = query.list();
		if(list != null && list.size() !=0){
			po = list.get(0);
		}
		return po;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EACH_USER> getDataByUserId(String userId){
		String sql = " FROM tw.org.twntch.po.EACH_USER WHERE USER_ID=? ";
		List<EACH_USER> list = new ArrayList<EACH_USER>();
		list=jdbcTemplate.query(sql,new BeanPropertyRowMapper(EACH_USER.class));
		return list;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EACH_USER> getDataByComId(String user_comId){
		
		String sql = "SELECT * FROM EACH_USER WHERE USER_COMPANY = " + user_comId; // 沒有預防植入
		List<EACH_USER> list = new ArrayList<EACH_USER>();
		list=jdbcTemplate.query(sql, new BeanPropertyRowMapper(EACH_USER.class));
		return list;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EACH_USER> getDataByPK(String userId ,String user_comId){
		String sql = " FROM tw.org.twntch.po.EACH_USER WHERE USER_ID =? AND USER_COMPANY = ?";
		List<EACH_USER> list = new ArrayList<EACH_USER>();
		list=jdbcTemplate.query(sql,new BeanPropertyRowMapper(EACH_USER.class));
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EACH_USER> getAllNotEach(){
		List<EACH_USER> list = null;
		String sql = "SELECT * FROM EACH_USER WHERE USER_COMPANY !='0188888' ";
		try{
			list=jdbcTemplate.query(sql,new BeanPropertyRowMapper(EACH_USER.class));
//			query.addEntity(EACH_USER.class);
//			list = query.list();
		}catch(HibernateException e){
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UserlogTchFunRs> getFuncItemByType(String func_type){
		String sql = "SELECT * FROM EACH_FUNC_LIST WHERE  FUNC_TYPE = '" + func_type + "' ORDER BY FUNC_ID";
		List<UserlogTchFunRs> list = new ArrayList<UserlogTchFunRs>();
		list=jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserlogTchFunRs.class));
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UserlogTchFunRs> getNextSubItem(String func_id){
		String sql = "SELECT * FROM EACH_FUNC_LIST WHERE FUNC_TYPE = '2'  AND UP_FUNC_ID = '" + func_id + "' ORDER BY FUNC_ID";
		List<UserlogTchFunRs> list = new ArrayList<UserlogTchFunRs>();
		list=jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserlogTchFunRs.class));
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UserlogTchSearchRs> getPage(String sql){
		List<UserlogTchSearchRs> list = new ArrayList<UserlogTchSearchRs>();
		System.out.println(sql);
		list=jdbcTemplate.query(sql, new BeanPropertyRowMapper(UserlogTchSearchRs.class));
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<UserlogTchSearchRs> getDetail(String serno , String user_id , String com_id){
		
		StringBuffer sql = new StringBuffer();

		sql.append(" SELECT  E.SERNO ,E.USERID ,E.USERIP,E.TXTIME");
		sql.append(" ,E.UP_FUNC_ID ||'-'|| (SELECT FUNC_NAME FROM EACH_FUNC_LIST WHERE FUNC_ID = E.UP_FUNC_ID) AS UP_FUNC_ID "); 
		sql.append(" ,E.FUNC_ID ||'-'||  (SELECT FUNC_NAME FROM EACH_FUNC_LIST WHERE FUNC_ID = E.FUNC_ID) AS FUNC_ID "); 
		sql.append(" ,coalesce(E.USER_COMPANY ||'-'||  (SELECT BGBK_NAME FROM BANK_GROUP WHERE BGBK_ID = E.USER_COMPANY),'') AS USER_COMPANY "); 
		sql.append(" ,E.FUNC_TYPE,E.OPITEM , E.BFCHCON,E.AFCHCON,E.ADEXCODE "); 
		sql.append(" FROM EACH_USERLOG E ");
		sql.append(" WHERE E.SERNO = '"+serno+"' AND E.USERID = '"+user_id+"' AND E.USER_COMPANY = '"+com_id+"' ");
		System.out.println("sql>>>>"+sql);
		
		List<UserlogTchSearchRs> list = new ArrayList<UserlogTchSearchRs>();
		list=jdbcTemplate.query(sql.toString(), new BeanPropertyRowMapper(UserlogTchSearchRs.class));
		return list;
	}
	
	// ---------------- 組裝 sql 區 ----------------
	public String getOrderBySql(String sidx ,String sord){
		StringBuffer orderbysql = new StringBuffer();
		System.out.println("sidx>>"+sidx);
		if(StrUtils.isEmpty(sidx)){
			orderbysql.append(" ORDER BY TXTIME DESC");
		}else{
			orderbysql.append(" ORDER BY "+sidx+" "+sord);
		}
		
		System.out.println("orderbysql>>"+orderbysql);
		return orderbysql.toString();
		
	}
	
	public Map<String,Object> getConditionData(Map<String,String> param ){
		StringBuffer sql = new StringBuffer();
		List<String> values = new LinkedList<>();
		Map<String,Object> retmap = new LinkedHashMap<>();
		
		int i =0;
		for(String key:param.keySet()){
			if(StrUtils.isNotEmpty(param.get(key))){
				if(i!=0){
					sql.append(" AND ");
				}
				if(key.equals("TXTIME_1")){
					sql.append("E.TXTIME >= '" + param.get("TXTIME_1")+" 00:00:00' ");
					values.add(param.get(key)+" 00:00:00 ");
					
				}else if(key.equals("TXTIME_2")){
					sql.append("E.TXTIME <= '" + param.get("TXTIME_1")+" 23:59:59' ");
					values.add(param.get(key)+" 23:59:59 ");
				}else if(key.equals("USER_TYPE") && param.get(key).equals("B")){
					sql.append("E.USER_COMPANY != '0188888' ");
					
				}else if(key.equals("USER_TYPE") && param.get(key).equals("A")){
					
					sql.append("'1' = '1'");
				}else if(key.equals("ROLE_TYPE")){
					if(! param.get("USER_TYPE").equals("A")){
						if(param.get("ROLE_TYPE").equals("B")){
							sql.append("A.USER_TYPE='B' AND A.USER_ID = E.USERID ");
						}else{
							sql.append("A.USER_TYPE='C' AND A.USER_ID = E.USERID ");
						}
					}
				}else{
					sql.append("E."+key+" = " + param.get("USER_COMPANY"));
					values.add(param.get(key));
				}
				i++;
			}
		}

		retmap.put("sqlPath", sql.toString());
		retmap.put("values", values);
		return retmap;
	}
	
	public String getSql(String sqlPath ,String orderbySql, String user_type){
		String userType_sql = "";
		if(!user_type.equals("A")){
			userType_sql = " JOIN EACH_USER A ON E.USER_COMPANY = A.USER_COMPANY ";
		}
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT  E.SERNO ,E.USERID ,E.USER_COMPANY ,E.USERIP ,E.TXTIME"); 
		sql.append(" ,E.UP_FUNC_ID ||'-'|| COALESCE ( (SELECT  FUNC_NAME  FROM EACH_FUNC_LIST WHERE FUNC_ID = E.UP_FUNC_ID) ,'') AS UP_FUNC_ID "); 
		sql.append(" ,E.FUNC_ID ||'-'||  COALESCE ( ( SELECT  FUNC_NAME  FROM EACH_FUNC_LIST WHERE FUNC_ID = E.FUNC_ID ) ,'')   AS FUNC_ID "); 
		sql.append(" ,E.FUNC_TYPE,E.OPITEM ,E.BFCHCON ,E.AFCHCON ,E.ADEXCODE "); 
		sql.append(" FROM EACH_USERLOG E "+userType_sql+"WHERE "); 
		sql.append(sqlPath); 
		sql.append(orderbySql); 
		System.out.println("getSql.sql>>"+sql);
		return sql.toString();
	}
	
	public String getCountSql(String sqlPath, String user_type){
		String userType_sql = "";
		if(!user_type.equals("A")){
			userType_sql = " JOIN EACH_USER A ON E.USER_COMPANY = A.USER_COMPANY ";
		}
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT count(*) "); 
		sql.append(" FROM EACH_USERLOG E "+userType_sql+"WHERE "); 
		sql.append(sqlPath); 
		System.out.println("getSql.sql>>"+sql);
		return sql.toString();
	}

}
