package com.fstop.eachadmin.repository;

import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.internal.SessionImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstop.eachadmin.dto.UndoneSendRs;

import util.NumericUtil;

@Repository
public class OnBlockTabRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;


	private SimpleJdbcCall simpleJdbcCall;

	// 待修改
	public Page getDataIII(int pageNo, int pageSize, String countQuerySql, String sql, String[] cols,
			Class targetClass) {
		int totalCount = countDataIII(countQuerySql);
//		int startIndex = Page.getStartOfPage(pageNo, pageSize) + 1;
		int lastIndex = pageNo * pageSize;
//		sql += " ) AS TEMP_ WHERE ROWNUMBER >= " + startIndex + " AND ROWNUMBER <= " + lastIndex;
//        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
//        AutoAddScalar addscalar = new AutoAddScalar();
//        addscalar.addScalar(query, targetClass, cols);
//        query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(targetClass));
//        // 實際查詢返回分頁對像
//        java.util.List list = query.list();
//
//        return new Page(startIndex - 1, totalCount, pageSize, list);

		List list = new ArrayList();
		list = jdbcTemplate.query(sql, new BeanPropertyRowMapper(targetClass));
//		return new Page(startIndex - 1, totalCount, pageSize, list);
		return new PageImpl(list);
	}

//    改寫存取SQL的Stored Procedures
	public Map getNewFeeDetail(String bizdate, String txid, String senderid, String senderbankid, String txamt) {
		java.util.Map resultMap = new java.util.HashMap();
//		System.out.println("getNewFeeDetail");
//		System.out.println(txid);
//		System.out.println(senderid);
//		System.out.println(senderbankid);
//		System.out.println(txamt);

//        String sql = "{CALL SP_CAL_NWFEE_SINGLE(?,?,?,?,?,?,?,?,?,?,?,?)}";
//		要另外建立SimpleJdbcCall,再把要call的名稱用withProcedureName方法存取
		simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("SP_CAL_NWFEE_SINGLE");
//		設定參數,這邊觀察前5項為參數,後面6項為要output的值
		SqlParameterSource param = new MapSqlParameterSource().addValue("1", "bizdate").addValue("2", "txid")
				.addValue("3", "senderid").addValue("4", "senderbankid").addValue("5", "txamt");

//            java.sql.CallableStatement callableStatement = ((org.hibernate.internal.SessionImpl) getCurrentSession()).connection().prepareCall(sql);
//            callableStatement.setString(1, bizdate);
//            callableStatement.setString(2, txid);
//            callableStatement.setString(3, senderid);
//            callableStatement.setString(4, senderbankid);
//            callableStatement.setString(5, txamt);
//            callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);//OSND_BANK_FEE (VARCHAR - OUT) - 5
//            callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);//OOUT_BANK_FEE (VARCHAR - OUT) - 6
//            callableStatement.registerOutParameter(8, java.sql.Types.VARCHAR);//OIN_BANK_FEE (VARCHAR - OUT) - 7
//            callableStatement.registerOutParameter(9, java.sql.Types.VARCHAR);//OTCH_FEE (VARCHAR - OUT) - 8
//            callableStatement.registerOutParameter(10, java.sql.Types.VARCHAR);//OWO_BANK_FEE (VARCHAR - OUT) - 9
//            callableStatement.registerOutParameter(11, java.sql.Types.VARCHAR);//OFEE_TYPE (VARCHAR - OUT) - 10
//            callableStatement.registerOutParameter(12, java.sql.Types.VARCHAR);//OHANDLECHARGE (VARCHAR - OUT) - 11
//            System.out.println("EXEC : {CALL SP_CAL_NWFEE_SINGLE('" + bizdate + "''" + txid + "','" + senderid + "','" + senderbankid + "','" + txamt + "')} = ");
//            callableStatement.executeUpdate();

//            resultMap.put("NEWSENDERFEE_NW", null == callableStatement.getString(6) ? "" : NumericUtil.formatNumberString(callableStatement.getString(6).startsWith(".") ? "0" + callableStatement.getString(6).trim() : callableStatement.getString(6).trim(), 2));
//            resultMap.put("NEWOUTFEE_NW", null == callableStatement.getString(7) ? "" : NumericUtil.formatNumberString(callableStatement.getString(7).startsWith(".") ? "0" + callableStatement.getString(7).trim() : callableStatement.getString(7).trim(), 2));
//            resultMap.put("NEWINFEE_NW", null == callableStatement.getString(8) ? "" : NumericUtil.formatNumberString(callableStatement.getString(8).startsWith(".") ? "0" + callableStatement.getString(8).trim() : callableStatement.getString(8).trim(), 2));
//            resultMap.put("NEWEACHFEE_NW", null == callableStatement.getString(9) ? "" : NumericUtil.formatNumberString(callableStatement.getString(9).startsWith(".") ? "0" + callableStatement.getString(9).trim() : callableStatement.getString(9).trim(), 2));
//            resultMap.put("NEWWOFEE_NW", null == callableStatement.getString(10) ? "" : NumericUtil.formatNumberString(callableStatement.getString(10).startsWith(".") ? "0" + callableStatement.getString(10).trim() : callableStatement.getString(10).trim(), 2));
//            resultMap.put("TXN_TYPE", null == callableStatement.getString(11) ? "" : callableStatement.getString(11).trim());
//            resultMap.put("NEWFEE_NW", null == callableStatement.getString(12) ? "" : NumericUtil.formatNumberString(callableStatement.getString(12).startsWith(".") ? "0" + callableStatement.getString(12).trim() : callableStatement.getString(12).trim(), 2));

		return resultMap;
	}

	public int countDataIII(String countQuerySql) {
		int count = 0;

		List list = new ArrayList();
		list = jdbcTemplate.query(countQuerySql, new BeanPropertyRowMapper(List.class));
		try {
			if (list != null && list.size() > 0) {
				count = (Integer) list.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public List dataSum(String dataSumSQL, String[] dataSumCols, Class targetClass) {
//		SQLQuery query = getCurrentSession().createSQLQuery(dataSumSQL);
//		AutoAddScalar addscalar = new AutoAddScalar();
//		addscalar.addScalar(query, targetClass, dataSumCols);
//		query.setResultTransformer(Transformers.aliasToBean(targetClass));
//		return query.list();	
		List list = new ArrayList();
		list = jdbcTemplate.query(dataSumSQL, new BeanPropertyRowMapper(targetClass));
		return list;
	}
}
