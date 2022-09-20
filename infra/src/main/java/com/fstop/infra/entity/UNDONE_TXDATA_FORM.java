package com.fstop.infra.entity;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class UNDONE_TXDATA_FORM  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3743571279535662345L;
	private String START_DATE;
	private String END_DATE;
	private String TXDATE;
	private String STAN;
	private String OSTAN;
	private String RESULTCODE;
	private String OPBK_ID;
	private String BGBK_ID;
	private String CLEARINGPHASE;
	private String BUSINESS_TYPE_ID;
	private List scaseary;
	private List opbkIdList;
	private List bsTypeList;
	private String sourcePage; // 來源網頁
	private String pageForSort;
	private String FLBIZDATE;// 整批營業日
	private String FLBATCHSEQ;// 整批處理序號(檔名)
	private String FLPROCSEQ;// 整批處理序號
	private String DATASEQ;// 資料序號
	private String FILTER_BAT;// 是否過濾整批
//--------------------------------------------------------------
	private	String	ac_key;
	private	String	target;
	private	String	result;
	private	String	msg;
	private	String	pagesize ="";//TODO Arguments.getStringArg("PAGE.SIZE");
	private String serchStrs = "{}";
	private String colForSort = "";
	private String ordForSort = "";
	private String s_auth_type = "R"; //該使用者對應功能的權限 預設為R(查詢)
	private String s_up_func_name;
	private String s_func_name;
	private String s_fcid;
	private String s_is_record = "N";
	private String p_is_record = "N";
	private String file_path;
	private String error_list;
	private String edit_params = "{}";
	private String sortname = "";
	private String sortorder = "";
//	private String pageForSort = "1"; 
}
