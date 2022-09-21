package com.fstop.eachadmin.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fstop.infra.entity.EACH_USERLOG;
import com.fstop.infra.entity.EACH_USERLOG_PK;
@Repository
public class EachUserLogRepository{
	@Autowired 
	private JdbcTemplate jdbcTemplate;
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EACH_USERLOG> getDetail(String serno , String user_id , String com_id){
		StringBuffer sql = new StringBuffer();
//		sql.append(" SELECT  E.SERNO ,E.USERID , E.USER_COMPANY ,E.USERIP,E.TXTIME");
		sql.append(" SELECT  E.SERNO ,E.USERID ,E.USERIP,E.TXTIME");
		sql.append(" ,E.UP_FUNC_ID ||'-'|| (SELECT FUNC_NAME FROM EACH_FUNC_LIST WHERE FUNC_ID = E.UP_FUNC_ID) AS UP_FUNC_ID "); 
		sql.append(" ,E.FUNC_ID ||'-'||  (SELECT FUNC_NAME FROM EACH_FUNC_LIST WHERE FUNC_ID = E.FUNC_ID) AS FUNC_ID "); 
//		sql.append(" ,coalesce(E.USER_COMPANY ||'-'||  (SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID = E.USER_COMPANY),'') AS USER_COMPANY "); 
		sql.append(" ,coalesce(E.USER_COMPANY ||'-'||  (SELECT BGBK_NAME FROM BANK_GROUP WHERE BGBK_ID = E.USER_COMPANY),'') AS USER_COMPANY "); 
		sql.append(" ,E.FUNC_TYPE,E.OPITEM , E.BFCHCON,E.AFCHCON,E.ADEXCODE "); 
		sql.append(" FROM EACH_USERLOG E ");
		sql.append(" WHERE E.SERNO = :serno AND E.USERID = :user_id AND E.USER_COMPANY = :com_id ");
//		sql.append(" WHERE E.SERNO = '"+serno+"' AND E.USERID = '"+user_id+"' AND E.USER_COMPANY = '"+com_id+"' ");
		System.out.println("sql>>>>"+sql);
		List<EACH_USERLOG> list = new ArrayList<EACH_USERLOG>();
		list=jdbcTemplate.query(serno,new BeanPropertyRowMapper(EACH_USERLOG.class));
		return list;
	}
	
	
	
	/**
	 * 分頁測試用
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EACH_USERLOG> getAllDataByParm(String pageNo , int pageSize){
		StringBuffer sql = new StringBuffer();
//		sql.append(" SELECT USERIP , TXTIME FROM  EACH_USERLOG ");
//		sql.append(" SELECT E.* FROM EACH_USERLOG E "); //不可單純加 * 要給別名
		sql.append(" SELECT  E.SERNO ,E.USERID , E.USER_COMPANY ,E.USERIP,E.TXTIME"); 
		sql.append(" ,E.UP_FUNC_ID ||'-'|| (SELECT FUNC_NAME FROM EACH_FUNC_LIST WHERE FUNC_ID = E.UP_FUNC_ID) AS UP_FUNC_ID "); 
		sql.append(" ,E.FUNC_ID ||'-'||  (SELECT FUNC_NAME FROM EACH_FUNC_LIST WHERE FUNC_ID = E.FUNC_ID) AS FUNC_ID "); 
		sql.append(" ,E.FUNC_TYPE,E.OPITEM , E.BFCHCON,E.AFCHCON,E.ADEXCODE "); 
		sql.append(" FROM EACH_USERLOG E "); 
//		String countQueryString = " select count (*) " + removeSelect(removeOrders(sql.toString())); ;
		String countQueryString = " select count (*) FROM EACH_USERLOG " ;	
		List<Integer> countList = new ArrayList<Integer>();
		int totalCount =  countList.get(0);
		countList=jdbcTemplate.query(pageNo,new BeanPropertyRowMapper(EACH_USERLOG.class));
		if (totalCount < 1)return new LinkedList<EACH_USERLOG>();		
//		if (totalCount < 1) return new LinkedList<EACH_USERLOG>();
//		query.addScalar("USERIP", Hibernate.STRING);
//		query.addScalar("TXTIME", Hibernate.STRING);
//		query.setResultTransformer(Transformers.aliasToBean(EACH_USERLOG.class));
		List<EACH_USERLOG> list = new ArrayList<EACH_USERLOG>();
		list=jdbcTemplate.query(pageNo,new BeanPropertyRowMapper(EACH_USERLOG.class));
		return list;
		//實際查詢返回分頁對像
//		int startIndex = Page.getStartOfPage(pageNo, pageSize);
//		System.out.println("startIndex>>"+startIndex);
//		List<EACH_USERLOG> list = query.setFirstResult(startIndex).setMaxResults(10).list();
//		new Page(startIndex, totalCount, pageSize, list);
	}
	
}
