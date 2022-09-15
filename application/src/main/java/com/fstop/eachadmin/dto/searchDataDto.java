package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class searchDataDto {
	@Schema(description = "交易日期")
	@JsonProperty("START_DATE")
	private String START_DATE;
	
	@Schema(description = "交易日期")
	@JsonProperty("END_DATE")
	private String END_DATE;
	
	@Schema(description = "操作行代號")
	@JsonProperty("OPBK_ID")
	private String OPBK_ID;
	
	@Schema(description = "總行代號")
	@JsonProperty("BGBK_ID")
	private	String  BGBK_ID	;
	
	@Schema(description = "清算階段代號")
	@JsonProperty("CLEARINGPHASE")
    private String CLEARINGPHASE;
	
	@Schema(description = "業務類別代號")
	@JsonProperty("BUSINESS_TYPE_ID")
	private	String	BUSINESS_TYPE_ID;
	
	@Schema(description = "業務類別代號")
	@JsonProperty("RESULTSTATUS")
    private String RESULTSTATUS;
	
	@Schema(description = "交易追踨序號")
	@JsonProperty("OSTAN")
    private String OSTAN;	
}