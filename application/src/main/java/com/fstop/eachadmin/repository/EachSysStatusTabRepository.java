package com.fstop.eachadmin.repository;



import java.util.List;

import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fstop.infra.entity.BANKAPSTATUSTAB;
import com.fstop.infra.entity.BANKSTATUS;
import com.fstop.infra.entity.BANKSYSSTATUSTAB;
import com.fstop.infra.entity.EACHSYSSTATUSTAB;

import util.zDateHandler;

@Repository
public class EachSysStatusTabRepository{
	
	 @Autowired
    private JdbcTemplate jdbcTemplate;
    
	public List<BANKSTATUS> getData(){
        List<BANKSTATUS> list = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT AP.BGBK_ID, AP.APID, AP.MBAPSTATUS, SYS.MBSYSSTATUS, ");
        sql.append("CASE AP.MBAPSTATUS ");
        sql.append("	WHEN '1' THEN '簽到' ");
        sql.append("	WHEN '2' THEN '暫時簽退' ");
        sql.append("	WHEN '9' THEN '簽退' END AS MBAPSTATUSNAME, ");
        sql.append("CASE SYS.MBSYSSTATUS ");
        sql.append("	WHEN '1' THEN '開機' ");
        sql.append("	WHEN '2' THEN '押碼基碼同步' ");
        sql.append("	WHEN '3' THEN '訊息通知' ");
        sql.append("	WHEN '9' THEN '關機' END AS MBSYSSTATUSNAME ");
        sql.append("FROM BANKAPSTATUSTAB AP JOIN BANKSYSSTATUSTAB SYS ON AP.BGBK_ID = SYS.BGBK_ID");


        try{
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
            String cols = "BGBK_ID,APID,MBAPSTATUS,MBSYSSTATUS,MBAPSTATUSNAME,MBSYSSTATUSNAME";
            AutoAddScalar addscalar = new AutoAddScalar();
            addscalar.addScalar(query, BANKSTATUS.class, cols.split(","));
            query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(BANKSTATUS.class));
            list = query.list();
        }catch(org.hibernate.HibernateException e){
            e.printStackTrace();
        }
        return list;
    }

    public List<BANKSTATUS> getData(String bgbkId, String apId){
        List<BANKSTATUS> list = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT AP.BGBK_ID, AP.APID, AP.MBAPSTATUS, SYS.MBSYSSTATUS, ");
        sql.append("CASE AP.MBAPSTATUS ");
        sql.append("	WHEN '1' THEN '簽到' ");
        sql.append("	WHEN '2' THEN '暫時簽退' ");
        sql.append("	WHEN '9' THEN '簽退' END AS MBAPSTATUSNAME, ");
        sql.append("CASE SYS.MBSYSSTATUS ");
        sql.append("	WHEN '1' THEN '開機' ");
        sql.append("	WHEN '2' THEN '押碼基碼同步' ");
        sql.append("	WHEN '3' THEN '訊息通知' ");
        sql.append("	WHEN '9' THEN '關機' END AS MBSYSSTATUSNAME ");
        sql.append("FROM BANKAPSTATUSTAB AP JOIN BANKSYSSTATUSTAB SYS ON AP.BGBK_ID = SYS.BGBK_ID ");
        sql.append("WHERE AP.BGBK_ID = :bgbkId AND AP.APID = :apId");


        try{
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
            String cols = "BGBK_ID,APID,MBAPSTATUS,MBSYSSTATUS,MBAPSTATUSNAME,MBSYSSTATUSNAME";
            AutoAddScalar addscalar = new AutoAddScalar();
            addscalar.addScalar(query, BANKSTATUS.class, cols.split(","));
            query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(BANKSTATUS.class));
            query.setParameter("bgbkId", bgbkId);
            query.setParameter("apId", apId);
            list = query.list();
        }catch(org.hibernate.HibernateException e){
            e.printStackTrace();
        }
        return list;
    }

    public List<EACHSYSSTATUSTAB> getEachSysStatus(){
        List<EACHSYSSTATUSTAB> list = null;
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT SYSSTATUS FROM EACHSYSSTATUSTAB");

        try{
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
            String cols = "SYSSTATUS";
            AutoAddScalar addscalar = new AutoAddScalar();
            addscalar.addScalar(query, EACHSYSSTATUSTAB.class, cols.split(","));
            query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(EACHSYSSTATUSTAB.class));
            list = query.list();
        }catch(org.hibernate.HibernateException e){
            e.printStackTrace();
        }
        return list;
    }

    public boolean saveData(BANKAPSTATUSTAB ap, BANKSYSSTATUSTAB sys){
        boolean result = false;
        if(ap != null && sys != null){
            Session session = getSessionFactory().openSession();
            session.beginTransaction();

            try {
                session.saveOrUpdate(ap);
                session.saveOrUpdate(sys);
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
        return result;
    }

    /**
     * 取得目前營業日(依照DATEMODE判斷)
     * @return
     */
    public List<EACHSYSSTATUSTAB> getBusinessDate(){
        List<EACHSYSSTATUSTAB> list = null;
        String sql = "SELECT (CASE DATEMODE WHEN '0' THEN THISDATE WHEN '1' THEN NEXTDATE ELSE THISDATE END) AS BUSINESS_DATE, CLEARINGPHASE FROM EACHSYSSTATUSTAB";
        try{
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
            query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(EACHSYSSTATUSTAB.class));
            query.addScalar("BUSINESS_DATE");
            query.addScalar("CLEARINGPHASE");
            list = query.list();
        }catch(org.hibernate.HibernateException e){
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 取得報表查詢條件預設的營業日
     * @return
     */
    public List<EACHSYSSTATUSTAB> getRptBusinessDate(boolean isTxnDate){
        List<EACHSYSSTATUSTAB> list = null;
//		20150529 edit by hugo req by UAT-20150525-02 查詢條件為日期時,中午12:00前預設日期為「上一個營業日」,中午12:00之後預設日期為「本營業日」
        String dateStr = "PREVDATE" ;
        String curTime = zDateHandler.getTheTime();
        int tmp = zDateHandler.compareDiffTime(curTime, "12:00:00");
        dateStr = tmp >= 0 && isTxnDate? "THISDATE" :dateStr;
//		String sql = "SELECT (CASE DATEMODE WHEN '0' THEN PREVDATE WHEN '1' THEN THISDATE ELSE THISDATE END) AS BUSINESS_DATE FROM EACHSYSSTATUSTAB";

        String sql = "SELECT "+dateStr+" AS BUSINESS_DATE FROM EACHSYSSTATUSTAB";
        try{
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
            query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(EACHSYSSTATUSTAB.class));
            query.addScalar("BUSINESS_DATE");
            list = query.list();
        }catch(org.hibernate.HibernateException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 取得次營業日
     * @return
     */
    public List<EACHSYSSTATUSTAB> getNextBusinessDate(){
        List<EACHSYSSTATUSTAB> list = null;
        String sql = " FROM tw.org.twntch.po.EACHSYSSTATUSTAB";
        try{
            Query query = getCurrentSession().createQuery(sql);
            list = query.list();
        }catch(org.hibernate.HibernateException e){
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 取得上一營業日
     * @return
     */
    public List<EACHSYSSTATUSTAB> getThisBusinessDate(){
        List<EACHSYSSTATUSTAB> list = null;
        String sql = " FROM tw.org.twntch.po.EACHSYSSTATUSTAB";
        try{
            Query query = getCurrentSession().createQuery(sql);
            list = query.list();
        }catch(org.hibernate.HibernateException e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 取得EACHSYSSTATUSTAB所有資料
     * @return
     */
    public List<EACHSYSSTATUSTAB> getSYSSTATUSTAB(){
        List<EACHSYSSTATUSTAB> list = null;
        StringBuffer sql = new StringBuffer();

        sql.append("SELECT SYSSTATUS,PREVDATE,THISDATE,NEXTDATE,DATEMODE,CLEARINGPHASE FROM EACHSYSSTATUSTAB");
        try{
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
            String cols = "SYSSTATUS,PREVDATE,THISDATE,NEXTDATE,DATEMODE,CLEARINGPHASE";
            AutoAddScalar addscalar = new AutoAddScalar();
            addscalar.addScalar(query, EACHSYSSTATUSTAB.class, cols.split(","));
            query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(EACHSYSSTATUSTAB.class));
            list = query.list();
        }catch(org.hibernate.HibernateException e){
            e.printStackTrace();
        }

        return list;
    }
}