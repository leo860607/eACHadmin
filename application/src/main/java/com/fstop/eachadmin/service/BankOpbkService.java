package com.fstop.eachadmin.service;

import java.util.List;

import com.fstop.infra.entity.BANK_OPBK;

public class BankOpbkService {
	/**
	 * 查詢對映BANK_GROUP(VIEW)的所有操作行清單
	 * @return
	 */
	public List<BANK_OPBK> getAllOpbkList(){
		List<BANK_OPBK> list = null;
		try{
			list = bank_opbk_Dao.getAllOpbkList();
		}catch(Exception e){
			e.printStackTrace();
		}
		return list==null?null:(list.size()<=0?null:list);
	}
}
