package com.fstop.eachadmin.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.fstop.infra.entity.BANK_GROUP;
import com.fstop.infra.entity.BANK_OPBK;
import com.fstop.infra.entity.BUSINESS_TYPE;

import util.GenericsUtils;

@Repository
public class BusinessTypeRepository {
	
    @Autowired
    private JdbcTemplate jdbcTemplate;
	
	// 暫時測試用
	public List<?> testFunction(){
		List<?> list = new LinkedList<>();
		return list;
	}
	
	/**
	 * 舊的 getAllOpbkList()
	public List<BANK_OPBK> getAllOpbkList(){
		List<BANK_OPBK> list = null;
		StringBuilder sql = new StringBuilder();
		
		sql.append("SELECT COALESCE(OP.OPBK_ID,'') AS OPBK_ID , COALESCE(BG.BGBK_NAME ,'') AS OPBK_NAME ");
		sql.append("FROM ( ");
		sql.append("    SELECT DISTINCT OPBK_ID ");
		sql.append("    FROM EACHUSER.BANK_OPBK ");
		sql.append(") AS OP JOIN ( ");
		sql.append("	SELECT BGBK_ID, BGBK_NAME ");
		sql.append("	FROM BANK_GROUP WHERE BGBK_ATTR <> '6' ");
		sql.append(") AS BG ON ");
		sql.append("OP.OPBK_ID = BG.BGBK_ID ");
		sql.append("ORDER BY OP.OPBK_ID ");
		
		try{
			SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
			query.setResultTransformer(Transformers.aliasToBean(BANK_OPBK.class));
			query.addScalar("OPBK_ID").addScalar("OPBK_NAME");
			list = query.list();
		}catch(HibernateException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public List<BANK_OPBK> getAllOpbkList(){
		List<BANK_OPBK> list = null;
		try{
			list = bank_opbk_Dao.getAllOpbkList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list==null?null:(list.size()<=0?null:list);
	}
	*/
	
	// 新的 getAllOpbkList()
	public List<BANK_OPBK> getAllOpbkList (String sql) {
		List<BANK_OPBK> list = new ArrayList<BANK_OPBK>();
		list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(BANK_OPBK.class));
		return list == null ? null : (list.size() <= 0 ? null : list);
	}
	
	/**
	 * 舊的 find()
	public Query getQuery(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		String hqll = hql;
		int vlen = values.length;
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}
	
	public List<BUSINESS_TYPE> find(String hql, Object... values) {
		Assert.hasText(hql);
		Query query = getQuery(hql, values);
		return query.list();
	}
	 */
	
	// 新的 find()
	public List<BUSINESS_TYPE> find(String sql) {
		List<BUSINESS_TYPE> list = new ArrayList<BUSINESS_TYPE>();
		list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(BUSINESS_TYPE.class));
		return list;
	}
}
