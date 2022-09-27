package com.fstop.eachadmin.repository;

import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class RponBlockTabRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public List<Map> getRptData(String sql , List<String> values){
		List<Map> list = null;
		try {
//			SQLQuery query =  getCurrentSession().createSQLQuery(sql.toString());		
			System.out.println("values>>"+values);
			int i =0;
			for(String value :values){
				jdbcTemplate.execute(value);
				i++;
			}
		list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Map.class));	
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return list;
	}
	
	public List<Map> getRptData(String sql , Map<String,String> values){
		List<Map> list = null;
		try {		
//			SQLQuery query =  getCurrentSession().createSQLQuery(sql.toString());
			System.out.println("values>>"+values);
			for( String key :values.keySet() ){
//				query.setParameter(key ,values.get(key));
				jdbcTemplate.queryForMap(key,values.keySet());
			}
//			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
//			list = query.list();
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Map.class));
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
		return list;
	}
}
