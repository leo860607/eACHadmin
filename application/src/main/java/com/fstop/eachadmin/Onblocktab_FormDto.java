package com.fstop.eachadmin;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Onblocktab_FormDto {
	@Schema(description = "查詢明細")
	@JsonProperty("searchCondition")
	private String searchCondition;
//	private static final long serialVersionUID = -2026944867513970378L;
	@JsonProperty("SERNO")
	private String SERNO;
//	private String USER_COMPANY;//操作行代號
	@JsonProperty("OPBK_ID")
	private String OPBK_ID;//操作行代號
	@JsonProperty("BGBK_ID")
	private	String  BGBK_ID	;//總行代號
	@JsonProperty("BRBK_ID")
	private	String	BRBK_ID ;//分行代號
	@JsonProperty("BUSINESS_TYPE_ID")
	private	String	BUSINESS_TYPE_ID;//業務類別代號
	@JsonProperty("CDNUMRAO")
	private String CDNUMRAO ;
	@JsonProperty("CARDNUM_ID")
	private String CARDNUM_ID ;
	@JsonProperty("USERID")
    private String USERID;
	@JsonProperty("TXTIME1")
    private String TXTIME1;
	@JsonProperty("TXTIME2")
    private String TXTIME2;
	@JsonProperty("TXTIME3")
    private String TXTIME3;
	@JsonProperty("TXTIME4")
    private String TXTIME4;
	@JsonProperty("FEE_TYPE")
    private String FEE_TYPE;
	@JsonProperty("HOUR1")
    private String HOUR1;
	@JsonProperty("HOUR2")
    private String HOUR2;
	@JsonProperty("MON1")
    private String MON1;
	@JsonProperty("MON2")
    private String MON2;
	
}
