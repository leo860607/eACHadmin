package com.fstop.eachadmin.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.repository.BankGroupRepository;
import com.fstop.infra.entity.BANK_GROUP;
@Service

public class BankGroupService {
	@Autowired
	private BankGroupRepository bank_group_Dao;
	
	public List<Map<String, String>> getBgbkIdList(){
//		List<BANK_GROUP> list = bank_group_Dao.getBgbkIdList_Not_5_And_6();
//		List<LabelValueBean> beanList = new LinkedList<LabelValueBean>();
//		LabelValueBean bean = null;
//		for(BANK_GROUP po : list){
//			bean = new LabelValueBean(po.getBGBK_ID() + " - " + po.getBGBK_NAME(), po.getBGBK_ID());
//			beanList.add(bean);
//		}
		List<BANK_GROUP> list = bank_group_Dao.getBgbkIdList_Not_5_And_6();
		
		List<Map<String, String>> beanList = new LinkedList<Map<String, String>>();

		Map<String, String> bean = null;

		for (BANK_GROUP po : list) {
			
			String k1 = "BGBKName";
			String v1 = (String) po.getBGBK_NAME();
			
			String k2 = "BGBKId";
			String v2 = (String) po.getBGBK_ID();
			
			bean = new HashMap<String, String>();
			bean.put(k1, v1);
			bean.put(k2, v2);
			beanList.add(bean);
		}
		System.out.println("beanList>>" + beanList);
		return beanList;
	}

}
