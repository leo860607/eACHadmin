package com.fstop.eachadmin.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import java.util.Optional;

import javax.xml.bind.JAXBException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstop.eachadmin.dto.DetailRq;
import com.fstop.eachadmin.dto.DetailRs;
import com.fstop.eachadmin.dto.PageSearchRq;
import com.fstop.eachadmin.dto.PageSearchRs;
import com.fstop.eachadmin.repository.BankGroupRepository;
import com.fstop.eachadmin.repository.BusinessTypeRepository;
import com.fstop.eachadmin.repository.CommonSpringRepository;
import com.fstop.eachadmin.repository.VwOnBlockTabRepository;
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
	private BankGroupRepository bankGroupRepository;

	@Autowired
	private EachSysStatusTabService eachSysStatusTabService;

	@Autowired
	private OnblocktabService onblocktabService;

	@Autowired
	private VwOnBlockTabRepository vwOnBlockTabRepository;

// 操作行------------------------------------------------------
	public List<Map<String, String>> getOpbkList() {

		String sql = "SELECT COALESCE( OP.OPBK_ID,'' ) AS OPBK_ID , COALESCE( BG.BGBK_NAME ,'' ) AS OPBK_NAME FROM ( SELECT DISTINCT OPBK_ID FROM EACHUSER.BANK_OPBK ) AS OP JOIN ( SELECT BGBK_ID, BGBK_NAME FROM BANK_GROUP WHERE BGBK_ATTR <> '6' ) AS BG ON OP.OPBK_ID = BG.BGBK_ID ORDER BY OP.OPBK_ID";
		List<BANK_OPBK> list = businessTypeRepository.getAllOpbkList(sql);
		List<Map<String, String>> beanList = new LinkedList<Map<String, String>>();

		Map<String, String> bean = null;

		for (BANK_OPBK po : list) {

			String k1 = "BankName";
			String v1 = (String) po.getOPBK_NAME();

			String k2 = "BankId";
			String v2 = (String) po.getOPBK_ID();

			bean = new HashMap<String, String>();
			bean.put(k1, v1);
			bean.put(k2, v2);
			beanList.add(bean);
		}
		System.out.println("beanList>>" + beanList);
		return beanList;
	}

