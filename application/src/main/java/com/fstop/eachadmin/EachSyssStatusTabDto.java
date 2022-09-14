package com.fstop.eachadmin;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EachSyssStatusTabDto {

	@Schema(description = "交易日期時間")
	@JsonProperty("TXDT")
	private String TXDT;
	
	@Schema(description = "系統追蹤序號")
	@JsonProperty("STAN")
	private String STAN;
	
	@Schema(description = "發動行代號")
	@JsonProperty("SENDERBANKID")
	private String SENDERBANKID;
	
	@Schema(description = "扣款行/扣款帳號")
	@JsonProperty("OUTBANKID")
	private String OUTBANKID;
	
	@Schema(description = "入帳行/入帳帳號")
	@JsonProperty("INBANKID")
	private String INBANKID;
	
	@Schema(description = "發動者統編/簡稱")
	@JsonProperty("SENDERID")
	private String SENDERID;
	
	@Schema(description = "交易類別/交易代號")
	@JsonProperty("PCODE")
	private String PCODE;
	
	@Schema(description = "交易金額")
	@JsonProperty("TXAMT")
	private String TXAMT;
	
	@Schema(description = "錯誤類別")
	@JsonProperty("ERR_TYPE")
	private String ERR_TYPE;
	
}
