package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxErrDto {
	
	@Schema(description = "營業日期")
	@JsonProperty("BIZDATE")
	private String BIZDATE;
	
	@Schema(description = "清算階段")
	@JsonProperty("CLEARINGPHASE")
	private String CLEARINGPHASE;
	
	
}
