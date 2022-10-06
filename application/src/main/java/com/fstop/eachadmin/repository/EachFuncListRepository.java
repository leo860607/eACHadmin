package com.fstop.eachadmin.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fstop.infra.entity.BANK_GROUP;
import com.fstop.infra.entity.EACH_FUNC_LIST;

@Repository
public class EachFuncListRepository {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
//	public List<EACH_FUNC_LIST> getAllSubItem(String path_sql){
//		StringBuffer sql = new StringBuffer();
////		sql.append(" SELECT M.FUNC_NAME AS UP_FUNC_NAME,S.UP_FUNC_ID||'_'||S.FUNC_ID AS htmlId,S.FUNC_ID ,S.FUNC_NAME ,S.UP_FUNC_ID ,S.IS_USED FROM EACH_FUNC_LIST S  ") ;
//		sql.append(" SELECT value(M.FUNC_NAME,'') AS UP_FUNC_NAME,value(S.UP_FUNC_ID||'_'||S.FUNC_ID ,'')AS htmlId,value(S.FUNC_ID,'') FUNC_ID ,value(M.FUNC_ID,'' ) UP_FUNC_ID ,value(S.FUNC_NAME,'') FUNC_NAME , value(S.IS_USED,'N') IS_USED FROM EACH_FUNC_LIST S  ") ;
////		sql.append(" JOIN EACH_FUNC_LIST  M ON S.UP_FUNC_ID=M.FUNC_ID  ") ;
//		sql.append(" RIGHT JOIN EACH_FUNC_LIST  M ON S.UP_FUNC_ID=M.FUNC_ID  ") ;
//		sql.append( path_sql ) ;
////		所方最後決定用FUNC_ID 來排序
////		sql.append(" ORDER BY  CAST (M.FUNC_SEQ AS INT),CAST ( S.FUNC_SEQ AS INT )  WITH UR  ") ;
//		sql.append(" ORDER BY  M.FUNC_ID  WITH UR  ") ;
//		System.out.println("getAllSubItem.sql>>"+sql);
//		SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
//		query.addScalar("htmlId" , Hibernate.STRING);
//		query.addScalar("UP_FUNC_NAME" , Hibernate.STRING);
//		query.addScalar("FUNC_ID" , Hibernate.STRING);
//		query.addScalar("FUNC_NAME" , Hibernate.STRING);
//		query.addScalar("UP_FUNC_ID" , Hibernate.STRING);
//		query.addScalar("IS_USED" , Hibernate.STRING);
//		query.setResultTransformer(Transformers.aliasToBean(EACH_FUNC_LIST.class));
////		query.addEntity(EACH_FUNC_LIST.class);
//		List<EACH_FUNC_LIST> list = query.list();
//		return list;
//	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<EACH_FUNC_LIST> getAllSubItem(String path_sql){
		StringBuffer sql = new StringBuffer();
//		sql.append(" SELECT M.FUNC_NAME AS UP_FUNC_NAME,S.UP_FUNC_ID||'_'||S.FUNC_ID AS htmlId,S.FUNC_ID ,S.FUNC_NAME ,S.UP_FUNC_ID ,S.IS_USED FROM EACH_FUNC_LIST S  ") ;
		sql.append(" SELECT value(M.FUNC_NAME,'') AS UP_FUNC_NAME,value(S.UP_FUNC_ID||'_'||S.FUNC_ID ,'')AS htmlId,value(S.FUNC_ID,'') FUNC_ID ,value(M.FUNC_ID,'' ) UP_FUNC_ID ,value(S.FUNC_NAME,'') FUNC_NAME , value(S.IS_USED,'N') IS_USED FROM EACH_FUNC_LIST S  ") ;
//		sql.append(" JOIN EACH_FUNC_LIST  M ON S.UP_FUNC_ID=M.FUNC_ID  ") ;
		sql.append(" RIGHT JOIN EACH_FUNC_LIST  M ON S.UP_FUNC_ID=M.FUNC_ID  ") ;
		sql.append( path_sql ) ;
//		所方最後決定用FUNC_ID 來排序
//		sql.append(" ORDER BY  CAST (M.FUNC_SEQ AS INT),CAST ( S.FUNC_SEQ AS INT )  WITH UR  ") ;
		sql.append(" ORDER BY  M.FUNC_ID  WITH UR  ") ;
		System.out.println("getAllSubItem.sql>>"+sql);
		List<EACH_FUNC_LIST> list = null;
//		SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
		list = jdbcTemplate.query(sql.toString() ,new BeanPropertyRowMapper(EACH_FUNC_LIST.class));
//		query.addScalar("htmlId" , Hibernate.STRING);
//		query.addScalar("UP_FUNC_NAME" , Hibernate.STRING);
//		query.addScalar("FUNC_ID" , Hibernate.STRING);
//		query.addScalar("FUNC_NAME" , Hibernate.STRING);
//		query.addScalar("UP_FUNC_ID" , Hibernate.STRING);
//		query.addScalar("IS_USED" , Hibernate.STRING);
//		query.setResultTransformer(Transformers.aliasToBean(EACH_FUNC_LIST.class));
//		query.addEntity(EACH_FUNC_LIST.class);
//		List<EACH_FUNC_LIST> list = query.list();
		return list;
	}
	
}
