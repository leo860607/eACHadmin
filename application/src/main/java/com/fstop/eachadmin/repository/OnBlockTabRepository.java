package com.fstop.eachadmin.repository;


import tw.org.twntch.po.ONBLOCKTAB;
import tw.org.twntch.po.ONBLOCKTAB_PK;
import tw.org.twntch.util.AutoAddScalar;
import tw.org.twntch.util.NumericUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OnBlockTabDao extends JpaRepository<ONBLOCKTAB, ONBLOCKTAB_PK> {

    public List<Map> getTXNLOG_Detail(String sql, java.util.List<String> values) {
        return getDataRetMap(sql, values);
    }

    public List<Map> getDataRetMap(String sql, java.util.List<String> values) {
        List<Map> list = null;
        try {
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
            int i = 0;
            for (String val : values) {
                query.setParameter(i, val);
                i++;
            }
            query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Page getDataRetPage(int startIndex, int pageSize, String countQuerySql, String sql, java.util.List<String> values) {
        int totalCount = countData(countQuerySql);
        List<Map> list = null;
        try {
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
            int i = 0;
            for (String val : values) {
                query.setParameter(i, val);
                i++;
            }
            query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Page(startIndex - 1, totalCount, pageSize, list);
    }

    public Page getDataRetPage(int startIndex, int pageSize, int totalCount, String sql, java.util.Map<String, String> values) {
        List<Map> list = null;
        java.util.List<java.util.Map<String, Integer>> cntlist = null;
        try {
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
            for (String key : values.keySet()) {
                query.setParameter(key, values.get(key));
            }
            query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Page(startIndex - 1, totalCount, pageSize, list);
    }

    public Page getDataRetPage(int startIndex, int pageSize, int totalCount, String sql, java.util.Map<String, String> values,
                               Object... cols) {
        List<Map> list = null;
        java.util.List<java.util.Map<String, Integer>> cntlist = null;
        try {
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
            for (String key : values.keySet()) {
                query.setParameter(key, values.get(key));
            }
            for (Object s : cols) {
                query.addScalar((String) s);
            }
            query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
            // System.out.println("Aliases>>"+query.getReturnAliases());
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Page(startIndex - 1, totalCount, pageSize, list);
    }

    public Page getData(int pageNo, int pageSize, String countQuerySql, String sql, String[] cols, Class targetClass) {
        int totalCount = countData(countQuerySql);
        int startIndex = Page.getStartOfPage(pageNo, pageSize) + 1;
        int lastIndex = pageNo * pageSize;
        sql += " ) AS TEMP_ WHERE ROWNUMBER >= " + startIndex + " AND ROWNUMBER <= " + lastIndex;

        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
        AutoAddScalar addscalar = new AutoAddScalar();
        addscalar.addScalar(query, targetClass, cols);
        query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(targetClass));
        // 實際查詢返回分頁對像
        java.util.List list = query.list();

        return new Page(startIndex - 1, totalCount, pageSize, list);
    }

    public int countData(String countQuerySql) {
        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(countQuerySql);
        java.util.List countList = query.list();
        return countList.size();
    }

    public java.util.List<java.util.Map<String, Integer>> countDataII(String countQuerySql) {
        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(countQuerySql);
        query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
        java.util.List<java.util.Map<String, Integer>> countList = query.list();
        return countList;
    }

    public java.util.List<java.util.Map<String, Integer>> DataSumList(String countQuerySql, java.util.Map<String, String> values) {
        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(countQuerySql);
        for (String key : values.keySet()) {
            if (key.equals("ROWNUMBER") || key.equals("LAST_ROWNUMBER")) {
                continue;
            }
            query.setParameter(key, values.get(key));
        }
        query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
        java.util.List<java.util.Map<String, Integer>> countList = query.list();
        return countList;
    }

    public Page getDataII(int pageNo, int pageSize, String countQuerySql, String sql, String[] cols,
                          java.util.List<String> values) {
        int totalCount = countDataII(countQuerySql, values);
        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(org.hibernate.Criteria.ALIAS_TO_ENTITY_MAP);
        int i = 0;
        for (String val : values) {
            query.setParameter(i, val);
            i++;
        }
        // 實際查詢返回分頁對像
        int startIndex = Page.getStartOfPage(pageNo, pageSize);
        java.util.List list = query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        return new Page(startIndex, totalCount, pageSize, list);
    }

    public int countDataII(String countQuerySql, java.util.List<String> values) {
        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(countQuerySql);
        int i = 0;
        for (String val : values) {
            query.setParameter(i, val);
            i++;
        }
        java.util.List<Integer> countList = query.list();
        return countList.size();
    }

    public Page getDataIII(int pageNo, int pageSize, String countQuerySql, String sql, String[] cols,
                           Class targetClass) {
        int totalCount = countDataIII(countQuerySql);
        int startIndex = Page.getStartOfPage(pageNo, pageSize) + 1;
        int lastIndex = pageNo * pageSize;
        sql += " ) AS TEMP_ WHERE ROWNUMBER >= " + startIndex + " AND ROWNUMBER <= " + lastIndex;

        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
        AutoAddScalar addscalar = new AutoAddScalar();
        addscalar.addScalar(query, targetClass, cols);
        query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(targetClass));
        // 實際查詢返回分頁對像
        java.util.List list = query.list();

        return new Page(startIndex - 1, totalCount, pageSize, list);
    }

    public int countDataIII(String countQuerySql) {
        int count = 0;
        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(countQuerySql);
        query.addScalar("NUM");
        try {
            java.util.List<Integer> countList = query.list();
            if (countList != null && countList.size() > 0) {
                count = (Integer) countList.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public Page getDataIIII(int pageNo, int pageSize, String countQuerySql, String sql, String[] cols,
                            Class targetClass) {
        int totalCount = countDataIII(countQuerySql);
        int startIndex = Page.getStartOfPage(pageNo, pageSize) + 1;

        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
        AutoAddScalar addscalar = new AutoAddScalar();
        addscalar.addScalar(query, targetClass, cols);
        query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(targetClass));
        // 實際查詢返回分頁對像
        java.util.List list = query.list();

        return new Page(startIndex - 1, totalCount, pageSize, list);
    }

    public List search(String sql, String[] cols, Class targetClass) {
        List list = null;
        try {
            org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(sql);
            AutoAddScalar addscalar = new AutoAddScalar();
            addscalar.addScalar(query, targetClass, cols);
            query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(targetClass));
            list = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 檢視明細
    public Map getDetail(String condition, String txdate, String stan) {
        List<Map> list = null;
        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(condition);
        query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
        list = query.list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    // 計算加總用
    public List dataSum(String dataSumSQL, String[] dataSumCols, Class targetClass) {
        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(dataSumSQL);
        AutoAddScalar addscalar = new AutoAddScalar();
        addscalar.addScalar(query, targetClass, dataSumCols);
        query.setResultTransformer(org.hibernate.transform.Transformers.aliasToBean(targetClass));
        return query.list();
    }

    // 計算加總用(RETURN MAP)
    public List dataSumII(String dataSumSQL, String[] dataSumCols, java.util.List<String> values) {
        org.hibernate.SQLQuery query = getCurrentSession().createSQLQuery(dataSumSQL);
        int i = 0;
        for (String val : values) {
            query.setParameter(i, val);
            i++;
        }
        query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
    }

    public Map getNewFeeDetail(String bizdate, String txid, String senderid, String senderbankid, String txamt) {
        java.util.Map resultMap = new java.util.HashMap();
//		System.out.println("getNewFeeDetail");
//		System.out.println(txid);
//		System.out.println(senderid);
//		System.out.println(senderbankid);
//		System.out.println(txamt);

        String sql = "{CALL SP_CAL_NWFEE_SINGLE(?,?,?,?,?,?,?,?,?,?,?,?)}";
        try {
            java.sql.CallableStatement callableStatement = ((org.hibernate.internal.SessionImpl) getCurrentSession()).connection().prepareCall(sql);
            callableStatement.setString(1, bizdate);
            callableStatement.setString(2, txid);
            callableStatement.setString(3, senderid);
            callableStatement.setString(4, senderbankid);
            callableStatement.setString(5, txamt);
            callableStatement.registerOutParameter(6, java.sql.Types.VARCHAR);//OSND_BANK_FEE (VARCHAR - OUT) - 5
            callableStatement.registerOutParameter(7, java.sql.Types.VARCHAR);//OOUT_BANK_FEE (VARCHAR - OUT) - 6
            callableStatement.registerOutParameter(8, java.sql.Types.VARCHAR);//OIN_BANK_FEE (VARCHAR - OUT) - 7
            callableStatement.registerOutParameter(9, java.sql.Types.VARCHAR);//OTCH_FEE (VARCHAR - OUT) - 8
            callableStatement.registerOutParameter(10, java.sql.Types.VARCHAR);//OWO_BANK_FEE (VARCHAR - OUT) - 9
            callableStatement.registerOutParameter(11, java.sql.Types.VARCHAR);//OFEE_TYPE (VARCHAR - OUT) - 10
            callableStatement.registerOutParameter(12, java.sql.Types.VARCHAR);//OHANDLECHARGE (VARCHAR - OUT) - 11
            System.out.println("EXEC : {CALL SP_CAL_NWFEE_SINGLE('" + bizdate + "''" + txid + "','" + senderid + "','" + senderbankid + "','" + txamt + "')} = ");
            callableStatement.executeUpdate();


            resultMap.put("NEWSENDERFEE_NW", null == callableStatement.getString(6) ? "" : NumericUtil.formatNumberString(callableStatement.getString(6).startsWith(".") ? "0" + callableStatement.getString(6).trim() : callableStatement.getString(6).trim(), 2));
            resultMap.put("NEWOUTFEE_NW", null == callableStatement.getString(7) ? "" : NumericUtil.formatNumberString(callableStatement.getString(7).startsWith(".") ? "0" + callableStatement.getString(7).trim() : callableStatement.getString(7).trim(), 2));
            resultMap.put("NEWINFEE_NW", null == callableStatement.getString(8) ? "" : NumericUtil.formatNumberString(callableStatement.getString(8).startsWith(".") ? "0" + callableStatement.getString(8).trim() : callableStatement.getString(8).trim(), 2));
            resultMap.put("NEWEACHFEE_NW", null == callableStatement.getString(9) ? "" : NumericUtil.formatNumberString(callableStatement.getString(9).startsWith(".") ? "0" + callableStatement.getString(9).trim() : callableStatement.getString(9).trim(), 2));
            resultMap.put("NEWWOFEE_NW", null == callableStatement.getString(10) ? "" : NumericUtil.formatNumberString(callableStatement.getString(10).startsWith(".") ? "0" + callableStatement.getString(10).trim() : callableStatement.getString(10).trim(), 2));
            resultMap.put("TXN_TYPE", null == callableStatement.getString(11) ? "" : callableStatement.getString(11).trim());
            resultMap.put("NEWFEE_NW", null == callableStatement.getString(12) ? "" : NumericUtil.formatNumberString(callableStatement.getString(12).startsWith(".") ? "0" + callableStatement.getString(12).trim() : callableStatement.getString(12).trim(), 2));


            System.out.println("SP_CAL_NWFEE_SINGLE result :" + resultMap.toString());
        } catch (org.hibernate.HibernateException e) {
            e.printStackTrace();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        return resultMap;
    }
            query.setResultTransformer(org.hibernate.transform.Transformers.ALIAS_TO_ENTITY_MAP);
    // System.out.println("Aliases>>"+query.getReturnAliases());
    list =query.list();
} catch(Exception e){
        e.printStackTrace();
        }
        return new Page(startIndex-1,totalCount,pageSize,list);
        }

public Page getData(int pageNo,int pageSize,String countQuerySql,String sql,String[]cols,Class targetClass){
        int totalCount=countData(countQuerySql);
        int startIndex=Page.getStartOfPage(pageNo,pageSize)+1;
        int lastIndex=pageNo*pageSize;
        sql+=" ) AS TEMP_ WHERE ROWNUMBER >= "+startIndex+" AND ROWNUMBER <= "+lastIndex;

        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        AutoAddScalar addscalar=new AutoAddScalar();
        addscalar.addScalar(query,targetClass,cols);
        query.setResultTransformer(Transformers.aliasToBean(targetClass));
        // 實際查詢返回分頁對像
        List list=query.list();

        return new Page(startIndex-1,totalCount,pageSize,list);
        }

public int countData(String countQuerySql){
        SQLQuery query=getCurrentSession().createSQLQuery(countQuerySql);
        List countList=query.list();
        return countList.size();
        }

public List<Map<String, Integer>>countDataII(String countQuerySql){
        SQLQuery query=getCurrentSession().createSQLQuery(countQuerySql);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Integer>>countList=query.list();
        return countList;
        }

public List<Map<String, Integer>>DataSumList(String countQuerySql,Map<String, String> values){
        SQLQuery query=getCurrentSession().createSQLQuery(countQuerySql);
        for(String key:values.keySet()){
        if(key.equals("ROWNUMBER")||key.equals("LAST_ROWNUMBER")){
        continue;
        }
        query.setParameter(key,values.get(key));
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Integer>>countList=query.list();
        return countList;
        }

public Page getDataII(int pageNo,int pageSize,String countQuerySql,String sql,String[]cols,
        List<String> values){
        int totalCount=countDataII(countQuerySql,values);
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        int i=0;
        for(String val:values){
        query.setParameter(i,val);
        i++;
        }
        // 實際查詢返回分頁對像
        int startIndex=Page.getStartOfPage(pageNo,pageSize);
        List list=query.setFirstResult(startIndex).setMaxResults(pageSize).list();
        return new Page(startIndex,totalCount,pageSize,list);
        }

public int countDataII(String countQuerySql,List<String> values){
        SQLQuery query=getCurrentSession().createSQLQuery(countQuerySql);
        int i=0;
        for(String val:values){
        query.setParameter(i,val);
        i++;
        }
        List<Integer> countList=query.list();
        return countList.size();
        }

public Page getDataIII(int pageNo,int pageSize,String countQuerySql,String sql,String[]cols,
        Class targetClass){
        int totalCount=countDataIII(countQuerySql);
        int startIndex=Page.getStartOfPage(pageNo,pageSize)+1;
        int lastIndex=pageNo*pageSize;
        sql+=" ) AS TEMP_ WHERE ROWNUMBER >= "+startIndex+" AND ROWNUMBER <= "+lastIndex;

        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        AutoAddScalar addscalar=new AutoAddScalar();
        addscalar.addScalar(query,targetClass,cols);
        query.setResultTransformer(Transformers.aliasToBean(targetClass));
        // 實際查詢返回分頁對像
        List list=query.list();

        return new Page(startIndex-1,totalCount,pageSize,list);
        }

public int countDataIII(String countQuerySql){
        int count=0;
        SQLQuery query=getCurrentSession().createSQLQuery(countQuerySql);
        query.addScalar("NUM");
        try{
        List<Integer> countList=query.list();
        if(countList!=null&&countList.size()>0){
        count=(Integer)countList.get(0);
        }
        }catch(Exception e){
        e.printStackTrace();
        }
        return count;
        }

public Page getDataIIII(int pageNo,int pageSize,String countQuerySql,String sql,String[]cols,
        Class targetClass){
        int totalCount=countDataIII(countQuerySql);
        int startIndex=Page.getStartOfPage(pageNo,pageSize)+1;

        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        AutoAddScalar addscalar=new AutoAddScalar();
        addscalar.addScalar(query,targetClass,cols);
        query.setResultTransformer(Transformers.aliasToBean(targetClass));
        // 實際查詢返回分頁對像
        List list=query.list();

        return new Page(startIndex-1,totalCount,pageSize,list);
        }

public List search(String sql,String[]cols,Class targetClass){
        List list=null;
        try{
        SQLQuery query=getCurrentSession().createSQLQuery(sql);
        AutoAddScalar addscalar=new AutoAddScalar();
        addscalar.addScalar(query,targetClass,cols);
        query.setResultTransformer(Transformers.aliasToBean(targetClass));
        list=query.list();
        }catch(Exception e){
        e.printStackTrace();
        }
        return list;
        }

// 檢視明細
public Map getDetail(String condition,String txdate,String stan){
        List<Map> list=null;
        SQLQuery query=getCurrentSession().createSQLQuery(condition);
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        list=query.list();
        if(list!=null&&list.size()>0){
        return list.get(0);
        }
        return null;
        }

// 計算加總用
public List dataSum(String dataSumSQL,String[]dataSumCols,Class targetClass){
        SQLQuery query=getCurrentSession().createSQLQuery(dataSumSQL);
        AutoAddScalar addscalar=new AutoAddScalar();
        addscalar.addScalar(query,targetClass,dataSumCols);
        query.setResultTransformer(Transformers.aliasToBean(targetClass));
        return query.list();
        }

// 計算加總用(RETURN MAP)
public List dataSumII(String dataSumSQL,String[]dataSumCols,List<String> values){
        SQLQuery query=getCurrentSession().createSQLQuery(dataSumSQL);
        int i=0;
        for(String val:values){
        query.setParameter(i,val);
        i++;
        }
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.list();
        }

public Map getNewFeeDetail(String bizdate,String txid,String senderid,String senderbankid,String txamt){
        Map resultMap=new HashMap();
//		System.out.println("getNewFeeDetail");
//		System.out.println(txid);
//		System.out.println(senderid);
//		System.out.println(senderbankid);
//		System.out.println(txamt);

        String sql="{CALL SP_CAL_NWFEE_SINGLE(?,?,?,?,?,?,?,?,?,?,?,?)}";
        try{
        CallableStatement callableStatement=((SessionImpl)getCurrentSession()).connection().prepareCall(sql);
        callableStatement.setString(1,bizdate);
        callableStatement.setString(2,txid);
        callableStatement.setString(3,senderid);
        callableStatement.setString(4,senderbankid);
        callableStatement.setString(5,txamt);
        callableStatement.registerOutParameter(6,java.sql.Types.VARCHAR);//OSND_BANK_FEE (VARCHAR - OUT) - 5
        callableStatement.registerOutParameter(7,java.sql.Types.VARCHAR);//OOUT_BANK_FEE (VARCHAR - OUT) - 6
        callableStatement.registerOutParameter(8,java.sql.Types.VARCHAR);//OIN_BANK_FEE (VARCHAR - OUT) - 7
        callableStatement.registerOutParameter(9,java.sql.Types.VARCHAR);//OTCH_FEE (VARCHAR - OUT) - 8
        callableStatement.registerOutParameter(10,java.sql.Types.VARCHAR);//OWO_BANK_FEE (VARCHAR - OUT) - 9
        callableStatement.registerOutParameter(11,java.sql.Types.VARCHAR);//OFEE_TYPE (VARCHAR - OUT) - 10
        callableStatement.registerOutParameter(12,java.sql.Types.VARCHAR);//OHANDLECHARGE (VARCHAR - OUT) - 11
        System.out.println("EXEC : {CALL SP_CAL_NWFEE_SINGLE('"+bizdate+"''"+txid+"','"+senderid+"','"+senderbankid+"','"+txamt+"')} = ");
        callableStatement.executeUpdate();


        resultMap.put("NEWSENDERFEE_NW",null==callableStatement.getString(6)?"":NumericUtil.formatNumberString(callableStatement.getString(6).startsWith(".")?"0"+callableStatement.getString(6).trim():callableStatement.getString(6).trim(),2));
        resultMap.put("NEWOUTFEE_NW",null==callableStatement.getString(7)?"":NumericUtil.formatNumberString(callableStatement.getString(7).startsWith(".")?"0"+callableStatement.getString(7).trim():callableStatement.getString(7).trim(),2));
        resultMap.put("NEWINFEE_NW",null==callableStatement.getString(8)?"":NumericUtil.formatNumberString(callableStatement.getString(8).startsWith(".")?"0"+callableStatement.getString(8).trim():callableStatement.getString(8).trim(),2));
        resultMap.put("NEWEACHFEE_NW",null==callableStatement.getString(9)?"":NumericUtil.formatNumberString(callableStatement.getString(9).startsWith(".")?"0"+callableStatement.getString(9).trim():callableStatement.getString(9).trim(),2));
        resultMap.put("NEWWOFEE_NW",null==callableStatement.getString(10)?"":NumericUtil.formatNumberString(callableStatement.getString(10).startsWith(".")?"0"+callableStatement.getString(10).trim():callableStatement.getString(10).trim(),2));
        resultMap.put("TXN_TYPE",null==callableStatement.getString(11)?"":callableStatement.getString(11).trim());
        resultMap.put("NEWFEE_NW",null==callableStatement.getString(12)?"":NumericUtil.formatNumberString(callableStatement.getString(12).startsWith(".")?"0"+callableStatement.getString(12).trim():callableStatement.getString(12).trim(),2));


        System.out.println("SP_CAL_NWFEE_SINGLE result :"+resultMap.toString());
        }catch(HibernateException e){
        e.printStackTrace();
        }catch(SQLException e){
        e.printStackTrace();
        }
        return resultMap;
        }
        }

        }
