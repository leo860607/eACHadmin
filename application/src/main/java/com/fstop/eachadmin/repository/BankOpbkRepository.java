package com.fstop.eachadmin.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.fstop.infra.entity.BANK_OPBK;
import com.fstop.infra.entity.EACH_TXN_CODE;
@Repository
public class BankOpbkRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public List<BANK_OPBK> getCurBgbkList(String sqlPath , Map<String,String> param,String bizdate  ){
		List<BANK_OPBK> list = null;
		StringBuffer sql = new StringBuffer();
		//--依據操作行id及營業日期取得所屬的總行清單 
		sql.append(" SELECT  A.BGBK_ID , A.OPBK_ID ,A.START_DATE "); 
		sql.append(" ,COALESCE ((SELECT BG.BGBK_NAME FROM BANK_GROUP BG WHERE BG.BGBK_ID = A.BGBK_ID ) , '') AS BGBK_NAME ");
		sql.append(" ,COALESCE ((SELECT BG.BGBK_NAME FROM BANK_GROUP BG WHERE BG.BGBK_ID = A.OPBK_ID ) , '') AS OPBK_NAME ");
		sql.append(" FROM EACHUSER.BANK_OPBK A ");
		sql.append(" JOIN ( ");
		sql.append("    SELECT  max(START_DATE) START_DATE , BGBK_ID  ");
		sql.append("    FROM EACHUSER.BANK_OPBK ");
		sql.append("    WHERE START_DATE <= :bizdate ");
		sql.append("    GROUP BY BGBK_ID ");
		sql.append("    ORDER BY BGBK_ID ");
		sql.append(" ) AS B ");
		sql.append(" ON A.BGBK_ID = B.BGBK_ID AND A.START_DATE = B.START_DATE ");
		sql.append(sqlPath);
		try{
			list = jdbcTemplate.query(sqlPath, new BeanPropertyRowMapper(BANK_OPBK.class));
//			SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
//			query.setParameter("bizdate", bizdate);
//			for( String key :param.keySet()){
//				query.setParameter(key, param.get(key));
//		}
//			query.setResultTransformer(Transformers.aliasToBean(BANK_OPBK.class));
//			query.addScalar("BGBK_ID").addScalar("BGBK_NAME").addScalar("START_DATE").addScalar("OPBK_ID").addScalar("OPBK_NAME");
//			list = query.list();
		}catch(HibernateException e){
			e.printStackTrace();
		}
		return list;
	}
}