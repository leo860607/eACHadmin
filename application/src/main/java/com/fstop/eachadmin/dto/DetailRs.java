package com.fstop.eachadmin.dto;


import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fstop.infra.entity.VW_ONBLOCKTAB;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailRs {
	private String TXDT;
	private String STAN;
	private String TXDATE;
	private String PCODE_DESC;
	private String SENDERBANK_NAME;
	private String RECEIVERBANK_NAME;
	private String CONRESULTCODE_DESC;
	private String ACCTCODE;
	private String SENDERCLEARING_NAME;
	private String INCLEARING_NAME;
	private String OUTCLEARING_NAME;
	private String SENDERACQUIRE_NAME;
	private String INACQUIRE_NAME;
	private String OUTACQUIRE_NAME;
	private String WOACQUIRE_NAME;
	private String SENDERHEAD_NAME;
	private String INHEAD_NAME;
	private String OUTHEAD_NAME;
	private String WOHEAD_NAME;
	private String NEWSENDERFEE;
	private String NEWINFEE;
	private String NEWOUTFEE;
	private String NEWWOFEE;
	private String NEWEACHFEE;
	private String NEWEXTENDFEE;
	private String SENDERID;
	private String TXN_NAME;
	private String NEWTXAMT;
	private String SENDERSTATUS;
	private String NEWFEE;
	private String SENDERBANKID_NAME;
	private String INBANKID_NAME;
	private String OUTBANKID_NAME;
	private String WOBANKID_NAME;
	private String BIZDATE;
	private String EACHDT;
	private String CLEARINGPHASE;
	private String INACCTNO;
	private String OUTACCTNO;
	private String INID;
	private String RESP;
	private String ERR_DESC1;
	private String ERR_DESC2;
	private String UPDATEDT;
	private String BILLTYPE;
	private String BILLDATA;
	private String CHARGETYPE;
	private String TOLLID;
	private String BILLFLAG;
	private String CHECKDATA;
	private String PFCLASS;
	private String SENDERDATA;
	private String RESULTCODE;
	
	
	
	
	
	
//	@JsonProperty("detailData")
//	private VW_ONBLOCKTAB detailData;
//
//	@JsonProperty("detailDataMap")
//	private Map DETAILDATAMAP;
//	
//	@JsonProperty("start_date")
//	private String START_DATE;
//	
//	@JsonProperty("end_date")
//	private String END_DATE;
	
//	private List OpbkIdList;
//	private List BsTypeList;
//	private String businessDate;
//	
//	private Map userData;
//	private String OPBK_ID;
//	
//	private String IsUndone;
//	
//	private String NEWEXTENDFEE;
//	private String TXN_TYPE;
//	private String NEWSENDERFEE_NW;
//	private String NEWINFEE_NW;
//	private String NEWOUTFEE_NW;
//	private String NEWWOFEE_NW;
//	private String NEWEACHFEE_NW;
//	private String NEWFEE_NW;
	
	
}
