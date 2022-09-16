package com.fstop.eachadmin.dto;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

public class BsFormDto {

	//業務行DTO
	@Schema(description = "業務行")
	@JsonProperty("serialVersionUID = 3743571279535662345L")
	private static final long serialVersionUID = 3743571279535662345L;
	@JsonProperty("START_DATE")
	private String START_DATE;
	@JsonProperty("END_DATE")
	private String END_DATE;
	@JsonProperty("TXDATE")
	private String TXDATE;
	
	@JsonProperty("OSTAN")
	private String OSTAN;
	@JsonProperty("RESULTCODE")
	private String RESULTCODE;
	@JsonProperty("OPBK_ID")
	private String OPBK_ID;
	@JsonProperty("BGBK_ID")
	private String BGBK_ID;
	@JsonProperty("CLEARINGPHASE")
	private String CLEARINGPHASE;
	@JsonProperty("BUSINESS_TYPE_ID")
	private String BUSINESS_TYPE_ID;
	@JsonProperty("scaseary")
	private List scaseary;
	@JsonProperty("opbkIdList")
	private List opbkIdList;
	@JsonProperty("bsTypeList")
	private List bsTypeList;
	@JsonProperty("sourcePage")
	private String sourcePage;	//來源網頁
	@JsonProperty("pageForSort")
	private String pageForSort;
	@JsonProperty("FLBIZDATE")
	private String FLBIZDATE;//整批營業日
	@JsonProperty("FLBATCHSEQ")
	private String FLBATCHSEQ;//整批處理序號(檔名)
	@JsonProperty("FLPROCSEQ")
	private String FLPROCSEQ;//整批處理序號
	@JsonProperty("DATASEQ")
	private String DATASEQ;//資料序號
	@JsonProperty("FILTER_BAT")
	private String FILTER_BAT;//是否過濾整批
	
}
