package com.fstop.eachadmin;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OpbkIdList {

	@Schema(description = "操作行名稱")
	@JsonProperty("OPBK_NAME")
	private	String	OPBK_NAME	;
	@JsonProperty("CTBK_NAME")
	private	String	CTBK_NAME	;	
	@JsonProperty("OP_START_DATE")
	private	String	OP_START_DATE	;
	@JsonProperty("CT_START_DATE")
	private	String	CT_START_DATE	;	
	@JsonProperty("OP_NOTE")
	private	String	OP_NOTE	;
	@JsonProperty("CT_NOTE")
	private	String	CT_NOTE	;	
	@Id
	@JsonProperty("BGBK_ID")
	private	String	BGBK_ID	;
	@JsonProperty("BGBK_NAME")
	private	String	BGBK_NAME	;
	@JsonProperty("BGBK_ATTR")
	private	String	BGBK_ATTR	;
	@JsonProperty("CTBK_ACCT")
	private	String	CTBK_ACCT	;
	@JsonProperty("TCH_ID")
	private	String	TCH_ID	;
	@JsonProperty("OPBK_ID")
	private	String	OPBK_ID	;
	@JsonProperty("CTBK_ID")
	private	String	CTBK_ID	;
	@JsonProperty("ACTIVE_DATE")
	private	String	ACTIVE_DATE	;
	@JsonProperty("STOP_DATE")
	private	String	STOP_DATE	;
	@JsonProperty("SND_FEE_BRBK_ID")
	private	String	SND_FEE_BRBK_ID	;
	@JsonProperty("OUT_FEE_BRBK_ID")
	private	String	OUT_FEE_BRBK_ID	;
	@JsonProperty("WO_FEE_BRBK_ID")
	private	String	WO_FEE_BRBK_ID	;
	@JsonProperty("IN_FEE_BRBK_ID")
	private	String	IN_FEE_BRBK_ID	;
	@JsonProperty("EDDA_FLAG")
	private	String	EDDA_FLAG	;
	@JsonProperty("CDATE")
	private	String	CDATE	;
	@JsonProperty("UDATE")
	private	String	UDATE	;
	@JsonProperty("HR_UPLOAD_MAX_FILE")
	private	String	HR_UPLOAD_MAX_FILE	;
	@JsonProperty("FILE_MAX_CNT")
	private	String	FILE_MAX_CNT	;
	@JsonProperty("IS_EACH")
	private	String	IS_EACH	;
}
