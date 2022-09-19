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
public abstract class PageSearchRq implements Map<String, String> {

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

}
