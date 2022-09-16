package com.fstop.eachadmin.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.fstop.infra.entity.BANK_GROUP;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class BankGroupRepository {

//    @Autowired
//    private JdbcTemplate jdbcTemplate;
    
    
	@SuppressWarnings("unchecked")
	public List<BANK_GROUP> getBgbkIdList_Not_5_And_6(){		
		List<BANK_GROUP> list = new ArrayList<BANK_GROUP>();
//		Query query = getCurrentSession().createQuery("FROM tw.org.twntch.po.BANK_GROUP BANK_GROUP WHERE BGBK_ATTR <> '6' AND BGBK_ATTR <> '5' ORDER BY BGBK_ID");
//		list = query.list();
		list=jdbcTemplate
                .query("SELECT * FROM BANK_GROUP WHERE BGBK_ATTR <> '6' AND BGBK_ATTR <> '5' ORDER BY BGBK_ID",
                		new BeanPropertyRowMapper(BANK_GROUP.class));
		return list;
	}
}
