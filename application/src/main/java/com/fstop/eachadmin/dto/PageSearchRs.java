package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageSearchRs<UNDONE_TXDATA> {
	
	
	@JsonProperty("DATASUMLIST")
	private List<UNDONE_TXDATA> dataSumList;
	
	@JsonProperty("TOTAL")
	private String total;

	@JsonProperty("PAGE")
	private String page;
	
	@JsonProperty("RECORDS")
	private String records;
	
	@JsonProperty("ROWS")
	private List<UNDONE_TXDATA> rows;
	
//
//	@Data
//	@Getter
//	@Setter
//	@AllArgsConstructor
//	@NoArgsConstructor
//	public class UNDONE_TXDATA_DETAIL{
//		
//		@Schema(description = "交易日期")
//		@JsonProperty("txdt")
//		private String TXDT;
//		
//		@Schema(description = "交易類別")
//		@JsonProperty("pcode")
//		private String PCODE;
//		
//		@Schema(description = "交易代號")
//		@JsonProperty("txid")
//		private String TXID;
//		
//		@Schema(description = "發動者統編")
//		@JsonProperty("senderid")
//		private String SENDERID;
//		
//		@Schema(description = "系統追蹤序號")
//		@JsonProperty("stan")
//		private String STAN;
//		
//		@Schema(description = "發動行代號")
//		@JsonProperty("senderbankid")
//		private String SENDERBANKID;
//		
//		@Schema(description = "代號")
//		@JsonProperty("outbankid")
//		private String OUTBANKID;
//		
//		@Schema(description = "帳號")
//		@JsonProperty("outacctno")
//		private String OUTACCTNO;
//		
//		@Schema(description = "代號")
//		@JsonProperty("inbankid")
//		private String INBANKID;
//		
//		@Schema(description = "帳號")
//		@JsonProperty("inacctno")
//		private String INACCTNO;
//		
//		@Schema(description = "交易金額")
//		@JsonProperty("txamt")
//		private String TXAMT;
//		
//		
//	}
	
	
	


}
