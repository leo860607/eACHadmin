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

public class CommonPageSearchRq {

	@JsonProperty("START_DATE")
	private String startDate;

	@JsonProperty("END_DATE")
	private String endDate;
	
	@JsonProperty("OSTAN")
	private String ostan;

	@JsonProperty("CLEARINGPHASE")
	private String clearingphase;
	
	@JsonProperty("OPBK_ID")
	private String opbkId;

	@JsonProperty("BGBK_ID")
	private String bgbkId;

	@JsonProperty("BUSINESS_TYPE_ID")
	private String businessTypeId;

	@JsonProperty("RESULTSTATUS")
	private String resultStatus;

	@JsonProperty("FILTER_BAT")
	private String filter_bat;
	
	@JsonProperty("SORD")
	private String sord;
	
	@JsonProperty("SIDX")
	private String sidx;
}
