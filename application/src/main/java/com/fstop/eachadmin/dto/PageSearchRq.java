package com.fstop.eachadmin.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public  class PageSearchRq  {
	@Schema(description = "交易日期")
	@JsonProperty("filter_bat")
	private String filter_bat;

	@JsonProperty("startDate")
	private String startDate;

	@JsonProperty("endDate")
	private String endDate;

	@JsonProperty("clearingphase")
	private String clearingphase;

	@JsonProperty("bgbkId")
	private String bgbkId;

	@JsonProperty("businessTypeId")
	private String businessTypeId;

	@JsonProperty("ostan")
	private String ostan;

	@JsonProperty("opbkId")
	private String opbkId;

	@JsonProperty("pageNo")
	private int pageNo;

	@JsonProperty("pageSize")
	private int pageSize;

	@JsonProperty("ResultCode")
	private String ResultCode;

	@JsonProperty("Row")
	private String Row;

	@JsonProperty("Page")
	private String Page;

	@JsonProperty("sord")
	private String sord;

	@JsonProperty("sidx")
	private String sidx;

	@JsonProperty("opt_date")
	private String opt_date;
	@JsonProperty("opt_date_2")
	private String opt_date_2;
	@JsonProperty("user_company")
	private String user_company;
	@JsonProperty("sUser_id")
	private String sUser_id;
	@JsonProperty("func_id")
	private String func_id;
	@JsonProperty("user_type")
	private String user_type;
	@JsonProperty("role_type")
	private String role_type;
	@JsonProperty("sql")
	private String sql;
	@JsonProperty("countSql")
	private String countSql;
	
	
}
