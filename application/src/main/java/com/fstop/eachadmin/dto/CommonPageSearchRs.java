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
public class CommonPageSearchRs<T, O> {

	@JsonProperty("DATASUMLIST")
	private List<T> dataSumList;
	
	@JsonProperty("TOTAL")
	private String total;

	@JsonProperty("PAGE")
	private String page;
	
	@JsonProperty("RECORDS")
	private String records;
	
	@JsonProperty("ROWS")
	private List<O> rows;
	
	@JsonProperty("MSG")
	private String msg;
}
