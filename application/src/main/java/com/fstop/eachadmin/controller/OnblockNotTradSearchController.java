//package com.fstop.eachadmin.controller;
//
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.fstop.eachadmin.service.OnblockNotTradSearchService;
//import com.fstop.eachadmin.service.OnblockNotTradResService;
//import com.fstop.eachadmin.service.PageSearchOutput;
//import com.fstop.infra.entity.FormSearch;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.parameters.RequestBody;
//import io.swagger.v3.oas.annotations.tags.Tag;
//@Tag(name = "測試用")
//@RestController
//@RequestMapping("api/demo/search")
//public class OnblockNotTradSearchController {
//	@Autowired
//	private OnblockNotTradSearchService onblockNotTradSearchService;
//  //查詢
//  @Operation(summary = "查詢表單產出", description = "查詢按鈕(label),點選後依據篩選條件將需要的表單產出")
//  @PostMapping(value = "/pageSearch")
//  public PageSearchOutput pageSearch(@RequestBody Map<String, String> param){
//      return onblockNotTradSearchService.pageSearch(param);
//  }
// 
//
//}
//
