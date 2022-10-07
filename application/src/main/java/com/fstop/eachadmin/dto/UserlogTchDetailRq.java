package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserlogTchDetailRq {
	
	@Schema(description = "檢視明細傳入值")
	@JsonProperty("SERNO")
	private String SERNO;
	
	@JsonProperty("USERID")
	private String USERID;
	
	@JsonProperty("USER_COMPANY")
	private String USER_COMPANY;
}
