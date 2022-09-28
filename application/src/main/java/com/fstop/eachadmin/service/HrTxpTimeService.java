package com.fstop.eachadmin.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.repository.EachTxnCodeRepository;
import com.fstop.infra.entity.EACH_TXN_CODE;
@Service
public class HrTxpTimeService {
	@Autowired
	private EachTxnCodeRepository each_txn_code_Dao;
	/**
	 * 取得交易代號(PCODE-4碼)清單
	 * @return
	 */
	public List<Map<String, String>> getPcodeList(){
//		List<EACH_TXN_CODE> list = each_txn_code_Dao.getTranCode();
//		List<LabelValueBean> beanList = new LinkedList<LabelValueBean>();
//		LabelValueBean bean = null;
//		for(EACH_TXN_CODE po : list){
//			bean = new LabelValueBean(po.getEACH_TXN_ID() + " - " + po.getEACH_TXN_NAME(), po.getEACH_TXN_ID());  //TODO
//			beanList.add(bean);
//		}
//		System.out.println("beanList>>"+beanList);
//		return beanList;
		List<EACH_TXN_CODE> list = each_txn_code_Dao.getTranCode();
		
		List<Map<String, String>> beanList = new LinkedList<Map<String, String>>();

		Map<String, String> bean = null;

		for (EACH_TXN_CODE po : list) {
			
			String k1 = "TXNName";
			String v1 = (String) po.getEACH_TXN_NAME();
			
			String k2 = "TXNId";
			String v2 = (String) po.getEACH_TXN_ID();
			
			bean = new HashMap<String, String>();
			bean.put(k1, v1);
			bean.put(k2, v2);
			beanList.add(bean);
		}
		System.out.println("beanList>>" + beanList);
		return beanList;
	}
}
