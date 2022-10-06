package com.fstop.eachadmin.dto;



import com.fasterxml.jackson.annotation.JsonProperty;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailRs {
	@Schema(description = "票交所處理日期時間")
	@JsonProperty("txdt")
	private String TXDT;

	@Schema(description = "系統追蹤序號")
	@JsonProperty("stan")
	private String STAN;

	@Schema(description = "原交易日期")
	@JsonProperty("txdate")
	private String TXDATE;

	@Schema(description = "交易類別")
	@JsonProperty("pcode_desc")
	private String PCODE_DESC;

	@Schema(description = "發動單位代號")
	@JsonProperty("senderbank_name")
	private String SENDERBANK_NAME;

	@Schema(description = "接收單位代號")
	@JsonProperty("receiverbank_name")
	private String RECEIVERBANK_NAME;

	@Schema(description = "錯誤原因")
	@JsonProperty("conresultcode_desc")
	private String CONRESULTCODE_DESC;

	@Schema(description = "記帳碼")
	@JsonProperty("acctcode")
	private String ACCTCODE;

	@Schema(description = "發動單位手續費 / 新版-發動單位手續費")
	@JsonProperty("newsenderfee")
	private String NEWSENDERFEE;

	@Schema(description = "入帳單位手續費 / 新版-入帳單位手續費")
	@JsonProperty("newinfee")
	private String NEWINFEE;

	@Schema(description = "扣款單位手續費 / 新版-扣款單位手續費")
	@JsonProperty("newoutfee")
	private String NEWOUTFEE;

	@Schema(description = "銷帳單位手續費 / 新版-銷帳單位手續費")
	@JsonProperty("newwofee")
	private String NEWWOFEE;

	@Schema(description = "交換所手續費 /    新版-交換所手續費")
	@JsonProperty("neweachfee")
	private String NEWEACHFEE;

	@Schema(description = "客戶實際支付手續費 / 新版-客戶支付手續費 / 電文客支")
	@JsonProperty("newextendfee")
	private String NEWEXTENDFEE;

	@Schema(description = "發動者統一編號")
	@JsonProperty("senderid")
	private String SENDERID;

	@Schema(description = "交易代號 / 收費類型")
	@JsonProperty("txn_name")
	private String TXN_NAME;

	@Schema(description = "交易金額")
	@JsonProperty("newtxamt")
	private String NEWTXAMT;

	@Schema(description = "客戶實際支付手續費 / 新版-客戶支付手續費 / 電文客支")
	@JsonProperty("newfee")
	private String NEWFEE;

	@Schema(description = "發動單位代號")
	@JsonProperty("senderbankid_name")
	private String SENDERBANKID_NAME;

	@Schema(description = "入帳行代號")
	@JsonProperty("inbankid_name")
	private String INBANKID_NAME;

	@Schema(description = "扣款行代號")
	@JsonProperty("outbankid_name")
	private String OUTBANKID_NAME;

	@Schema(description = "銷帳行代號")
	@JsonProperty("wobankid_name")
	private String WOBANKID_NAME;

	@Schema(description = "營業日")
	@JsonProperty("bizdate")
	private String BIZDATE;

	@Schema(description = "票交所處理日期時間")
	@JsonProperty("eachdt")
	private String EACHDT;

	@Schema(description = "清算階段代號")
	@JsonProperty("clearingphase")
	private String CLEARINGPHASE;

	@Schema(description = "入帳者帳號")
	@JsonProperty("inacctno")
	private String INACCTNO;

	@Schema(description = "扣款者帳號")
	@JsonProperty("outacctno")
	private String OUTACCTNO;

	@Schema(description = "入帳者統一編號")
	@JsonProperty("inid")
	private String INID;

	@Schema(description = "交易結果")
	@JsonProperty("resp")
	private String RESP;

	@Schema(description = "RC1")
	@JsonProperty("err_desc1")
	private String ERR_DESC1;

	@Schema(description = "RC2")
	@JsonProperty("err_desc2")
	private String ERR_DESC2;

	@Schema(description = "最後處理時間")
	@JsonProperty("updatedt")
	private String UPDATEDT;

	@Schema(description = "銷帳資料類型")
	@JsonProperty("billtype")
	private String BILLTYPE;

	@Schema(description = "銷帳資料區")
	@JsonProperty("billdate")
	private String BILLDATA;

	@Schema(description = "繳費工具類型")
	@JsonProperty("chargettype")
	private String CHARGETYPE;

	@Schema(description = "收費業者統一編號")
	@JsonProperty("tollid")
	private String TOLLID;

	@Schema(description = "銷帳單位")
	@JsonProperty("billflag")
	private String BILLFLAG;

	@Schema(description = "繳費工具驗證資料區")
	@JsonProperty("checkdata")
	private String CHECKDATA;

	@Schema(description = "繳費類別")
	@JsonProperty("pfclass")
	private String PFCLASS;

	@Schema(description = "發動者專用區")
	@JsonProperty("senderdata")
	private String SENDERDATA;

	@Schema(description = "回覆代號")
	@JsonProperty("resultcode")
	private String RESULTCODE;
//------------------------------------------------------------------

//	@Schema(description = "")
//	private String SENDERSTATUS;
//	@Schema(description = "發動行代號")
//	private String SENDERCLEARING_NAME;
//	@Schema(description = "")
//	private String INCLEARING_NAME;
//	@Schema(description = "")
//	private String OUTCLEARING_NAME;
//	@Schema(description = "")
//	private String SENDERACQUIRE_NAME;
//	@Schema(description = "")
//	private String INACQUIRE_NAME;
//	@Schema(description = "")
//	private String OUTACQUIRE_NAME;
//	@Schema(description = "")
//	private String WOACQUIRE_NAME;
//	@Schema(description = "")
//	private String SENDERHEAD_NAME;
//	@Schema(description = "")
//	private String INHEAD_NAME;
//	@Schema(description = "")
//	private String OUTHEAD_NAME;
//	@Schema(description = "")
//	private String WOHEAD_NAME;
}
