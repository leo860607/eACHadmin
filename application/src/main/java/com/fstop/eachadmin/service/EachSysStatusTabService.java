package com.fstop.eachadmin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fstop.eachadmin.dto.EachSysStatusTabRq;
import com.fstop.eachadmin.dto.EachSysStatusTabRs;
import com.fstop.eachadmin.dto.UndoneSendRs;
import com.fstop.eachadmin.repository.EachSysStatusTabRepository;
import com.fstop.infra.entity.EACHSYSSTATUSTAB;
import com.fstop.infra.entity.UNDONE_TXDATA;

import util.DateTimeUtils;

import util.StrUtils;
import util.zDateHandler;
@Service
public class EachSysStatusTabService {
	@Autowired
	private EachSysStatusTabRepository eachSysStatusTabRepository;
	@Autowired
	private WkDateService WkDateService;
	
	public EachSysStatusTabRs<EACHSYSSTATUSTAB> checkBizDate(EachSysStatusTabRq params){
		String activeDate = StrUtils.isNotEmpty(params.getActiveDate()) ?params.getActiveDate() : "";
		String bizDate = "";
		Map<String,String> retmap = new HashMap<String,String>();
		if(StrUtils.isEmpty(activeDate)){
			retmap.put("result", "FALSE");
			retmap.put("msg", "activeDate 不可為空值");
//			json = JSONUtils.map2json(retmap);
//			return json;
		}
		bizDate = getBusinessDate();
		activeDate = DateTimeUtils.convertDate(DateTimeUtils.INTERCONVERSION, activeDate, "yyyyMMdd", "yyyyMMdd");
		if(checkBizDate(activeDate)){
			retmap.put("result", "TRUE");
			retmap.put("msg", "activeDate:"+activeDate+"小於"+bizDate+"");
		}else{
			retmap.put("result", "FALSE");
			retmap.put("msg", "activeDate:"+activeDate+"大於"+bizDate+"");
		}
//		json = JSONUtils.map2json(retmap);
//		
//		return json;
		ObjectMapper mapper = new ObjectMapper();
		EachSysStatusTabRs result1 = mapper.convertValue(retmap, EachSysStatusTabRs.class);
		return result1;
	}
	
