package com.fstop.eachadmin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.dto.DetailRq;
import com.fstop.eachadmin.dto.DetailRs;
import com.fstop.eachadmin.repository.OnBlockTabRepository;
import com.fstop.fcore.util.StrUtils;
import com.fstop.infra.entity.LOGIN_FORM;
import com.fstop.infra.entity.ONBLOCKTAB_FORM;
import com.fstop.infra.entity.UNDONE_TXDATA_FORM;

import util.JSONUtils;
@Service
public class UndoneDetailService {
	@Autowired
	private EachSysStatusTabService eachSysStatusTabService;
	@Autowired
	private OnblocktabService onblocktabService;
	@Autowired
	private UndoneService undoneService;

	public DetailRs showDetail(DetailRq param) {
	


	
		String txDate = DetailRq.getTXDATE();
		String stan = DetailRq.getSTAN();
		
		ONBLOCKTAB_FORM onblocktab_form = new ONBLOCKTAB_FORM();
		Map detailDataMap = onblocktabService.showDetail(txDate, stan);
		String bizdate = eachSysStatusTabService.getBusinessDateII();
//-----------------------------------------------------------------------------------------------				
		// 20220321新增FOR EXTENDFEE 位數轉換
		if (detailDataMap.get("EXTENDFEE") != null) {
			BigDecimal orgNewExtendFee = (BigDecimal) detailDataMap.get("EXTENDFEE");
			// 去逗號除100 1,000 > 1000/100 = 10
			String strOrgNewExtendFee = orgNewExtendFee.toString();
			double realNewExtendFee = Double.parseDouble(strOrgNewExtendFee.replace(",", "")) / 100;
			detailDataMap.put("NEWEXTENDFEE", String.valueOf(realNewExtendFee));
		} else {
			// 如果是null 顯示空字串
			detailDataMap.put("NEWEXTENDFEE", "");
		}

		// 如果FEE_TYPE有值 且結果為成功或未完成 新版手續直接取後面欄位
		if (StrUtils.isNotEmpty((String) detailDataMap.get("FEE_TYPE"))
				&& ("成功".equals((String) detailDataMap.get("RESP"))
						|| "未完成".equals((String) detailDataMap.get("RESP")))) {
			switch ((String) detailDataMap.get("FEE_TYPE")) {
			case "A":
				detailDataMap.put("TXN_TYPE", "固定");
				break;
			case "B":
				detailDataMap.put("TXN_TYPE", "外加");
				break;
			case "C":
				detailDataMap.put("TXN_TYPE", "百分比");
				break;
			case "D":
				detailDataMap.put("TXN_TYPE", "級距");
				break;
			}

			// 如果FEE_TYPE有值 且結果為失敗或處理中 新版手續跟舊的一樣
		} else if (StrUtils.isNotEmpty((String) detailDataMap.get("FEE_TYPE"))
				&& ("失敗".equals((String) detailDataMap.get("RESP"))
						|| "處理中".equals((String) detailDataMap.get("RESP")))) {

			switch ((String) detailDataMap.get("FEE_TYPE")) {
			case "A":
				detailDataMap.put("TXN_TYPE", "固定");
				break;
			case "B":
				detailDataMap.put("TXN_TYPE", "外加");
				break;
			case "C":
				detailDataMap.put("TXN_TYPE", "百分比");
				break;
			case "D":
				detailDataMap.put("TXN_TYPE", "級距");
				break;
			}
			detailDataMap.put("NEWSENDERFEE_NW",
					detailDataMap.get("NEWSENDERFEE") != null ? detailDataMap.get("NEWSENDERFEE") : "0");
			detailDataMap.put("NEWINFEE_NW",
					detailDataMap.get("NEWINFEE") != null ? detailDataMap.get("NEWINFEE") : "0");
			detailDataMap.put("NEWOUTFEE_NW",
					detailDataMap.get("NEWOUTFEE") != null ? detailDataMap.get("NEWOUTFEE") : "0");
			detailDataMap.put("NEWWOFEE_NW",
					detailDataMap.get("NEWWOFEE") != null ? detailDataMap.get("NEWWOFEE") : "0");
			detailDataMap.put("NEWEACHFEE_NW",
					detailDataMap.get("NEWEACHFEE") != null ? detailDataMap.get("NEWEACHFEE") : "0");
			detailDataMap.put("NEWFEE_NW", detailDataMap.get("NEWFEE") != null ? detailDataMap.get("NEWFEE") : "0");

			// 如果FEE_TYPE為空 且結果為成功或未完成 新版手續call sp
		} else if (StrUtils.isEmpty((String) detailDataMap.get("FEE_TYPE"))
				&& ("成功".equals((String) detailDataMap.get("RESP"))
						|| "未完成".equals((String) detailDataMap.get("RESP")))) {
			Map newFeeDtailMap = onblocktabService.getNewFeeDetail(bizdate, (String) detailDataMap.get("TXN_NAME"),
					(String) detailDataMap.get("SENDERID"), (String) detailDataMap.get("SENDERBANKID_NAME"),
					(String) detailDataMap.get("NEWTXAMT"));
			detailDataMap.put("TXN_TYPE", newFeeDtailMap.get("TXN_TYPE"));
			detailDataMap.put("NEWSENDERFEE_NW", newFeeDtailMap.get("NEWSENDERFEE_NW"));
			detailDataMap.put("NEWINFEE_NW", newFeeDtailMap.get("NEWINFEE_NW"));
			detailDataMap.put("NEWOUTFEE_NW", newFeeDtailMap.get("NEWOUTFEE_NW"));
			detailDataMap.put("NEWWOFEE_NW", newFeeDtailMap.get("NEWWOFEE_NW"));
			detailDataMap.put("NEWEACHFEE_NW", newFeeDtailMap.get("NEWEACHFEE_NW"));
			detailDataMap.put("NEWFEE_NW", newFeeDtailMap.get("NEWFEE_NW"));

			// 如果FEE_TYPE為空 且結果為失敗或處理中 新版手續跟舊的一樣
		} else if (StrUtils.isEmpty((String) detailDataMap.get("FEE_TYPE"))
				&& ("失敗".equals((String) detailDataMap.get("RESP"))
						|| "處理中".equals((String) detailDataMap.get("RESP")))) {
			Map newFeeDtailMap = onblocktabService.getNewFeeDetail(bizdate, (String) detailDataMap.get("TXN_NAME"),
					(String) detailDataMap.get("SENDERID"), (String) detailDataMap.get("SENDERBANKID_NAME"),
					(String) detailDataMap.get("NEWTXAMT"));
			detailDataMap.put("TXN_TYPE", newFeeDtailMap.get("TXN_TYPE"));
			detailDataMap.put("NEWSENDERFEE_NW",
					detailDataMap.get("NEWSENDERFEE") != null ? detailDataMap.get("NEWSENDERFEE") : "0");
			detailDataMap.put("NEWINFEE_NW",
					detailDataMap.get("NEWINFEE") != null ? detailDataMap.get("NEWINFEE") : "0");
			detailDataMap.put("NEWOUTFEE_NW",
					detailDataMap.get("NEWOUTFEE") != null ? detailDataMap.get("NEWOUTFEE") : "0");
			detailDataMap.put("NEWWOFEE_NW",
					detailDataMap.get("NEWWOFEE") != null ? detailDataMap.get("NEWWOFEE") : "0");
			detailDataMap.put("NEWEACHFEE_NW",
					detailDataMap.get("NEWEACHFEE") != null ? detailDataMap.get("NEWEACHFEE") : "0");
			detailDataMap.put("NEWFEE_NW", detailDataMap.get("NEWFEE") != null ? detailDataMap.get("NEWFEE") : "0");

		}

		onblocktab_form.setDetailData(detailDataMap);
//-----------------------------------------------------------------------------------------------			
		BeanUtils.copyProperties(DetailRq, DetailRq);
		onblocktab_form.setIsUndone("Y");
	
	
		System.out.println("FILTER_BAT>>" + DetailRq.getFILTER_BAT());
		// 操作行代號清單
//		undone_txdata_form.setOpbkIdList(undone_txdata_bo.getOpbkIdList());
		DetailRq.setOpbkIdList(undoneService.getOpbkList());
		// 業務類別清單
		DetailRq.setBsTypeList(undoneService.getBsTypeIdList());

		String businessDate = eachSysStatusTabService.getBusinessDate();
		DetailRq.setSTART_DATE(businessDate);
		DetailRq.setEND_DATE(businessDate);

		Map userData = BeanUtils.describe(DetailRq.getUserData());
		// 銀行端預設帶入操作行
		if (((String) userData.get("USER_TYPE")).equals("B")) {
//			20150407 edit by hugo 只會有操作行故只能抓總行檔 抓分行檔 997會查無資料
//			BANK_BRANCH po = bank_branch_bo.searchByBrbkId((String)userData.get("USER_COMPANY")).get(0);
//			undone_txdata_form.setOPBK_ID(bank_group_bo.search(po.getId().getBGBK_ID()).get(0).getOPBK_ID());
			DetailRq.setOPBK_ID(undoneService.search((String) userData.get("USER_COMPANY")).get(0).getOPBK_ID());
		}
	
	
	

	
	return DetailRs;
	
}
}
