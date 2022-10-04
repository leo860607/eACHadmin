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
	
//	@JsonProperty("Row")
//	private String row;

//	@JsonProperty("PAGE")
//	private String Page;

//
//	@JsonProperty("OPT_DATE")
//	private String opt_date;
//
//	@JsonProperty("OPT_DATE_2")
//	private String opt_date_2;
//
//	@JsonProperty("USER_COMPANY")
//	private String user_company;
//
//	@JsonProperty("SUSER_ID")
//	private String sUser_id;
//
//	@JsonProperty("FUNC_ID")
//	private String func_id;
//
//	@JsonProperty("USER_TYPE")
//	private String user_type;
//
//	@JsonProperty("ROLE_TYPE")
//	private String role_type;
//
//	@JsonProperty("SQL")
//	private String sql;
//
//	@JsonProperty("COUNTSQL")
//	private String countSql;

}
