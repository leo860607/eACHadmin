package com.fstop.eachadmin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExampleDto {
	@Schema(description = "會員名稱")
	@JsonProperty("STAN")
	private String STAN;
	@Schema(description = "會員代號")
	@JsonProperty("PCODE")
	private String PCODE;
	@JsonProperty("SENDERBANK")
	private String SENDERBANK;
	@JsonProperty("RECEIVERBANK")
	private String RECEIVERBANK;
	@JsonProperty("TXDT")
	private String TXDT;
	@JsonProperty("SENDERSTATUS")
	private String SENDERSTATUS;
	@JsonProperty("RECEIVERSTATUS")
	private String RECEIVERSTATUS;
	@JsonProperty("TIMEOUTCODE")
	private String TIMEOUTCODE;
	@JsonProperty("CONRESULTCODE")
	private String CONRESULTCODE;
	


	
	
}


