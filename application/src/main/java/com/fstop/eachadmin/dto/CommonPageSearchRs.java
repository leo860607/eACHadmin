package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommonPageSearchRs<ONBLOCKNOTTRADRES_SEARCH> {

	@JsonProperty("DATASUMLIST")
	private List<ONBLOCKNOTTRADRES_SEARCH> dataSumList;
	
	@JsonProperty("TOTAL")
	private String total;

	@JsonProperty("PAGE")
	private String page;
	
	@JsonProperty("RECORDS")
	private String records;
	
	@JsonProperty("ROWS")
	private List<ONBLOCKNOTTRADRES_SEARCH> rows;
	
	@JsonProperty("MSG")
	private String msg;
}
