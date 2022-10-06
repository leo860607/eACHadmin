package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fstop.infra.entity.TX_ERR_ONBLOCKTAB;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TxErrRs {

	@Schema(description = "數據總和列表")
	@JsonProperty("DATASUMLIST")
	private List<TX_ERR_ONBLOCKTAB> dataSumList;

	@Schema(description = "總和")
	@JsonProperty("TOTAL")
	private Integer total;

	@Schema(description = "頁面")
	@JsonProperty("PAGE")
	private String page;

	@Schema(description = "紀錄")
	@JsonProperty("RECORDS")
	private Long records;

	@Schema(description = "列表")
	@JsonProperty("ROWS")
	private List<TxErrRsList> rows;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public class TxErrRsList {
		@Schema(description = "錯誤類別")
		@JsonProperty("ERR_TYPE")
		private String ERR_TYPE;
		
		@Schema(description = "交易日期")
		@JsonProperty("TXDATE")
		private String TXDATE;
		
		@Schema(description = "時間")
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
	}

}
