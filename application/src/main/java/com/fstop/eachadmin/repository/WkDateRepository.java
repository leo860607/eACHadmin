package com.fstop.eachadmin.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fstop.infra.entity.BANK_GROUP;
import com.fstop.infra.entity.WK_DATE_CALENDAR;

import lombok.extern.slf4j.Slf4j;

import util.DateTimeUtils;
import util.zDateHandler;

@Repository
@Slf4j
public class WkDateRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	/**
	 * @param first_date(西元年)
	 * @param last_date(西元年)
	 * @return
	 */
	public List<Map> getFirst_Bizdate_of_Month(String first_date, String last_date) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT txn_date FROM ( ");
		sql.append(
				" SELECT EACHUSER.TRANS_DATE(txn_date,'W','') txn_date  ,row_number()  OVER (ORDER BY txn_date asc) row_num ");
		sql.append(" FROM	EACHUSER.WK_DATE_CALENDAR ");
		sql.append(" where (txn_date between EACHUSER.TRANS_DATE(" + first_date + ",'T','') and  EACHUSER.TRANS_DATE("
				+ last_date + ",'T','') ) and IS_TXN_DATE='Y' ) ");
		sql.append(" where row_num =1 ");
		List list = new ArrayList();
		list = jdbcTemplate.query(sql.toString(),
				new Object[] { org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP },
				new BeanPropertyRowMapper(BANK_GROUP.class));

		return list;
	}

	/**
	 * @param first_date(西元年)
	 * @param last_date(西元年)
	 * @return
	 */
	public List<Map> getLast_Bizdate_of_Month(String first_date, String last_date) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT txn_date FROM ( ");
		sql.append(
				" SELECT EACHUSER.TRANS_DATE(txn_date,'W','') txn_date  ,row_number()  OVER (ORDER BY txn_date desc) row_num ");
		sql.append(" FROM	EACHUSER.WK_DATE_CALENDAR ");
		sql.append(" where (txn_date between EACHUSER.TRANS_DATE(" + first_date + ",'T','') and  EACHUSER.TRANS_DATE("
				+ last_date + ",'T','') ) and IS_TXN_DATE='Y' ) ");
		sql.append(" where row_num =1 ");
		List list = new ArrayList();
		list = jdbcTemplate.query(sql.toString(),
				new Object[] { org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP },
				new BeanPropertyRowMapper(BANK_GROUP.class));

		return list;
	}

	public List<WK_DATE_CALENDAR> getByTwYear(String twYear) {
		List<WK_DATE_CALENDAR> list = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM tw.org.twntch.po.WK_DATE_CALENDAR WITHUR WHERE TXN_DATE LIKE :twYear ORDER BY TXN_DATE");
		try {

			list = jdbcTemplate.query(sql.toString(), new Object[] { "twYear", twYear + "%" },
					new BeanPropertyRowMapper(BANK_GROUP.class));

		} catch (org.hibernate.HibernateException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<WK_DATE_CALENDAR> getByTwYearMonth(String twYear, String twMonth) {
		List<WK_DATE_CALENDAR> list = null;
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM tw.org.twntch.po.WK_DATE_CALENDAR WITHUR WHERE TXN_DATE LIKE :twYearMonth ORDER BY TXN_DATE");
		try {

			list = jdbcTemplate.query(sql.toString(), new Object[] { "twYearMonth", twYear + twMonth + "%" },
					new BeanPropertyRowMapper(BANK_GROUP.class));

		} catch (org.hibernate.HibernateException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<WK_DATE_CALENDAR> getByStartAndEndDate(String startDate, String endDate) {
		List<WK_DATE_CALENDAR> list = null;
		StringBuffer sql = new StringBuffer();
		sql.append(
				" FROM tw.org.twntch.po.WK_DATE_CALENDAR WITHUR WHERE TXN_DATE BETWEEN :startDate AND :endDate ORDER BY TXN_DATE");
		try {
			list = jdbcTemplate.query(sql.toString(), new Object[] { startDate, endDate },
					new BeanPropertyRowMapper(BANK_GROUP.class));

		} catch (org.hibernate.HibernateException e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean createWholeYearData(String twYear) throws java.sql.SQLException {
		boolean result = false;
		List list = new ArrayList();
		String sql = "CALL MAKEWK_ALLDATE(" + Integer.valueOf(twYear) + ")";
		try {
			list = jdbcTemplate.query(sql.toString(),

					new BeanPropertyRowMapper(BANK_GROUP.class));

//			session.beginTransaction().commit();
			result = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
//			session.beginTransaction().rollback();
			result = false;
		}
		return result;
	}

	// 從某一時間起(含)往前或往後取N個營業日
	public String gotoBusinessDate(String west_startDate, int offset) {
		String tDate = west_startDate;

		StringBuilder sql = new StringBuilder();
		String tw_startDate = DateTimeUtils.convertDate(west_startDate, "yyyyMMdd", "yyyyMMdd");
		sql.append("SELECT * FROM ( ");
		if (offset < 0) {
			sql.append("    SELECT ROW_NUMBER() OVER(ORDER BY TXN_DATE DESC) AS NUM, WK.TXN_DATE ");
			sql.append("    FROM WK_DATE_CALENDAR AS WK ");
			sql.append("    WHERE TXN_DATE < '" + tw_startDate + "' ");
		} else {
			sql.append("    SELECT ROW_NUMBER() OVER(ORDER BY TXN_DATE ASC) AS NUM, WK.TXN_DATE ");
			sql.append("    FROM WK_DATE_CALENDAR AS WK ");
			sql.append("    WHERE TXN_DATE >= '" + tw_startDate + "' ");
			offset++;
		}
		sql.append("    AND IS_TXN_DATE = 'Y' ");
		sql.append(") AS TEMP ");
		sql.append("WHERE NUM = " + Math.abs(offset));
		System.out.println("GOTO SQL >> " + sql);

		List<Map> wkList = null;
		try {
			wkList = jdbcTemplate.query(sql.toString(),

					new BeanPropertyRowMapper(WK_DATE_CALENDAR.class));

		} catch (org.hibernate.HibernateException e) {
			e.printStackTrace();
		}

		if (wkList != null && wkList.size() > 0) {
			tDate = (String) wkList.get(0).get("TXN_DATE");
			tDate = DateTimeUtils.convertDate(tDate, "yyyyMMdd", "yyyyMMdd");
		}
		return tDate;
	}

	// 取得最新STAN並加1(當日不重複)
	public String getStan() {
		String nTraceNo = null;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CASE MAX(COALESCE(WEBTRACENO,'')) WHEN '' THEN '0000000' ELSE ( ");
		sql.append(
				"	COALESCE(RIGHT(RTRIM(REPEAT('0', 7) || CAST(CAST(MAX(WEBTRACENO) AS INT) + 1 AS CHAR(20))), 7), '0000000') ");
		sql.append(") END AS N_TRACENO ");
		sql.append("FROM OPCTRANSACTIONLOGTAB ");
		sql.append("WHERE TXDATE = '" + zDateHandler.getDateNum() + "' ");
		try {
			List list = new ArrayList();
			list = jdbcTemplate.query(sql.toString(),

					new BeanPropertyRowMapper(com.fstop.infra.entity.OPCTRANSACTIONLOGTAB.class));
		
			if (list != null && list.size() > 0) {
//				nTraceNo = list.get(0).getN_TRACENO();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nTraceNo;
	}

	// 將onblocktab, onpendingtab, onclearing在typhDate的資料營業日改為nextBizdate
	public String updateTyphData(String typhDate, BeanPropertyRowMapper nextBizdate) {
		// 測試用
		boolean isTest = false;

		String rtnStr = "";
		String sql = "{CALL TYPH_UPD_BIZDATE(?,?,?)}";
		List list = new ArrayList();
		try {
			
//			list = jdbcTemplate.query(sql.toString(),
//					Object[] {typhDate,nextBizdate}
//					new BeanPropertyRowMapper(WK_DATE_CALENDAR.class));
//			java.sql.CallableStatement callableStatement = ((org.hibernate.internal.SessionImpl) getCurrentSession())
//					.connection().prepareCall(sql);
//			callableStatement.registerOutParameter(1, java.sql.Types.VARCHAR);
//			callableStatement.setString(2, typhDate);
//			callableStatement.setString(3, nextBizdate);
			log("EXEC : {CALL TYPH_UPD_BIZDATE(?,'" + typhDate + "','" + nextBizdate + "')} = ");
			// 測試時不執行，避免影響資料
			if (!isTest) {
//				callableStatement.executeUpdate();
//				rtnStr = callableStatement.getString(1);
			} else {
				rtnStr = "0,1,2,3";
			}
			log(rtnStr);
		} catch (org.hibernate.HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rtnStr;
	}

	public void log(String msg) {
		System.out.println("### " + msg);
		log.debug("### " + msg);

	}



	public WK_DATE_CALENDAR get() {
		WK_DATE_CALENDAR x = new WK_DATE_CALENDAR();
		x.getTXN_DATE();
		return x;
	}


}
