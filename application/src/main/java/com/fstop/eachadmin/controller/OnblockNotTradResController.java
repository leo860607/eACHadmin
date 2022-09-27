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
import com.fstop.eachadmin.dto.UndoneSendRq;
import com.fstop.eachadmin.dto.UndoneSendRs;
import com.fstop.eachadmin.service.NTRDetailService;
import com.fstop.eachadmin.service.UndoneService;
import com.fstop.infra.entity.UNDONE_TXDATA;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.v3.oas.annotations.parameters.*;

@Tag(name = "未完成交易查詢")
@RestController
@RequestMapping("api/NotTradRes")
public class OnblockNotTradResController {

	@Autowired
	private NTRDetailService NTRDetailS;
	
	@Autowired
	private UndoneService UndoneS;

	// 操作行下拉選單
	@Operation(summary = "操作行 API", description = "操作行下拉選單資料")
	@GetMapping(value = "/opbkList")
	public List<Map<String, String>> opbkList () {
		return UndoneS.getOpbkList();
	}

	// 總行業務下拉選單
    @Operation(summary = "業務 API", description = "總行業務下拉選單")
    @GetMapping(value = "/bsTypeIdList")
    public List<Map<String, String>> bsTypeIdList () {
        return UndoneS.getBsTypeIdList();
    }
    // 查詢
 	@Operation(summary = "查詢表單產出", description = "查詢按鈕(label),點選後依據篩選條件將需要的表單產出")
 	@PostMapping(value = "/pageSearch")
 	public PageSearchRs<UNDONE_TXDATA> pageSearch(@RequestBody PageSearchRq param) {
		return UndoneS.pageSearch(param);
	}

	// 明細
	@Operation(summary = "明細 API", description = "明細表單產出")
	@PostMapping(value = "/detail")
	public ObtkNtrRs detail(@RequestBody ObtkNtrRq param) {
		return NTRDetailS.showDetail(param);
	}

	// 請求傳送未完成交易結果(send_1406)
	@Operation(summary = "請求傳送未完成交易結果(1406)", description = "API功能說明")
	@PostMapping(value = "/send_1406data")
	public UndoneSendRs send_1406(@RequestBody UndoneSendRq param) {
		return UndoneS.send_1406(param);
	}

	// 請求傳送確認訊息(send_1400)
	@Operation(summary = "請求傳送確認訊息(1400)", description = "API功能說明")
	@PostMapping(value = "/send_1400data")
	public UndoneSendRs send_1400(@RequestBody UndoneSendRq param) {
		return UndoneS.send_1400(param);
	}
	// 票交所代為處理未完成交易(send)
	@Operation(summary = "票交所代為處理未完成交易(send)", description = "API功能說明")
	@PostMapping(value = "/send_data")
	public UndoneSendRs send(@RequestBody UndoneSendRq param) {
		return UndoneS.send(param);
	}

}
