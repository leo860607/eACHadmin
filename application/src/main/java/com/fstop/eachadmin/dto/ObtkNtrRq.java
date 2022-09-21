package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ObtkNtrRq {
	@Schema(description = "")
	@JsonProperty("")
	private	String	ac_key;
	@JsonProperty("")
	private	String	target;
	@JsonProperty("")
	private String SERNO;	
	@JsonProperty("")
	private String TXDATE;
	@JsonProperty("")
	private String STAN;//交易追踨序號
	@JsonProperty("")
	private String OLDBIZDATE;
	@JsonProperty("")
	private String serchStrs = "{}";
	@JsonProperty("")
	private String pageForSort;
	@JsonProperty("")
	private String sortname = "";
	@JsonProperty("")
	private String sortorder = "";
	@JsonProperty("")
	private String edit_params = "{}";
	@JsonProperty("")
	private String START_DATE;
	@JsonProperty("")
    private String END_DATE;
	@JsonProperty("")
    private String CLEARINGCODE;
	@JsonProperty("")
    private String SENDERACQUIRE;
	@JsonProperty("")
    private String OPBK_ID;//操作行代號
	@JsonProperty("")
    private	String  BGBK_ID	;//總行代號
	@JsonProperty("")
    private	String	BUSINESS_TYPE_ID;//業務類別代號
	@JsonProperty("")
    private String RESULTSTATUS;
	@JsonProperty("")
    private String FILTER_BAT ;
	@JsonProperty("")
    private String OSTAN;//交易追踨序號
    
	
	
	
	
	
}
