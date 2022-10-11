package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserlogTchSearchRs {
	
	@Schema(description = "票交端操作紀錄查詢回傳值")
	@JsonProperty("USERIP")
	private String USERIP;
	
	@JsonProperty("TXTIME")
	private String TXTIME;
	
	@JsonProperty("UP_FUNC_ID")
	private String UP_FUNC_ID;
	
	@JsonProperty("FUNC_ID")
	private String FUNC_ID;
	
	@JsonProperty("FUNC_TYPE")
	private String FUNC_TYPE;
	
	@JsonProperty("OPITEM")
	private String OPITEM;
	
	@JsonProperty("BFCHCON")
	private String BFCHCON;
	
	@JsonProperty("AFCHCON")
	private String AFCHCON;
	
	@JsonProperty("ADEXCODE")
	private String ADEXCODE;

	@JsonProperty("SERNO")
	private String SERNO;
	
	@JsonProperty("USERID")
	private String USERID;
	
	@JsonProperty("USER_COMPANY")
	private String USER_COMPANY;

}
