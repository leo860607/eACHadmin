package com.fstop.eachadmin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fstop.eachadmin.dto.TxErrDetailRq;
import com.fstop.eachadmin.dto.TxErrDetailRs;
import com.fstop.eachadmin.dto.TxErrRq;
import com.fstop.eachadmin.dto.TxErrRs;
import com.fstop.eachadmin.repository.OnBlockTabRepository;
import com.fstop.eachadmin.repository.PageQueryRepository;
import com.fstop.eachadmin.repository.VwOnBlockTabRepository;
import com.fstop.fcore.util.StrUtils;
import com.fstop.infra.entity.TX_ERR;
import com.fstop.infra.entity.TX_ERR_ONBLOCKTAB;
import com.fstop.infra.entity.VW_ONBLOCKTAB;

import util.DateTimeUtils;

@Service
public class TxErrService {
	@Autowired
	private OnBlockTabRepository onblocktab_Dao;
	@Autowired
	private VwOnBlockTabRepository vw_onblocktab_Dao;
	@Autowired
	private EachSysStatusTabService eachsysstatustab_bo;
	@Autowired
	private OnblocktabService onblocktab_bo;
	@Autowired
	private PageQueryRepository<TX_ERR> pageR;

	public TxErrRs pageSearch(TxErrRq param ,Page... page) {
		String pageNo = StrUtils.isEmpty(param.getPage()) ? "0" : param.getPage();
//		String pageSize = StrUtils.isEmpty(param.get("rows")) ?Arguments.getStringArg("PAGE.SIZE"):param.get("rows");
//		TODO
		Map rtnMap = new HashMap();

		List<TX_ERR> list = null;
		Page nextpage = null;
		TxErrRs result=null;
		try {
			list = new ArrayList<TX_ERR>();
			String condition = getConditionList(param).get(0);

			StringBuffer withTemp = new StringBuffer();
			withTemp.append("WITH TEMP AS ( ");
			withTemp.append("    SELECT ");
			withTemp.append("    (CASE COALESCE(A.RC2,'') WHEN '' THEN 1 ELSE 0 END) AS TYPE1_FLAG ");
			withTemp.append("    , (CASE COALESCE(A.RC1,'') WHEN '' THEN 1 ELSE 0 END) AS TYPE2_FLAG ");
			withTemp.append("    , (CASE COALESCE(A.RC3,'') WHEN '' THEN 1 ELSE 0 END) AS TYPE3_FLAG ");
			withTemp.append(
					"    , (CASE WHEN SENDERSTATUS = 2 AND RESULTSTATUS = 'P' THEN 1 ELSE 0 END) AS TYPE4_FLAG ");
			withTemp.append("    , (CASE COALESCE(A.RC4,'') WHEN '' THEN 1 ELSE 0 END) AS TYPE5_FLAG ");
			withTemp.append(
					"    , COALESCE(CONRESULTCODE,'') AS CONRESULTCODE, VARCHAR_FORMAT(A.NEWTXDT, 'YYYY/MM/DD HH24:MI:SS') AS TXDT, A.STAN, A.NEWTXAMT AS TXAMT ");
			withTemp.append("    , COALESCE(FLBATCHSEQ,'') AS FLBATCHSEQ, TXDATE ");
			withTemp.append(
					"    , (CASE WHEN (TIMESTAMP_FORMAT(A.DT_REQ_2, 'YYYYMMDDHH24MISSFF', 6) + 3 MINUTE) < CURRENT_TIMESTAMP THEN 1 ELSE 0 END) AS IS_OVER_3_MINS ");
			withTemp.append("    , COALESCE(EACHUSER.GETBKNAME(A.SENDERBANKID),'') AS SENDERBANKID ");
			withTemp.append(
					"    , (COALESCE(EACHUSER.GETBKNAME(A.OUTBANKID),'') || '<br/>' || A.OUTACCTNO) AS OUTBANKID ");
			withTemp.append(
					"    , (COALESCE(EACHUSER.GETBKNAME(A.INBANKID),'') || '<br/>' || A.INACCTNO) AS INBANKID ");
			withTemp.append("    , (A.SENDERID || '<br/>' || EACHUSER.GETCOMPANY_ABBR(A.SENDERID)) AS SENDERID ");
			withTemp.append(
					"    , (A.PCODE || COALESCE((SELECT '-' || EACH_TXN_NAME FROM EACH_TXN_CODE WHERE EACH_TXN_ID = A.PCODE), '') || '<br/>' || A.TXID || COALESCE((SELECT '-' || TXN_NAME FROM TXN_CODE WHERE TXN_ID = A.TXID), '')) AS PCODE ");
			withTemp.append("    FROM VW_ONBLOCKTAB AS A ");
			withTemp.append("    WHERE COALESCE(TRIM(A.DT_REQ_2),'') <> '' ");
			withTemp.append("    " + (StrUtils.isNotEmpty(condition) ? "AND " + condition : ""));
			withTemp.append("), TEMP_2 AS ( ");
			withTemp.append("    SELECT ( ");
			withTemp.append("        CASE WHEN IS_OVER_3_MINS = 1 AND TYPE1_FLAG = 1 THEN '01' ");
			withTemp.append("        WHEN IS_OVER_3_MINS = 1 AND TYPE2_FLAG = 1 THEN '02' ");
			withTemp.append("        WHEN CONRESULTCODE <> '' AND TYPE3_FLAG = 1 THEN '03' ");
			withTemp.append("        WHEN FLBATCHSEQ <> '' AND TYPE4_FLAG = 1 THEN '04' ");
			withTemp.append("        WHEN TYPE3_FLAG = 0 AND TYPE5_FLAG = 1 THEN '05' ");
			withTemp.append("        ELSE NULL END ");
			withTemp.append("    ) AS ERR_TYPE, ");
			withTemp.append("    TXDATE, TXDT, STAN, SENDERBANKID, OUTBANKID, INBANKID, SENDERID, PCODE, TXAMT ");
			withTemp.append("    FROM TEMP ");
			withTemp.append("    WHERE (TYPE1_FLAG + TYPE2_FLAG + TYPE3_FLAG + TYPE4_FLAG + TYPE5_FLAG) <> 0 ");
			withTemp.append(") ");

			// 總計與總數的語法
			StringBuffer countAndSumQuery = new StringBuffer();
			countAndSumQuery.append(withTemp.toString());
			countAndSumQuery.append("SELECT COUNT(*) AS NUM, SUM(TXAMT) AS TXAMT ");
			countAndSumQuery.append("FROM TEMP_2 ");
			countAndSumQuery.append("WHERE ERR_TYPE IS NOT NULL ");
			String countAndSumCols[] = { "NUM", "TXAMT" };
			List<TX_ERR_ONBLOCKTAB> countAndSumList = vw_onblocktab_Dao.dataSum(countAndSumQuery.toString(),
					countAndSumCols, TX_ERR_ONBLOCKTAB.class);
			rtnMap.put("dataSumList", countAndSumList);

			StringBuffer sql = new StringBuffer();
			sql.append(withTemp.toString());
			sql.append("SELECT * FROM ( ");
			sql.append("    SELECT ROWNUMBER() OVER(");
			if (StrUtils.isNotEmpty(param.getSidx())) {
				sql.append("ORDER BY " + param.getSidx() + " " + param.getSord());
			}
			sql.append(") AS ROWNUMBER, C.* ");
			sql.append("    FROM TEMP_2 AS C ");
			sql.append("    WHERE ERR_TYPE IS NOT NULL ) ");
			// System.out.println("### SQL >> " + sql);

			PageRequest pageable = PageRequest.of(Integer.parseInt(pageNo), 5);

			nextpage = pageR.getPageData(pageable,countAndSumQuery.toString(), sql.toString(), TX_ERR.class);
			result=new TxErrRs(countAndSumList, nextpage.getTotalPages(),Integer.toString(nextpage.getPageable().getPageNumber()), nextpage.getTotalElements(), nextpage.getContent());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	public List<String> getConditionList(TxErrRq param) {
		List<String> conditionList = new ArrayList<String>();

		List<String> conditions = new ArrayList<String>();

		String bizdate = "";
		if (StrUtils.isNotEmpty(param.getBIZDATE().trim())) {
			bizdate = DateTimeUtils.convertDate(param.getBIZDATE().trim(), "yyyyMMdd", "yyyyMMdd");
			conditions.add(" A.BIZDATE = '" + bizdate + "' ");
		}

		String clearingPhase = "";
		if (StrUtils.isNotEmpty(param.getCLEARINGPHASE().trim())
				&& !param.getCLEARINGPHASE().equals("all")) {
			clearingPhase = param.getCLEARINGPHASE().trim();
			conditions.add(" A.CLEARINGPHASE = '" + clearingPhase + "' ");
		}

		conditionList.add(combine(conditions));
		return conditionList;
	}

	// 檢視明細
	public VW_ONBLOCKTAB searchByPk(String txdate, String stan) {
		VW_ONBLOCKTAB po = null;
		Map rtnMap = new HashMap();
		String condition = "";
		try {
			txdate = StrUtils.isEmpty(txdate) ? "" : txdate;
			stan = StrUtils.isEmpty(stan) ? "" : stan;

			StringBuffer sql = new StringBuffer();
			sql.append(
					"SELECT TRANSLATE('abcd-ef-gh', VARCHAR(A.TXDATE), 'abcdefgh') AS TXDATE,VARCHAR(A.STAN) AS STAN,VARCHAR_FORMAT(NEWTXDT, 'YYYY-MM-DD HH24:MI:SS') AS TXDT,A.PCODE || '-' || ETC.EACH_TXN_NAME AS PCODE_DESC, ");
			sql.append(
					"A.SENDERBANK || '-' || (SELECT B.BRBK_NAME FROM BANK_BRANCH B WHERE A.SENDERBANK = B.BRBK_ID) AS SENDERBANK_NAME, ");
			sql.append(
					"A.RECEIVERBANK || '-' || (SELECT B.BRBK_NAME FROM BANK_BRANCH B WHERE A.RECEIVERBANK = B.BRBK_ID) AS RECEIVERBANK_NAME, ");
			sql.append(
					"(SELECT B.ERR_DESC FROM TXN_ERROR_CODE B WHERE A.CONRESULTCODE = B.ERROR_ID) AS CONRESULTCODE_DESC,A.ACCTCODE, ");
			sql.append(
					"A.SENDERCLEARING || '-' || (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE A.SENDERCLEARING = B.BGBK_ID) AS SENDERCLEARING_NAME, ");
			sql.append(
					"A.INCLEARING || '-' || (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE A.INCLEARING = B.BGBK_ID) AS INCLEARING_NAME, ");
			sql.append(
					"A.OUTCLEARING || '-' || (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE A.OUTCLEARING = B.BGBK_ID) AS OUTCLEARING_NAME, ");
			sql.append(
					"A.SENDERACQUIRE || '-' || (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE A.SENDERACQUIRE = B.BGBK_ID) AS SENDERACQUIRE_NAME, ");
			sql.append(
					"A.INACQUIRE || '-' || (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE A.INACQUIRE = B.BGBK_ID) AS INACQUIRE_NAME, ");
			sql.append(
					"A.OUTACQUIRE || '-' || (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE A.OUTACQUIRE = B.BGBK_ID) AS OUTACQUIRE_NAME, ");
			sql.append(
					"A.SENDERHEAD || '-' || (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE A.SENDERHEAD = B.BGBK_ID) AS SENDERHEAD_NAME, ");
			sql.append(
					"A.INHEAD || '-' || (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE A.INHEAD = B.BGBK_ID) AS INHEAD_NAME, ");
			sql.append(
					"A.OUTHEAD || '-' || (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE A.OUTHEAD = B.BGBK_ID) AS OUTHEAD_NAME, ");
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.NEWSENDERFEE ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.NEWSENDERFEE ELSE (CASE A.ACCTCODE WHEN '0' THEN A.NEWSENDERFEE ELSE 0 END) END) END) AS NEWSENDERFEE, ");
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.NEWINFEE ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.NEWINFEE ELSE (CASE A.ACCTCODE WHEN '0' THEN A.NEWINFEE ELSE 0 END) END) END) AS NEWINFEE, ");
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.NEWOUTFEE ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.NEWOUTFEE ELSE (CASE A.ACCTCODE WHEN '0' THEN A.NEWOUTFEE ELSE 0 END) END) END) AS NEWOUTFEE, ");
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.NEWEACHFEE ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.NEWEACHFEE ELSE (CASE A.ACCTCODE WHEN '0' THEN A.NEWEACHFEE ELSE 0 END) END) END) AS NEWEACHFEE, ");
			// 20200824新增start
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.SENDERFEE_NW ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.SENDERFEE_NW ELSE (CASE A.ACCTCODE WHEN '0' THEN A.SENDERFEE_NW ELSE 0 END) END) END) AS NEWSENDERFEE_NW, ");
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.INFEE_NW ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.INFEE_NW ELSE (CASE A.ACCTCODE WHEN '0' THEN A.INFEE_NW ELSE 0 END) END) END) AS NEWINFEE_NW, ");
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.OUTFEE_NW ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.OUTFEE_NW ELSE (CASE A.ACCTCODE WHEN '0' THEN A.OUTFEE_NW ELSE 0 END) END) END) AS NEWOUTFEE_NW, ");
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.WOFEE_NW ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.WOFEE_NW ELSE (CASE A.ACCTCODE WHEN '0' THEN A.WOFEE_NW ELSE 0 END) END) END) AS NEWWOFEE_NW, ");
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.EACHFEE_NW ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.EACHFEE_NW ELSE (CASE A.ACCTCODE WHEN '0' THEN A.EACHFEE_NW ELSE 0 END) END) END) AS NEWEACHFEE_NW, ");
			sql.append(
					" (CASE CAST(A.FEE_TYPE AS VARCHAR(1)) WHEN ' ' THEN '' ELSE CAST(A.FEE_TYPE AS VARCHAR(1)) END) AS FEE_TYPE ,");
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.HANDLECHARGE_NW ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.HANDLECHARGE_NW ELSE (CASE A.ACCTCODE WHEN '0' THEN A.HANDLECHARGE_NW ELSE 0 END) END) END) AS NEWFEE_NW, ");
			// 20200824新增end
			// 20220321新增
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.NEWEXTENDFEE ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.NEWEXTENDFEE ELSE (CASE A.ACCTCODE WHEN '0' THEN A.NEWEXTENDFEE ELSE 0 END) END) END) AS NEWEXTENDFEE, ");
			sql.append(" A.EXTENDFEE AS EXTENDFEE , ");
			// 20220321新增end
			sql.append(
					"A.SENDERID,A.RECEIVERID,TRANSLATE('abcd-ef-gh',A.REFUNDDEADLINE,'abcdefgh') AS REFUNDDEADLINE, ");
			sql.append("A.TXID || '-' || TC.TXN_NAME AS TXN_NAME, A.NEWTXAMT AS NEWTXAMT, A.SENDERSTATUS, ");
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'R' THEN 0 WHEN 'A' THEN A.NEWFEE ELSE (CASE COALESCE(OP.RESULTCODE,'00') WHEN '00' THEN A.NEWFEE ELSE (CASE A.ACCTCODE WHEN '0' THEN A.NEWFEE ELSE 0 END) END) END) AS NEWFEE, ");
			sql.append(
					"A.SENDERBANKID || '-' || (SELECT B.BRBK_NAME FROM BANK_BRANCH B WHERE A.SENDERBANKID = B.BRBK_ID) AS SENDERBANKID_NAME, ");
			sql.append(
					"A.INBANKID || '-' || (SELECT B.BRBK_NAME FROM BANK_BRANCH B WHERE A.INBANKID = B.BRBK_ID) AS INBANKID_NAME, ");
			sql.append(
					"A.OUTBANKID || '-' || (SELECT B.BRBK_NAME FROM BANK_BRANCH B WHERE A.OUTBANKID = B.BRBK_ID) AS OUTBANKID_NAME, ");
			sql.append(
					"TRANSLATE('abcd-ef-gh', A.BIZDATE, 'abcdefgh') AS BIZDATE,TRANSLATE('abcd-ef-gh ij:kl:mn', A.EACHDT, 'abcdefghijklmn') AS EACHDT,A.CLEARINGPHASE,A.INACCTNO,A.OUTACCTNO,A.INID,A.OUTID,A.ACCTBAL,A.AVAILBAL,A.CHECKTYPE,A.MERCHANTID,A.ORDERNO,A.TRMLID,A.TRMLCHECK,A.TRMLMCC,A.BANKRSPMSG,A.RRN, ");
			// 20150319 by 李建利 「交易資料查詢」、「未完成交易資料查詢」的檢視明細的「交易結果」顯示最初交易結果即可
			// sql.append("(CASE A.NEWRESULT WHEN 'A' THEN '成功' WHEN 'R' THEN '失敗' ELSE
			// COALESCE((CASE OP.RESULTCODE WHEN '01' THEN '失敗' ELSE '成功' END),(CASE
			// A.SENDERSTATUS WHEN '1' THEN '處理中' ELSE '未完成' END)) END) AS RESP, ");
			sql.append(
					"(CASE A.RESULTSTATUS WHEN 'A' THEN '成功' WHEN 'R' THEN '失敗' ELSE (CASE A.SENDERSTATUS WHEN '1' THEN '處理中' ELSE '未完成' END) END) AS RESP, ");
			sql.append("RC1 || '-' || (SELECT B.ERR_DESC FROM TXN_ERROR_CODE B WHERE A.RC1=B.ERROR_ID) ERR_DESC1, ");
			sql.append("RC2 || '-' || (SELECT B.ERR_DESC FROM TXN_ERROR_CODE B WHERE A.RC2=B.ERROR_ID) ERR_DESC2, ");
			sql.append("RC3 || '-' || (SELECT B.ERR_DESC FROM TXN_ERROR_CODE B WHERE A.RC3=B.ERROR_ID) ERR_DESC3, ");
			sql.append("RC4 || '-' || (SELECT B.ERR_DESC FROM TXN_ERROR_CODE B WHERE A.RC4=B.ERROR_ID) ERR_DESC4, ");
			sql.append("RC5 || '-' || (SELECT B.ERR_DESC FROM TXN_ERROR_CODE B WHERE A.RC5=B.ERROR_ID) ERR_DESC5, ");
			sql.append(
					"RC6 || '-' || (SELECT B.ERR_DESC FROM TXN_ERROR_CODE B WHERE A.RC6=B.ERROR_ID) ERR_DESC6, TRANSLATE('abcd-ef-gh ij:kl:mn.opq',UPDATEDT,'abcdefghijklmnopq') AS UPDATEDT, ");
			sql.append(
					"OBA.USERNO, A.CONMEMO, OBA.SENDERDATA, OBA.COMPANYID, A.MERCHANTID, A.ORDERNO, A.TRMLID, OBA.OTXAMT, COALESCE(TRANSLATE('abcd-ef-gh',OBA.OTXDATE,'abcdefgh'),'') AS OTXDATE, OBA.OTRMLID, OBA.OMERCHANTID, OBA.OORDERNO, OBA.PAN, OBA.OPAN, OBA.PSN, OBA.OPSN, ");
			sql.append("COALESCE(TRANSLATE('abcd-ef-gh',VARCHAR(OP.BIZDATE),'abcdefgh'),'') AS NEWBIZDATE, ");
			sql.append("COALESCE(OP.CLEARINGPHASE,'') AS NEWCLRPHASE, COALESCE(OP.RESULTCODE,'eACH') AS RESULTCODE ");
			sql.append("FROM VW_ONBLOCKTAB A LEFT JOIN EACH_TXN_CODE ETC ON A.PCODE = ETC.EACH_TXN_ID ");
			sql.append("LEFT JOIN TXN_CODE TC ON A.TXID = TC.TXN_ID ");
			sql.append("LEFT JOIN ONBLOCKAPPENDTAB OBA ON A.TXDATE = OBA.TXDATE AND A.STAN = OBA.STAN ");
			sql.append("LEFT JOIN ONPENDINGTAB OP ON OP.OTXDATE = A.TXDATE AND OP.OSTAN = A.STAN ");
			condition += "WHERE ";
			if (!txdate.equals("")) {
				condition += " A.TXDATE='" + txdate + "'";
				condition += " AND ";
			}
			if (!stan.equals("")) {
				condition += " A.STAN='" + stan + "'";
			}
			sql.append(condition);
			po = vw_onblocktab_Dao.getDetail(sql.toString(), txdate, stan);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return po;
	}

	public String combine(List<String> conditions) {
		String conStr = "";
		for (int i = 0; i < conditions.size(); i++) {
			conStr += conditions.get(i);
			if (i < conditions.size() - 1) {
				conStr += " AND ";
			}
		}
		return conStr;
	}



