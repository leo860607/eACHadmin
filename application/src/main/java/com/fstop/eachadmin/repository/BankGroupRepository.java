package com.fstop.eachadmin.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fstop.infra.entity.BANK_GROUP;

@Repository
public class BankGroupRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<BANK_GROUP> getBgbkIdList_Not_5_And_6() {
		List<BANK_GROUP> list = new ArrayList<BANK_GROUP>();
//		Query query = getCurrentSession().createQuery("FROM tw.org.twntch.po.BANK_GROUP BANK_GROUP WHERE BGBK_ATTR <> '6' AND BGBK_ATTR <> '5' ORDER BY BGBK_ID");
//		list = query.list();
		list = jdbcTemplate.query(
				"SELECT * FROM BANK_GROUP WHERE BGBK_ATTR <> '6' AND BGBK_ATTR <> '5' ORDER BY BGBK_ID",
				new BeanPropertyRowMapper(BANK_GROUP.class));
		return list;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BANK_GROUP> getAllData() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
//		sql.append("BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE, ");
		sql.append(
				"BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE, ");
		sql.append(
				"(CASE BGBK_ATTR WHEN '0' THEN '本國銀行' WHEN '1' THEN '地區企銀' WHEN '2' THEN '合作社' WHEN '3' THEN '農漁會' WHEN '4' THEN '外商銀行' WHEN '5' THEN '共用中心' WHEN '6' THEN '票交分所' ELSE '未定義屬性' END) AS BGBK_ATTR, ");
		sql.append("COALESCE( ");
		sql.append(
				"    (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE B.BGBK_ID =  EACHUSER.GET_CUR_OPBKID (A.BGBK_ID , '' , 0 )), '' ");
		sql.append(") AS OPBK_NAME, ");
		sql.append("COALESCE( ");
		sql.append(
				"    (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE B.BGBK_ID =  EACHUSER.GET_CUR_CTBKID (A.BGBK_ID , '' , 0)), '' ");
		sql.append(") AS CTBK_NAME ");
//		20150810 add by hugo 因應變更操作行、清算行
		sql.append(",  COALESCE( EACHUSER.GET_CUR_OPBKID (BGBK_ID , ''  , 0) , '') AS OPBK_ID ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_CTBKID (BGBK_ID , ''  , 0) , '') AS CTBK_ID ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_OPBKID (BGBK_ID , '' ,1 ) , '') AS OP_START_DATE ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_CTBKID (BGBK_ID , '' ,1 ) , '') AS CT_START_DATE ");
		sql.append("FROM BANK_GROUP A ");
		sql.append("ORDER BY A.BGBK_ID ");
		/*
		 * sql.
		 * append(" SELECT value((SELECT BANK_BRANCH.BRBK_NAME FROM BANK_BRANCH WHERE BANK_BRANCH.BRBK_ID = BANK_GROUP.OPBK_ID  ),'無此分行') AS OPBK_NAME "
		 * ); sql.
		 * append(" , value((SELECT BANK_BRANCH.BRBK_NAME FROM BANK_BRANCH WHERE BANK_BRANCH.BRBK_ID = BANK_GROUP.CTBK_ID   ) ,'無此分行')AS CTBK_NAME "
		 * ); sql.
		 * append(" ,BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE"
		 * ) ; sql.
		 * append(" ,(CASE WHEN (BGBK_ATTR = '0') THEN '本國銀行'  WHEN (BGBK_ATTR = '1') THEN '地區企銀'  WHEN (BGBK_ATTR = '2') THEN '合作社'  WHEN (BGBK_ATTR = '3') THEN '農漁會'"
		 * ); sql.
		 * append(" WHEN (BGBK_ATTR = '4') THEN '外商銀行' WHEN (BGBK_ATTR = '5') THEN '共用中心'  WHEN (BGBK_ATTR = '6') THEN '票交分所' ELSE '未定義屬性' END) AS BGBK_ATTR "
		 * ); sql.append(" FROM BANK_GROUP ORDER BY BANK_GROUP.BGBK_ID ");
		 */
		// System.out.println(sql.toString());
		List<BANK_GROUP> list = null;
		try {
			list = jdbcTemplate.query(
					" OPBK_NAME, CTBK_NAME ,BGBK_ID,BGBK_NAME,BGBK_ATTR,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE,OP_START_DATE,CT_START_DATE",
					new BeanPropertyRowMapper(BANK_GROUP.class));

			list = list.size() == 0 ? null : list;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<BANK_GROUP> getDataByBgbkId(String bgbkId) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
//		sql.append("BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE, ");
		sql.append(
				"BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE,IS_EACH ");
		sql.append(
				" , (CASE BGBK_ATTR WHEN '0' THEN '本國銀行' WHEN '1' THEN '地區企銀' WHEN '2' THEN '合作社' WHEN '3' THEN '農漁會' WHEN '4' THEN '外商銀行' WHEN '5' THEN '共用中心' WHEN '6' THEN '票交分所' ELSE '未定義屬性' END) AS BGBK_ATTR, ");
		sql.append("COALESCE( ");
		sql.append(
				"    (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE B.BGBK_ID = EACHUSER.GET_CUR_OPBKID (A.BGBK_ID , '' , 0 ) ), '' ");
		sql.append(") AS OPBK_NAME, ");
		sql.append("COALESCE( ");
		sql.append(
				"    (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE B.BGBK_ID = EACHUSER.GET_CUR_CTBKID (A.BGBK_ID , '' , 0 ) ), '' ");
		sql.append(") AS CTBK_NAME ");
//		20150810 add by hugo 因應變更操作行、清算行
		sql.append(",  COALESCE( EACHUSER.GET_CUR_OPBKID (BGBK_ID , '' , 0 ) , '') AS OPBK_ID ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_CTBKID (BGBK_ID , '' , 0 ) , '') AS CTBK_ID ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_OPBKID (BGBK_ID , '',1 ) , '') AS OP_START_DATE  ");
		sql.append(", COALESCE( EACHUSER.GET_CUR_CTBKID (BGBK_ID , '' ,1) , '') AS CT_START_DATE ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_OPBKID (BGBK_ID , '',2 ) , '') AS OP_NOTE  ");
		sql.append(", COALESCE( EACHUSER.GET_CUR_CTBKID (BGBK_ID , '' ,2) , '') AS CT_NOTE ");
		sql.append(", COALESCE( HR_UPLOAD_MAX_FILE , 0) AS HR_UPLOAD_MAX_FILE  ");

		sql.append("FROM BANK_GROUP A ");
		sql.append("WHERE A.BGBK_ID = :bgbkId ");
		/*
		 * sql.
		 * append(" SELECT value((SELECT BANK_BRANCH.BRBK_NAME FROM BANK_BRANCH WHERE BANK_BRANCH.BRBK_ID = BANK_GROUP.OPBK_ID   ),'無此分行') AS OPBK_NAME "
		 * ); sql.
		 * append(" , value((SELECT BANK_BRANCH.BRBK_NAME FROM BANK_BRANCH WHERE BANK_BRANCH.BRBK_ID = BANK_GROUP.CTBK_ID   ) ,'無此分行')AS CTBK_NAME "
		 * ); sql.
		 * append(" ,BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE"
		 * ) ; sql.
		 * append(" ,(CASE WHEN (BGBK_ATTR = '0') THEN '本國銀行'  WHEN (BGBK_ATTR = '1') THEN '地區企銀'  WHEN (BGBK_ATTR = '2') THEN '合作社'  WHEN (BGBK_ATTR = '3') THEN '農漁會'"
		 * ); sql.
		 * append(" WHEN (BGBK_ATTR = '4') THEN '外商銀行' WHEN (BGBK_ATTR = '5') THEN '共用中心'  WHEN (BGBK_ATTR = '6') THEN '票交分所' ELSE '未定義屬性' END) AS BGBK_ATTR "
		 * ); sql.append(" FROM BANK_GROUP ");
		 * sql.append(" WHERE BANK_GROUP.BGBK_ID = :bgbkId");
		 */
		System.out.println("getDataByBgbkId.sql>>" + sql);
		List<BANK_GROUP> list = null;
		try {

			list = jdbcTemplate.query(
					" OPBK_NAME, CTBK_NAME ,BGBK_ID,BGBK_NAME,BGBK_ATTR,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE , OP_START_DATE , CT_START_DATE , HR_UPLOAD_MAX_FILE , OP_NOTE , CT_NOTE , IS_EACH ",
					new Object[] { bgbkId }, new BeanPropertyRowMapper(BANK_GROUP.class));

			list = list == null ? null : list.size() == 0 ? null : list;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 屬票交的總行清單
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<BANK_GROUP> getBgbkIdList_ACH(){
		String sql = "SELECT * FROM BANK_GROUP BANK_GROUP WHERE BGBK_ATTR = '6' ORDER BY BGBK_ID";
		List<BANK_GROUP> list = new ArrayList<BANK_GROUP>();
		list= jdbcTemplate.query(sql, new BeanPropertyRowMapper(BANK_GROUP.class));
//		Query query = getCurrentSession().createQuery("FROM tw.org.twntch.po.BANK_GROUP BANK_GROUP WHERE BGBK_ATTR = '6' ORDER BY BGBK_ID");
//		list = jdbc.query.list();
		return list;
	}
	
	/**
	 * 非票交的總行清單
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<BANK_GROUP> getBgbkIdListwithoutACH(){
		String sql = "SELECT * FROM BANK_GROUP BANK_GROUP WHERE BGBK_ATTR != '6' ORDER BY BGBK_ID";
		List<BANK_GROUP> list = new ArrayList<BANK_GROUP>();
		list= jdbcTemplate.query(sql, new BeanPropertyRowMapper(BANK_GROUP.class));
//		List list = new ArrayList();
//		Query query = getCurrentSession().createQuery("FROM tw.org.twntch.po.BANK_GROUP BANK_GROUP WHERE BGBK_ATTR != '6' ORDER BY BGBK_ID");
//		list = query.list();
		return list;
	}
}
