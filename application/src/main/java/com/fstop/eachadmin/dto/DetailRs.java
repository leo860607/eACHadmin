package com.fstop.eachadmin.dto;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

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
}
