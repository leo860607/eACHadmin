package com.fstop.eachadmin.service;

import com.fstop.eachadmin.dto.ObtkNtrRq;
import com.fstop.eachadmin.dto.ObtkNtrRs;
import com.fstop.infra.entity.BANK_BRANCH;
import com.fstop.infra.entity.ONBLOCKTAB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.BeanUtils;
import util.JSONUtils;
import util.StrUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NTRDetailService {

    @Autowired
    private OnblocktabService onblocktabService;

    public ObtkNtrRs showDetail(ObtkNtrRq param) {

        ObtkNtrRs obtkNtrRs = new ObtkNtrRs();
        String ac_key = StrUtils.isEmpty(param.getAc_key())?"":param.getAc_key();
        List<BANK_BRANCH> list = null;
        List<ONBLOCKTAB> onblist = null;
        Map<String, String> m = new HashMap<String, String>();

        if (ac_key.equals("search")) {

        }
        else if (ac_key.equals("edit")) {

            // BeanUtils.populate(param, JSONUtils.json2map(param.getEdit_params()));

            String txDate = param.getTXDATE();
            String stan = param.getSTAN();

            Map detailDataMap = onblocktabService.showNotTradResDetail(txDate, stan);
            //要用舊的營業日去查手續費
            String obizdate = param.getOLDBIZDATE();

            //20220321新增FOR EXTENDFEE 位數轉換
            if (detailDataMap.get("EXTENDFEE") != null) {
                BigDecimal orgNewExtendFee = (BigDecimal) detailDataMap.get("EXTENDFEE");
                //去逗號除100 1,000 > 1000/100 = 10
                String strOrgNewExtendFee = orgNewExtendFee.toString();
                double realNewExtendFee = Double.parseDouble(strOrgNewExtendFee.replace(",", "")) / 100;
                detailDataMap.put("NEWEXTENDFEE", String.valueOf(realNewExtendFee));
            } else {
                //如果是null 顯示空字串
                detailDataMap.put("NEWEXTENDFEE", "");
            }

            //如果FEE_TYPE有值 且結果為成功或未完成  此功能都拿新的值
            if (StrUtils.isNotEmpty((String) detailDataMap.get("FEE_TYPE")) &&
                    ("成功".equals((String) detailDataMap.get("RESP")) || "未完成".equals((String) detailDataMap.get("RESP")))) {
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

                //如果FEE_TYPE有值 且結果為失敗或處理中 此功能都拿新的值
            } else if (StrUtils.isNotEmpty((String) detailDataMap.get("FEE_TYPE")) &&
                    ("失敗".equals((String) detailDataMap.get("RESP")) || "處理中".equals((String) detailDataMap.get("RESP")))) {
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

                //如果FEE_TYPE為空 且結果為成功或未完成 新版手續call sp  此功能都拿新的值
            } else if (StrUtils.isEmpty((String) detailDataMap.get("FEE_TYPE")) &&
                    ("成功".equals((String) detailDataMap.get("RESP")) || "未完成".equals((String) detailDataMap.get("RESP")))) {
                Map newFeeDtailMap = onblocktabService.getNewFeeDetail(obizdate, (String) detailDataMap.get("TXN_NAME"), (String) detailDataMap.get("SENDERID")
                        , (String) detailDataMap.get("SENDERBANKID_NAME"), (String) detailDataMap.get("NEWTXAMT"));
                detailDataMap.put("TXN_TYPE", newFeeDtailMap.get("TXN_TYPE"));
                detailDataMap.put("NEWSENDERFEE_NW", newFeeDtailMap.get("NEWSENDERFEE_NW"));
                detailDataMap.put("NEWINFEE_NW", newFeeDtailMap.get("NEWINFEE_NW"));
                detailDataMap.put("NEWOUTFEE_NW", newFeeDtailMap.get("NEWOUTFEE_NW"));
                detailDataMap.put("NEWWOFEE_NW", newFeeDtailMap.get("NEWWOFEE_NW"));
                detailDataMap.put("NEWEACHFEE_NW", newFeeDtailMap.get("NEWEACHFEE_NW"));
                detailDataMap.put("NEWFEE_NW", newFeeDtailMap.get("NEWFEE_NW"));

                //如果FEE_TYPE為空 且結果為失敗或處理中 call sp 拿FEE_TYPE	  此功能都拿新的值
            } else if (StrUtils.isEmpty((String) detailDataMap.get("FEE_TYPE")) &&
                    ("失敗".equals((String) detailDataMap.get("RESP")) || "處理中".equals((String) detailDataMap.get("RESP")))) {
                Map newFeeDtailMap = onblocktabService.getNewFeeDetail(obizdate, (String) detailDataMap.get("TXN_NAME"), (String) detailDataMap.get("SENDERID")
                        , (String) detailDataMap.get("SENDERBANKID_NAME"), (String) detailDataMap.get("NEWTXAMT"));

                detailDataMap.put("TXN_TYPE", newFeeDtailMap.get("TXN_TYPE"));
                detailDataMap.put("NEWSENDERFEE_NW", newFeeDtailMap.get("NEWSENDERFEE") != null ? newFeeDtailMap.get("NEWSENDERFEE") : "0");
                detailDataMap.put("NEWINFEE_NW", newFeeDtailMap.get("NEWINFEE") != null ? newFeeDtailMap.get("NEWINFEE") : "0");
                detailDataMap.put("NEWOUTFEE_NW", newFeeDtailMap.get("NEWOUTFEE") != null ? newFeeDtailMap.get("NEWOUTFEE") : "0");
                detailDataMap.put("NEWWOFEE_NW", newFeeDtailMap.get("NEWWOFEE") != null ? newFeeDtailMap.get("NEWWOFEE") : "0");
                detailDataMap.put("NEWEACHFEE_NW", newFeeDtailMap.get("NEWEACHFEE") != null ? newFeeDtailMap.get("NEWEACHFEE") : "0");
                detailDataMap.put("NEWFEE_NW", newFeeDtailMap.get("NEWFEE") != null ? newFeeDtailMap.get("NEWFEE") : "0");

            }

            obtkNtrRs.setDetailData(detailDataMap);
            obtkNtrRs.setIsUndone("Y");

            // BeanUtils.copyProperties(obtkNtrRs, param);
            // request.setAttribute("ObtkNtrRs", obtkNtrRs);
        }
        return obtkNtrRs;
    }
}
