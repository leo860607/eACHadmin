package com.fstop.eachadmin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstop.eachadmin.dto.HrTxpTimeRq;
import com.fstop.eachadmin.dto.HrTxpTimeRs;
import com.fstop.eachadmin.dto.countAndSumListRs;
import com.fstop.eachadmin.repository.EachTxnCodeRepository;
import com.fstop.eachadmin.repository.OnBlockTabRepository;
import com.fstop.infra.entity.BANK_OPBK;
import com.fstop.infra.entity.EACH_TXN_CODE;
import com.fstop.infra.entity.HR_TXP_TIME;
import lombok.extern.slf4j.Slf4j;

import com.fstop.eachadmin.repository.PageQueryRepository;
import com.fstop.fcore.util.Page;

import util.StrUtils;
import util.DateTimeUtils;
@Slf4j
@Service
public class HrTxpTimeService {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private EachTxnCodeRepository each_txn_code_Dao;
	@Autowired
	private OnBlockTabRepository onblocktab_Dao;
	@Autowired
	private PageQueryRepository<HR_TXP_TIME> pageR;
	/**
	 * 取得交易代號(PCODE-4碼)清單
	 * @return
	 */
	public List<Map<String, String>> getPcodeList(){
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
	
	@SuppressWarnings("unchecked")
	public HrTxpTimeRs pageSearch(HrTxpTimeRq param){
		List<String> conditions = getConditionList(param);
		Map rtnMap = new HashMap();
		List<HR_TXP_TIME> list = null;
		Page page = null;
		try {
			list = new ArrayList<HR_TXP_TIME>();
			String condition = conditions.get(0);
			
			StringBuffer withTemp = new StringBuffer();
			withTemp.append("WITH TEMP AS ( ");
			withTemp.append("    SELECT ");
			withTemp.append("    DOUBLE(DATE_DIFF(ENDTIME, DT_REQ_1)) / CAST (1000000 AS BIGINT) AS TIME_1, ");
			withTemp.append("    DOUBLE(DATE_DIFF(DT_RSP_1, DT_REQ_2)) / CAST (1000000 AS BIGINT) AS TIME_2, ");
			withTemp.append("    (DOUBLE(DATE_DIFF(DT_REQ_2, DT_REQ_1)) + DOUBLE(DATE_DIFF(DT_RSP_2, DT_RSP_1))) / CAST (1000000 AS BIGINT) AS TIME_3, ");
			withTemp.append("    C.* ");
			withTemp.append("    FROM ( ");
			withTemp.append("        SELECT ");
			withTemp.append("        (CASE WHEN RC2 = '3001' THEN 1 ELSE 0 END) OKCOUNT, ");
			withTemp.append("        (CASE WHEN RC2 NOT IN ('3001','0601') THEN 1 ELSE 0 END) FAILCOUNT, ");
			withTemp.append("        (CASE WHEN RC2 = '0601' THEN 1 ELSE 0 END) PENDCOUNT, ");
			withTemp.append("        INTEGER(SUBSTR(A.TXDT,9,2)) AS HOURLAP, A.RESULTSTATUS, ");
			withTemp.append("        A.PCODE, SUBSTR(COALESCE(A.PCODE,'0000'),4,1) AS TXN_TYPE, A.RC2, A.SENDERSTATUS, ");
			withTemp.append("        COALESCE(A.DT_RSP_2, COALESCE(A.DT_RSP_1, COALESCE(A.DT_REQ_2, COALESCE(A.DT_REQ_1, 0)))) AS ENDTIME, ");
			withTemp.append("        (CASE WHEN LENGTH(COALESCE(A.DT_REQ_1,''))=0 THEN '0' ELSE A.DT_REQ_1 END ) DT_REQ_1, ");
			withTemp.append("        (CASE WHEN LENGTH(COALESCE(A.DT_REQ_2,''))=0 THEN '0' ELSE A.DT_REQ_2 END ) DT_REQ_2, ");
			withTemp.append("        (CASE WHEN LENGTH(COALESCE(A.DT_REQ_3,''))=0 THEN '0' ELSE A.DT_REQ_3 END ) DT_REQ_3, ");
			withTemp.append("        (CASE WHEN LENGTH(COALESCE(A.DT_RSP_1,''))=0 THEN '0' ELSE A.DT_RSP_1 END ) DT_RSP_1, ");
			withTemp.append("        (CASE WHEN LENGTH(COALESCE(A.DT_RSP_2,''))=0 THEN '0' ELSE A.DT_RSP_2 END ) DT_RSP_2, ");
			withTemp.append("        (CASE WHEN LENGTH(COALESCE(A.DT_RSP_3,''))=0 THEN '0' ELSE A.DT_RSP_3 END ) DT_RSP_3, ");
			withTemp.append("        A.CLEARINGPHASE, A.SENDERACQUIRE, A.OUTACQUIRE, A.INACQUIRE, A.SENDERHEAD, A.OUTHEAD, A.INHEAD ");
			withTemp.append("        FROM ONBLOCKTAB AS A ");
			withTemp.append("        WHERE ( A.RESULTSTATUS IN ('A', 'R') OR (A.RESULTSTATUS = 'P' AND A.SENDERSTATUS <> '1') ) AND COALESCE(DT_REQ_2,'') <> '' ");
			withTemp.append("        " + (StrUtils.isEmpty(condition)? "" : "AND " + condition) + " ");
			withTemp.append("    ) AS C ");
			withTemp.append(") ");
			//先算出總計的欄位值，放到rtnMap裡面，到頁面再放到下面的總計Grid裡面
			String dataSumCols[] = {"TOTALCOUNT", "OKCOUNT", "FAILCOUNT", "PENDCOUNT"};
			StringBuffer dataSumSQL = new StringBuffer();
			dataSumSQL.append(withTemp);
			dataSumSQL.append("SELECT ");
			dataSumSQL.append("SUM(COALESCE(B.TOTALCOUNT,0)) AS TOTALCOUNT, SUM(COALESCE(B.OKCOUNT,0)) AS OKCOUNT, ");
			dataSumSQL.append("SUM(COALESCE(B.FAILCOUNT,0)) AS FAILCOUNT, SUM(COALESCE(B.PENDCOUNT,0)) AS PENDCOUNT ");
			dataSumSQL.append("FROM HOURITEM AS A LEFT JOIN ( ");
			dataSumSQL.append("    SELECT ");
			dataSumSQL.append("    HOURLAP, ");
			dataSumSQL.append("    SUM(OKCOUNT + FAILCOUNT + PENDCOUNT) AS TOTALCOUNT, SUM(OKCOUNT) AS OKCOUNT, ");
			dataSumSQL.append("    SUM(FAILCOUNT) AS FAILCOUNT, SUM(PENDCOUNT) AS PENDCOUNT ");
			dataSumSQL.append("    FROM TEMP ");
			dataSumSQL.append("    GROUP BY HOURLAP ");
			dataSumSQL.append(") AS B ON A.HOURLAP = B.HOURLAP ");
			dataSumSQL.append("WHERE A.HOURLAP IS NOT NULL ");
			System.out.println("dataSumSQL = " + dataSumSQL.toString().toUpperCase());
			
			List countAndSumList = onblocktab_Dao.dataSum(dataSumSQL.toString(),dataSumCols,countAndSumListRs.class);
			log.debug(countAndSumList.toString());
			rtnMap.put("COUNTANDSUMLIST", (List<countAndSumListRs>)countAndSumList);
			
			StringBuffer countQuery = new StringBuffer();
			StringBuffer sql = new StringBuffer();
			String[] cols = {"HOURLAP","HOURLAPNAME","TOTALCOUNT","OKCOUNT","FAILCOUNT","PENDCOUNT","PRCTIME","DEBITTIME","SAVETIME","ACHPRCTIME"};
			
			sql.append(withTemp);
			sql.append("SELECT * FROM ( ");
			sql.append("    SELECT ROWNUMBER() OVER(");
			if(StrUtils.isNotEmpty(param.getSidx())){
				if("PRCTIME".equalsIgnoreCase(param.getSidx())){
					sql.append(" ORDER BY DECIMAL(COALESCE((SELECT SUM(TIME_1) / CAST(COUNT(*) AS BIGINT) FROM TEMP WHERE HOURLAP = A.HOURLAP AND RC2 <> '0601'),0),18,2) "+param.getSord());
				}
				else if("SAVETIME".equalsIgnoreCase(param.getSidx())){
					sql.append(" ORDER BY ( DECIMAL(COALESCE((SELECT SUM(TIME_2) / CAST(COUNT(*) AS BIGINT) FROM TEMP WHERE HOURLAP = A.HOURLAP AND TXN_TYPE = '2' AND RC2 <> '0601'),0),18,2) + ");
					sql.append("DECIMAL(COALESCE((SELECT SUM((DOUBLE(DATE_DIFF(DT_RSP_3, DT_REQ_3)) / CAST (1000000 AS BIGINT))) / CAST(COUNT(*) AS BIGINT) FROM TEMP WHERE HOURLAP = A.HOURLAP AND TXN_TYPE = '4' AND DT_REQ_3 <> 0 AND RC2 <> '0601'),0),18,2) " + param.getSord());
				}
				else if("DEBITTIME".equalsIgnoreCase(param.getSidx())){
					//20160130 edit by hugo req by SRS_20160122
					sql.append(" ORDER BY DECIMAL(COALESCE((SELECT SUM(TIME_2) / CAST(COUNT(*) AS BIGINT) FROM TEMP WHERE HOURLAP = A.HOURLAP AND TXN_TYPE IN ('1','3','4','5','6' ) AND RC2 <> '0601'),0),18,2) "+param.getSord());
				}
				else if("ACHPRCTIME".equalsIgnoreCase(param.getSidx())){
					//20160130 edit by hugo req by SRS_20160122
					sql.append(" ORDER BY DECIMAL(COALESCE((SELECT SUM(CASE  WHEN TXN_TYPE IN ('4','6') THEN (DATE_DIFF(DT_REQ_2,DT_REQ_1) + DATE_DIFF(DT_REQ_3,DT_RSP_1) + DATE_DIFF(DT_RSP_2,DT_RSP_3)) / CAST (1000000 AS BIGINT) ELSE TIME_3 END) / CAST(COUNT(*) AS BIGINT) FROM TEMP WHERE HOURLAP = A.HOURLAP AND RC2 <> '0601'),0),18,2) "+param.getSord());
				}
				else{
					sql.append(" ORDER BY "+param.getSidx()+" "+param.getSord());
				}
			}else{
				sql.append(" ORDER BY A.HOURLAP ");
			}
			sql.append(") AS ROWNUMBER, A.*, ");
			sql.append("    COALESCE(B.TOTALCOUNT,0) AS TOTALCOUNT, COALESCE(B.OKCOUNT,0) AS OKCOUNT, ");
			sql.append("    COALESCE(B.FAILCOUNT,0) AS FAILCOUNT, COALESCE(B.PENDCOUNT,0) AS PENDCOUNT, ");
			sql.append("    DECIMAL(COALESCE((SELECT SUM(TIME_1) / CAST(COUNT(*) AS BIGINT) FROM TEMP WHERE HOURLAP = A.HOURLAP AND RC2 <> '0601'),0),18,2) PRCTIME, ");
			sql.append("    DECIMAL(COALESCE((SELECT SUM(TIME_2) / CAST(COUNT(*) AS BIGINT) FROM TEMP WHERE HOURLAP = A.HOURLAP AND TXN_TYPE = '2' AND RC2 <> '0601'),0),18,2) + ");
			//20160130 edit by hugo req by SRS_20160122
			sql.append("    DECIMAL(COALESCE((SELECT SUM((DOUBLE(DATE_DIFF(DT_RSP_3, DT_REQ_3)) / CAST (1000000 AS BIGINT))) / CAST(COUNT(*) AS BIGINT) FROM TEMP WHERE HOURLAP = A.HOURLAP AND TXN_TYPE IN ('4','6') AND DT_REQ_3 <> 0 AND RC2 <> '0601'),0),18,2) SAVETIME, ");
			sql.append("    DECIMAL(COALESCE((SELECT SUM(TIME_2) / CAST(COUNT(*) AS BIGINT) FROM TEMP WHERE HOURLAP = A.HOURLAP AND TXN_TYPE IN ('1','3','4' ,'5','6') AND RC2 <> '0601'),0),18,2) DEBITTIME, ");
			sql.append("    DECIMAL(COALESCE((SELECT SUM(CASE  WHEN TXN_TYPE IN ('4','6') THEN (DATE_DIFF(DT_REQ_2,DT_REQ_1) + DATE_DIFF(DT_REQ_3,DT_RSP_1) + DATE_DIFF(DT_RSP_2,DT_RSP_3)) / CAST (1000000 AS BIGINT) ELSE TIME_3 END) / CAST(COUNT(*) AS BIGINT) FROM TEMP WHERE HOURLAP = A.HOURLAP AND RC2 <> '0601'),0),18,2) ACHPRCTIME ");
			sql.append("    FROM HOURITEM AS A LEFT JOIN ( ");
			sql.append("        SELECT ");
			sql.append("        HOURLAP, ");
			sql.append("        SUM(OKCOUNT + FAILCOUNT + PENDCOUNT) AS TOTALCOUNT, SUM(OKCOUNT) AS OKCOUNT, ");
			sql.append("        SUM(FAILCOUNT) AS FAILCOUNT, SUM(PENDCOUNT) AS PENDCOUNT ");
			sql.append("        FROM TEMP ");
			sql.append("        GROUP BY HOURLAP ");
			sql.append("    ) AS B ON A.HOURLAP = B.HOURLAP ");
			sql.append("    WHERE A.HOURLAP IS NOT NULL ");
			sql.append("    ) AS TEMP_ ");
			System.out.println("sql = " + sql.toString().toUpperCase());	
			log.debug(sql.toString());
			countQuery.append(withTemp);
			countQuery.append("SELECT A.* FROM HOURITEM AS A ");
			countQuery.append("WHERE A.HOURLAP IS NOT NULL ");
			System.out.println("countQuery = " + countQuery.toString().toUpperCase());	
			List list1 =  onblocktab_Dao.getData(sql.toString(),HR_TXP_TIME.class);
			rtnMap.put("ROWS",(List<HR_TXP_TIME>) list1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
//-------------------資料轉換swagger輸出----------------------------------------------------
		ObjectMapper mapper = new ObjectMapper();
		HrTxpTimeRs result = mapper.convertValue(rtnMap, HrTxpTimeRs.class);
		return result;
	}
	
	
	public List<String> getConditionList(HrTxpTimeRq param){
		List<String> conditionList = new ArrayList<String>();
		List<String> conditions = new ArrayList<String>();
		String txDate = "";
		if(StrUtils.isNotEmpty(param.getTXDATE().trim())){
			txDate = param.getTXDATE().trim();
			//20151022 HUANGPU 改用交易日期查詢較合邏輯
			//conditions.add(" A.BIZDATE = '" + DateTimeUtils.convertDate(txDate, "yyyyMMdd", "yyyyMMdd") + "' ");
			conditions.add(" A.TXDATE = '" + DateTimeUtils.convertDate(txDate, "yyyyMMdd", "yyyyMMdd") + "' ");
		}
		
		String pcode = "";
		if(StrUtils.isNotEmpty(param.getPCODE().trim()) && !param.getPCODE().trim().equals("all")){
			pcode = param.getPCODE().trim();
			conditions.add(" A.PCODE = '" + pcode + "' ");
		}
		
		String opbkId = "";
		if(StrUtils.isNotEmpty(param.getOPBK_ID().trim()) && !param.getOPBK_ID().trim().equals("all")){
			opbkId = param.getOPBK_ID().trim();
			conditions.add(" (A.SENDERACQUIRE = '" + opbkId + "' OR A.OUTACQUIRE = '" + opbkId + "' OR A.INACQUIRE = '" + opbkId + "') ");
		}
		
		String bgbkId = "";
		if(StrUtils.isNotEmpty(param.getBGBK_ID().trim()) && !param.getBGBK_ID().trim().equals("all")){
			bgbkId = param.getBGBK_ID().trim();
			conditions.add(" (A.SENDERHEAD = '" + bgbkId + "' OR A.OUTHEAD = '" + bgbkId + "' OR A.INHEAD = '" + bgbkId + "') ");
		}
		
		String clearingPhase = "";
		if(StrUtils.isNotEmpty(param.getCLEARINGPHASE().trim()) && !param.getCLEARINGPHASE().trim().equals("all")){
			clearingPhase = param.getCLEARINGPHASE().trim();
			conditions.add(" A.CLEARINGPHASE = '" + clearingPhase + "' ");
		}
		
		conditionList.add( combine(conditions) );
		return conditionList;
	}
	
	public String combine(List<String> conditions){
		String conStr = "";
		for(int i = 0 ; i < conditions.size(); i++){
			conStr += conditions.get(i);
			if(i < conditions.size() - 1){
				conStr += " AND ";
			}
		}
		return conStr;
	}
}		