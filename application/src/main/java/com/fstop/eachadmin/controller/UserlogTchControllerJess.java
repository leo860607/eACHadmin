package com.fstop.eachadmin.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.dto.UserlogTchDetailRq;
import com.fstop.eachadmin.dto.UserlogTchFunRs;
import com.fstop.eachadmin.dto.UserlogTchSearchRq;
import com.fstop.eachadmin.dto.UserlogTchSearchRs;
import com.fstop.eachadmin.dto.UserlogTchUserIdRs;
import com.fstop.eachadmin.service.UserlogTchService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "票交端操作紀錄查詢")
@RestController
@RequestMapping("api/UserlogTch")
public class UserlogTchControllerJess {

	@Autowired
	private UserlogTchService userlogTchService;
	
	// 用戶代號下拉選單
	@Operation(summary = "用戶代號 API", description = "用戶代號下拉選單")
	@GetMapping(value = "/userIdList")
	public List<UserlogTchUserIdRs> userIdList () {
		return userlogTchService.getUserIdListByComId("0188888");
	}
	
	// 功能名稱下拉選單
	@Operation(summary = "功能名稱 API", description = "功能名稱下拉選單")
	@GetMapping(value = "/funcList")
	public List<UserlogTchFunRs> funcList () {
		return userlogTchService.getFuncList();
	}
	
	// 送出表單進行查詢
	@Operation(summary = "查詢按鈕 API", description = "送出表單進行查詢")
	@PostMapping(value = "/serchList")
	public List<UserlogTchSearchRs> serchList (@RequestBody UserlogTchSearchRq param) {
		return userlogTchService.pageSearch(param);
	}
	
	// 送出表單進行查詢
	@Operation(summary = "檢視明細按鈕 API", description = "送出表單進行查詢")
	@PostMapping(value = "/detailList")
	public List<UserlogTchSearchRs> detailList (@RequestBody UserlogTchDetailRq param) {
		return userlogTchService.getDetail(param);
	}
	
}
