package com.fstop.infra.entity;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ONBLOCKTAB_FORM {
	private String searchCondition;
//	private static final long serialVersionUID = -2026944867513970378L;
	private String SERNO;	
//	private String USER_COMPANY;//操作行代號
	private String OPBK_ID;//操作行代號
	private	String  BGBK_ID	;//總行代號
	private	String	BRBK_ID ;//分行代號	
	private	String	BUSINESS_TYPE_ID;//業務類別代號
	private String CDNUMRAO ;
	private String CARDNUM_ID ;	
    private String USERID;    
    private String TXTIME1;
    private String TXTIME2;
    private String TXTIME3;
    private String TXTIME4;
    private String FEE_TYPE;
    private String HOUR1;
    private String HOUR2;
    private String MON1;
    private String MON2;
    
    private String FUNC_ID;
	private String TXDATE;
    private String STAN;//交易追踨序號(起
    private String STAN2;//交易追踨序號(迄
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
	private String RESSTATUS;
	private String RCSERCTEXT;
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
	private String UPDATEDT;
	private String ACHFLAG;
	//查詢統計的交易資料查詢的onblocktab_trad_edit_q.jsp裡的RC1~RC6的說明要加上TXN_ERROR_CODE的ERROR_DESC而設
	private String ERR_DESC1;
	private String ERR_DESC2;
	private String ERR_DESC3;
	private String ERR_DESC4;
	private String ERR_DESC5;
	private String ERR_DESC6;
	private String GARBAGEDATA;
	private List userIdList;
    private List userCompanyList;
    private List funcList;
    private List bgIdList	;//總行檔清單
    private	 List txnIdList	;//交易代號檔取得交易類別(入帳、扣帳)
//    private	 List resList	;//交易結果
//    private	 List scaseary	;
//    private String	scaseJson	;
    private	List	bsIdKist	;
    private List searchResList;
    private List opbkIdList;
    private String searchAspect;	//查詢角度
    private String opAction1;
    private Map detailData;
    private String sourcePage;	//來源網頁
    private String isUndone ="N";//判斷是否為未完成交易.jsp過來的
    private String isUndoneRes ="N";//判斷是否為未完成交易結果.jsp過來的
    
  //整批作業
    private List bsTypeList;
    private String ACCTNO;
    private String FLBIZDATE;//整批營業日
	private String FLBATCHSEQ;//整批處理序號(檔名)
	private String FLPROCSEQ;//整批處理序號
	private String DATASEQ;//資料序號
	private String FILTER_BAT;//是否過濾整批
	
	//20170719新增查詢條件
	private String PFCLASS;//繳費類別
	private String TOLLID;//收費業者
	private String CHARGETYPE;//繳費工具
	private String BILLFLAG;//銷帳單位
}
