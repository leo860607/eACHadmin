package com.fstop.eachadmin.service;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.Optional;

import javax.xml.bind.JAXBException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstop.eachadmin.dto.PageSearchRq;
import com.fstop.eachadmin.dto.PageSearchRs;
import com.fstop.eachadmin.repository.BankGroupRepository;
import com.fstop.eachadmin.repository.BusinessTypeRepository;
import com.fstop.eachadmin.repository.CommonSpringRepository;
import com.fstop.eachadmin.repository.OnPendingTabRepository;
import com.fstop.fcore.util.Page;
import com.fstop.infra.entity.BANK_GROUP;
import com.fstop.infra.entity.BANK_OPBK;
import com.fstop.infra.entity.BUSINESS_TYPE;

import com.fstop.eachadmin.dto.UndoneSendRq;
import com.fstop.eachadmin.dto.UndoneSendRs;

import com.fstop.infra.entity.ONPENDINGTAB;
import com.fstop.infra.entity.ONPENDINGTAB_PK;

import com.fstop.infra.entity.UNDONE_TXDATA;

import util.DateTimeUtils;

import util.StrUtils;
import util.messageConverter;
import util.socketPackage;
import util.zDateHandler;
import util.socketPackage.Body;
import util.socketPackage.Header;

@Service
public class UndoneService {

	@Autowired
	private CommonSpringRepository commonSpringRepository;

	@Autowired
	private BusinessTypeRepository businessTypeRepository;

	@Autowired
	private OnPendingTabRepository OnPendingTabR;

	@Autowired
	private BankGroupRepository bankGroupRepository;

	// 操作行------------------------------------------------------
	public List<Map<String, String>> getOpbkList() {

		// String sql = "SELECT COALESCE( OP.OPBK_ID,'' ) AS OPBK_ID , COALESCE(
		// BG.BGBK_NAME ,'' ) AS OPBK_NAME FROM ( SELECT DISTINCT OPBK_ID FROM
		// EACHUSER.BANK_OPBK ) AS OP JOIN ( SELECT BGBK_ID, BGBK_NAME FROM BANK_GROUP
		// WHERE BGBK_ATTR <> '6' ) AS BG ON OP.OPBK_ID = BG.BGBK_ID ORDER BY OP.OPBK_ID
		// ";
		// List<BANK_OPBK> list = business_type_Dao.getAllOpbkList(sql);
		// TODO:
		// jdbc 還沒有好, 暫時先用
		List<BANK_GROUP> list = bankGroupRepository.getBgbkIdList_Not_5_And_6();
		List<Map<String, String>> beanList = new LinkedList<Map<String, String>>();

		Map<String, String> bean = null;

		for (BANK_GROUP po : list) {
			po.getOPBK_ID();
			String k = (String) po.getOPBK_ID() + " - " + po.getOPBK_NAME();
			String v = (String) po.getOPBK_ID();
			bean = new HashMap<String, String>();
			bean.put(k, v);
			beanList.add(bean);
		}
		System.out.println("beanList>>" + beanList);
		return beanList;
	}

	// 業務行------------------------------------------------------
	public List<Map<String, String>> getBsTypeIdList() {

		// String sql = "FROM tw.org.twntch.po.BUSINESS_TYPE ORDER BY BUSINESS_TYPE_ID";
		// List<BUSINESS_TYPE> list = business_type_Dao.find(sql);
		// TODO:
		// jdbc 還沒有好, 暫時先用
		List<BUSINESS_TYPE> list = (List<BUSINESS_TYPE>) businessTypeRepository.testFunction();
		List<Map<String, String>> beanList = new LinkedList<Map<String, String>>();

		Map<String, String> bean = null;

		for (BUSINESS_TYPE po : list) {
			po.getBUSINESS_TYPE_ID();
			String k = (String) po.getBUSINESS_TYPE_ID() + " - " + po.getBUSINESS_TYPE_NAME();
			String v = (String) po.getBUSINESS_TYPE_ID();
			bean = new HashMap<String, String>();
			bean.put(k, v);
			beanList.add(bean);
		}
		System.out.println("beanList>>" + beanList);
		return beanList;
	}

