package com.fstop.eachadmin.repository;

import java.io.Serializable;
import java.util.ArrayList;
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
import org.springframework.stereotype.Repository;

import com.fstop.fcore.util.AutoAddScalar;
import com.fstop.infra.entity.EACH_USER;
import com.fstop.infra.entity.EACH_USERLOG;
import com.fstop.infra.entity.EACH_USER_PK;

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
//		String sql = " FROM tw.org.twntch.po.EACH_USER WHERE USER_COMPANY=? ";
		String sql = " SELECT * FROM EACH_USER WHERE USER_COMPANY= " + user_comId ;
		List<EACH_USER> list = new ArrayList<EACH_USER>();
		list=jdbcTemplate.query(sql,new BeanPropertyRowMapper(EACH_USER.class));
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
	public List<EACH_USER> getAll(){
		String sql = "SELECT * FROM EACH_USER";
		List<EACH_USER> list = new ArrayList<EACH_USER>();
		list=jdbcTemplate.query(sql,new BeanPropertyRowMapper(EACH_USER.class));
		return list;
	}
	
	
}
