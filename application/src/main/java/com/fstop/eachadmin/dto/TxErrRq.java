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
	
	@Schema(description = "")
	@JsonProperty("PAGE")
	private String page;
	
	@Schema(description = "")
	@JsonProperty("ROWS")
	private List<VW_ONBLOCKTAB> rows;
	
	@Schema(description = "")
	@JsonProperty("SIDX")
	private String sidx;
	
	@Schema(description = "")
	@JsonProperty("SORD")
	private String sord;
	
}
