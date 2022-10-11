package com.fstop.eachadmin.dto;

import java.util.List;

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
public class CommonPageSearchRs<ONBLOCKNOTTRADRES_SEARCH, ONBKNOTTRADRES_SEARCH> {

	@Schema(description = "數據總和列表")
	@JsonProperty("DATASUMLIST")
	private List<ONBKNOTTRADRES_SEARCH> dataSumList;
	
	@Schema(description = "總和")
	@JsonProperty("TOTAL")
	private String total;

	@Schema(description = "頁面")
	@JsonProperty("PAGE")
	private String page;
	
	@Schema(description = "查詢結果總數筆")
	@JsonProperty("RECORDS")
	private String records;
	
	@Schema(description = "列表")
	@JsonProperty("ROWS")
	private List<ONBLOCKNOTTRADRES_SEARCH> rows;
	
	@Schema(description = "訊息")
	@JsonProperty("MSG")
	private String msg;
}
