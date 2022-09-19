package com.fstop.eachadmin.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fstop.eachadmin.dto.TxErrRq;
import com.fstop.eachadmin.dto.TxErrRs;
import com.fstop.eachadmin.dto.SendRq;
import com.fstop.eachadmin.dto.SendRs;
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
	public TxErrRs getPageSearch(@RequestBody TxErrRq param) {
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
	
	 @Operation(summary = "請求傳送未完成交易結果(1406)", description = "API功能說明")
	 @GetMapping(value = "/send_1406data")
	 public SendRs send_1406(@RequestBody SendRq param){
	    return OnblockNotTradResS.send_1406(param);
	 }
	    
    //return json
    //controller
    @Operation(summary = "請求傳送確認訊息(1400)", description = "API功能說明")
    @GetMapping(value = "/send_1400data")
    public SendRs send_1400(@RequestBody SendRq param){
        return OnblockNotTradResS.send_1400( param);
	
}
