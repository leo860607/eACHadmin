package com.fstop.eachadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.service.OnblockNotTradResService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "交易異常處理狀況查詢")
@RestController
@RequestMapping("api/TxErr")
public class TxErrController {

	@Autowired
	private OnblockNotTradResService OnblockNotTradResS;

	@Operation(summary = "查詢 API", description = "查詢")
	@GetMapping(value = "/search")
	public String txerrList() {
		return "123";
	}
	
	@Operation(summary = "檢視明細 API", description = "檢視明細")
	@GetMapping(value = "/detail")
	public String txerrdetailList() {
		return "123";
	}
	
	@Operation(summary = "未知 API", description = "未知")
	@GetMapping(value = "/detail")
	public String txerrunknown() {
		return "123";
	}
	
}
