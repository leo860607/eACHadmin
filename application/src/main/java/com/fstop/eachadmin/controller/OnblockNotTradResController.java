package com.fstop.eachadmin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.dto.UndoneSendRq;
import com.fstop.eachadmin.dto.UndoneSendRs;

import com.fstop.eachadmin.dto.searchDataDto;
import com.fstop.eachadmin.service.OnblockNotTradResService;
import com.fstop.eachadmin.service.UndoneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "未完成交易查詢")
@RestController
@RequestMapping("api/NotTradRes")
public class OnblockNotTradResController {

	@Autowired
	private OnblockNotTradResService OnblockNotTradResS;

	@Operation(summary = "操作行 API", description = "操作行下拉選單資料")
	@GetMapping(value = "/opbkList")
	public List<Map<String, String>> opbkList () {
		return OnblockNotTradResS.getOpbkList();
	}
    
    @Operation(summary = "業務 API", description = "總行業務下拉選單")
    @GetMapping(value = "/bsTypeIdList")
    public List<Map<String, String>> bsTypeIdList () {
        return OnblockNotTradResS.getBsTypeIdList();
    }
    

    
//  @Operation(summary = "帳單明細 API", description = "API功能說明")
//  @PostMapping(value = "/billdata")
//  public String  billData(@RequestBody SearchDataDto param){
//      return "ok";
//  }
    
    
  //return json
    //controller
    @Operation(summary = "請求傳送未完成交易結果(1406)", description = "API功能說明")
    @GetMapping(value = "/send_1406data")
    public UndoneSendRs send_1406(@RequestBody UndoneSendRq param){
    	return UndoneService.send_1406(param);
    }
    
    //return json
    //controller
    @Operation(summary = "請求傳送確認訊息(1400)", description = "API功能說明")
    @GetMapping(value = "/send_1400data")
    public UndoneSendRs send_1400(@RequestBody UndoneSendRq param){
        return UndoneService.send_1400(param);
}
}
