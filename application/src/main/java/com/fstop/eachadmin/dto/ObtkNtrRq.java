package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ObtkNtrRq {
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
	
	
//	@Schema(description = "發動行")
//	@JsonProperty("SENDERBANKID")
//	private String SENDERBANKID;
//	@Schema(description = "扣款行")
//	@JsonProperty("OUTBANKID")
//	private String OUTBANKID;
//	@Schema(description = "入帳行")
//	@JsonProperty("INBANKID")
//	private String INBANKID;
//	@Schema(description = "扣帳帳號")
//	@JsonProperty("OUTACCT")
//	private String OUTACCT;
//	@Schema(description = "入帳帳號")
//	@JsonProperty("INACCT")
//	private String INACCT;
//	@Schema(description = "交易金額")
//	@JsonProperty("TXAMT")
//	private String TXAMT;
//	@Schema(description = "回覆代號")
//	@JsonProperty("CONRESULTCODE")
//	private String CONRESULTCODE;
//	@Schema(description = "交易結果")
//	@JsonProperty("RESULTCODE")
//	private String RESULTCODE;
//	@Schema(description = "票交所代行")
//	@JsonProperty("ACHFLAG")
//	private String ACHFLAG;
	
    
	
	
	
	
	
}
