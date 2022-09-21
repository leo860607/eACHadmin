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
	
	@JsonProperty("detailDataMap")
	private Map detailDataMap;
	
	private Map DetailData;
	private Map FILTER_BAT;
	private String CLEARINGPHASE;
	private String BIZDATE;
	private String TXDT;
	private String sourcePage;
	private String EACHDT;
	private String NEWTXAMT;
	private String PCODE_DESC;
	private String TXN_NAME;
	private String TXN_TYPE;
	private String SENDERBANK_NAME;
	private String RECEIVERBANK_NAME;
	private String SENDERID;
	private String RECEIVERID;
	private String INACCTNO;
	private String INID;
	private String OUTACCTNO;
	private String OUTID;
	private String SENDERBANKID_NAME;
	private String NEWSENDERFEE;
	private String INBANKID_NAME;
	private String NEWINFEE;
	private String OUTBANKID_NAME;
	private String NEWOUTFEE;
	private String WOBANKID_NAME;
	private String NEWWOFEE;
	private String REFUNDDEADLINE;
	private String NEWEACHFEE;
	private String TIMEOUTCODE;
	private String NEWFEE;
	private String FLBIZDATE;
	private String FLBATCHSEQ;
	private String FLPROCSEQ;
	private String DATASEQ;
	

	
	
}
