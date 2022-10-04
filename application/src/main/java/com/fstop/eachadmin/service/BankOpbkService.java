package com.fstop.eachadmin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.repository.BankOpbkRepository;
import com.fstop.infra.entity.BANK_OPBK;
import util.StrUtils;
@Service
public class BankOpbkService {
	@Autowired
	private BankOpbkRepository bank_opbk_Dao;
	public List<BANK_OPBK> getCurBgbkList(String opbk_id ,String s_bizdate  ){
		List<BANK_OPBK> list = null;
		String sqlPath = ""; 
		Map<String,String> param =  new HashMap<String, String>();
		if(StrUtils.isNotEmpty(opbk_id)){
			sqlPath = "WHERE OPBK_ID = :opbk_id ";
			param.put("opbk_id", opbk_id);
		}
		try{
			list = bank_opbk_Dao.getCurBgbkList(sqlPath ,param, s_bizdate);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list==null?null:(list.size()<=0?null:list);
	}
}
