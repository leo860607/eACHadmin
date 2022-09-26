package com.fstop.eachadmin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

//@Tag(name = "票交端操作紀錄查詢")
//@RestController
//@RequestMapping("api/UserlogTch")
public class UserlogTch {

	// 用戶代號下拉選單
	@Operation(summary = "用戶代號 API", description = "用戶代號下拉選單資料")
	@GetMapping(value = "/userIdList")
	public List<Map<String, String>> userIdList () {
//		return UndoneS.getOpbkList();
		return null;
	}
}
