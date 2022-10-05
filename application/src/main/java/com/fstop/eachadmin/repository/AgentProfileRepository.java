package com.fstop.eachadmin.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.fstop.infra.entity.AGENT_PROFILE;


@Repository
public class AgentProfileRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AGENT_PROFILE> getCompany_Id_List(String bizdate){
//		List<AGENT_PROFILE> list = null;
//		StringBuffer sql = new StringBuffer();
		List<AGENT_PROFILE> list = new ArrayList<AGENT_PROFILE>();
		try{
			String sql ="SELECT * FROM AGENT_PROFILE WHERE '" +bizdate+ "' BETWEEN (CASE LENGTH(RTRIM(COALESCE(ACTIVE_DATE,''))) WHEN 0 THEN '99999999' ELSE ACTIVE_DATE END) AND (CASE LENGTH(RTRIM(COALESCE(STOP_DATE,''))) WHEN 0 THEN '99999999' ELSE STOP_DATE-1 END) ORDER BY COMPANY_ID";
//			sql.setString(1, bizdate);
			list=jdbcTemplate.query(sql,new BeanPropertyRowMapper(AGENT_PROFILE.class));
			System.out.println("bizdate>>"+bizdate);
//			sql.append("SELECT * FROM AGENT_PROFILE WHERE  ");
//			sql.append(bizdate+" BETWEEN  ");
//			sql.append("  (CASE LENGTH(RTRIM(COALESCE(ACTIVE_DATE,''))) WHEN 0 THEN '99999999' ELSE ACTIVE_DATE END) ");
////			-1 是因STOP_DATE當天就算到期(停用)
//			sql.append(" AND  (CASE LENGTH(RTRIM(COALESCE(STOP_DATE,''))) WHEN 0 THEN '99999999' ELSE STOP_DATE-1 END) ");
//			sql.append(" ORDER BY COMPANY_ID ");
//			SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
//			query.addEntity(AGENT_PROFILE.class);
//			list = query.list();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	public List<AGENT_PROFILE> getCompany_Id_List(){
		List<AGENT_PROFILE> list = null;
//		StringBuffer sql = new StringBuffer();
		try{
			list = this.getAll();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AGENT_PROFILE> getAll(){
		String sql = "SELECT * FROM AGENT_PROFILE";
		List<AGENT_PROFILE> list = new ArrayList<AGENT_PROFILE>();
		list=jdbcTemplate.query(sql,new BeanPropertyRowMapper(AGENT_PROFILE.class));
		return list;
	}
}
