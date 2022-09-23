package com.fstop.eachadmin.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PageQueryRepository<T> {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	//取得總頁數
	public int countToatalPageSize(String countQuerySql) {
		return jdbcTemplate.queryForObject(countQuerySql, Integer.class);
	}
	
	//分頁查詢
	/**
	 * @param page 分頁data
	 * @param countQuerySql 查詢總筆數的SQL
	 * @param sql 實際查詢SQL
	 * @param outputClass output的Class
	 * @return
	 */
	public Page getPageData(Pageable page,String countQuerySql,String sql, Class outputClass) {
		int totalCount = countToatalPageSize(countQuerySql);
		List<T> datalist = jdbcTemplate.query(
				sql + " LIMIT " + page.getPageSize() + " OFFSET " + page.getOffset(),
				new BeanPropertyRowMapper(outputClass));
		return new PageImpl<T>(datalist, page, totalCount);
	}
}
