package com.fstop.eachadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.TestRptService;
import com.fstop.eachadmin.service.OnblockNotTradResService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "未完成交易查詢")
@RestController
@RequestMapping("api/NotTradRes")
public class OnblockNotTradResController {

	@Autowired
	private OnblockNotTradResService OnblockNotTradResS;

	@Operation(summary = "操作行 API", description = "操作行下拉選單資料")
	@GetMapping(value = "/opbkList")
	public String opbkList() {
		return "bank_group_bo.getBgbkIdList()";
	}
}
