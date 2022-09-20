package com.fstop.eachadmin.repository;


import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.fstop.infra.entity.WK_DATE_CALENDAR;
import util.DateTimeUtils;

@Repository
public class WkDateRepository  {

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
        sql.append(" SELECT EACHUSER.TRANS_DATE(txn_date,'W','') txn_date  ,row_number()  OVER (ORDER BY txn_date asc) row_num ");
        sql.append(" FROM	EACHUSER.WK_DATE_CALENDAR ");
        sql.append(" where (txn_date between EACHUSER.TRANS_DATE(" + first_date + ",'T','') and  EACHUSER.TRANS_DATE(" + last_date + ",'T','') ) and IS_TXN_DATE='Y' ) ");
        sql.append(" where row_num =1 ");

        org.hibernate.SQLQuery sqlquery = getCurrentSession().createSQLQuery(sql.toString());
        sqlquery.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = sqlquery.list();
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
        sql.append(" SELECT EACHUSER.TRANS_DATE(txn_date,'W','') txn_date  ,row_number()  OVER (ORDER BY txn_date desc) row_num ");
        sql.append(" FROM	EACHUSER.WK_DATE_CALENDAR ");
        sql.append(" where (txn_date between EACHUSER.TRANS_DATE(" + first_date + ",'T','') and  EACHUSER.TRANS_DATE(" + last_date + ",'T','') ) and IS_TXN_DATE='Y' ) ");
        sql.append(" where row_num =1 ");

        org.hibernate.SQLQuery sqlquery = getCurrentSession().createSQLQuery(sql.toString());
        sqlquery.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map> list = sqlquery.list();
        return list;
    }


    public List<WK_DATE_CALENDAR> getByTwYear(String twYear) {
        List<WK_DATE_CALENDAR> list = null;
        StringBuffer sql = new StringBuffer();
        sql.append(" FROM tw.org.twntch.po.WK_DATE_CALENDAR WITHUR WHERE TXN_DATE LIKE :twYear ORDER BY TXN_DATE");
        try {
            Query query = getCurrentSession().createQuery(sql.toString());
            query.setParameter("twYear", twYear + "%");
            list = query.list();
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
            Query query = getCurrentSession().createQuery(sql.toString());
            query.setParameter("twYearMonth", twYear + twMonth + "%");
            list = query.list();
        } catch (org.hibernate.HibernateException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean createWholeYearData(String twYear) throws java.sql.SQLException {
        boolean result = false;
        Session session = getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "CALL MAKEWK_ALLDATE(" + Integer.valueOf(twYear) + ")";
        try {
            java.sql.CallableStatement query = session.connection().prepareCall(sql);
            query.execute();
            session.beginTransaction().commit();
            result = true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            session.beginTransaction().rollback();
            result = false;
        }
        return result;
    }

    //從某一時間起(含)往前或往後取N個營業日
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
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
            query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
            wkList = query.list();
        } catch (org.hibernate.HibernateException e) {
            e.printStackTrace();
        }

        if (wkList != null && wkList.size() > 0) {
            tDate = (String) wkList.get(0).get("TXN_DATE");
            tDate = DateTimeUtils.convertDate(tDate, "yyyyMMdd", "yyyyMMdd");
        }
        return tDate;
    }
}
