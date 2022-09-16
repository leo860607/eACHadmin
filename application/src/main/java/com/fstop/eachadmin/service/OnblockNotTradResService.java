package com.fstop.eachadmin.service;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.repository.BUSINESS_TYPE_Dao;
import com.fstop.infra.entity.BANK_OPBK;
import com.fstop.infra.entity.BUSINESS_TYPE;


@Service
public class OnblockNotTradResService {
	
	@Autowired
	private BUSINESS_TYPE_Dao business_type_Dao ;

	// 暫時測試用
	public void testFunction () {
		System.out.println("哭哭");
	}
	
	// 操作行下拉選單
	public List<Map<String,String>> getOpbkList () {
		
		// String sql = "SELECT COALESCE( OP.OPBK_ID,'' ) AS OPBK_ID , COALESCE( BG.BGBK_NAME ,'' ) AS OPBK_NAME FROM ( SELECT DISTINCT OPBK_ID FROM EACHUSER.BANK_OPBK ) AS OP JOIN ( SELECT BGBK_ID, BGBK_NAME FROM BANK_GROUP WHERE BGBK_ATTR <> '6' ) AS BG ON OP.OPBK_ID = BG.BGBK_ID ORDER BY OP.OPBK_ID ";
		// List<BANK_OPBK> list = business_type_Dao.getAllOpbkList(sql);
		// TODO:
		// jdbc 還沒有好, 暫時先用
		List<BANK_OPBK> list = (List<BANK_OPBK>) business_type_Dao.testFunction();
		List<Map<String,String>> beanList = new LinkedList<Map<String,String>>();
		
		Map<String,String> bean = null;
		
		for(BANK_OPBK po : list){
			po.getOPBK_ID();
			String k =(String) po.getOPBK_ID() + " - " + po.getOPBK_NAME();
			String v = (String) po.getOPBK_ID();
			bean = new HashMap<String,String>();
			bean.put(k, v);
			beanList.add(bean);
		}
		System.out.println("beanList>>" + beanList);
		return beanList;
	}
	
	// businessLabel
	public List<Map<String,String>> getBsTypeIdList () {
		
		// String sql = "FROM tw.org.twntch.po.BUSINESS_TYPE ORDER BY BUSINESS_TYPE_ID";
		// List<BUSINESS_TYPE> list = business_type_Dao.find(sql);
		// TODO:
		// jdbc 還沒有好, 暫時先用
		List<BUSINESS_TYPE> list = (List<BUSINESS_TYPE>) business_type_Dao.testFunction();
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
}
