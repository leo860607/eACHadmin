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
	
	@Schema(description = "數據總和列表")
	@JsonProperty("DATASUMLIST")
	private List<UNDONE_TXDATA> dataSumList;
	
	@Schema(description = "總和")
	@JsonProperty("TOTAL")
	private String total;

	@Schema(description = "頁面")
	@JsonProperty("PAGE")
	private String page;
	
	@Schema(description = "查詢結果總筆數")
	@JsonProperty("RECORDS")
	private String records;
	
	@Schema(description = "列表")
	@JsonProperty("ROWS")
	private List<UNDONE_TXDATA> rows;


}
