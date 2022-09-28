package com.fstop.eachadmin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstop.eachadmin.dto.BankGroupRq;
import com.fstop.eachadmin.dto.BankGroupRs;
import com.fstop.fcore.util.StrUtils;
import com.fstop.infra.entity.BANK_OPBK;
import com.fstop.eachadmin.service.BankOpbkService;
import util.DateTimeUtils;
import util.zDateHandler;
@Service

public class BankGroupService {
	@Autowired
	private BankOpbkService bank_opbk_bo;
	public BankGroupRs getByOpbkId_Single_Date(BankGroupRq params){
		List<BANK_OPBK> list  = null;
		Map rtnMap = new HashMap();
		String OPBK_ID = StrUtils.isNotEmpty(params.getOPBK_ID()) ? params.getOPBK_ID() : "";
//		if (StrUtils.isNotEmpty(params.getOPBK_ID())){
//			OPBK_ID = paramValue;
//		}
		String s_bizdate = StrUtils.isNotEmpty(params.getS_bizdate()) ? params.getS_bizdate() : "";
//		if (StrUtils.isNotEmpty(paramValue)){
//			s_bizdate = paramValue;
//		}else{
//			rtnMap.put("result", "FALSE");
//			rtnMap.put("msg", "營業日期不可空白");
//			result = JSONUtils.map2json(rtnMap);
//			return result;
//		}
		rtnMap = zDateHandler.verify_BizDate(DateTimeUtils.convertDate(1, s_bizdate, "yyyyMMdd", "yyyyMMdd"));
		if(rtnMap.get("result").equals("FALSE")){
//			result = JSONUtils.map2json(rtnMap);
//			ObjectMapper mapper = new ObjectMapper();
//			UndoneSendRs result = mapper.convertValue(rtnMap, UndoneSendRs.class);
//			return result;
		}
		list  = bank_opbk_bo.getCurBgbkList(OPBK_ID, s_bizdate);
			
		if(list != null && list.size() > 0){
			rtnMap.put("result", "TRUE");
			rtnMap.put("msg", list);
		}else{
			rtnMap.put("result", "FALSE");
			rtnMap.put("msg", "無總行資料");
		}
//		result = JSONUtils.map2json(rtnMap);
		ObjectMapper mapper = new ObjectMapper();
		BankGroupRs result = mapper.convertValue(rtnMap, BankGroupRs.class);
		System.out.println(result);
		return result;
	}
	
}
