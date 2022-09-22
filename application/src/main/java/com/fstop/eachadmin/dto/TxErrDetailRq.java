package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TxErrDetailRq {
	
	@Schema(description = "")
	@JsonProperty("STAN")
	private String STAN;
	
	@JsonProperty("TXDATE")
	private String TXDATE;
	@Schema(description = "")
	@JsonProperty("ac_key")
	private	String	ac_key;
	@JsonProperty("target")
	private	String	target;

	private String EXTENDFEE;
	private String FEE_TYPE;
	private String RESP;
	private String NEWSENDERFEE;
	private String NEWINFEE;
	private String NEWOUTFEE;
	private String NEWWOFEE;
	private String NEWEACHFEE;
	private String NEWFEE;
	private String SENDERBANKID_NAME;
	private String TXN_NAME;
	
}
