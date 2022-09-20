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

	@SuppressWarnings("unchecked")
	public List<BANK_GROUP> getBgbkIdList_Not_5_And_6() {
		List<BANK_GROUP> list = new ArrayList<BANK_GROUP>();
//		Query query = getCurrentSession().createQuery("FROM tw.org.twntch.po.BANK_GROUP BANK_GROUP WHERE BGBK_ATTR <> '6' AND BGBK_ATTR <> '5' ORDER BY BGBK_ID");
//		list = query.list();
		list = jdbcTemplate.query(
				"SELECT * FROM BANK_GROUP WHERE BGBK_ATTR <> '6' AND BGBK_ATTR <> '5' ORDER BY BGBK_ID",
				new BeanPropertyRowMapper(BANK_GROUP.class));
		return list;
	}
	
	public List<BANK_GROUP> getAllData(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
//		sql.append("BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE, ");
		sql.append("BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE, ");
		sql.append("(CASE BGBK_ATTR WHEN '0' THEN '本國銀行' WHEN '1' THEN '地區企銀' WHEN '2' THEN '合作社' WHEN '3' THEN '農漁會' WHEN '4' THEN '外商銀行' WHEN '5' THEN '共用中心' WHEN '6' THEN '票交分所' ELSE '未定義屬性' END) AS BGBK_ATTR, ");
		sql.append("COALESCE( ");
		sql.append("    (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE B.BGBK_ID =  EACHUSER.GET_CUR_OPBKID (A.BGBK_ID , '' , 0 )), '' ");
		sql.append(") AS OPBK_NAME, ");
		sql.append("COALESCE( ");
		sql.append("    (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE B.BGBK_ID =  EACHUSER.GET_CUR_CTBKID (A.BGBK_ID , '' , 0)), '' ");
		sql.append(") AS CTBK_NAME ");
//		20150810 add by hugo 因應變更操作行、清算行
		sql.append(",  COALESCE( EACHUSER.GET_CUR_OPBKID (BGBK_ID , ''  , 0) , '') AS OPBK_ID ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_CTBKID (BGBK_ID , ''  , 0) , '') AS CTBK_ID ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_OPBKID (BGBK_ID , '' ,1 ) , '') AS OP_START_DATE ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_CTBKID (BGBK_ID , '' ,1 ) , '') AS CT_START_DATE ");
		sql.append("FROM BANK_GROUP A ");
		sql.append("ORDER BY A.BGBK_ID ");
		/*
		sql.append(" SELECT value((SELECT BANK_BRANCH.BRBK_NAME FROM BANK_BRANCH WHERE BANK_BRANCH.BRBK_ID = BANK_GROUP.OPBK_ID  ),'無此分行') AS OPBK_NAME ");
		sql.append(" , value((SELECT BANK_BRANCH.BRBK_NAME FROM BANK_BRANCH WHERE BANK_BRANCH.BRBK_ID = BANK_GROUP.CTBK_ID   ) ,'無此分行')AS CTBK_NAME ");
		sql.append(" ,BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE") ;
		sql.append(" ,(CASE WHEN (BGBK_ATTR = '0') THEN '本國銀行'  WHEN (BGBK_ATTR = '1') THEN '地區企銀'  WHEN (BGBK_ATTR = '2') THEN '合作社'  WHEN (BGBK_ATTR = '3') THEN '農漁會'" );
		sql.append(" WHEN (BGBK_ATTR = '4') THEN '外商銀行' WHEN (BGBK_ATTR = '5') THEN '共用中心'  WHEN (BGBK_ATTR = '6') THEN '票交分所' ELSE '未定義屬性' END) AS BGBK_ATTR ");
		sql.append(" FROM BANK_GROUP ORDER BY BANK_GROUP.BGBK_ID ");
		*/
		//System.out.println(sql.toString());
		List<BANK_GROUP> list = null;
		try {
			SQLQuery query = getSession().createSQLQuery(sql.toString());
			String cols = " OPBK_NAME, CTBK_NAME ,BGBK_ID,BGBK_NAME,BGBK_ATTR,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE,OP_START_DATE,CT_START_DATE" ;
			String[] objs =  cols.split(",");
			AutoAddScalar addscalar = new AutoAddScalar();
			addscalar.addScalar(query, BANK_GROUP.class, objs);
			query.setResultTransformer(Transformers.aliasToBean(BANK_GROUP.class));
			list = query.list();
			list = list.size()==0 ? null :list;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
