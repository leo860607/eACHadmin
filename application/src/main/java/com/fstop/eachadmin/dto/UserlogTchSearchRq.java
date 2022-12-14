package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserlogTchSearchRq {

	@Schema(description = "票交端操作紀錄查詢傳入值")
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

	@JsonProperty("sord")
	private String sord;

	@JsonProperty("sidx")
	private String sidx;
}
