package com.fstop.eachadmin.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fstop.eachadmin.dto.BankGroupRq;
import com.fstop.fcore.util.StrUtils;
import com.fstop.infra.entity.BANK_OPBK;
import lombok.extern.slf4j.Slf4j;
import util.DateTimeUtils;
import util.zDateHandler;
@Service
@Slf4j
public class BankGroupService {
	@Autowired
	private BankOpbkService bank_opbk_bo;
	public List<Map<String, String>> getByOpbkId_Single_Date(BankGroupRq params){
		String paramValue;
		Map rtnMap = new HashMap();
		String OPBK_ID = "";
		paramValue = params.getOPBK_ID();
		if (StrUtils.isNotEmpty(paramValue)){
			OPBK_ID = paramValue;
		}
		String s_bizdate = "";
		paramValue = params.getS_bizdate();
		if (StrUtils.isNotEmpty(paramValue)){
			s_bizdate = paramValue;}
		rtnMap = zDateHandler.verify_BizDate(DateTimeUtils.convertDate(1, s_bizdate, "yyyyMMdd", "yyyyMMdd"));
		log.debug(DateTimeUtils.convertDate(1, s_bizdate, "yyyyMMdd", "yyyyMMdd"));
		log.debug(rtnMap.toString());
		List<BANK_OPBK> list = bank_opbk_bo.getCurBgbkList(OPBK_ID, s_bizdate);;
		List<Map<String, String>> beanList = new LinkedList<Map<String, String>>();
		Map<String, String> bean = null;
		for (BANK_OPBK po : list) {
			
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