	/**
	 * 輸入日期與營業日做比較
	 * @param activeDate 20150101 , 
	 * @param compareWay > , <  , = , >= , <=
	 * @return true or false
	 */
	public EachSysStatusTabRs<EACHSYSSTATUSTAB> checkBizDate2(EachSysStatusTabRq params){
		String activeDate = StrUtils.isNotEmpty(params.getActiveDate()) ?params.getActiveDate() : "";
		String compareWay = StrUtils.isNotEmpty(params.getCompareWay()) ?params.getCompareWay() : "";
		System.out.println("activeDate : "+activeDate);
		System.out.println("compareWay : "+compareWay);
		String bizDate = "";
		Map<String,String> retmap = new HashMap<String,String>();
		if(StrUtils.isEmpty(activeDate)){
			retmap.put("result", "FALSE");
			retmap.put("msg", "activeDate 不可為空值");
//			json = JSONUtils.map2json(retmap);
//			return json;
		}
		bizDate = getBusinessDate();
//		activeDate = DateTimeUtils.convertDate(DateTimeUtils.INTERCONVERSION, activeDate, "yyyyMMdd", "yyyyMMdd");
		System.out.println("bizDate : "+ bizDate );
		System.out.println("activeDate convertDate : "+ activeDate );
		int result = zDateHandler.compareDiffDate( activeDate, bizDate , "yyyyMMdd");
		switch (compareWay) {
		case ">":
			if(result==1) {
				retmap.put("result", "TRUE");
				retmap.put("msg", "activeDate:"+activeDate+"大於"+bizDate+"");
			}else{
				retmap.put("result", "FALSE");
				retmap.put("msg", "activeDate:"+activeDate+"小於等於"+bizDate+"");
			}
			break;
		case "=":
			if(result==0) {
				retmap.put("result", "TRUE");
				retmap.put("msg", "activeDate:"+activeDate+"等於"+bizDate+"");
			}else{
				retmap.put("result", "FALSE");
				retmap.put("msg", "activeDate:"+activeDate+"不等於"+bizDate+"");
			}
			break;
		case "<":
			if(result==0) {
				retmap.put("result", "TRUE");
				retmap.put("msg", "activeDate:"+activeDate+"小於"+bizDate+"");
			}else{
				retmap.put("result", "FALSE");
				retmap.put("msg", "activeDate:"+activeDate+"大於等於"+bizDate+"");
			}
			break;
		case ">=":
			if(result==0||result==1) {
				retmap.put("result", "TRUE");
				retmap.put("msg", "activeDate:"+activeDate+"大於等於"+bizDate+"");
			}else{
				retmap.put("result", "FALSE");
				retmap.put("msg", "activeDate:"+activeDate+"小於"+bizDate+"");
			}
			break;
		case "<=":
			if(result==0||result==-1) {
				retmap.put("result", "TRUE");
				retmap.put("msg", "activeDate:"+activeDate+"小於等於"+bizDate+"");
			}else{
				retmap.put("result", "FALSE");
				retmap.put("msg", "activeDate:"+activeDate+"大於"+bizDate+"");
			}
			break;
		}
//		json = JSONUtils.map2json(retmap);
//		
//		return json;
		ObjectMapper mapper = new ObjectMapper();
		EachSysStatusTabRs result1 = mapper.convertValue(retmap, EachSysStatusTabRs.class);
		return result1;
	}
	
	
	/**
	 * 輸入日期與前一營業日做比較
	 * @param activeDate 20150101 , 
	 * @param compareWay > , <  , = , >= , <=
	 * @return true or false
	 */
	public EachSysStatusTabRs<EACHSYSSTATUSTAB> checkBizDate3(EachSysStatusTabRq params){
		String activeDate = StrUtils.isNotEmpty(params.getActiveDate()) ?params.getActiveDate() : "";
		String compareWay = StrUtils.isNotEmpty(params.getCompareWay()) ?params.getCompareWay() : "";
		System.out.println("activeDate : "+activeDate);
		System.out.println("compareWay : "+compareWay);
		String bizDate = "";
		Map<String,String> retmap = new HashMap<String,String>();
		if(StrUtils.isEmpty(activeDate)){
			retmap.put("result", "FALSE");
			retmap.put("msg", "activeDate 不可為空值");
//			json = JSONUtils.map2json(retmap);
//			return json;
		}
		bizDate = getThisBusinessDate();
//		activeDate = DateTimeUtils.convertDate(DateTimeUtils.INTERCONVERSION, activeDate, "yyyyMMdd", "yyyyMMdd");
		System.out.println("bizDate : "+ bizDate );
		System.out.println("activeDate convertDate : "+ activeDate );
		int result = zDateHandler.compareDiffDate( activeDate, bizDate , "yyyyMMdd");
		switch (compareWay) {
		case ">":
			if(result==1) {
				retmap.put("result", "TRUE");
				retmap.put("msg", "activeDate:"+activeDate+"大於"+bizDate+"");
			}else{
				retmap.put("result", "FALSE");
				retmap.put("msg", "activeDate:"+activeDate+"小於等於"+bizDate+"");
			}
			break;
		case "=":
			if(result==0) {
				retmap.put("result", "TRUE");
				retmap.put("msg", "activeDate:"+activeDate+"等於"+bizDate+"");
			}else{
				retmap.put("result", "FALSE");
				retmap.put("msg", "activeDate:"+activeDate+"不等於"+bizDate+"");
			}
			break;
		case "<":
			if(result==0) {
				retmap.put("result", "TRUE");
				retmap.put("msg", "activeDate:"+activeDate+"小於"+bizDate+"");
			}else{
				retmap.put("result", "FALSE");
				retmap.put("msg", "activeDate:"+activeDate+"大於等於"+bizDate+"");
			}
			break;
		case ">=":
			if(result==0||result==1) {
				retmap.put("result", "TRUE");
				retmap.put("msg", "activeDate:"+activeDate+"大於等於"+bizDate+"");
			}else{
				retmap.put("result", "FALSE");
				retmap.put("msg", "activeDate:"+activeDate+"小於"+bizDate+"");
			}
			break;
		case "<=":
			if(result==0||result==-1) {
				retmap.put("result", "TRUE");
				retmap.put("msg", "activeDate:"+activeDate+"小於等於"+bizDate+"");
			}else{
				retmap.put("result", "FALSE");
				retmap.put("msg", "activeDate:"+activeDate+"大於"+bizDate+"");
			}
			break;
		}
//		json = JSONUtils.map2json(retmap);
//		
//		return json;
		ObjectMapper mapper = new ObjectMapper();
		EachSysStatusTabRs result1 = mapper.convertValue(retmap, EachSysStatusTabRs.class);
		return result1;
	}
	
	/**
	 * 檢查日期是否小於營業日
	 * @param activeDate 20150101
	 * @return 小於或等於>>true 大於 false 
	 */
	public boolean checkBizDate(String activeDate){
		List<EACHSYSSTATUSTAB> list = null;
		String businessDate = "";
		boolean result = false ; 
		int tmp  = -1;
		System.out.println("activeDate>>"+activeDate);
		try{
			list = WkDateService.getEachsysstatustab_Dao().getThisBusinessDate();
			if(list != null && list.size() > 0){
				businessDate = list.get(0).getBUSINESS_DATE();
				tmp = zDateHandler.compareDiffDate( activeDate, businessDate , "yyyyMMdd");
			}
			if(tmp <= 0){
				result = true ; 
			}
		}catch(Exception e){
			e.printStackTrace();
			result = false ; 
		}
		System.out.println("result>>"+result);
		return result;
		
	}
	
	
	
