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
	
	
	@JsonProperty("TXTIME_1")
	private String TXTIME_1;
	@JsonProperty("TXTIME_2")
	private String TXTIME_2;
	@JsonProperty("USER_COMPANY")
	private String USER_COMPANY;
	@JsonProperty("USERID")
	private String USERID;
	@JsonProperty("FUNC_ID")
	private String FUNC_ID;
	@JsonProperty("USER_TYPE")
	private String USER_TYPE;
	@JsonProperty("ROLE_TYPE")
	private String ROLE_TYPE;
	

}