// 業務行------------------------------------------------------
	public List<Map<String, String>> getBsTypeIdList() {

		String sql = "SELECT * FROM BUSINESS_TYPE ORDER BY BUSINESS_TYPE_ID";
		List<BUSINESS_TYPE> list = businessTypeRepository.find(sql);
		List<Map<String, String>> beanList = new LinkedList<Map<String, String>>();

		Map<String, String> bean = null;

		for (BUSINESS_TYPE po : list) {

			String k1 = "BusinessTypeName";
			String v1 = (String) po.getBUSINESS_TYPE_NAME();

			String k2 = "BusinessTypeID";
			String v2 = (String) po.getBUSINESS_TYPE_ID();

			bean = new HashMap<String, String>();
			bean.put(k1, v1);
			bean.put(k2, v2);
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
		String filter_bat = param.getFilter_bat() == null ? "N" : "Y";

		/* 20150210 HUANGPU 改以營業日(BIZDATE)查詢資料，非交易日期(TXDATE) */
		String startDate = "";
		if (StrUtils.isNotEmpty(param.getStartDate().trim())) {
			startDate = param.getStartDate().trim();
			conditions_1.add(" BIZDATE >= '" + DateTimeUtils.convertDate(startDate, "yyyyMMdd", "yyyyMMdd") + "' ");
		}

		String endDate = "";
		if (StrUtils.isNotEmpty(param.getEndDate().trim())) {
			endDate = param.getEndDate().trim();
			conditions_1.add(" BIZDATE <= '" + DateTimeUtils.convertDate(endDate, "yyyyMMdd", "yyyyMMdd") + "' ");
		}

		String clearingphase = "";
		if (StrUtils.isNotEmpty(param.getClearingphase().trim()) && !param.getClearingphase().equals("all")) {
			clearingphase = param.getClearingphase().trim();
			conditions_1.add(" CLEARINGPHASE = '" + clearingphase + "' ");
		}

		String bgbkId = "";
		if (StrUtils.isNotEmpty(param.getBgbkId().trim()) && !param.getBgbkId().trim().equals("all")) {
			bgbkId = param.getBgbkId().trim();
			conditions_1.add(
					" (SENDERHEAD = '" + bgbkId + "' OR OUTHEAD = '" + bgbkId + "' OR INHEAD = '" + bgbkId + "') ");
		}

		String businessTypeId = "";
		if (StrUtils.isNotEmpty(param.getBusinessTypeId().trim()) && !param.getBusinessTypeId().trim().equals("all")) {
			businessTypeId = param.getBusinessTypeId().trim();
			conditions_1.add(" BUSINESS_TYPE_ID = '" + businessTypeId + "' ");
		}

		String ostan = "";
		if (StrUtils.isNotEmpty(param.getOstan()) && !param.getOstan().equals("all")) {
			ostan = param.getOstan();
			conditions_1.add(" STAN = '" + ostan + "' ");
		}

		if (StrUtils.isNotEmpty(param.getResultCode()) && !param.getResultCode().equals("all")) {
			if ("A".equals(param.getResultCode())) {
				conditions_1.add(" RESULTCODE IS NOT NULL ");
			} else if ("P".equals(param.getResultCode())) {
				conditions_1.add(" RESULTCODE IS NULL ");
			}
		}

		String opbkId = "";
		if (StrUtils.isNotEmpty(param.getOpbkId().trim()) && !param.getOpbkId().trim().equals("all")) {
			opbkId = param.getOpbkId().trim();
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

		int pageNo = StrUtils.isEmpty(param.getPage()) ? 0 : Integer.valueOf(param.getPage());
		int pageSize = StrUtils.isEmpty(param.getRow()) ? Integer.valueOf(param.getPage()// TODOArguments.getStringArg("PAGE.SIZE")
		) : Integer.valueOf(param.getRow());

		Map<String, Object> rtnMap = new HashMap<String, Object>();

		List<UNDONE_TXDATA> list = null;
		Page page = null;
		try {
			list = new ArrayList<UNDONE_TXDATA>();
			String condition_1 = "", condition_2 = "";
			condition_1 = combine(conditions_1);
			condition_2 = combine(conditions_2);
			String sord = StrUtils.isNotEmpty(param.getSord()) ? param.getSord() : "";
			String sidx = StrUtils.isNotEmpty(param.getSidx()) ? param.getSidx() : "";
			String orderSQL = StrUtils.isNotEmpty(sidx) ? " ORDER BY " + sidx + " " + sord : "";
			StringBuffer tmpSQL = new StringBuffer();
			StringBuffer cntSQL = new StringBuffer();
			StringBuffer sumSQL = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			if (StrUtils.isNotEmpty(param.getSidx())) {
				if ("TXDT".equalsIgnoreCase(param.getSidx())) {
					orderSQL = " ORDER BY NEWTXDT " + param.getSord();
				}
				if ("TXAMT".equalsIgnoreCase(param.getSidx())) {
					orderSQL = " ORDER BY NEWTXAMT " + param.getSord();
				}
				if ("RESULTCODE".equalsIgnoreCase(param.getSidx())) {
					orderSQL = " ORDER BY NEWRESULT " + param.getSord();
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
			// 跟資料庫相關的先註解掉 20220914
			rtnMap.put("dataSumList", dataSumList);

			// String cols[] =
			// {"PCODE","NEWTXDT","TXDATE","STAN","SENDERBANKID","OUTBANKID","INBANKID","OUTACCTNO","INACCTNO","NEWTXAMT","TXID","SENDERID"};
			String cols[] = { "PCODE", "TXDT", "TXDATE", "STAN", "SENDERBANKID", "OUTBANKID", "INBANKID", "OUTACCTNO",
					"INACCTNO", "TXAMT", "TXID", "SENDERID" };
			System.out.println("cntSQL==>" + cntSQL.toString().toUpperCase());
			System.out.println("sql==>" + sql.toString().toUpperCase());
			page = vwOnBlockTabRepository.getDataIII(pageNo, pageSize, cntSQL.toString(), sql.toString(), cols,
					UNDONE_TXDATA.class);
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

// 請求傳送未完成交易結果(1406)------------------------------------------------------
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
//			Optional<ONPENDINGTAB> po = OnPendingTabR.findById(id);
			Optional<ONPENDINGTAB> po = null;
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
				// body.setResultCode(resultCode);
				msg.setBody(body);// 11
				String telegram = messageConverter.marshalling(msg);
				// TODO
				// rtnMap = socketClient.send(telegram);//socket先註解
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

		// json = JSONUtils.map2json(rtnMap);//json先不搬
		// return json;
		ObjectMapper mapper = new ObjectMapper();
		UndoneSendRs result = mapper.convertValue(rtnMap, UndoneSendRs.class);
		return result;
	}

// 請求傳送確認訊息(1400)------------------------------------------------------
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
//			Optional<ONPENDINGTAB> po = OnPendingTabR.findById(id);
			Optional<ONPENDINGTAB> po = null;
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

//票交所代為處理未完成交易(send)------------------------------------------------------
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
//			Optional<ONPENDINGTAB> po = OnPendingTabR.findById(id);
			Optional<ONPENDINGTAB> po = null;
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
//				Optional<ONPENDINGTAB> po = OnPendingTabR.findById(id);
				po = null;
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

//查詢明細---------------------------------------------------------------------------
	public DetailRs showDetail(DetailRq param) {
		DetailRs detailRs = new DetailRs();

		String txDate = param.getTXDATE();
		String stan = param.getSTAN();

		Map detailDataMap = onblocktabService.showDetail(txDate, stan);
		String bizdate = eachSysStatusTabService.getBusinessDateII();

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
//			detailDataMap.put("NEWSENDERFEE_NW",detailDataMap.get("NEWSENDERFEE") != null ? detailDataMap.get("NEWSENDERFEE") : "0");
//			detailDataMap.put("NEWINFEE_NW",detailDataMap.get("NEWINFEE") != null ? detailDataMap.get("NEWINFEE") : "0");
//			detailDataMap.put("NEWOUTFEE_NW",detailDataMap.get("NEWOUTFEE") != null ? detailDataMap.get("NEWOUTFEE") : "0");
//			detailDataMap.put("NEWWOFEE_NW",detailDataMap.get("NEWWOFEE") != null ? detailDataMap.get("NEWWOFEE") : "0");
//			detailDataMap.put("NEWEACHFEE_NW",detailDataMap.get("NEWEACHFEE") != null ? detailDataMap.get("NEWEACHFEE") : "0");
//			detailDataMap.put("NEWFEE_NW", detailDataMap.get("NEWFEE") != null ? detailDataMap.get("NEWFEE") : "0");

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
			detailDataMap.put("NEWSENDERFEE_NW",detailDataMap.get("NEWSENDERFEE") != null ? detailDataMap.get("NEWSENDERFEE") : "0");
			detailDataMap.put("NEWINFEE_NW",detailDataMap.get("NEWINFEE") != null ? detailDataMap.get("NEWINFEE") : "0");
			detailDataMap.put("NEWOUTFEE_NW",detailDataMap.get("NEWOUTFEE") != null ? detailDataMap.get("NEWOUTFEE") : "0");
			detailDataMap.put("NEWWOFEE_NW",detailDataMap.get("NEWWOFEE") != null ? detailDataMap.get("NEWWOFEE") : "0");
			detailDataMap.put("NEWEACHFEE_NW",detailDataMap.get("NEWEACHFEE") != null ? detailDataMap.get("NEWEACHFEE") : "0");
			detailDataMap.put("NEWFEE_NW", detailDataMap.get("NEWFEE") != null ? detailDataMap.get("NEWFEE") : "0");

		}

		detailRs.setDetailData(detailDataMap);

		String businessDate = eachSysStatusTabService.getBusinessDate();

		detailRs.setSTART_DATE(businessDate);
		detailRs.setEND_DATE(businessDate);
		ObjectMapper mapper = new ObjectMapper();
		UndoneSendRs result = mapper.convertValue(detailDataMap, UndoneSendRs.class);

		try {
			BeanUtils.copyProperties(param, param);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		detailRs.setIsUndone("Y");

		System.out.println("FILTER_BAT>>" + param.getFILTER_BAT());

		// 操作行代號清單
		// undone_txdata_form.setOpbkIdList(undone_txdata_bo.getOpbkIdList());
		detailRs.setOpbkIdList(getOpbkList());
		// 業務類別清單
		detailRs.setBsTypeList(getBsTypeIdList());

		Map userData = null;
		try {
			userData = BeanUtils.describe(detailRs.getUserData());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 銀行端預設帶入操作行
		if (((String) userData.get("USER_TYPE")).equals("B")) {
			// 20150407 edit by hugo 只會有操作行故只能抓總行檔 抓分行檔 997會查無資料
			// BANK_BRANCH po =
			// bank_branch_bo.searchByBrbkId((String)userData.get("USER_COMPANY")).get(0);
			// undone_txdata_form.setOPBK_ID(bank_group_bo.search(po.getId().getBGBK_ID()).get(0).getOPBK_ID());
			detailRs.setOPBK_ID(search((String) userData.get("USER_COMPANY")).get(0).getOPBK_ID());
		}

		return detailRs;

	}

//-------------------明細所需資料-------------------------------------------------
	public List<BANK_GROUP> search(String bgbkId) {
		List<BANK_GROUP> list = null;
		if (StrUtils.isEmpty(bgbkId)) {
			//list = bank_group_Dao.getAll();
			list = bankGroupRepository.getAllData();
		} else {
			//list = new ArrayList<BANK_GROUP>();
			//BANK_GROUP po = bank_group_Dao.get(bgbkId);
			//if(po != null){
			//list.add(po);
			//}
			list = bankGroupRepository.getDataByBgbkId(bgbkId);
		}
		System.out.println("list>>" + list);
		list = list == null ? null : list.size() == 0 ? null : list;

		// 測試
		// bank_group_Dao.creatWK();

		return list;
	}
}
