package com.fstop.eachadmin.repository;




import tw.org.twntch.po.EACH_USER;
import tw.org.twntch.po.EACH_USER_PK;
import tw.org.twntch.util.AutoAddScalar;
import tw.org.twntch.util.zDateHandler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EachUserDao extends JpaRepository<EACH_USER, java.io.Serializable> {



    public List<EACH_USER> getAllData(String orderSQL){
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT A.USER_COMPANY ||'-'|| B.BGBK_NAME COM_NAME , R.ROLE_ID||'-'||R.ROLE_NAME ROLE_ID ,A.* FROM EACH_USER A ");
        sql.append(" JOIN EACH_ROLE_LIST R ON A.ROLE_ID = R.ROLE_ID  ");
        sql.append(" JOIN BANK_GROUP B  ON A.USER_COMPANY = B.BGBK_ID ");
        sql.append(orderSQL);
        org.hibernate.SQLQuery query =  getSession().createSQLQuery(sql.toString());
        AutoAddScalar addScalr = new AutoAddScalar();
        String cols = "COM_NAME,USER_COMPANY,USER_ID,USER_TYPE,USER_STATUS,USER_DESC,USE_IKEY,ROLE_ID,NOLOGIN_EXPIRE_DAY,IP,IDLE_TIMEOUT,LAST_LOGIN_DATE,LAST_LOGIN_IP,CDATE,UDATE ";
        addScalr.addScalar(query, EACH_USER.class, cols.split(","));
//		query.addEntity(EACH_USER.class);
        query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(EACH_USER.class));
//		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<EACH_USER> list = query.list();
        return list;
    }
    public List<EACH_USER> getData(String sql , java.util.List<String> values ){
        org.hibernate.SQLQuery query =  getSession().createSQLQuery(sql.toString());
        int i =0;
        for(String value :values){
            query.setParameter(i, value);
            i++;
        }
        AutoAddScalar addScalr = new AutoAddScalar();
        String cols = "COM_NAME,USER_COMPANY,USER_ID,USER_TYPE,USER_STATUS,USER_DESC,USE_IKEY,ROLE_ID,NOLOGIN_EXPIRE_DAY,IP,IDLE_TIMEOUT,LAST_LOGIN_DATE,LAST_LOGIN_IP,CDATE,UDATE ";
        addScalr.addScalar(query, EACH_USER.class, cols.split(","));
        query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(EACH_USER.class));
        java.util.List list = query.list();
        return list;
    }


    public List<EACH_USER_PK> getUserCompanyList(){
        List<EACH_USER_PK> list = null;
        String sql = "SELECT DISTINCT USER_COMPANY AS USER_COMPANY FROM EACH_USER";
        try{
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
            query.addScalar("USER_COMPANY", org.hibernate.Hibernate.STRING);
            query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(EACH_USER_PK.class));
            list = query.list();
        }catch(org.hibernate.HibernateException e){
            e.printStackTrace();
        }
        return list;
    }
    public List<EACH_USER_PK> getUserCompanyList(String user_type){
        List<EACH_USER_PK> list = null;
        String sql = "SELECT DISTINCT USER_COMPANY AS USER_COMPANY FROM EACH_USER WHERE USER_TYPE =:user_type";
        try{
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
            query.setParameter("user_type", user_type);
            query.addScalar("USER_COMPANY", org.hibernate.Hibernate.STRING);
            query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(EACH_USER_PK.class));
            list = query.list();
        }catch(org.hibernate.HibernateException e){
            e.printStackTrace();
        }
        return list;
    }

    public EACH_USER getUserData(String userId){
        StringBuffer sql = new  StringBuffer();
        sql.append(" SELECT * FROM EACH_USER WHERE USER_ID =:userId ");
        sql.append(" AND USER_STATUS = 'Y' ");
        org.hibernate.SQLQuery query =  getCurrentSession().createSQLQuery(sql.toString());
        query.setParameter("userId", userId);
        query.addEntity(EACH_USER.class);
        EACH_USER po = null;
        List<EACH_USER> list = query.list();
        if(list != null && list.size() !=0){
            po = list.get(0);
        }
        return po;
    }
    public List<EACH_USER> getDataByUserId(String userId){
        List<EACH_USER> list = this.find(" FROM tw.org.twntch.po.EACH_USER WHERE USER_ID=? ", userId);
        return list;
    }
    public List<EACH_USER> getDataByComId(String user_comId){
        List<EACH_USER> list = this.find(" FROM tw.org.twntch.po.EACH_USER WHERE USER_COMPANY=? ", user_comId);
        return list;
    }
    public List<EACH_USER> getDataByPK(String userId ,String user_comId){
        List<EACH_USER> list = this.find(" FROM tw.org.twntch.po.EACH_USER WHERE USER_ID =? AND USER_COMPANY = ?", userId ,user_comId);
        return list;
    }

    public List<EACH_USER> getAllNotEach(){
        List<EACH_USER> list = null;
        String sql = "SELECT * FROM EACH_USER WHERE USER_COMPANY !='0188888' ";
        try{
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
            query.addEntity(EACH_USER.class);
            list = query.list();
        }catch(org.hibernate.HibernateException e){
            e.printStackTrace();
        }
        return list;
    }
}

