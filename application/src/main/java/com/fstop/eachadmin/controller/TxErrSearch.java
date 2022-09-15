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
@RequestMapping("api/TxErr/Detail")
public class TxErrSearch {

	@Autowired
	private OnblockNotTradResService OnblockNotTradResS;

	@Operation(summary = "檢視明細 API", description = "檢視明細")
	@GetMapping(value = "/txErrDetail")
	public String txerrdetailList() {
		return "123";
	}
	
}