	/**
	 * 依照DATEMODE取得目前營業日
	 * @return
	 */
	public String getBusinessDate(){
		List<EACHSYSSTATUSTAB> list = null;
		String businessDate = "";
		try{
			list = eachSysStatusTabRepository.getThisBusinessDate();
			if(list != null && list.size() > 0){
				businessDate = list.get(0).getBUSINESS_DATE();
				businessDate = DateTimeUtils.convertDate(businessDate, "yyyyMMdd", "yyyyMMdd");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return businessDate;
	}

	/**
	 * 依照DATEMODE取得目前營業日
	 * @return
	 */
	public String getBusinessDateII(){
		List<EACHSYSSTATUSTAB> list = null;
		String businessDate = "";
		try{
			list = eachSysStatusTabRepository.getThisBusinessDate();
			if(list != null && list.size() > 0){
				businessDate = list.get(0).getBUSINESS_DATE();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return businessDate;
	}
	/**
	 * 取得報表所需的預設營業日期
	 * @return
	 */
	public String getRptBusinessDate(){
		List<EACHSYSSTATUSTAB> list = null;
		String businessDate = "";
		boolean isTxnDate = false;
		try{
			isTxnDate = WkDateService.isTxnDate();
			list = eachSysStatusTabRepository.getRptBusinessDate(isTxnDate, isTxnDate);
			if(list != null && list.size() > 0){
				businessDate = list.get(0).getBUSINESS_DATE();
				businessDate = DateTimeUtils.convertDate(businessDate, "yyyyMMdd", "yyyyMMdd");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return businessDate;
	}
	
	/**
	 * 執意取得次營業日
	 * @param params
	 * @return
	 */
	public String getNextBusinessDate(){
		List<EACHSYSSTATUSTAB> list = null;
		String businessDate = "";
		try{
			list = eachSysStatusTabRepository.getNextBusinessDate();
			if(list != null && list.size() > 0){
				businessDate = list.get(0).getNEXTDATE();
				businessDate = DateTimeUtils.convertDate(businessDate, "yyyyMMdd", "yyyyMMdd");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return businessDate;
	}
	
	/**
	 * 執意取得THISDATE
	 * @param params
	 * @return
	 */
	public String getThisBusinessDate(){
		List<EACHSYSSTATUSTAB> list = null;
		String businessDate = "";
		try{
			list = eachSysStatusTabRepository.getThisBusinessDate();
			if(list != null && list.size() > 0){
				businessDate = list.get(0).getTHISDATE();
				businessDate = DateTimeUtils.convertDate(businessDate, "yyyyMMdd", "yyyyMMdd");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return businessDate;
	}
	
	/**
	 * 執意取得現在營業日(THIS_DATE)
	 * @param params
	 * @return
	 */
	public String getThisBusinessDate(int mode){
		List<EACHSYSSTATUSTAB> list = null;
		String businessDate = "";
		try{
			list = eachSysStatusTabRepository.getNextBusinessDate();
			if(list != null && list.size() > 0){
				businessDate = list.get(0).getTHISDATE();
				businessDate = DateTimeUtils.convertDate(mode, businessDate, "yyyyMMdd", "yyyyMMdd");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return businessDate;
	}
	
	/**
	 * 執意取得目前清算階段
	 * @param params
	 * @return
	 */
	public String getClearingphase(){
		List<EACHSYSSTATUSTAB> list = null;
		String clrPhase = "";
		try{
			list = eachSysStatusTabRepository.getNextBusinessDate();
			if(list != null && list.size() > 0){
				clrPhase = list.get(0).getCLEARINGPHASE();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return clrPhase;
	}
	
	public Map getRowData(){
		List<EACHSYSSTATUSTAB> list = null;
		Map data = new HashMap();
		try{
			list = eachSysStatusTabRepository.getNextBusinessDate();
			if(list != null && list.size() > 0){
				data = BeanUtils.describe(list.get(0));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return data;
	}
	
	public EachSysStatusTabRepository getEachsysstatustab_Dao() {
		return eachSysStatusTabRepository;
	}

	public void setEachsysstatustab_Dao(EachSysStatusTabRepository eachsysstatustab_Dao) {
		this.eachSysStatusTabRepository = eachsysstatustab_Dao;
	}
	public WkDateService getWkDateService() {
		return WkDateService;
	}
	public void setWkDateService(WkDateService WkDateService) {
		this.WkDateService =WkDateService;
	}
	
	
}
