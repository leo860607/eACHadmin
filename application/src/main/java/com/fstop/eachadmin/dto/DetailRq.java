package com.fstop.eachadmin.dto;

import java.util.Map;

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
public class DetailRq {
	
	@Schema(description = "")
	@JsonProperty("STAN")
	private String STAN;
	
	@JsonProperty("TXDATE")
	private String TXDATE;
	
	@JsonProperty("bizdate")
	private String bizdate;
	
	
	private String EXTENDFEE;
	private String NEWEXTENDFEE;
	private String FEE_TYPE;
	private String RESP;
	private String NEWSENDERFEE_NW;
	
	private String NEWSENDERFEE;
	private String NEWINFEE;
	private String NEWOUTFEE;
	private String NEWWOFEE;
	private String NEWEACHFEE;
	private String SENDERID;
	private String TXN_NAME;
	private String SENDERBANKID_NAME;
	private String NEWTXAMT;
	private String FILTER_BAT;
	

	
	
}
