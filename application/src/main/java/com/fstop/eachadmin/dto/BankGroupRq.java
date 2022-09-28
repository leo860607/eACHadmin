package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BankGroupRq {
	@Schema(description = "操作行")
	@JsonProperty("OPBK_ID")
	private String OPBK_ID;
	
	@Schema(description = "營業日期")
	@JsonProperty("s_bizdate")
	private String s_bizdate;
}
