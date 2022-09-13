package com.fstop.infra.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.transform.Transformers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Repository
@Transactional(
        readOnly = true,
        rollbackFor = {Throwable.class}
)
public abstract class CommonDao<T, ID >implements JpaRepository<T,ID>{
    @PersistenceContext
    private EntityManager entityManager;
    
	public List<T> getListDataBySql(String sql ,  List<String> values){
		List<T> list = null;
		try{
			Query query = entityManager.createQuery(sql);
			int i = 0;
			for(String val :values){
				query.setParameter(i, val);
				i++;
			}
			list = query.getResultList(); 
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
}
