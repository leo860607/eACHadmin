package com.fstop.infra.entity;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class LOGIN_FORM {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2660281177423438063L;
		private String userCompany;
		private String userId;
		private List scaseary;
		private List breadcrumb;
		private EACH_USER_FORM userData;
//		20150402 edit by hugo 給空字串 否則null時登入頁面會出錯
//		private String THIS_LOGIN_DATE;
		private String THIS_LOGIN_DATE = "";
		private String THIS_LOGIN_IP;
		private String IS_PROXY;//判斷登入者是否為代理清算行
		//IKey使用
		private String RAOName;//的帳號
		private String signvalue;//用頁面getSignedPKCS7StrByPara的function取得的簽章字串
		private String ikeyValidateDate;//IKey的有效日期
		private String ikeyExtendURL;//IKey展期的URL
		private String isFormal;//判斷是否為正式環境
		//
//		eToken使用
		private String cardType;
		private String method;
		private String CompanyID;
		private String EmployeeID;
		private String LoginType;
		private String browserType;
		
//      EachUserlogService-writeLog
		
	}