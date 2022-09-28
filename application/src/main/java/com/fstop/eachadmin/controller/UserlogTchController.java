package com.fstop.eachadmin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.dto.ObtkNtrRq;
import com.fstop.eachadmin.dto.ObtkNtrRs;
import com.fstop.eachadmin.dto.PageSearchRq;
import com.fstop.eachadmin.dto.PageSearchRs;
import com.fstop.eachadmin.service.BankGroupService;
import com.fstop.eachadmin.service.EachUserlogService;
import com.fstop.infra.entity.UNDONE_TXDATA;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "參加單位操作紀錄查詢")
@RestController
@RequestMapping("api/UserlogTch")
public class UserlogTchController {
	
	@Autowired
	private EachUserlogService EachUserlogS;
	
	@Autowired
	private BankGroupService BankGroupS;
	
	//用戶代號
	@Operation(summary = "用戶代號 API", description = "用戶代號下拉選單資料")
	@GetMapping(value = "/userIdList")
	public List<Map<String, String>> userIdList () {
		return EachUserlogS.getUserIdListByComId();
	}
	
	//用戶所屬單位
	@Operation(summary = "用戶所屬單位 API", description = "用戶所屬單位下拉選單資料")
	@GetMapping(value = "/userCompanyList")
	public List<Map<String, String>> userCompanyList () {
		return EachUserlogS.getUserCompanyList();
	}
	
//	//功能名稱
	//要有RsRq
	@Operation(summary = "功能名稱 API", description = "功能名稱下拉選單資料")
	@GetMapping(value = "/funcList")
	public List<Map<String, String>> funcList () {
		return EachUserlogS.getFuncList();
	}
	
	//群組類型
	@Operation(summary = "群組類型 API", description = "群組類型點選按鈕，分為銀行端與代理業者")
	@GetMapping(value = "/FuncListByRoleType")
	public List<Map<String, String>> FuncListByRoleType () {
		return EachUserlogS.getFuncListByRoleType();
	}
	
	//查詢按鈕
	@Operation(summary = "查詢表單產出", description = "查詢按鈕(label),點選後依據篩選條件將需要的表單產出")
 	@PostMapping(value = "/pageSearch")
 	public PageSearchRs<UNDONE_TXDATA> pageSearch(@RequestBody PageSearchRq param) {
		return UndoneS.pageSearch(param);
	}
	
	//檢視明細按鈕
 	@Operation(summary = "檢視明細 API", description = "檢視明細按鈕，點選後明細表單頁面顯示")
	@PostMapping(value = "/detail")
	public ObtkNtrRs detail(@RequestBody ObtkNtrRq param) {
		return NTRDetailS.showDetail(param);
	}
}
