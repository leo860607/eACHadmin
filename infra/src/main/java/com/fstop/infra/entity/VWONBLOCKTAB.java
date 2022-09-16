package com.fstop.infra.entity;

//ToDo 不知道groovy是甚麼
//import groovy.beans.Vetoable;


import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
@Entity(name="tw.org.twntch.po.VW_ONBLOCKTAB")
@Table(name = "VW_ONBLOCKTAB")
@Getter
@Setter
public class VWONBLOCKTAB implements Serializable{
	
	private String ACCTBAL;
	private String ACCTCODE;
	private String AVAILBAL;
	private String BANKRSPMSG;
	@Id
	private String BIZDATE;
	private String CHECKTYPE;
	private String CLEARINGCODE;
	private String CLEARINGPHASE;
	private String CONMEMO;
	private String CONRESULTCODE;
	private String DT_CON_1;
	private String DT_CON_2;
	private String DT_CON_3;
	private String DT_REQ_1;
	private String DT_REQ_2;
	private String DT_REQ_3;
	private String DT_RSP_1;
	private String DT_RSP_2;
	private String DT_RSP_3;
	private String EACHDT;
	private String EACHFEE;
	private String FEE;
	private String INACCTNO;
	private String INACQUIRE;
	private String INBANKID;
	private String INCLEARING;
	private String INFEE;
	private String INHEAD;
	private String INID;
	private String MERCHANTID;
	private String NEWEACHFEE;
	private String NEWFEE;
	private String NEWINFEE;
	private String NEWOUTFEE;
	private String NEWRESULT;
	private String NEWSENDERFEE;
	private String NEWTXAMT;
	private String NEWTXDT;
	private String ORDERNO;
	private String OUTACCTNO;
	private String OUTACQUIRE;
	private String OUTBANKID;
	private String OUTCLEARING;
	private String OUTFEE;
	private String OUTHEAD;
	private String OUTID;
	private String PCODE;
	private String PENDINGCODE;
	private String RC1;
	private String RC2;
	private String RC3;
	private String RC4;
	private String RC5;
	private String RC6;
	private String RECEIVERBANK;
	private String RECEIVERID;
	private String RECEIVERSTATUS;
	private String REFUNDDEADLINE;
	private String RESULTSTATUS;
	private String RRN;
	private String SENDERACQUIRE;
	private String SENDERBANK;
	private String SENDERBANKID;
	private String SENDERCLEARING;
	private String SENDERFEE;
	private String SENDERHEAD;
	private String SENDERID;
	private String SENDERSTATUS;
	private String STAN;
	private String TIMEOUTCODE;
	private String TRMLCHECK;
	private String TRMLID;
	private String TRMLMCC;
	private String TXAMT;
	private String TXDATE;
	private String TXDT;
	private String TXID;
	private String UPDATEDT;
	private String FLBATCHSEQ;
	//查詢角度銷帳行20170704
	private String WOACQUIRE;
	private String WOHEAD;
	private String WOBANKID;
	private String WOFEE;
	private String NEWWOFEE;
	//查詢條件
	private String PFCLASS;
	private String TOLLID;
	private String CHARGETYPE;
	private String BILLFLAG;
	
	//2022/01/20新增欄位給頁面顯示
	private String EXTENDFEE;
//	//新增欄位
//	private String FEE_TYPE;
//	private String NEWEACHFEE_NW;
//	private String NEWFEE_NW;
//	private String NEWINFEE_NW;
//	private String NEWOUTFEE_NW;
//	private String NEWSENDERFEE_NW;
//	private String HANDLECHARGE_NW;
//	private String FEE_LVL_TYPE;
//	private String SND_BANK_FEE_DISC;
//	private String IN_BANK_FEE_DISC;
//	private String OUT_BANK_FEE_DISC;
//	private String WO_BANK_FEE_DISC;
	
	
	@Transient
	private String RESP;
	@Transient 
	private String NUM;	//for Count Query
	@Transient 
	private String ROWNUMBER;	//
	@Transient 
	private String FLBIZDATE; 
	

	@Transient 
	private String BKHEADNAME; 
	@Transient 
	private String TIMEOUTCNT;
	
	
}
