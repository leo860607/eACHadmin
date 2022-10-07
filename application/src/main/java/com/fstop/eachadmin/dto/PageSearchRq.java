package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageSearchRq {

	@JsonProperty("FILTER_BAT")
	private String filter_bat;

	@JsonProperty("STARTDATE")
	private String startDate;

	@JsonProperty("ENDDATE")
	private String endDate;

	@JsonProperty("CLEARINGPHASE")
	private String clearingphase;

	@JsonProperty("BGBKID")
	private String bgbkId;

	@JsonProperty("BUSINESSTYPEID")
	private String businessTypeId;

	@JsonProperty("OSTAN")
	private String ostan;

	@JsonProperty("RESULTCODE")
	private String ResultCode;
	
	@JsonProperty("OPBKID")
	private String opbkId;

	@JsonProperty("PAGENO")
	private Integer pageNo;

	@JsonProperty("PAGESIZE")
	private Integer pageSize;

	@JsonProperty("SORD")
	private String sord;
	
	@JsonProperty("SIDX")
	private String sidx;
	
	@JsonProperty("RESULTSTATUS")
	private String resultStatus;
	
	
	@JsonProperty("ISSEARCH")
    private String isSearch ;
	
	@JsonProperty("serchStrs")
    private String serchStrs ;
	


}
