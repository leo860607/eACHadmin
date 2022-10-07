package com.fstop.eachadmin.dto;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class TxErrDetailRq {
	
	@NotEmpty(message = "系統追蹤序號")
	@Length(min = 10 , max = 10 )
	@Schema(description = "系統追蹤序號")
	@JsonProperty("STAN")
	private String STAN;
	
	@NotEmpty(message = "交易日期為年分為西元，例如：20191014")
	@Length(min = 8, max = 8)
	@Schema(description = "交易日期")
	@JsonProperty("TXDATE")
	private String TXDATE;
	
	@Schema(description = "")
	@JsonProperty("ac_key")
	private	String	ac_key;
	
	@Schema(description = "")
	@JsonProperty("target")
	private	String	target;

//	private String EXTENDFEE;
//	private String FEE_TYPE;
//	private String RESP;
//	private String NEWSENDERFEE;
//	private String NEWINFEE;
//	private String NEWOUTFEE;
//	private String NEWWOFEE;
//	private String NEWEACHFEE;
//	private String NEWFEE;
//	private String SENDERBANKID_NAME;
//	private String TXN_NAME;
	
}
