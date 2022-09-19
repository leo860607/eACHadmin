package com.fstop.eachadmin.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.service.UndoneService;
import com.fstop.infra.entity.UNDONETXDATA;
import com.fstop.eachadmin.dto.PageSearchRq;
import com.fstop.eachadmin.dto.PageSearchRs;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "測試用")
@RestController
@RequestMapping("api/demo/search")
public class UndoneController {
	@Autowired
	private UndoneService undoneService;

	// 查詢
	@Operation(summary = "查詢表單產出", description = "查詢按鈕(label),點選後依據篩選條件將需要的表單產出")
	@PostMapping(value = "/pageSearch")
	public PageSearchRs<UNDONETXDATA> pageSearch(@RequestBody PageSearchRq param) {
		return undoneService.pageSearch(param);
	}

}
