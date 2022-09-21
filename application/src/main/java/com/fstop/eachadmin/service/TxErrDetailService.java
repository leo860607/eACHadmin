package com.fstop.eachadmin.service;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import com.fstop.eachadmin.dto.TxErrDetailRq;
import com.fstop.eachadmin.dto.TxErrDetailRs;
import com.fstop.fcore.util.StrUtils;


public class TxErrDetailService {
	@Autowired
	private TxErrService tx_err_bo;
	@Autowired
	private EachSysStatusTabService eachsysstatustab_bo;
	@Autowired
	private OnblocktabService onblocktab_bo;
	
	public TxErrDetailRs showDetail(TxErrDetailRq param) {
		
		
		String txdate = param.getTXDATE();
		String stan = param.getSTAN();
		
		Map detailDataMap =tx_err_bo.searchByPk(txdate, stan);
		String bizdate = eachsysstatustab_bo.getBusinessDateII();
		
		//20220321新增FOR EXTENDFEE 位數轉換
		if(detailDataMap.get("EXTENDFEE")!=null) {
		  BigDecimal orgNewExtendFee = (BigDecimal) detailDataMap.get("EXTENDFEE");
		   //去逗號除100 1,000 > 1000/100 = 10
		  String strOrgNewExtendFee = orgNewExtendFee.toString();
		   double realNewExtendFee = Double.parseDouble(strOrgNewExtendFee.replace(",", ""))/100;
		   detailDataMap.put("NEWEXTENDFEE", String.valueOf(realNewExtendFee));
		}else {
			//如果是null 顯示空字串
			detailDataMap.put("NEWEXTENDFEE", "");
		}
		
		//如果FEE_TYPE有值 且結果為成功或未完成 新版手續直接取後面欄位
		if(StrUtils.isNotEmpty((String)detailDataMap.get("FEE_TYPE")) && 
		   ("成功".equals((String)detailDataMap.get("RESP"))||"未完成".equals((String)detailDataMap.get("RESP")))) {
			switch ((String)detailDataMap.get("FEE_TYPE")){
			case "A":
				detailDataMap.put("TXN_TYPE","固定");
				break;
			case "B":
				detailDataMap.put("TXN_TYPE","外加");
				break;
			case "C":
				detailDataMap.put("TXN_TYPE","百分比");
				break;
			case "D":
				detailDataMap.put("TXN_TYPE","級距");
				break;
			}
			
		//如果FEE_TYPE有值 且結果為失敗或處理中 新版手續跟舊的一樣
		}else if (StrUtils.isNotEmpty((String)detailDataMap.get("FEE_TYPE")) && 
				   ("失敗".equals((String)detailDataMap.get("RESP"))||"處理中".equals((String)detailDataMap.get("RESP")))) {
			switch ((String)detailDataMap.get("FEE_TYPE")){
			case "A":
				detailDataMap.put("TXN_TYPE","固定");
				break;
			case "B":
				detailDataMap.put("TXN_TYPE","外加");
				break;
			case "C":
				detailDataMap.put("TXN_TYPE","百分比");
				break;
			case "D":
				detailDataMap.put("TXN_TYPE","級距");
				break;
			}
			
			detailDataMap.put("NEWSENDERFEE_NW", detailDataMap.get("NEWSENDERFEE")!=null?detailDataMap.get("NEWSENDERFEE"):"0");
			detailDataMap.put("NEWINFEE_NW", detailDataMap.get("NEWINFEE")!=null?detailDataMap.get("NEWINFEE"):"0");
			detailDataMap.put("NEWOUTFEE_NW", detailDataMap.get("NEWOUTFEE")!=null?detailDataMap.get("NEWOUTFEE"):"0");
			detailDataMap.put("NEWWOFEE_NW", detailDataMap.get("NEWWOFEE")!=null?detailDataMap.get("NEWWOFEE"):"0");
			detailDataMap.put("NEWEACHFEE_NW", detailDataMap.get("NEWEACHFEE")!=null?detailDataMap.get("NEWEACHFEE"):"0");
			detailDataMap.put("NEWFEE_NW",detailDataMap.get("NEWFEE")!=null?detailDataMap.get("NEWFEE"):"0");
			
		//如果FEE_TYPE為空 且結果為成功或未完成 新版手續call sp	
		}else if (StrUtils.isEmpty((String)detailDataMap.get("FEE_TYPE")) && 
				   ("成功".equals((String)detailDataMap.get("RESP"))||"未完成".equals((String)detailDataMap.get("RESP")))) {
			Map newFeeDtailMap = onblocktab_bo.getNewFeeDetail(bizdate,(String) detailDataMap.get("TXN_NAME"),(String) detailDataMap.get("SENDERID")
					,(String) detailDataMap.get("SENDERBANKID_NAME"),(String)detailDataMap.get("NEWTXAMT"));
			detailDataMap.put("TXN_TYPE", newFeeDtailMap.get("TXN_TYPE"));
			detailDataMap.put("NEWSENDERFEE_NW", newFeeDtailMap.get("NEWSENDERFEE_NW"));
			detailDataMap.put("NEWINFEE_NW", newFeeDtailMap.get("NEWINFEE_NW"));
			detailDataMap.put("NEWOUTFEE_NW", newFeeDtailMap.get("NEWOUTFEE_NW"));
			detailDataMap.put("NEWWOFEE_NW", newFeeDtailMap.get("NEWWOFEE_NW"));
			detailDataMap.put("NEWEACHFEE_NW", newFeeDtailMap.get("NEWEACHFEE_NW"));
			detailDataMap.put("NEWFEE_NW", newFeeDtailMap.get("NEWFEE_NW"));
			
		//如果FEE_TYPE為空 且結果為失敗或處理中 新版手續跟舊的一樣
		}else if (StrUtils.isEmpty((String)detailDataMap.get("FEE_TYPE")) && 
				   ("失敗".equals((String)detailDataMap.get("RESP"))||"處理中".equals((String)detailDataMap.get("RESP")))) {
			Map newFeeDtailMap = onblocktab_bo.getNewFeeDetail(bizdate,(String) detailDataMap.get("TXN_NAME"),(String) detailDataMap.get("SENDERID")
					,(String) detailDataMap.get("SENDERBANKID_NAME"),(String)detailDataMap.get("NEWTXAMT"));
			detailDataMap.put("TXN_TYPE", newFeeDtailMap.get("TXN_TYPE"));
			detailDataMap.put("NEWSENDERFEE_NW", detailDataMap.get("NEWSENDERFEE")!=null?detailDataMap.get("NEWSENDERFEE"):"0");
			detailDataMap.put("NEWINFEE_NW", detailDataMap.get("NEWINFEE")!=null?detailDataMap.get("NEWINFEE"):"0");
			detailDataMap.put("NEWOUTFEE_NW", detailDataMap.get("NEWOUTFEE")!=null?detailDataMap.get("NEWOUTFEE"):"0");
			detailDataMap.put("NEWWOFEE_NW", detailDataMap.get("NEWWOFEE")!=null?detailDataMap.get("NEWWOFEE"):"0");
			detailDataMap.put("NEWEACHFEE_NW", detailDataMap.get("NEWEACHFEE")!=null?detailDataMap.get("NEWEACHFEE"):"0");
			detailDataMap.put("NEWFEE_NW",detailDataMap.get("NEWFEE")!=null?detailDataMap.get("NEWFEE"):"0");
		
		}
		
		TxErrDetailRs txerrDetailRs = new TxErrDetailRs();
		txerrDetailRs.setDetailData(detailDataMap);
		return txerrDetailRs;

		
	}
}