	// 表單查詢產出------------------------------------------------------
	public PageSearchRs<UNDONE_TXDATA> pageSearch(PageSearchRq param) {
		List<String> conditions_1 = new ArrayList<String>();
		List<String> conditions_2 = new ArrayList<String>();
		// 是否包含整批資料("N"表示不過濾)
		String filter_bat = param.get("FILTER_BAT") == null ? "N" : "Y";

		/* 20150210 HUANGPU 改以營業日(BIZDATE)查詢資料，非交易日期(TXDATE) */
		String startDate = "";
		if (StrUtils.isNotEmpty(param.get("START_DATE").trim())) {
			startDate = param.get("START_DATE").trim();
			conditions_1.add(" BIZDATE >= '" + DateTimeUtils.convertDate(startDate, "yyyyMMdd", "yyyyMMdd") + "' ");
		}

		String endDate = "";
		if (StrUtils.isNotEmpty(param.get("END_DATE").trim())) {
			endDate = param.get("END_DATE").trim();
			conditions_1.add(" BIZDATE <= '" + DateTimeUtils.convertDate(endDate, "yyyyMMdd", "yyyyMMdd") + "' ");
		}

		String clearingphase = "";
		if (StrUtils.isNotEmpty(param.get("CLEARINGPHASE").trim())
				&& !param.get("CLEARINGPHASE").trim().equals("all")) {
			clearingphase = param.get("CLEARINGPHASE").trim();
			conditions_1.add(" CLEARINGPHASE = '" + clearingphase + "' ");
		}

		String bgbkId = "";
		if (StrUtils.isNotEmpty(param.get("BGBK_ID").trim()) && !param.get("BGBK_ID").trim().equals("all")) {
			bgbkId = param.get("BGBK_ID").trim();
			conditions_1.add(
					" (SENDERHEAD = '" + bgbkId + "' OR OUTHEAD = '" + bgbkId + "' OR INHEAD = '" + bgbkId + "') ");
		}

		String businessTypeId = "";
		if (StrUtils.isNotEmpty(param.get("BUSINESS_TYPE_ID").trim())
				&& !param.get("BUSINESS_TYPE_ID").trim().equals("all")) {
			businessTypeId = param.get("BUSINESS_TYPE_ID").trim();
			conditions_1.add(" BUSINESS_TYPE_ID = '" + businessTypeId + "' ");
		}

		String ostan = "";
		if (StrUtils.isNotEmpty(param.get("OSTAN")) && !param.get("OSTAN").equals("all")) {
			ostan = param.get("OSTAN");
			conditions_1.add(" STAN = '" + ostan + "' ");
		}

		if (StrUtils.isNotEmpty(param.get("RESULTCODE")) && !param.get("RESULTCODE").equals("all")) {
			if ("A".equals(param.get("RESULTCODE"))) {
				conditions_1.add(" RESULTCODE IS NOT NULL ");
			} else if ("P".equals(param.get("RESULTCODE"))) {
				conditions_1.add(" RESULTCODE IS NULL ");
			}
		}

		String opbkId = "";
		if (StrUtils.isNotEmpty(param.get("OPBK_ID").trim()) && !param.get("OPBK_ID").trim().equals("all")) {
			opbkId = param.get("OPBK_ID").trim();
			if (filter_bat.equals("Y")) {
				conditions_2.addAll(conditions_1);
				conditions_2.add(" COALESCE(FLBIZDATE,'') <> ''  ");
				conditions_2.add(
						" ((INACQUIRE = '" + opbkId + "' AND substr(COALESCE(PCODE,''),4) = '2' ) OR (OUTACQUIRE = '"
								+ opbkId + "' AND substr(COALESCE(PCODE,''),4) = '1')) ");
				conditions_1.add(" COALESCE(FLBIZDATE,'') = ''  ");
				conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId + "' OR INACQUIRE = '"
						+ opbkId + "') ");
			} else {
				conditions_1.add(" (SENDERACQUIRE = '" + opbkId + "' OR OUTACQUIRE = '" + opbkId + "' OR INACQUIRE = '"
						+ opbkId + "') ");
			}
		} else {
			if (filter_bat.equals("Y")) {
				conditions_2.addAll(conditions_1);
				conditions_2.add(" COALESCE(FLBIZDATE,'') <> ''  ");
				conditions_1.add(" COALESCE(FLBIZDATE,'') = ''  ");
			}
		}

		int pageNo = StrUtils.isEmpty(param.get("page")) ? 0 : Integer.valueOf(param.get("page"));
		int pageSize = StrUtils.isEmpty(param.get("rows")) ? Integer.valueOf(param.get("rows")// TODOArguments.getStringArg("PAGE.SIZE")
		) : Integer.valueOf(param.get("rows"));

		Map<String, Object> rtnMap = new HashMap<String, Object>();

