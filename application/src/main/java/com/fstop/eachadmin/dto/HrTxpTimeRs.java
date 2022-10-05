package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HrTxpTimeRs {
	@Schema(description = "交易時間(每小時)")
	@JsonProperty("HOURLAPNAME")
	private String HOURLAPNAME;
	
	@Schema(description = "交易筆數")
	@JsonProperty("TOTALCOUNT")
	private String TOTALCOUNT;
	
	@Schema(description = "成功筆數")
	@JsonProperty("OKCOUNT")
	private String OKCOUNT;
	
	@Schema(description = "失敗筆數")
	@JsonProperty("FAILCOUNT")
	private String FAILCOUNT;
	
	@Schema(description = "逾時未回覆筆數")
	@JsonProperty("PENDCOUNT")
	private String PENDCOUNT;
	
	@Schema(description = "單筆平均處理時間(秒)")
	@JsonProperty("PRCTIME")
	private String PRCTIME;
	
	@Schema(description = "銀行每筆扣款平均處理時間(秒)")
	@JsonProperty("DEBITTIME")
	private String DEBITTIME;
	
	@Schema(description = "銀行每筆入帳平均處理時間(秒)")
	@JsonProperty("SAVETIME")
	private String SAVETIME;
	
	@Schema(description = "交換所平均處理時間(秒)")
	@JsonProperty("ACHPRCTIME")
	private String ACHPRCTIME;
}
