package com.fstop.eachadmin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;



import com.fstop.eachadmin.dto.PageSearchRq;
import com.fstop.eachadmin.dto.PageSearchRs;
import com.fstop.eachadmin.dto.UserIdRs;
import com.fstop.eachadmin.dto.UserCompanyRs;
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
	
	
	//用戶代號
	@Operation(summary = "用戶代號 API", description = "用戶代號下拉選單資料")
	@GetMapping(value = "/userIdList")
	public List<UserIdRs> userIdList (@RequestParam String comId) {
		return EachUserlogS.getUserIdListByComId(comId);
	}
	
	//用戶所屬單位
//	@Operation(summary = "用戶所屬單位 API", description = "用戶所屬單位下拉選單資料")
//	@GetMapping(value = "/userCompanyList")
//	public List<UserCompanyRs> userCompanyList () {
//		return EachUserlogS.getUserCompanyList();
//	}
	
	//用戶所屬單位
	@Operation(summary = "用戶所屬單位 API", description = "用戶所屬單位下拉選單資料")
	@GetMapping(value = "/bgbkListByUserType")
	public List<Map<String, String>> bgbkListByUserType (@RequestParam String userType) {
		//輸入"B or C"
		return EachUserlogS.getBgbkListByUser_Type(userType);
	}
	
	//功能名稱
	//要有RsRq
	@Operation(summary = "功能名稱 API", description = "功能名稱下拉選單資料")
	@GetMapping(value = "/funcList")
	public List<Map<String, Object>> funcList (@RequestParam String roleType) {
		return EachUserlogS.getFuncListByRole_Type(roleType);
	}
	
		
	//查詢按鈕
//	@Operation(summary = "查詢表單產出", description = "查詢按鈕(label),點選後依據篩選條件將需要的表單產出")
// 	@PostMapping(value = "/pageSearch")
// 	public PageSearchRs<UNDONE_TXDATA> pageSearch(@RequestBody PageSearchRq param) {
//		return EachUserlogS.pageSearch(param);
//	}
 	
//	
//	//檢視明細按鈕
// 	@Operation(summary = "檢視明細 API", description = "檢視明細按鈕，點選後明細表單頁面顯示")
//	@PostMapping(value = "/detail")
//	public List<Map<String, String>>detail() {
//		return EachUserlogS.showDetail();
//	}
}
