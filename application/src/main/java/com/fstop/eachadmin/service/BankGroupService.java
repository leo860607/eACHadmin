package com.fstop.eachadmin.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fstop.infra.entity.BANKGROUP;

public class BankGroupService {
	public List<Map> getBgbkIdList(){
		List<BANKGROUP> list = bank_group_Dao.getBgbkIdList_Not_5_And_6();
		List<Map> beanList = new LinkedList<Map>();
		HashMap x = new HashMap();
		for(BANKGROUP po : list){
			x = (po.getBGBK_ID() + " - " + po.getBGBK_NAME(), po.getBGBK_ID());
			beanList.add(x);
		}
		System.out.println("beanList>>"+beanList);
		return beanList;
	}

}
