package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PageSearchRs<UNDONE_TXDATA> {

	@JsonProperty("DATASUMLIST")
	private List<UNDONE_TXDATA> dataSumList;

	@Schema(description = "")
	@JsonProperty("TOTAL")
	private Integer total;

	@Schema(description = "")
	@JsonProperty("PAGE")
	private Integer page;
	
	

	@Schema(description = "")
	@JsonProperty("RECORDS")
	private Integer records;

	@Schema(description = "")
	@JsonProperty("ROWS")
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
	private String TOTAL;
	private String ROWS;
	private String DATASUMLIST;
	
	
	
	
	

}
