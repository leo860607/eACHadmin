package com.fstop.eachadmin;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public class Onblocktab_NotTradRes_Form {
	@JsonProperty("SERNO")
	private String SERNO;
	@Schema(description = "操作行代號")
	@JsonProperty("OPBK_ID")
	private String OPBK_ID;
	@Schema(description = "總行代號")
	@JsonProperty("BGBK_ID")
	private String BGBK_ID;
	@Schema(description = "業務類別代號")
	@JsonProperty("BUSINESS_TYPE_ID")
	private String BUSINESS_TYPE_ID;
	@JsonProperty("CDNUMRAO")
	private String CDNUMRAO;
	@JsonProperty("CARDNUM_ID")
	private String CARDNUM_ID;
	@JsonProperty("USERID")
	private String USERID;
	@JsonProperty("TXTIME")
	private String TXTIME;
	@JsonProperty("FUNC_ID")
	private String FUNC_ID;
	@JsonProperty("TXDATE")
	private String TXDATE;
	@JsonProperty("STAN")
	private String STAN;//交易追踨序號
	@JsonProperty("OSTAN")
    private String OSTAN;//交易追踨序號
	@JsonProperty("PCODE")
    private String PCODE;
	@JsonProperty("SENDERBANK")
    private String SENDERBANK;
	@JsonProperty("RECEIVERBANK")
	private String RECEIVERBANK;
	@JsonProperty("TXDT")
	private String TXDT;
	@JsonProperty("SENDERSTATUS")
	private String SENDERSTATUS;
	@JsonProperty("RECEIVERSTATUS")
	private String RECEIVERSTATUS;
	@JsonProperty("TIMEOUTCODE")
	private String TIMEOUTCODE;
	@JsonProperty("CONRESULTCODE")
	private String CONRESULTCODE;
	@JsonProperty("ACCTCODE")
	private String ACCTCODE;
	@JsonProperty("CLEARINGCODE")
	private String CLEARINGCODE;
	@JsonProperty("PENDINGCODE")
	private String PENDINGCODE;
	@JsonProperty("SENDERCLEARING")
	private String SENDERCLEARING;
	@JsonProperty("INCLEARING")
	private String INCLEARING;
	@JsonProperty("OUTCLEARING")
	private String OUTCLEARING;
	@JsonProperty("SENDERACQUIRE")
	private String SENDERACQUIRE;
	@JsonProperty("INACQUIRE")
	private String INACQUIRE;
	@JsonProperty("OUTACQUIRE")
	private String OUTACQUIRE;
	@JsonProperty("SENDERHEAD")
	private String SENDERHEAD;
	@JsonProperty("INHEAD")
	private String INHEAD;
	@JsonProperty("OUTHEAD")
	private String OUTHEAD;
	@JsonProperty("SENDERFEE")
	private String SENDERFEE;
	@JsonProperty("INFEE")
	private String INFEE;
	@JsonProperty("OUTFEE")
	private String OUTFEE;
	@JsonProperty("EACHFEE")
	private String EACHFEE;
	@JsonProperty("REFUNDDEADLINE")
	private String REFUNDDEADLINE;
	@JsonProperty("SENDERID")
	private String SENDERID;
	@JsonProperty("RECEIVERID")
	private String RECEIVERID;
	@JsonProperty("TXID")
	private String TXID;
	@JsonProperty("TXAMT")
	private String TXAMT;
	@JsonProperty("FEE")
	private String FEE;
	@JsonProperty("SENDERBANKID")
	private String SENDERBANKID;
	@JsonProperty("INBANKID")
	private String INBANKID;
	@JsonProperty("OUTBANKID")
	private String OUTBANKID;
	@JsonProperty("BIZDATE")
	private String BIZDATE;
	@JsonProperty("EACHDT")
	private String EACHDT;
	@JsonProperty("CLEARINGPHASE")
	private String CLEARINGPHASE;
	@JsonProperty("INACCTNO")
	private String INACCTNO;
	@JsonProperty("OUTACCTNO")
	private String OUTACCTNO;
	@JsonProperty("INID")
	private String INID;
	@JsonProperty("OUTID")
	private String OUTID;
	@JsonProperty("ACCTBAL")
	private String ACCTBAL;
	@JsonProperty("AVAILBAL")
	private String AVAILBAL;
	@JsonProperty("CHECKTYPE")
	private String CHECKTYPE;
	@JsonProperty("MERCHANTID")
	private String MERCHANTID;
	@JsonProperty("ORDERNO")
	private String ORDERNO;
	@JsonProperty("TRMLID")
	private String TRMLID;
	@JsonProperty("TRMLCHECK")
	private String TRMLCHECK;
	@JsonProperty("TRMLMCC")
	private String TRMLMCC;
	@JsonProperty("BANKSPMSG")
	private String BANKRSPMSG;
	@JsonProperty("RRN")
	private String RRN;
	@JsonProperty("RESULTSTATUS")
	private String RESULTSTATUS;
	@JsonProperty("RC1")
	private String RC1;
	@JsonProperty("RC2")
	private String RC2;
	@JsonProperty("RC3")
	private String RC3;
	@JsonProperty("RC4")
	private String RC4;
	@JsonProperty("RC5")
	private String RC5;
	@JsonProperty("RC6")
	private String RC6;
	@JsonProperty("DT_REQ_1")
	private String DT_REQ_1;
	@JsonProperty("DT_REQ_2")
	private String DT_REQ_2;
	@JsonProperty("DT_REQ_3")
	private String DT_REQ_3;
	@JsonProperty("DT_RSP_1")
	private String DT_RSP_1;
	@JsonProperty("DT_RSP_2")
	private String DT_RSP_2;
	@JsonProperty("DT_RSP_3")
	private String DT_RSP_3;
	@JsonProperty("DT_CON_1")
	private String DT_CON_1;
	@JsonProperty("DT_CON_2")
	private String DT_CON_2;
	@JsonProperty("DT_CON_3")
	private String DT_CON_3;
	@JsonProperty("userIdList")
	private List userIdList;
	@JsonProperty("userCompanyList")
    private List userCompanyList;
	@JsonProperty("funcList")
    private List funcList;
	@JsonProperty("opbkIdList")
    private List opbkIdList;
	@JsonProperty("FILTER_BAT")
    private String FILTER_BAT ;
	
	@JsonProperty("bsIdKist")
	private	List	bsIdKist	;
	@JsonProperty("searchResList")
    private List searchResList;
	@JsonProperty("sourcePage")
    private String sourcePage;
	@JsonProperty("pageForSort")
    private String pageForSort;
	@JsonProperty("START_DATE")
    private String START_DATE;
	@JsonProperty("END_DATE")
    private String END_DATE;
    
	@JsonProperty("OLDBIZDATE")
    private String OLDBIZDATE;

}
