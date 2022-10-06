package com.fstop.eachadmin.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fstop.infra.entity.VW_ONBLOCKTAB;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxErrDetailRs {
	
	@Schema(description = "客戶實際支付手續費 / 新版-客戶支付手續費 / 電文客支")
	@JsonProperty("NEWEXTENDFEE")
	private String NEWEXTENDFEE;
	
	//-----------------------------------------------------------------------------------	
	@Schema(description = "營業日")
	@JsonProperty("TXDT")
	private String TXDT;
	
	@Schema(description = "系統追蹤序號")
	@JsonProperty("STAN")
	private String STAN;
	
	@Schema(description = "交易日期")
	@JsonProperty("TXDATE")
	private String TXDATE;
	
	@Schema(description = "交易類別")
	@JsonProperty("PCODE_DESC")
	private String PCODE_DESC;
	
	@Schema(description = "發動單位代號")
	@JsonProperty("SENDERBANK_NAME")
	private String SENDERBANK_NAME;
	
	@Schema(description = "接收單位代號")
	@JsonProperty("RECEIVERBANK_NAME")
	private String RECEIVERBANK_NAME;
	
	@Schema(description = "記帳碼")
	@JsonProperty("ACCTCODE")
	private String ACCTCODE;
	
//	@Schema(description = "")
//	@JsonProperty("SENDERCLEARING_NAME")
//	private String SENDERCLEARING_NAME;
//	
//	@Schema(description = "")
//	@JsonProperty("INCLEARING_NAME")
//	private String INCLEARING_NAME;
//	
//	@Schema(description = "")
//	@JsonProperty("OUTCLEARING_NAME")
//	private String OUTCLEARING_NAME;
//	
//	@Schema(description = "")
//	@JsonProperty("SENDERACQUIRE_NAME")
//	private String SENDERACQUIRE_NAME;
//	
//	@Schema(description = "")
//	@JsonProperty("INACQUIRE_NAME")
//	private String INACQUIRE_NAME;
//	
//	@Schema(description = "")
//	@JsonProperty("OUTACQUIRE_NAME")
//	private String OUTACQUIRE_NAME;
//	
//	@Schema(description = "")
//	@JsonProperty("SENDERHEAD_NAME")
//	private String SENDERHEAD_NAME;
//	
//	@Schema(description = "")
//	@JsonProperty("INHEAD_NAME")
//	private String INHEAD_NAME;
//	
//	@Schema(description = "")
//	@JsonProperty("OUTHEAD_NAME")
//	private String OUTHEAD_NAME;
	
	@Schema(description = "發動單位手續費 / 新版-發動單位手續費")
	@JsonProperty("NEWSENDERFEE")
	private String NEWSENDERFEE;
	
	@Schema(description = "入帳單位手續費 / 新版-入帳單位手續費")
	@JsonProperty("NEWINFEE")
	private String NEWINFEE;
	
	@Schema(description = "扣款單位手續費 / 新版-扣款單位手續費")
	@JsonProperty("NEWOUTFEE")
	private String NEWOUTFEE;
	
	@Schema(description = "交換所手續費 /    新版-交換所手續費")
	@JsonProperty("NEWEACHFEE")
	private String NEWEACHFEE;
	
	@Schema(description = "發動者統編/簡稱")
	@JsonProperty("SENDERID")
	private String SENDERID;
	
	@Schema(description = "交易代號 / 收費類型")
	@JsonProperty("TXN_NAME")
	private String TXN_NAME;
	
	@Schema(description = "交易金額")
	@JsonProperty("NEWTXAMT")
	private String NEWTXAMT;
	
//	@Schema(description = "")
//	@JsonProperty("SENDERSTATUS")
//	private String SENDERSTATUS;
	
	@Schema(description = "客戶實際支付手續費 / 新版-客戶支付手續費 / 電文客支")
	@JsonProperty("NEWFEE")
	private String NEWFEE;
	
	@Schema(description = "發動行代號")
	@JsonProperty("SENDERBANKID_NAME")
	private String SENDERBANKID_NAME;
	
	@Schema(description = "入帳行代號")
	@JsonProperty("INBANKID_NAME")
	private String INBANKID_NAME;
	
	@Schema(description = "扣款行代號")
	@JsonProperty("OUTBANKID_NAME")
	private String OUTBANKID_NAME;
	
	@Schema(description = "營業日")
	@JsonProperty("BIZDATE")
	private String BIZDATE;
	
	@Schema(description = "票交所處理日期時間")
	@JsonProperty("EACHDT")
	private String EACHDT;
	
	@Schema(description = "清算階段代號")
	@JsonProperty("clearingphase")
	private String CLEARINGPHASE;
	
	@Schema(description = "入帳者帳號")
	@JsonProperty("INACCTNO")
	private String INACCTNO;
	
	@Schema(description = "扣款者帳號")
	@JsonProperty("OUTACCTNO")
	private String OUTACCTNO;
	
	@Schema(description = "入帳者統一編號")
	@JsonProperty("INID")
	private String INID;
	
	@Schema(description = "交易結果")
	@JsonProperty("RESP")
	private String RESP;
	
	@Schema(description = "RC1")
	@JsonProperty("ERR_DESC1")
	private String ERR_DESC1;
	
	@Schema(description = "RC2")
	@JsonProperty("ERR_DESC2")
	private String ERR_DESC2;
	
	@Schema(description = "最後處理時間")
	@JsonProperty("UPDATEDT")
	private String UPDATEDT;
	
	@Schema(description = "發動者專用區")
	@JsonProperty("SENDERDATA")
	private String SENDERDATA;
	
	@Schema(description = "回覆代號")
	@JsonProperty("RESULTCODE")
	private String RESULTCODE;
	
	
	

//	private String WOHEAD_NAME;
//	private String CONRESULTCODE_DESC;
//	private String WOACQUIRE_NAME;
//	private String TXN_TYPE;
//	private String NEWSENDERFEE_NW;
//	private String NEWINFEE_NW;
//	private String NEWOUTFEE_NW;
//	private String NEWWOFEE_NW;
//	private String NEWEACHFEE_NW;
//	private String NEWFEE_NW;
//	private Map DetailData;
//	private String WOBANKID_NAME;
//	private String NEWWOFEE;
//	private String BILLTYPE;
//	private String BILLDATA;
//	private String CHARGETYPE;
//	private String TOLLID;
//	private String BILLFLAG;
//	private String CHECKDATA;
//	private String PFCLASS;
//	private VW_ONBLOCKTAB DetailMapData;

}
