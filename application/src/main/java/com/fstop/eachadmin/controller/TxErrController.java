package com.fstop.eachadmin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.dto.TxErrDto;
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
	@GetMapping(value = "/search")
	public TxErrDto getPageSearch(@RequestBody TxErrDto param) {
		return txErrService.pageSearch() ;
	}
	
	@Operation(summary = "檢視明細 API", description = "檢視明細")
	@GetMapping(value = "/detail")
	public Map((String txdate, String stan) getSearchByPk() {
		return TxErrService.searchByPk();
	}
	
	@Operation(summary = "未知 API", description = "未知")
	@GetMapping(value = "/detail")
	public String txerrunknown() {
		return "123";
	}
	
}
