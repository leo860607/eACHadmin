package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PageSearchRs<UNDONE_TXDATA> {

	@JsonProperty("dataSumList")
	private List<UNDONE_TXDATA> dataSumList;

	@JsonProperty("total")
	private Integer total;

	@JsonProperty("page")
	private String page;

	@JsonProperty("records")
	private Integer records;

	@JsonProperty("rows")
	private List<UNDONE_TXDATA> rows;

}
