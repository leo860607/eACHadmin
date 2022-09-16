package com.fstop.eachadmin.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.repository.BUSINESS_TYPE_Dao;
import com.fstop.eachadmin.repository.BankOpbkRepositry;
import com.fstop.infra.entity.BANK_OPBK;
import com.fstop.infra.entity.BUSINESS_TYPE;


@Service
public class OnblockNotTradResService {
	
	
	
	@Autowired
	private BankOpbkRepositry bankOpbkRepositry ;

	//操作行
	public List<Map<String,String>> getOpbkList() {
		List<BANK_OPBK> list = bankOpbkRepositry.getAllOpbkList();
		List<Map<String,String>> beanList = new LinkedList<Map<String,String>>();
		Map<String,String> bean = null;
		for (BANK_OPBK po : list) {
			bean = new HashMap<String,String>(po.getOPBK_ID() + " - " + po.getOPBK_NAME(), po.getOPBK_ID());
			beanList.add(bean);
		}
		return beanList;
	}
	
	@Autowired
	private BUSINESS_TYPE_Dao business_type_Dao ;
	
	// businessLabel
	public List<Map<String,String>> getBsTypeIdList () {
		
		// List<BUSINESS_TYPE> list = business_type_Dao.getAll();
		// List<BUSINESS_TYPE> list = business_type_Dao.find("FROM tw.org.twntch.po.BUSINESS_TYPE ORDER BY BUSINESS_TYPE_ID");
		// TODO:
		// dao 還沒有改寫好, 暫時先用
		List<BUSINESS_TYPE> list = business_type_Dao.testFunction();
		List<Map<String,String>> beanList = new LinkedList<Map<String,String>>();
		
		Map<String,String> bean = null;
		
		for (BUSINESS_TYPE po :list) {
			po.getBUSINESS_TYPE_ID();
			String k = (String) po.getBUSINESS_TYPE_ID() + " - " + po.getBUSINESS_TYPE_NAME();
			String v = (String) po.getBUSINESS_TYPE_ID();
			bean = new HashMap<String,String>();
			bean.put(k, v);
			beanList.add(bean);
		}
		System.out.println("beanList>>" + beanList);
		return beanList;
	}
	
	public void testFunction () {
		System.out.println("哭哭");
	}
}
