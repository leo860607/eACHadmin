package com.fstop.infra.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Table(name = "ONBLOCKTAB")
@Entity(name = "MASTER_ONBLOCKTAB")
public class ONBLOCKTAB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7398564032769110134L;
	@EmbeddedId
	private ONBLOCKTAB_PK id;
	@Transient
	private String TXDATE;
	@Transient
	@Schema(description = "The password of user.", example = "123456", minLength = 6)
	@Size(min = 0, max = 20)
	private String STAN;//案件編號
	@Schema(description = "The password of user.", example = "123456", minLength = 6)
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
	private String TRMLMCC;
	private String DT_CON_2;
	private String DT_CON_3;
	// 查詢統計的交易資料查詢的onblocktab_trad_edit_q.jsp裡的RC1~RC6的說明要加上TXN_ERROR_CODE的ERROR_DESC而設
	private String ERR_DESC1;
	private String ERR_DESC2;
	private String ERR_DESC3;
	private String ERR_DESC4;
	private String ERR_DESC5;
	private String ERR_DESC6;
	
	@Data
	@AllArgsConstructor
	public class ONBLOCKTAB_PK implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 487364488070862427L;
		private  String     TXDATE          ;    
		private  String     STAN            ;
	}
}
