package com.fstop.eachadmin.repository;

import java.util.List;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fstop.infra.entity.BANK_BRANCH;
import com.fstop.infra.entity.BANK_GROUP;
@Repository
public class BankBranchRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	/**
	 * 根據總行Id取得分行相關資料
	 * @param bgbkId
	 * @return
	 */
	public List<BANK_BRANCH> getDataByBgBkId(String bgbkId ){
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM tw.org.twntch.po.BANK_BRANCH WHERE BGBK_ID =:bgbkId ORDER BY BRBK_ID");
		List<BANK_BRANCH> list = null;
		try {
			list=jdbcTemplate.query(bgbkId,new BeanPropertyRowMapper(BANK_BRANCH.class));
//			Query query = getSession().createQuery(sql.toString());
//			query.setParameter("bgbkId", bgbkId);
//			list = query.list();
			System.out.println("list>>"+list);
			list = list.size()==0?null:list;
		} catch (DataAccessResourceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return list;
	}

	
	public List<BANK_BRANCH> getBranchName(String brbkId){
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM tw.org.twntch.po.BANK_BRANCH WHERE BRBK_ID =:brbkId");
		List<BANK_BRANCH> list = null;
		try {
			list=jdbcTemplate.query(brbkId,new BeanPropertyRowMapper(BANK_BRANCH.class));
//			Query query = getSession().createQuery(sql.toString());
//			query.setParameter("brbkId", brbkId);
//			list = query.list();
			System.out.println("list>>" + list);
		} catch (DataAccessResourceFailureException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<BANK_GROUP> getOpbkByBrbkId(String brbkId){
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT * FROM BANK_GROUP ");
		sql.append("WHERE BGBK_ID = (");
		sql.append("	SELECT A.OPBK_ID ");
		sql.append("	FROM BANK_GROUP A JOIN BANK_GROUP B ON A.OPBK_ID = B.BGBK_ID ");
		sql.append("	WHERE A.BGBK_ID = ( ");
		sql.append("    	SELECT BGBK_ID ");
		sql.append("    	FROM BANK_BRANCH ");
		sql.append("    	WHERE BRBK_ID = :brbkId ");
		sql.append("	) ");
		sql.append(") ");
		List<BANK_GROUP> list = null;
		try{
			list=jdbcTemplate.query(brbkId,new BeanPropertyRowMapper(BANK_BRANCH.class));
//			SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());
//			query.setParameter("brbkId", brbkId);
//			query.addEntity(BANK_GROUP.class);
//			list = query.list();
		}catch(HibernateException e){
			e.printStackTrace();
		}
		return list;
	}
}
