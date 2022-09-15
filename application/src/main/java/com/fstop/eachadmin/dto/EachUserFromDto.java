package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class EachUserFromDto {
	@Schema(description = "使用者表單")
	@JsonProperty("scaseary")
	private	List	scaseary	;
	
	private	List	role_list	;
//	private	List	gbank_list	;
	private	String 	USER_COMPANY 	;
	private	String 	USER_ID	;
	private String USER_TYPE;
	private String USER_STATUS="N";
	private String USER_DESC;
	private String USE_IKEY = "N";
	private String ROLE_ID;
	private Integer NOLOGIN_EXPIRE_DAY;
	private String IP;
	private Integer IDLE_TIMEOUT;
	private String LAST_LOGIN_DATE = "";
	private String LAST_LOGIN_IP;
	private String CDATE;
	private String UDATE;
	private String TMPUSER_TYPE;
}
