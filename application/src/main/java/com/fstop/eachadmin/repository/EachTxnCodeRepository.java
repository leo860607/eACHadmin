package com.fstop.eachadmin.repository;

import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fstop.infra.entity.EACH_TXN_CODE;
@Repository
public class EachTxnCodeRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	// 暫時測試用
		public List<?> testFunction() {
			List<?> list = new LinkedList<>();
			return list;
		}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<EACH_TXN_CODE> getTranCode(){
		List<EACH_TXN_CODE> list = null;
		String sql = "SELECT * FROM EACH_TXN_CODE WHERE (EACH_TXN_ID LIKE '2%' OR EACH_TXN_ID LIKE '4%' OR EACH_TXN_ID LIKE '6%') ORDER BY EACH_TXN_ID";
			list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(EACH_TXN_CODE.class));
//			Query query = getCurrentSession().createQuery(sql);
//			list = query.list();
		return list;
	}
}
