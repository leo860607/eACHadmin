package com.fstop.eachadmin.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;
@Repository
public class CommonSpringRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> list(String sql, Object[] paramObject){
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if(paramObject!=null){
			resultList=jdbcTemplate.queryForList(sql, paramObject);
		}else{
			resultList=jdbcTemplate.queryForList(sql);
		}
		return resultList;
	}

}
