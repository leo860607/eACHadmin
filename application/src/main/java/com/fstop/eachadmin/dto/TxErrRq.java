package com.fstop.eachadmin.dto;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxErrRq<VW_ONBLOCKTAB> {
	
	@Schema(description = "營業日期")
	@JsonProperty("BIZDATE")
	private String BIZDATE;
	
	@Schema(description = "清算階段")
	@JsonProperty("CLEARINGPHASE")
	private String CLEARINGPHASE;
	
	@Schema(description = "頁數")
	@JsonProperty("PAGE")
	private String page;
	
	@Schema(description = "列表")
	@JsonProperty("ROWS")
	private List<VW_ONBLOCKTAB> rows;
	
	@Schema(description = "查詢結果總筆數")
	@JsonProperty("SIDX")
	private String sidx;
	
	@Schema(description = "交易金額")
	@JsonProperty("SORD")
	private String sord;
	
}
