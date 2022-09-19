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
	private BankGroupRepository bankGroupRepository;
	
	public List<Map> getBgbkIdList(){
		List<BANK_GROUP> list = bankGroupRepository.getBgbkIdList_Not_5_And_6();
		List<Map> beanList = new LinkedList<Map>();
		HashMap x = new HashMap();
		for(BANK_GROUP po : list){
			x = (po.getBGBK_ID() + " - " + po.getBGBK_NAME(), po.getBGBK_ID());
			beanList.add(x);
		}
		System.out.println("beanList>>"+beanList);
		return beanList;
	}

}
