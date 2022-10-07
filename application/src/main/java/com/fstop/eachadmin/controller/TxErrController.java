package com.fstop.eachadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.dto.TxErrDetailRq;
import com.fstop.eachadmin.dto.TxErrDetailRs;
import com.fstop.eachadmin.dto.TxErrRq;
import com.fstop.eachadmin.dto.TxErrRs;
import com.fstop.eachadmin.service.TxErrService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "交易異常處理狀況查詢")
@RestController
@RequestMapping("api/TxErr")
public class TxErrController {

	@Autowired
	private TxErrService txErrService;

	@Operation(summary = "查詢 API", description = "查詢按鈕")
	@PostMapping(value = "/search")
	public TxErrRs getPageSearch(@RequestBody @Validated TxErrRq param) {
		return txErrService.pageSearch(param) ;
	}
	
	@Operation(summary = "檢視明細 API", description = "檢視明細")
	@PostMapping(value = "/detail")
	public TxErrDetailRs detil(@RequestBody @Validated TxErrDetailRq param) {
		return txErrService.showDetail(param);
	}
	
}
