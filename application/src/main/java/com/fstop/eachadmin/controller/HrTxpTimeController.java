package com.fstop.eachadmin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.service.BankGroupService;
import com.fstop.eachadmin.service.HrTxpTimeService;
import com.fstop.eachadmin.service.UndoneService;

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
		@Operation(summary = "總行代號 API", description = "總行代號下拉選單資料")
		@PostMapping(value = "/BankGroup")
		public List<Map<String, String>> BgbkIdList() {
			return BankGroupS.getBgbkIdList();
		}
	//查詢
		@Operation(summary = "查詢 API", description = "查詢按鈕")
		@PostMapping(value = "/search")
		public String b () {
			return "GG";
		}
	//列印匯出
		@Operation(summary = "列印匯出 API", description = "列印匯出資料按鈕")
		@PostMapping(value = "/print")
		public String c () {
			return "GG";
		}
}
