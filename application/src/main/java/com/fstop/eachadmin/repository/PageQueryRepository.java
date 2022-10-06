package com.fstop.eachadmin.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fstop.infra.entity.HR_TXP_TIME;
import com.fstop.infra.entity.VW_ONBLOCKTAB;

@Repository
public class PageQueryRepository<T> {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	//取得總頁數
	public int countToatalPageSize(String countQuerySql) {
		List<VW_ONBLOCKTAB> tmp=jdbcTemplate.query(countQuerySql, new BeanPropertyRowMapper(VW_ONBLOCKTAB.class));
		return Integer.parseInt(tmp.get(0).getNUM());
	}
	
	public int countToatalPageSize1(String countQuerySql) {
		List<VW_ONBLOCKTAB> tmp=jdbcTemplate.query(countQuerySql, new BeanPropertyRowMapper(HR_TXP_TIME.class));
		return Integer.parseInt(tmp.get(0).getNUM());
	}
	//分頁查詢
	/**
	 * @param page 分頁data
	 * @param countQuerySql 查詢總筆數的SQL
	 * @param sql 實際查詢SQL
	 * @param outputClass output的Class
	 * @return
	 */
	public Page getPageData(Pageable page,String countQuerySql,String sql,Class outputClass) {
		int totalCount = countToatalPageSize(countQuerySql);
		List<T> datalist = jdbcTemplate.query(
				sql + " LIMIT " + page.getPageSize() + " OFFSET " + (page.getPageNumber())*page.getPageSize(),
				new BeanPropertyRowMapper(outputClass));
		return new PageImpl<T>(datalist, page, totalCount);
	}
	
	public Page getPageData1(Pageable page,String countQuerySql,String sql,Class outputClass) {
		int totalCount = countToatalPageSize1(countQuerySql);
		List<T> datalist = jdbcTemplate.query(
				sql + " LIMIT " + page.getPageSize() + " OFFSET " + (page.getPageNumber())*page.getPageSize(),
				new BeanPropertyRowMapper(outputClass));
		return new PageImpl<T>(datalist, page, totalCount);
	}
}
