package com.fstop.eachadmin.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fstop.fcore.util.AutoAddScalar;
import com.fstop.fcore.util.Page;
import com.fstop.infra.entity.UNDONE_TXDATA;
import com.fstop.infra.entity.VW_ONBLOCKTAB;
import com.fstop.infra.entity.WK_DATE_CALENDAR;

@Repository
public class VwOnBlockTabRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;

//	// 檢視明細
//	public Map getDetail(String sql, Map<String, String> params) {
//		List<Map> list = null;
//		list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Map.class));
//
//		if (list != null && list.size() > 0) {
//			return list.get(0);
//		}
//		return null;
//	}
//
//	// 檢視明細
//	public List<Map> getData(String sql, Map<String, String> params) {
//		List<Map> list = null;
//		list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Map.class));
//
//		return list;
//	}

	// 檢視明細
	public Map getDetail(String condition, String txdate, String stan) {
		List<Map> list = null;
		list = jdbcTemplate.query(condition, new BeanPropertyRowMapper(Map.class));
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	// 計算加總用
//    public List dataSum(String dataSumSQL,String[] dataSumCols,Class targetClass){
//        org.hibernate.SQLQuery query =  getCurrentSession().createSQLQuery(dataSumSQL);
//        AutoAddScalar addscalar = new AutoAddScalar();
//        addscalar.addScalar(query,targetClass,dataSumCols);
//        query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(targetClass));
//        return query.list();
//    }

	public List dataSum(String dataSumSQL, String[] dataSumCols, Class targetClass) {
		List list = new ArrayList();
		list = jdbcTemplate.query(dataSumSQL, new BeanPropertyRowMapper(targetClass));
		return list;

	}

	public Page getDataIII(int pageNo, int pageSize, String countQuerySql, String sql, String[] cols,
			Class targetClass) {
		int totalCount = countDataIII(countQuerySql);
		int startIndex = 0;
//		= Page.getStartOfPage(pageNo, pageSize) + 1;
		List<Map> list = null;
		list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(UNDONE_TXDATA.class));

		return new Page(startIndex , totalCount, pageSize, list);
	}

	public int countDataIII(String countQuerySql) {
		int count = 0;
		List<Map> list = null;
		list = jdbcTemplate.query(countQuerySql,new BeanPropertyRowMapper(UNDONE_TXDATA.class) );

		List countList = list.subList(count, count);
		if (countList != null && countList.size() > 0) {
			count = (Integer) countList.get(0);
		}
		return count;
		/*
		 * SQLQuery query = getCurrentSession().createSQLQuery(countQuerySql); List
		 * countList = query.list(); return countList.size();
		 */
	}
}
