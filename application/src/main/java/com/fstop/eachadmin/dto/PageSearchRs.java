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
	


}
