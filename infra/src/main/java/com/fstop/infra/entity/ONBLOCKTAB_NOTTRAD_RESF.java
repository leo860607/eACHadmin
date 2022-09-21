package com.fstop.infra.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ONBLOCKTAB_NOTTRAD_RESF {
	private static final long serialVersionUID = -2026944867513970378L;
	//從commonform拉來的
	private	String	ac_key;
	private	String	target;
	private String serchStrs = "{}";
	private String sortname = "";
	private String sortorder = "";
	private String edit_params = "{}";
	
	private String SERNO;	
//	private String USER_COMPANY;//操作行代號
	private String OPBK_ID;//操作行代號
	private	String  BGBK_ID	;//總行代號
//	private	String	BRBK_ID ;//分行代號	
	private	String	BUSINESS_TYPE_ID;//業務類別代號
	private String CDNUMRAO ;
	private String CARDNUM_ID ;	
    private String USERID;    
    private String TXTIME;    
    private String FUNC_ID;
	private String TXDATE;
    private String STAN;//交易追踨序號
    private String OSTAN;//交易追踨序號
    private String PCODE;
    private String SENDERBANK;
	private String RECEIVERBANK;
	private String TXDT;
	private String SENDERSTATUS;
	private String RECEIVERSTATUS;
	private String TIMEOUTCODE;
	private String CONRESULTCODE;
	private String ACCTCODE;
	private String CLEARINGCODE;
	private String PENDINGCODE;
	private String SENDERCLEARING;
	private String INCLEARING;
	private String OUTCLEARING;
	private String SENDERACQUIRE;
	private String INACQUIRE;
	private String OUTACQUIRE;
	private String SENDERHEAD;
	private String INHEAD;
	private String OUTHEAD;
	private String SENDERFEE;
	private String INFEE;
	private String OUTFEE;
	private String EACHFEE;
	private String REFUNDDEADLINE;
	private String SENDERID;
	private String RECEIVERID;
	private String TXID;
	private String TXAMT;
	private String FEE;
	private String SENDERBANKID;
	private String INBANKID;
	private String OUTBANKID;
	private String BIZDATE;
	private String EACHDT;
	private String CLEARINGPHASE;
	private String INACCTNO;
	private String OUTACCTNO;
	private String INID;
	private String OUTID;
	private String ACCTBAL;
	private String AVAILBAL;
	private String CHECKTYPE;
	private String MERCHANTID;
	private String ORDERNO;
	private String TRMLID;
	private String TRMLCHECK;
	private String TRMLMCC;
	private String BANKRSPMSG;
	private String RRN;
	private String RESULTSTATUS;
	private String RC1;
	private String RC2;
	private String RC3;
	private String RC4;
	private String RC5;
	private String RC6;
	private String DT_REQ_1;
	private String DT_REQ_2;
	private String DT_REQ_3;
	private String DT_RSP_1;
	private String DT_RSP_2;
	private String DT_RSP_3;
	private String DT_CON_1;
	private String DT_CON_2;
	private String DT_CON_3;
	private List userIdList;
    private List userCompanyList;
    private List funcList;
    private List opbkIdList;
    private String FILTER_BAT ;
//    private List bgIdList	;//總行檔清單
//    private	 List txnIdList	;//交易代號檔取得交易類別(入帳、扣帳)
//    private	 List resList	;//交易結果
//    private	 List scaseary	;
//    private String	scaseJson	;
    private	List	bsIdKist	;
    private List searchResList;
    private String sourcePage;
    private String pageForSort;
    private String START_DATE;
    private String END_DATE;
    
    
    private String OLDBIZDATE;
}