//檢視明細
	public TxErrDetailRs showDetail(TxErrDetailRq param) {

		TxErrDetailRs txerrDetailRs = new TxErrDetailRs();
		Map TxerrDetailRs = new HashMap();
//		String ac_key = StrUtils.isEmpty(param.getAc_key()) ? "" : param.getAc_key();
//		String target = StrUtils.isEmpty(param.getTarget()) ? "search" : param.getTarget();
//		param.setTarget(target);
		String txdate = param.getTXDATE();
		String stan = param.getSTAN();

//		if (ac_key.equalsIgnoreCase("edit")) {
			VW_ONBLOCKTAB detailDataMap = searchByPk(txdate, stan);
			String bizdate = eachsysstatustab_bo.getBusinessDateII();

			// 20220321新增FOR EXTENDFEE 位數轉換
			if (detailDataMap.getEXTENDFEE() != null) {
				BigDecimal orgNewExtendFee =  detailDataMap.getEXTENDFEE();
				// 去逗號除100 1,000 > 1000/100 = 10
				String strOrgNewExtendFee = orgNewExtendFee.toString();
				double realNewExtendFee = Double.parseDouble(strOrgNewExtendFee.replace(",", "")) / 100;
				TxerrDetailRs.put("NEWEXTENDFEE", String.valueOf(realNewExtendFee));
			} else {
				// 如果是null 顯示空字串
				TxerrDetailRs.put("NEWEXTENDFEE", "");
			}

			// 如果FEE_TYPE有值 且結果為成功或未完成 新版手續直接取後面欄位
			if (StrUtils.isNotEmpty((String) detailDataMap.getFEE_TYPE())
					&& ("成功".equals((String) detailDataMap.getRESP())
							|| "未完成".equals((String) detailDataMap.getRESP()))) {
				switch ((String) detailDataMap.getFEE_TYPE()) {
				case "A":
					TxerrDetailRs.put("TXN_TYPE", "固定");
					break;
				case "B":
					TxerrDetailRs.put("TXN_TYPE", "外加");
					break;
				case "C":
					TxerrDetailRs.put("TXN_TYPE", "百分比");
					break;
				case "D":
					TxerrDetailRs.put("TXN_TYPE", "級距");
					break;
				}

				// 如果FEE_TYPE有值 且結果為失敗或處理中 新版手續跟舊的一樣
			} else if (StrUtils.isNotEmpty((String) detailDataMap.getFEE_TYPE())
					&& ("失敗".equals((String) detailDataMap.getRESP())
							|| "處理中".equals((String) detailDataMap.getRESP()))) {
				switch ((String) detailDataMap.getFEE_TYPE()) {
				case "A":
					TxerrDetailRs.put("TXN_TYPE", "固定");
					break;
				case "B":
					TxerrDetailRs.put("TXN_TYPE", "外加");
					break;
				case "C":
					TxerrDetailRs.put("TXN_TYPE", "百分比");
					break;
				case "D":
					TxerrDetailRs.put("TXN_TYPE", "級距");
					break;
				}

				TxerrDetailRs.put("NEWSENDERFEE_NW",
						detailDataMap.getNEWSENDERFEE() != null ? detailDataMap.getNEWSENDERFEE() : "0");
				TxerrDetailRs.put("NEWINFEE_NW",
						detailDataMap.getNEWINFEE() != null ? detailDataMap.getNEWINFEE() : "0");
				TxerrDetailRs.put("NEWOUTFEE_NW",
						detailDataMap.getNEWOUTFEE()  != null ? detailDataMap.getNEWOUTFEE() : "0");
				TxerrDetailRs.put("NEWWOFEE_NW",
						detailDataMap.getNEWWOFEE() != null ? detailDataMap.getNEWWOFEE() : "0");
				TxerrDetailRs.put("NEWEACHFEE_NW",
						detailDataMap.getNEWEACHFEE() != null ? detailDataMap.getNEWEACHFEE() : "0");
				TxerrDetailRs.put("NEWFEE_NW", detailDataMap.getNEWFEE()  != null ? detailDataMap.getNEWFEE() : "0");

				// 如果FEE_TYPE為空 且結果為成功或未完成 新版手續call sp
			} else if (StrUtils.isEmpty((String) detailDataMap.getFEE_TYPE())
					&& ("成功".equals((String) detailDataMap.getRESP())
							|| "未完成".equals((String) detailDataMap.getRESP()))) {
				Map newFeeDtailMap = onblocktab_bo.getNewFeeDetail(bizdate, (String) detailDataMap.getTXN_NAME(),
						(String) detailDataMap.getSENDERID(), (String) detailDataMap.getSENDERBANKID_NAME(),
						(String) detailDataMap.getNEWTXAMT());
				TxerrDetailRs.put("TXN_TYPE", newFeeDtailMap.get("TXN_TYPE"));
				TxerrDetailRs.put("NEWSENDERFEE_NW", newFeeDtailMap.get("NEWSENDERFEE_NW"));
				TxerrDetailRs.put("NEWINFEE_NW", newFeeDtailMap.get("NEWINFEE_NW"));
				TxerrDetailRs.put("NEWOUTFEE_NW", newFeeDtailMap.get("NEWOUTFEE_NW"));
				TxerrDetailRs.put("NEWWOFEE_NW", newFeeDtailMap.get("NEWWOFEE_NW"));
				TxerrDetailRs.put("NEWEACHFEE_NW", newFeeDtailMap.get("NEWEACHFEE_NW"));
				TxerrDetailRs.put("NEWFEE_NW", newFeeDtailMap.get("NEWFEE_NW"));

				// 如果FEE_TYPE為空 且結果為失敗或處理中 新版手續跟舊的一樣
			} else if (StrUtils.isEmpty((String) detailDataMap.getFEE_TYPE())
					&& ("失敗".equals((String) detailDataMap.getRESP())
							|| "處理中".equals((String) detailDataMap.getRESP()))) {
				Map newFeeDtailMap = onblocktab_bo.getNewFeeDetail(bizdate, (String) detailDataMap.getTXN_NAME(),
						(String) detailDataMap.getSENDERID(), (String) detailDataMap.getSENDERBANKID_NAME(),
						(String) detailDataMap.getNEWTXAMT());
				TxerrDetailRs.put("TXN_TYPE", detailDataMap.getTXN_TYPE());
			TxerrDetailRs.put("NEWSENDERFEE_NW",
						detailDataMap.getNEWSENDERFEE() != null ? detailDataMap.getNEWSENDERFEE() : "0");
			TxerrDetailRs.put("NEWINFEE_NW",
						detailDataMap.getNEWINFEE() != null ? detailDataMap.getNEWINFEE() : "0");
			TxerrDetailRs.put("NEWOUTFEE_NW",
						detailDataMap.getNEWOUTFEE() != null ? detailDataMap.getNEWOUTFEE() : "0");
			TxerrDetailRs.put("NEWWOFEE_NW",
						detailDataMap.getNEWWOFEE() != null ? detailDataMap.getNEWWOFEE() : "0");
			TxerrDetailRs.put("NEWEACHFEE_NW",
						detailDataMap.getNEWEACHFEE() != null ? detailDataMap.getNEWEACHFEE() : "0");
			TxerrDetailRs.put("NEWFEE_NW", detailDataMap.getNEWFEE() != null ? detailDataMap.getNEWFEE() : "0");

			}
//			txerrDetailRs.setDetailMapData(detailDataMap);
//			txerrDetailRs.setDetailData(TxerrDetailRs);
			
//		}
			
			String a = detailDataMap.getTXDATE();
			String b = detailDataMap.getSTAN();
			String c = detailDataMap.getTXDT();
			String d = detailDataMap.getPCODE_DESC();
			String e = detailDataMap.getSENDERBANK_NAME();
			String f = detailDataMap.getRECEIVERBANK_NAME();
//			String g = detailDataMap.getCONRESULTCODE_DESC();
			String h = detailDataMap.getACCTCODE();
//			String i = detailDataMap.getSENDERCLEARING_NAME();
//			String j = detailDataMap.getINCLEARING_NAME();
//			String k = detailDataMap.getOUTCLEARING_NAME();
//			String l = detailDataMap.getSENDERACQUIRE_NAME();
//			String m = detailDataMap.getINACQUIRE_NAME();
//			String n = detailDataMap.getOUTACQUIRE_NAME();
//			String o = detailDataMap.getWOACQUIRE_NAME();
//			String p = detailDataMap.getSENDERHEAD_NAME();
//			String q = detailDataMap.getINHEAD_NAME();
//			String r = detailDataMap.getOUTHEAD_NAME();
//			String s = detailDataMap.getWOHEAD_NAME();
			String t = detailDataMap.getNEWSENDERFEE();
			String u = detailDataMap.getNEWINFEE();
			String v = detailDataMap.getNEWOUTFEE();
//			String w = detailDataMap.getNEWWOFEE();
			String x = detailDataMap.getNEWEACHFEE();
			String y = detailDataMap.getNEWEXTENDFEE();
			String z = detailDataMap.getSENDERID();
			String aa = detailDataMap.getTXN_NAME();
			String bb = detailDataMap.getNEWTXAMT();
//			String cc = detailDataMap.getSENDERSTATUS();
			String dd = detailDataMap.getNEWFEE();
			String ee = detailDataMap.getSENDERBANKID_NAME();
			String ff = detailDataMap.getINBANKID_NAME();
			String gg = detailDataMap.getOUTBANKID_NAME();
//			String hh = detailDataMap.getWOBANKID_NAME();
			String ii = detailDataMap.getBIZDATE();
			String jj = detailDataMap.getEACHDT();
			String kk = detailDataMap.getCLEARINGPHASE();
			String ll = detailDataMap.getINACCTNO();
			String mm = detailDataMap.getOUTACCTNO();
			String nn = detailDataMap.getINID();
			String oo = detailDataMap.getRESP();
			String pp = detailDataMap.getERR_DESC1();
			String qq = detailDataMap.getERR_DESC2();
			String rr = detailDataMap.getUPDATEDT();
//			String ss = detailDataMap.getBILLTYPE();
//			String tt = detailDataMap.getBILLDATA();
//			String uu = detailDataMap.getCHARGETYPE();
//			String vv = detailDataMap.getTOLLID();
//			String ww = detailDataMap.getBILLFLAG();
//			String xx = detailDataMap.getCHECKDATA();
//			String yy = detailDataMap.getPFCLASS();
			String zz = detailDataMap.getSENDERDATA();
			String aaa = detailDataMap.getRESULTCODE();
//			String bbb = detailDataMap.getTXN_TYPE();
			
			
			txerrDetailRs.setTXDATE(a);
			txerrDetailRs.setSTAN(b);
			txerrDetailRs.setTXDT(c);
			txerrDetailRs.setPCODE_DESC(d);
			txerrDetailRs.setSENDERBANK_NAME(e);
			txerrDetailRs.setRECEIVERBANK_NAME(f);
//			txerrDetailRs.setCONRESULTCODE_DESC(g);
			txerrDetailRs.setACCTCODE(h);
//			txerrDetailRs.setSENDERCLEARING_NAME(i);
//			txerrDetailRs.setINCLEARING_NAME(j);
//			txerrDetailRs.setOUTCLEARING_NAME(k);
//			txerrDetailRs.setSENDERACQUIRE_NAME(l);
//			txerrDetailRs.setINACQUIRE_NAME(m);
//			txerrDetailRs.setOUTACQUIRE_NAME(n);
//			txerrDetailRs.setWOACQUIRE_NAME(o);
//			txerrDetailRs.setSENDERHEAD_NAME(p);
//			txerrDetailRs.setINHEAD_NAME(q);
//			txerrDetailRs.setOUTHEAD_NAME(r);
//			txerrDetailRs.setWOHEAD_NAME(s);
			txerrDetailRs.setNEWSENDERFEE(t);
			txerrDetailRs.setNEWINFEE(u);
			txerrDetailRs.setNEWOUTFEE(v);
//			txerrDetailRs.setNEWWOFEE(w);
			txerrDetailRs.setNEWEACHFEE(x);
			txerrDetailRs.setNEWEXTENDFEE(y);
			txerrDetailRs.setSENDERID(z);
			txerrDetailRs.setTXN_NAME(aa);
			txerrDetailRs.setNEWTXAMT(bb);
//			txerrDetailRs.setSENDERSTATUS(cc);
			txerrDetailRs.setNEWFEE(dd);
			txerrDetailRs.setSENDERBANKID_NAME(ee);
			txerrDetailRs.setINBANKID_NAME(ff);
			txerrDetailRs.setOUTBANKID_NAME(gg);
//			txerrDetailRs.setWOBANKID_NAME(hh);
			txerrDetailRs.setBIZDATE(ii);
			txerrDetailRs.setEACHDT(jj);
			txerrDetailRs.setCLEARINGPHASE(kk);
			txerrDetailRs.setINACCTNO(ll);
			txerrDetailRs.setOUTACCTNO(mm);
			txerrDetailRs.setINID(nn);
			txerrDetailRs.setRESP(oo);
			txerrDetailRs.setERR_DESC1(pp);
			txerrDetailRs.setERR_DESC2(qq);
			txerrDetailRs.setUPDATEDT(rr);
//			txerrDetailRs.setBILLTYPE(ss);
//			txerrDetailRs.setBILLDATA(tt);
//			txerrDetailRs.setCHARGETYPE(uu);
//			txerrDetailRs.setTOLLID(vv);
//			txerrDetailRs.setBILLFLAG(ww);
//			txerrDetailRs.setCHECKDATA(xx);
//			txerrDetailRs.setPFCLASS(yy);
			txerrDetailRs.setSENDERDATA(zz);
			txerrDetailRs.setRESULTCODE(aaa);
//			txerrDetailRs.setRESULTCODE(bbb);
		return txerrDetailRs;
	}
}