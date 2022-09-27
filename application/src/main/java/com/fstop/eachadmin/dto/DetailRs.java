package com.fstop.eachadmin.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public class DetailRs {

	private List OpbkIdList;
	private List BsTypeList;
	private String businessDate;
	private String START_DATE;
	private String END_DATE;
	private Map userData;
	private String OPBK_ID;
	private Map DetailData;
	private String IsUndone;
	
	private String NEWEXTENDFEE;
	private String TXN_TYPE;
	private String NEWSENDERFEE_NW;
	private String NEWINFEE_NW;
	private String NEWOUTFEE_NW;
	private String NEWWOFEE_NW;
	private String NEWEACHFEE_NW;
	private String NEWFEE_NW;
	
	
}
