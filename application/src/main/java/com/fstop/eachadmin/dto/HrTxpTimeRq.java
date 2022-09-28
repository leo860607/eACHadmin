package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HrTxpTimeRq<Hr_Txp_Form> {
	@Schema(description = "交易日期")
	@JsonProperty("TXDATE")
	private String TXDATE;
	
	@Schema(description = "交易類別")
	@JsonProperty("PCODE")
	private String PCODE;
	
	@Schema(description = "操作行")
	@JsonProperty("OPBK_ID")
	private String OPBK_ID;
	
	@Schema(description = "總行代號")
	@JsonProperty("BGBK_ID")
	private String BGBK_ID;
	
	@Schema(description = "清算階段")
	@JsonProperty("CLEARINGPHASE")
	private String CLEARINGPHASE;
	
	@Schema(description = "")
	@JsonProperty("PAGE")
	private String page;
	
	@Schema(description = "")
	@JsonProperty("SIDX")
	private String sidx;
	
	@Schema(description = "")
	@JsonProperty("SORD")
	private String sord;
}
