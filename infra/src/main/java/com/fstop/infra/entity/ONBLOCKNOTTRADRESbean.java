package com.fstop.infra.entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Getter
@Setter
public class ONBLOCKNOTTRADRESbean implements Serializable{
    @EmbeddedId
    private String TXDATE;
    private String STAN;
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
    private BigDecimal SENDERFEE;
    private BigDecimal INFEE;
    private BigDecimal OUTFEE;
    private BigDecimal EACHFEE;
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
    private String DT_Req_1;
    private String DT_Req_2;
    private String DT_Req_3;
    private String DT_Rsp_1;
    private String DT_Rsp_2;
    private String DT_Rsp_3;
    private String DT_Con_1;
    private String DT_Con_2;
    private String DT_Con_3;
    private String Resp;
    private String OBIZDATE;
    private String OCLEARINGPHASE;
    private String RESULTCODE;
    private String EndTime;
    private String FIRECOUNT;
    private String AVGTIME;
    private String ACHAVGTIME;
    private String ACHSAVETIME;
    private String ACHDEBITTIME;
    private String INSAVETIME;
    private String OUTDEBITTIME;
    private String TCH_STD_ECHO_TIME;
    private String PARTY_STD_ECHO_TIME;
    private String TXN_STD_PROC_TIME;
    private String ACHFLAG;
    @Transient
    private String OUTACCT;
    @Transient
    private String INACCT;
    @Transient
    private String BANKID;
    @Transient
    private String BANKIDANDNAME;
    @Transient
    private Integer TOTALCOUNT;
    @Transient
    private Integer PENDCOUNT;
    @Transient
    private Integer PRCCOUNT;
    @Transient
    private BigDecimal PRCTIME;
    @Transient
    private Integer SAVECOUNT;
    @Transient
    private BigDecimal SAVETIME;
    @Transient
    private Integer DEBITCOUNT;
    @Transient
    private BigDecimal DEBITTIME;
    @Transient
    private Integer ACHPRCCOUNT;
    @Transient
    private BigDecimal ACHPRCTIME;

    @Transient
    public String getOUTACCT() {
        return OUTACCT;
    }

    @Transient
    public String getINACCT() {
        return INACCT;
    }
}
