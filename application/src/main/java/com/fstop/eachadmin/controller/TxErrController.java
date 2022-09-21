package com.fstop.eachadmin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.dto.TxErrDetailRs;
import com.fstop.eachadmin.dto.TxErrDetailRq;
import com.fstop.eachadmin.dto.TxErrRq;
import com.fstop.eachadmin.dto.TxErrRs;
import com.fstop.eachadmin.service.TxErrService;
import com.fstop.eachadmin.service.TxErrDetailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "交易異常處理狀況查詢")
@RestController
@RequestMapping("api/TxErr")
public class TxErrController {

	@Autowired
	private TxErrService txErrService;
	
	@Autowired
	private TxErrDetailService txErrDetailService;

	@Operation(summary = "查詢 API", description = "查詢按鈕")
	@GetMapping(value = "/search")
	public TxErrRs getPageSearch(@RequestBody TxErrRq param) {
		return txErrService.pageSearch(param) ;
	}
	
	@Operation(summary = "檢視明細 API", description = "檢視明細")
	@GetMapping(value = "/detail")
	public TxErrDetailRs detil(@RequestBody TxErrDetailRq param) {
		return txErrDetailService.showDetail(param);
	}
	
}
