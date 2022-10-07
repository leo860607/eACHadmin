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
public class countAndSumListRs {
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
}


