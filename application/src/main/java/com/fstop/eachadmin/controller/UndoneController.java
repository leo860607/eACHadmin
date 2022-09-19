package com.fstop.eachadmin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.service.OnblockNotTradResService;
import com.fstop.eachadmin.service.UndoneService;
import com.fstop.infra.entity.UNDONE_TXDATA;
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
	private OnblockNotTradResService OnblockNotTradResS;
	@Autowired 
	private UndoneService undoneService;

	@Operation(summary = "操作行 API", description = "操作行下拉選單資料")
	@GetMapping(value = "/opbkList")
	public List<Map<String, String>> opbkList () {
		return undoneService.getOpbkList();
	}
    
    @Operation(summary = "業務 API", description = "總行業務下拉選單")
    @GetMapping(value = "/bsTypeIdList")
    public List<Map<String, String>> bsTypeIdList () {
        return undoneService.getBsTypeIdList();
    }

	// 查詢
	@Operation(summary = "查詢表單產出", description = "查詢按鈕(label),點選後依據篩選條件將需要的表單產出")
	@PostMapping(value = "/pageSearch")
	public PageSearchRs<UNDONE_TXDATA> pageSearch(@RequestBody PageSearchRq param) {
		return undoneService.pageSearch(param);
	}

}
