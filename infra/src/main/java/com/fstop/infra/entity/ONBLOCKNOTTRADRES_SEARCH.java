package com.fstop.infra.entity;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//未完成交易結果--查詢--response
public class ONBLOCKNOTTRADRES_SEARCH {
	
	@Schema(description = "回覆代號")
	@JsonProperty("CONRESULTCODE")
	private String CONRESULTCODE;	
	
	@Schema(description = "交易結果")
	@JsonProperty("RESULTCODE")
	private String RESULTCODE;
	
	@Schema(description = "扣帳帳號")
	@JsonProperty("OUTACCT")
	private String OUTACCT;	
	
	@Schema(description = "系統追蹤序號")
	@JsonProperty("STAN")
	private String STAN;	
	
	@Schema(description = "入帳帳號")
	@JsonProperty("INACCT")
	private String INACCT;	
	
	@Schema(description = "交易日期/時間")
	@JsonProperty("TXDT")
	private String TXDT;
	
	@Schema(description = "交易類別")
	@JsonProperty("PCODE")
	private String PCODE;
	
	@Schema(description = "交易金額")
	@JsonProperty("TXAMT")
	private BigDecimal TXAMT;	
	
	@Schema(description = "發動行")
	@JsonProperty("SENDERBANKID")
	private String SENDERBANKID;	
	
	@Schema(description = "扣款行")
	@JsonProperty("OUTBANKID")
	private String OUTBANKID;
	
	@Schema(description = "入帳行")
	@JsonProperty("INBANKID")
	private String INBANKID;	

	@Schema(description = "交易日")
	@JsonProperty("TXDATE")
	private String TXDATE;
	

	///////////////////////////////////////////
	@Schema(description = "發動者統一編號")
	@JsonProperty("SENDERID")
	private String SENDERID;
	
	@Schema(description = "交易金額")
	@JsonProperty("NEWTXAMT")
	private String NEWTXAMT;
	
	@Schema(description = "交易日期時間")
	@JsonProperty("NEWTXDT")
	private String NEWTXDT;
	
	@Schema(description = "")
	@JsonProperty("TXID")
	private String TXID;
}
