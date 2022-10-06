package com.fstop.eachadmin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.dto.BankGroupRq;
import com.fstop.eachadmin.dto.HrTxpTimeRq;
import com.fstop.eachadmin.dto.HrTxpTimeRs;
import com.fstop.eachadmin.service.BankGroupService;
import com.fstop.eachadmin.service.HrTxpTimeService;
import com.fstop.eachadmin.service.UndoneService;
import com.fstop.infra.entity.HR_TXP_TIME;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "每小時交易處理時間統計")
@RestController
@RequestMapping("api/HrTxpTime")
public class HrTxpTimeController {
	@Autowired
	private UndoneService UndoneS;
	@Autowired
	private HrTxpTimeService HrTxpTimeS;
	@Autowired
	private BankGroupService BankGroupS;
	// 操作行下拉選單
		@Operation(summary = "操作行 API", description = "操作行下拉選單資料")
		@GetMapping(value = "/opbkList")
		public List<Map<String, String>> opbkList () {
			return UndoneS.getOpbkList();
		}
	//交易類別下拉選單
		@Operation(summary = "交易類別 API", description = "交易類別下拉選單資料")
		@GetMapping(value = "/PcodeList")
		public List<Map<String, String>> PcodeList () {
			return HrTxpTimeS.getPcodeList();
		}
		
	//總行代號
		@Operation(summary = "總行代號 API", description = "<br>總行代號下拉選單資料</br>"+ "<br>\"OPBK_ID\" : \"0000012\",</br>" + "\"s_bizdate\" : \"01090121\"" )
		@PostMapping(value = "/BankGroup")
		public List<Map<String, String>> BgbkIdList(@RequestBody BankGroupRq param) {
			return BankGroupS.getByOpbkId_Single_Date(param);
		}
	//查詢
		@Operation(summary = "查詢 API", description = "<br>查詢按鈕</br>"+"<br>\"TXDATE\" : \"01090121\",</br>"+"\"PCODE\" : \"all\","+"<br>\"OPBK_ID\" : \"all\",</br>"+"\"BGBK_ID\" : \"all\","+"<br>\"CLEARINGPHASE\" : \"all\",</br>"+"\"PAGE\" : \"1\","+ "<br>\"SIDX\" : \"\",</br> "+ "\"SORD\" : \"\" ")
		@PostMapping(value = "/search")
		public HrTxpTimeRs getPageSearch (@RequestBody HrTxpTimeRq param) {
			return  HrTxpTimeS.pageSearch(param);
		}
	//列印匯出
		@Operation(summary = "列印匯出 API", description = "列印匯出資料按鈕")
		@PostMapping(value = "/print")
		public String c () {
			return "GG";
		}
}
