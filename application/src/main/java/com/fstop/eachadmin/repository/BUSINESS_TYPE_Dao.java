package com.fstop.eachadmin.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

import com.fstop.infra.entity.BUSINESS_TYPE;

@Repository
public class BUSINESS_TYPE_Dao {
	
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
	
	// 暫時測試用
	public List<BUSINESS_TYPE> testFunction(){
		List<BUSINESS_TYPE> list = new LinkedList<>();
		return list;
	}

	public List<BUSINESS_TYPE> getDataByBsName(String name){
		StringBuffer sql = new StringBuffer();
		sql.append(" FROM tw.org.twntch.po.BUSINESS_TYPE WHERE BUSINESS_TYPE_NAME =:name ");
		Query query = getSession().createQuery(sql.toString());
		query.setParameter("name", name);
		List<BUSINESS_TYPE> list = query.list();
		list = list.size()==0?null :list;
		return list;
	}
	
	public boolean saveData(BUSINESS_TYPE po, List<BANK_GROUP_BUSINESS> bgbList){
		boolean result = false;
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		
		try {
			session.saveOrUpdate(po);
			
			for(int i = 0; i < bgbList.size(); i++){
				session.saveOrUpdate(bgbList.get(i));
			}
			
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
	
	public boolean updateData(BUSINESS_TYPE po, List<BANK_GROUP_BUSINESS> insList, List<BANK_GROUP_BUSINESS> delList){
		boolean result = false;
		Session session = getSessionFactory().openSession();
		session.beginTransaction();
		
		try {
			session.saveOrUpdate(po);
			
			for(int i = 0; i < delList.size(); i++){
				session.delete(delList.get(i));
			}
			
			for(int i = 0; i < insList.size(); i++){
				session.merge(insList.get(i));
			}
			
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
}
