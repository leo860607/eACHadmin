package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fstop.infra.entity.UndoneTxData;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PageSearchRs<UndoneTxData> {
	
	@JsonProperty("dataSumList")
	private List<UndoneTxData> dataSumList;
	
	@JsonProperty("total")
	private Integer total;
	
	@JsonProperty("page")
	private String page;
	
	@JsonProperty("records")
	private Integer records;
	
	@JsonProperty("rows")
	private List<UndoneTxData> rows;

}