		List<UNDONE_TXDATA> list = null;
		Page page = null;
		try {
			list = new ArrayList<UNDONE_TXDATA>();
			String condition_1 = "", condition_2 = "";
			condition_1 = combine(conditions_1);
			condition_2 = combine(conditions_2);
			String sord = StrUtils.isNotEmpty(param.get("sord")) ? param.get("sord") : "";
			String sidx = StrUtils.isNotEmpty(param.get("sidx")) ? param.get("sidx") : "";
			String orderSQL = StrUtils.isNotEmpty(sidx) ? " ORDER BY " + sidx + " " + sord : "";
			StringBuffer tmpSQL = new StringBuffer();
			StringBuffer cntSQL = new StringBuffer();
			StringBuffer sumSQL = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			if (StrUtils.isNotEmpty(param.get("sidx"))) {
//				if("TXDT".equalsIgnoreCase(param.get("sidx"))){
//					orderSQL = " ORDER BY NEWTXDT "+param.get("sord");
//				}
//				if("TXAMT".equalsIgnoreCase(param.get("sidx"))){
//					orderSQL = " ORDER BY NEWTXAMT "+param.get("sord");
//				}
				if ("RESULTCODE".equalsIgnoreCase(param.get("sidx"))) {
					orderSQL = " ORDER BY NEWRESULT " + param.get("sord");
				}
			}
			tmpSQL.append(" WITH TEMP AS ");
			tmpSQL.append(" ( ");
			tmpSQL.append(
					" SELECT   COALESCE(NEWTXDT ,'') AS TXDT, TXDATE, BIZDATE, STAN, OUTACCTNO, INACCTNO, COALESCE(NEWTXAMT,0) AS TXAMT , NEWRESULT ,SENDERACQUIRE, SENDERHEAD ");

			tmpSQL.append("  , SENDERID, EACH_TXN_NAME ");
			tmpSQL.append(
					" , COALESCE(TXID,'') ||'-'|| COALESCE((SELECT TC.TXN_NAME FROM TXN_CODE TC WHERE TC.TXN_ID = TXID ),'') AS TXID ");
			tmpSQL.append(
					" , COALESCE(SENDERBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=SENDERBANKID),'') SENDERBANKID ");
			tmpSQL.append(
					" , COALESCE(OUTBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=OUTBANKID),'') OUTBANKID ");
			tmpSQL.append(
					" , COALESCE(INBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=INBANKID),'') INBANKID ");
			tmpSQL.append(" , COALESCE(PCODE,'') || '-' || COALESCE(EACH_TXN_NAME,'') AS PCODE ");
//			20151127 add by hugo req by UAT-20151126-01
			tmpSQL.append(" , BUSINESS_TYPE_ID ");
			tmpSQL.append(" FROM VW_ONBLOCKTAB ");
			tmpSQL.append(
					" LEFT JOIN (  SELECT EACH_TXN_ID, EACH_TXN_NAME FROM EACH_TXN_CODE  ) ON PCODE = EACH_TXN_ID   ");
			tmpSQL.append(
					" LEFT JOIN (  SELECT OTXDate, OSTAN, RESULTCODE FROM ONPENDINGTAB  ) ON TXDATE = OTXDate AND STAN = OSTAN   ");
			// 20150302 BY 李建利(與陳淑華開會討論)：只要是曾經是未完成交易(不包含處理中)，無論是否有回應結果，都可在此功能中查詢出來
			tmpSQL.append(" WHERE RESULTSTATUS = 'P' AND SENDERSTATUS <> '1' ");
			tmpSQL.append((StrUtils.isEmpty(condition_1) ? "" : "AND " + condition_1));
			if (filter_bat.equals("Y")) {
				tmpSQL.append(" UNION ALL ");
				tmpSQL.append(
						" SELECT   COALESCE(NEWTXDT ,'') AS TXDT, TXDATE, BIZDATE, STAN, OUTACCTNO, INACCTNO, COALESCE(NEWTXAMT,0) AS TXAMT, NEWRESULT,SENDERACQUIRE, SENDERHEAD ");

				tmpSQL.append("  , SENDERID, EACH_TXN_NAME ");
				tmpSQL.append(
						" , COALESCE(TXID,'') ||'-'|| COALESCE((SELECT TC.TXN_NAME FROM TXN_CODE TC WHERE TC.TXN_ID = TXID ),'') AS TXID ");
				tmpSQL.append(
						" , COALESCE(SENDERBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=SENDERBANKID),'') SENDERBANKID ");
				tmpSQL.append(
						" , COALESCE(OUTBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=OUTBANKID),'') OUTBANKID ");
				tmpSQL.append(
						" , COALESCE(INBANKID,'') ||'-'|| COALESCE((SELECT BRBK_NAME FROM BANK_BRANCH WHERE BRBK_ID=INBANKID),'') INBANKID ");
				tmpSQL.append(" , COALESCE(PCODE,'') || '-' || COALESCE(EACH_TXN_NAME,'') AS PCODE ");
//				20151127 add by hugo req by UAT-20151126-01
				tmpSQL.append(" , BUSINESS_TYPE_ID ");
				tmpSQL.append(" FROM VW_ONBLOCKTAB ");
				tmpSQL.append(
						" LEFT JOIN (  SELECT EACH_TXN_ID, EACH_TXN_NAME FROM EACH_TXN_CODE  ) ON PCODE = EACH_TXN_ID   ");
				tmpSQL.append(" WHERE RESULTSTATUS = 'P' AND SENDERSTATUS <> '1' ");
				tmpSQL.append((StrUtils.isEmpty(condition_2) ? "" : "AND " + condition_2));
			}
			tmpSQL.append(" ) ");

			sql.append(" SELECT * FROM ( ");
			sql.append("  	SELECT  ROWNUMBER() OVER( " + orderSQL + ") AS ROWNUMBER , TEMP.*  ");
			sql.append(" FROM TEMP ");
			sql.append(" ) AS A    ");
			sql.append("  WHERE ROWNUMBER >= " + (Page.getStartOfPage(pageNo, pageSize) + 1) + " AND ROWNUMBER <= "
					+ (pageNo * pageSize) + " ");
			sql.insert(0, tmpSQL.toString());
			sql.append(orderSQL);

			cntSQL.append(" SELECT COALESCE(COUNT(*),0) AS NUM FROM TEMP ");
			cntSQL.insert(0, tmpSQL.toString());
			sumSQL.append(" SELECT COALESCE( SUM(TXAMT) ,0) AS TXAMT FROM TEMP ");
			sumSQL.insert(0, tmpSQL.toString());

			// 先算出總計的欄位值，放到rtnMap裡面，到頁面再放到下面的總計Grid裡面
			System.out.println("sumSQL=" + sumSQL);
			List dataSumList = commonSpringRepository.list(sumSQL.toString(), null);
//			跟資料庫相關的先註解掉 20220914
			rtnMap.put("dataSumList", dataSumList);

//			String cols[] = {"PCODE","NEWTXDT","TXDATE","STAN","SENDERBANKID","OUTBANKID","INBANKID","OUTACCTNO","INACCTNO","NEWTXAMT","TXID","SENDERID"};
			String cols[] = { "PCODE", "TXDT", "TXDATE", "STAN", "SENDERBANKID", "OUTBANKID", "INBANKID", "OUTACCTNO",
					"INACCTNO", "TXAMT", "TXID", "SENDERID" };
			System.out.println("cntSQL==>" + cntSQL.toString().toUpperCase());
			System.out.println("sql==>" + sql.toString().toUpperCase());
//			page = vw_onblocktab_Dao.getDataIII(pageNo, pageSize, cntSQL.toString(), sql.toString(), cols, UNDONE_TXDATA.class);
// 因為還沒有寫資料庫的串法,所以把跟資料庫相關的Dao都先註解,只留方法 20220914
			list = (List<UNDONE_TXDATA>) page.getResult();
			System.out.println("UNDONE_TXDATA.list>>" + list);
			list = list != null && list.size() == 0 ? null : list;
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (page == null) {
			rtnMap.put("total", "0");
			rtnMap.put("page", "0");
			rtnMap.put("records", "0");
			rtnMap.put("rows", new ArrayList());
		} else {
			rtnMap.put("total", page.getTotalPageCount());
			rtnMap.put("page", String.valueOf(page.getCurrentPageNo()));
			rtnMap.put("records", page.getTotalCount());
			rtnMap.put("rows", list);
		}
//-------------------資料轉換swagger輸出----------------------------------------------------
		ObjectMapper mapper = new ObjectMapper();
		PageSearchRs result = mapper.convertValue(rtnMap, PageSearchRs.class);
		return result;
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

	// details----請求傳送未完成交易結果(1406)------------------------------------------------------
	@SuppressWarnings({ "unchecked", "unused" })
	public UndoneSendRs send_1406(UndoneSendRq param) {
		/*
		 * 查詢未完成交易處理結果 <?xml version="1.0" encoding="UTF-8" standalone="yes"?> <msg>
		 * <header> <SystemHeader>eACH01</SystemHeader> <MsgType>0100</MsgType>
		 * <PrsCode>1406</PrsCode> <Stan>XXXXXXX</Stan> <InBank>0000000</InBank>
		 * <OutBank>9990000</OutBank> <DateTime>YYYYMMDDHHMMSS</DateTime>
		 * <RspCode>0000</RspCode> </header> <body> <OTxDate></OTXDate> <OSTAN></OSTAN>
		 * </body> </msg>
		 * 
		 */
		String json = "{}";
		// String stan = StrUtils.isNotEmpty(param.get("STAN"))?param.get("STAN"):"" ;
//		String stan = StrUtils.isNotEmpty(param.get("STAN"))?param.get("STAN"):"" ;//innput
		String stan = StrUtils.isNotEmpty(param.getStan()) ? param.getStan() : "";// innput
//		String txdate = StrUtils.isNotEmpty(param.get("TXDATE"))?param.get("TXDATE"):"" ;//innput
		String txdate = StrUtils.isNotEmpty(param.getTxdate()) ? param.getTxdate() : "";// innput
		txdate = DateTimeUtils.convertDate(2, txdate, "yyyy-MM-dd", "yyyyMMdd");
		String resultCode = "";
		@SuppressWarnings("rawtypes")
		Map rtnMap = new HashMap();
		try {
			// 先檢查onpendingtab中是否有該筆資料存在
			ONPENDINGTAB_PK id = new ONPENDINGTAB_PK(txdate, stan);
			Optional<ONPENDINGTAB> po = OnPendingTabR.findById(id);
			if (po == null) {
				rtnMap.put("result", "FALSE");// outtput
				rtnMap.put("msg", "失敗，資料尚未轉移，PK={STAN:" + stan + ",TXDATE:" + txdate + "}");// outtput
			} else {
//				20150529 add by hugo req by UAT-20150526-06
				if (po.get().getBIZDATE() != null) {// 表示已有處理結果
					rtnMap.put("result", "FALSE");// outtput
					rtnMap.put("msg", "已有未完成交易處理結果，營業日=" + po.get().getBIZDATE());// outtput
					// TODO
					// json = JSONUtils.map2json(rtnMap);
//					return json;
				}
				Header msgHeader = new Header();
				msgHeader.setSystemHeader("eACH01");
				msgHeader.setMsgType("0100");
				msgHeader.setPrsCode("1406");
				msgHeader.setStan("");// 此案例未使用
				msgHeader.setInBank("0000000");
				msgHeader.setOutBank("9990000"); // 20150109 FANNY說 票交發動的電文，「OUTBANK」必須固定為「9990000」
				msgHeader.setDateTime(zDateHandler.getDateNum() + zDateHandler.getTheTime().replaceAll(":", ""));
				msgHeader.setRspCode("0000");
				socketPackage msg = new socketPackage();
				msg.setHeader(msgHeader);
				Body body = new Body();
				body.setOSTAN(stan);// bsformdto
				body.setOTxDate(txdate);// otxdate11//txdate bsformdto
//				body.setResultCode(resultCode);
				msg.setBody(body);// 11
				String telegram = messageConverter.marshalling(msg);
				// TODO
//				rtnMap = socketClient.send(telegram);//socket先註解
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnMap.put("result", "FALSE");// outtput
			rtnMap.put("msg", "失敗，電文發送異常");// outtput
		} catch (Exception ee) {
			ee.printStackTrace();
			rtnMap.put("result", "FALSE");// outtput
			rtnMap.put("msg", "失敗，系統異常");// outtput
		}
		// TODO
		// object mapper把JS轉RS型別

//		json = JSONUtils.map2json(rtnMap);//json先不搬
//		return json;
		ObjectMapper mapper = new ObjectMapper();
		UndoneSendRs result = mapper.convertValue(rtnMap, UndoneSendRs.class);
		return result;
	}

	// details----請求傳送確認訊息(1400)------------------------------------------------------
	@SuppressWarnings("unchecked")
	public UndoneSendRs send_1400(UndoneSendRq param) {
		/*
		 * 請求傳送確認訊息 <?xml version="1.0" encoding="UTF-8" standalone="yes"?> <msg>
		 * <header> <SystemHeader>eACH01</SystemHeader> <MsgType>0100</MsgType>
		 * <PrsCode>1400</PrsCode> <Stan>XXXXXXX</Stan> <InBank>0000000</InBank>
		 * <OutBank>9990000</OutBank> <DateTime>YYYYMMDDHHMMSS</DateTime>
		 * <RspCode>0000</RspCode> </header> <body> <OTxDate></OTxDate> <OSTAN></OSTAN>
		 * </body> </msg>
		 * 
		 * 
		 * 
		 */
		@SuppressWarnings("unused")
		String json = "{}";
//		String stan = StrUtils.isNotEmpty(param.get("STAN"))?param.get("STAN"):"" ;//innput
//		String txdate = StrUtils.isNotEmpty(param.get("TXDATE"))?param.get("TXDATE"):"" ;//innput
		String stan = StrUtils.isNotEmpty(param.getStan()) ? param.getStan() : "";// innput
		String txdate = StrUtils.isNotEmpty(param.getTxdate()) ? param.getTxdate() : "";// innput
		txdate = DateTimeUtils.convertDate(2, txdate, "yyyy-MM-dd", "yyyyMMdd");
		@SuppressWarnings("unused")
		String resultCode = "";
		@SuppressWarnings("rawtypes")
		Map rtnMap = new HashMap();
		try {
			// 先檢查onpendingtab中是否有該筆資料存在
			ONPENDINGTAB_PK id = new ONPENDINGTAB_PK(txdate, stan);
			Optional<ONPENDINGTAB> po = OnPendingTabR.findById(id);
			if (po != null) {
				rtnMap.put("result", "FALSE");// outtput
				rtnMap.put("msg", "失敗，資料已轉移，PK={STAN:" + stan + ",TXDATE:" + txdate + "}");// outtput
			} else {
				Header msgHeader = new Header();
				msgHeader.setSystemHeader("eACH01");
				msgHeader.setMsgType("0100");
				msgHeader.setPrsCode("1400");
				msgHeader.setStan("");// 此案例未使用
				msgHeader.setInBank("0000000");
				msgHeader.setOutBank("9990000"); // 20150109 FANNY說 票交發動的電文，「OUTBANK」必須固定為「9990000」
				msgHeader.setDateTime(zDateHandler.getDateNum() + zDateHandler.getTheTime().replaceAll(":", ""));
				msgHeader.setRspCode("0000");
				socketPackage msg = new socketPackage();
				msg.setHeader(msgHeader);
				Body body = new Body();
				body.setOSTAN(stan);
				body.setOTxDate(txdate);
//				body.setResultCode(resultCode);
				msg.setBody(body);
				@SuppressWarnings("unused")
				String telegram = messageConverter.marshalling(msg);
				// TODO
//				rtnMap = socketClient.send(telegram);
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnMap.put("result", "FALSE");// outtput
			rtnMap.put("msg", "失敗，電文發送異常");// outtput
		} catch (Exception ee) {
			ee.printStackTrace();
			rtnMap.put("result", "FALSE");// outtput
			rtnMap.put("msg", "失敗，系統異常");// outtput
		}

		// TODO
		// object mapper 轉RS型別
		ObjectMapper mapper = new ObjectMapper();
		UndoneSendRs response = mapper.convertValue(rtnMap, UndoneSendRs.class);

//		json = JSONUtils.map2json(rtnMap);
//		return json;
		return response;
	}

	// details----票交所代為處理未完成交易(send)------------------------------------------------------
	@SuppressWarnings("unchecked")
	public UndoneSendRs send(UndoneSendRq param) {
		/*
		 * 未完成處理結果 沖正作業電文格式 <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
		 * <msg> <header> <SystemHeader>eACH01</SystemHeader> <MsgType>0100</MsgType>
		 * <PrsCode>1403</PrsCode> <Stan>XXXXXXX</Stan> <InBank>0000000</InBank>
		 * <OutBank>9990000</OutBank> <DateTime>YYYYMMDDHHMMSS</DateTime>
		 * <RspCode>0000</RspCode> </header> <body> <OTxDate></OTxDate> <OSTAN></OSTAN>
		 * <ResultCode></ResultCode> </body> </msg>
		 * 
		 * ResultCode - 00 成功 - 01 失敗(沖正)
		 * 
		 */
		String json = "{}";
//		String stan = StrUtils.isNotEmpty(param.get("STAN"))?param.get("STAN"):"" ;
//		String txdate = StrUtils.isNotEmpty(param.get("TXDATE"))?param.get("TXDATE"):"" ;
		String stan = StrUtils.isNotEmpty(param.getStan()) ? param.getStan() : "";
		String txdate = StrUtils.isNotEmpty(param.getStan()) ? param.getStan() : "";

		String type = StrUtils.isNotEmpty(param.getStan()) ? param.getStan() : "";
//		String type = StrUtils.isNotEmpty(param.get("TYPE"))?param.get("TYPE"):"" ;

//		txdate = DateTimeUtils.convertDate(2, txdate, "yyyy-MM-dd", "yyyyMMdd");
		txdate = DateTimeUtils.convertDate(2, txdate, "yyyy-MM-dd", "yyyyMMdd");

		String resultCode = "";
		@SuppressWarnings("rawtypes")
		Map rtnMap = new HashMap();
		try {
			// 先檢查onpendingtab中是否有該筆資料存在
			ONPENDINGTAB_PK id = new ONPENDINGTAB_PK(txdate, stan);
			Optional<ONPENDINGTAB> po = OnPendingTabR.findById(id);
			if (po == null) {
				rtnMap.put("result", "FALSE");
				rtnMap.put("msg", "失敗，資料尚未轉移，PK={STAN:" + stan + ",TXDATE:" + txdate + "}");
			} else {
//				20150529 add by hugo req by UAT-20150526-06
				if (po.get().getBIZDATE() != null) {// 表示已有處理結果
					rtnMap.put("result", "FALSE");
					rtnMap.put("msg", "已有未完成交易處理結果，營業日=" + po.get().getBIZDATE());
//					TODO
//					json = JSONUtils.map2json(rtnMap);
//					return json;
				}

//				判斷type來選擇電文種類 普鴻尚未完成
				if (type.equals("S")) {
					resultCode = "00";
				}
				if (type.equals("F")) {
					resultCode = "01";
				}
				Header msgHeader = new Header();
				msgHeader.setSystemHeader("eACH01");
				msgHeader.setMsgType("0100");
				msgHeader.setPrsCode("1403");
				msgHeader.setStan("");// 此案例未使用
				msgHeader.setInBank("");// 此案例未使用
				msgHeader.setOutBank("9990000"); // 20150109 FANNY說 票交發動的電文，「OUTBANK」必須固定為「9990000」
				msgHeader.setDateTime(zDateHandler.getDateNum() + zDateHandler.getTheTime().replaceAll(":", ""));
				msgHeader.setRspCode("0000");
				socketPackage msg = new socketPackage();
				msg.setHeader(msgHeader);
				Body body = new Body();
				body.setOSTAN(stan);
				body.setOTxDate(txdate);
				body.setResultCode(resultCode);
				msg.setBody(body);
				String telegram = messageConverter.marshalling(msg);
//				rtnMap = socketClient.send(telegram);

				// 過10秒後再查詢，檢查是否已沖正完畢
				Thread.sleep(5 * 1000);
				po = OnPendingTabR.findById(id);
				if (po != null) {
					if (po.get().getACHFLAG().equals("*")) {
						rtnMap.put("result", "TRUE");
						rtnMap.put("msg", "成功，已完成作業");
					} else {
						rtnMap.put("result", "FALSE");
						rtnMap.put("msg", "失敗，未完成作業");
					}
				} else {
					rtnMap.put("result", "FALSE");
					rtnMap.put("msg", "失敗，無此資料，PK={STAN:" + stan + ",TXDATE:" + txdate + "}");
				}
			}
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rtnMap.put("result", "FALSE");
			rtnMap.put("msg", "失敗，電文發送異常");
		} catch (Exception ee) {
			ee.printStackTrace();
			rtnMap.put("result", "FALSE");
			rtnMap.put("msg", "失敗，系統異常");
		}
//		json = JSONUtils.map2json(rtnMap);
//		return json;
		// TODO
		// object mapper 轉RS型別
		ObjectMapper mapper = new ObjectMapper();
		UndoneSendRs result = mapper.convertValue(rtnMap, UndoneSendRs.class);// 這裡面是甚麼?尚未確認
		return result;
	}
	//明細
	public List<BANK_GROUP> search(String bgbkId){
		List<BANK_GROUP> list = null ;
		if(StrUtils.isEmpty(bgbkId)){
//			list = bank_group_Dao.getAll();
			list = bankGroupRepository.getAllData();
		}else{
//			list = new ArrayList<BANK_GROUP>();
//			BANK_GROUP po = bank_group_Dao.get(bgbkId);
//			if(po != null){
//				list.add(po);
//			}
			list = bankGroupRepository.getDataByBgbkId(bgbkId);
		}
		System.out.println("list>>"+list);
		list = list == null? null : list.size() == 0? null:list;
		
		//測試
		//bank_group_Dao.creatWK();
		
		return list;
	}
	
	public List<BANK_GROUP> getDataByBgbkId(String bgbkId){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ");
//		sql.append("BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE, ");
		sql.append("BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE,IS_EACH ");
		sql.append(" , (CASE BGBK_ATTR WHEN '0' THEN '本國銀行' WHEN '1' THEN '地區企銀' WHEN '2' THEN '合作社' WHEN '3' THEN '農漁會' WHEN '4' THEN '外商銀行' WHEN '5' THEN '共用中心' WHEN '6' THEN '票交分所' ELSE '未定義屬性' END) AS BGBK_ATTR, ");
		sql.append("COALESCE( ");
		sql.append("    (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE B.BGBK_ID = EACHUSER.GET_CUR_OPBKID (A.BGBK_ID , '' , 0 ) ), '' ");
		sql.append(") AS OPBK_NAME, ");
		sql.append("COALESCE( ");
		sql.append("    (SELECT B.BGBK_NAME FROM BANK_GROUP B WHERE B.BGBK_ID = EACHUSER.GET_CUR_CTBKID (A.BGBK_ID , '' , 0 ) ), '' ");
		sql.append(") AS CTBK_NAME ");
//		20150810 add by hugo 因應變更操作行、清算行
		sql.append(",  COALESCE( EACHUSER.GET_CUR_OPBKID (BGBK_ID , '' , 0 ) , '') AS OPBK_ID ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_CTBKID (BGBK_ID , '' , 0 ) , '') AS CTBK_ID ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_OPBKID (BGBK_ID , '',1 ) , '') AS OP_START_DATE  ");
		sql.append(", COALESCE( EACHUSER.GET_CUR_CTBKID (BGBK_ID , '' ,1) , '') AS CT_START_DATE ");
		sql.append(",  COALESCE( EACHUSER.GET_CUR_OPBKID (BGBK_ID , '',2 ) , '') AS OP_NOTE  ");
		sql.append(", COALESCE( EACHUSER.GET_CUR_CTBKID (BGBK_ID , '' ,2) , '') AS CT_NOTE ");
		sql.append(", COALESCE( HR_UPLOAD_MAX_FILE , 0) AS HR_UPLOAD_MAX_FILE  ");
		
		
		sql.append("FROM BANK_GROUP A ");
		sql.append("WHERE A.BGBK_ID = :bgbkId ");
		/*
		sql.append(" SELECT value((SELECT BANK_BRANCH.BRBK_NAME FROM BANK_BRANCH WHERE BANK_BRANCH.BRBK_ID = BANK_GROUP.OPBK_ID   ),'無此分行') AS OPBK_NAME ");
		sql.append(" , value((SELECT BANK_BRANCH.BRBK_NAME FROM BANK_BRANCH WHERE BANK_BRANCH.BRBK_ID = BANK_GROUP.CTBK_ID   ) ,'無此分行')AS CTBK_NAME ");
		sql.append(" ,BGBK_ID,BGBK_NAME,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE") ;
		sql.append(" ,(CASE WHEN (BGBK_ATTR = '0') THEN '本國銀行'  WHEN (BGBK_ATTR = '1') THEN '地區企銀'  WHEN (BGBK_ATTR = '2') THEN '合作社'  WHEN (BGBK_ATTR = '3') THEN '農漁會'" );
		sql.append(" WHEN (BGBK_ATTR = '4') THEN '外商銀行' WHEN (BGBK_ATTR = '5') THEN '共用中心'  WHEN (BGBK_ATTR = '6') THEN '票交分所' ELSE '未定義屬性' END) AS BGBK_ATTR ");
		sql.append(" FROM BANK_GROUP ");
		sql.append(" WHERE BANK_GROUP.BGBK_ID = :bgbkId");
		*/
		System.out.println("getDataByBgbkId.sql>>"+sql);
		List<BANK_GROUP> list = null;
		try {
			SQLQuery query = getSession().createSQLQuery(sql.toString());
			query.setParameter("bgbkId", bgbkId);
			String cols = " OPBK_NAME, CTBK_NAME ,BGBK_ID,BGBK_NAME,BGBK_ATTR,CTBK_ACCT,TCH_ID,OPBK_ID,CTBK_ID,ACTIVE_DATE,STOP_DATE,SND_FEE_BRBK_ID,OUT_FEE_BRBK_ID,IN_FEE_BRBK_ID,EDDA_FLAG,CDATE,UDATE , OP_START_DATE , CT_START_DATE , HR_UPLOAD_MAX_FILE , OP_NOTE , CT_NOTE , IS_EACH " ;
			String[] objs =  cols.split(",");
			AutoAddScalar addscalar = new AutoAddScalar();
			addscalar.addScalar(query, BANK_GROUP.class, objs);
			query.setResultTransformer(Transformers.aliasToBean(BANK_GROUP.class));
			list = query.list();
			list = list == null? null:list.size() == 0? null:list;
		 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

}
