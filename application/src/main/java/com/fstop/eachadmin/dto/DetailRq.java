package com.fstop.eachadmin.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailRq {
	
	@Schema(description = "")
	@JsonProperty("ac_key")
	private	String	ac_key;
	@JsonProperty("target")
	private	String	target;
	@JsonProperty("SERNO")
	private String SERNO;	
	@JsonProperty("TXDATE")
	private String TXDATE;
	@JsonProperty("STAN")
	private String STAN;//交易追踨序號
	@JsonProperty("OLDBIZDATE")
	private String OLDBIZDATE;
	@JsonProperty("serchStrs")
	private String serchStrs = "{}";
	@JsonProperty("pageForSort")
	private String pageForSort;
	@JsonProperty("sortname")
	private String sortname = "";
	@JsonProperty("sortorder")
	private String sortorder = "";
	@JsonProperty("edit_params")
	private String edit_params = "{}";
	@JsonProperty("START_DATE")
	private String START_DATE;
	@JsonProperty("END_DATE")
    private String END_DATE;
	@JsonProperty("CLEARINGCODE")
    private String CLEARINGCODE;
	@JsonProperty("SENDERACQUIRE")
    private String SENDERACQUIRE;
	@JsonProperty("OPBK_ID")
    private String OPBK_ID;//操作行代號
	@JsonProperty("BGBK_ID")
    private	String  BGBK_ID	;//總行代號
	@JsonProperty("BUSINESS_TYPE_ID")
    private	String	BUSINESS_TYPE_ID;//業務類別代號
	@JsonProperty("RESULTSTATUS")
    private String RESULTSTATUS;
	@JsonProperty("FILTER_BAT")
    private String FILTER_BAT ;
	@JsonProperty("OSTAN")
    private String OSTAN;//交易追踨序號
	
	private String EXTENDFEE;
	private String FEE_TYPE;
	private String NEWEXTENDFEE;
	private String TXN_TYPE;
	private String NEWSENDERFEE_NW;
	private String NEWINFEE_NW;
	private String NEWOUTFEE_NW;
	private String NEWWOFEE_NW;
	private String NEWEACHFEE_NW;
	private String NEWFEE_NW;
	private String USER_COMPANY;
	
	
	// 未完成交易結果查詢
	@Schema(description = "交易日期/時間")
	@JsonProperty("TXDT")
	private String TXDT;
	private String EACHDT;
	private String NEWTXAMT;
	@Schema(description = "交易類別")
	@JsonProperty("PCODE_DESC")
	private String PCODE_DESC;
	@JsonProperty("TXN_NAME")
	private String TXN_NAME;
	@JsonProperty("SENDERBANK_NAME")
	private String SENDERBANK_NAME;
	@JsonProperty("RECEIVERBANK_NAME")
	private String RECEIVERBANK_NAME;
	@JsonProperty("SENDERID")
	private String SENDERID;
	@JsonProperty("RECEIVERID")
	private String RECEIVERID;
	@JsonProperty("INACCTNO")
	private String INACCTNO;
	@JsonProperty("INID")
	private String INID;
	@JsonProperty("OUTACCTNO")
	private String OUTACCTNO;
	@JsonProperty("OUTID")
	private String OUTID;
	@JsonProperty("SENDERBANKID_NAME")
	private String SENDERBANKID_NAME;
	@JsonProperty("NEWSENDERFEE")
	private String NEWSENDERFEE;
	@JsonProperty("INBANKID_NAME")
	private String INBANKID_NAME;
	@JsonProperty("NEWINFEE")
	private String NEWINFEE;
	@JsonProperty("OUTBANKID_NAME")
	private String OUTBANKID_NAME;
	@JsonProperty("NEWOUTFEE")
	private String NEWOUTFEE;
	@JsonProperty("WOBANKID_NAME")
	private String WOBANKID_NAME;
	@JsonProperty("NEWWOFEE")
	private String NEWWOFEE;
	@JsonProperty("REFUNDDEADLINE")
	private String REFUNDDEADLINE;
	@JsonProperty("NEWEACHFEE")
	private String NEWEACHFEE;
	@JsonProperty("TIMEOUTCODE")
	private String TIMEOUTCODE;
	@JsonProperty("NEWFEE")
	private String NEWFEE;
	@JsonProperty("FLBIZDATE")
	private String FLBIZDATE;
	@JsonProperty("FLBATCHSEQ")
	private String FLBATCHSEQ;
	@JsonProperty("FLPROCSEQ")
	private String FLPROCSEQ;
	@JsonProperty("DATASEQ")
	private String DATASEQ;
	private String RESP;
	private String NEWBIZDATE;
	private String RESULTCODE;
	private String NEWCLRPHASE;
	

	
	
}
