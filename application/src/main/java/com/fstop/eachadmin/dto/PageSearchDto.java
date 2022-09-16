package com.fstop.eachadmin.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PageSearchDto<UNDONETXDATA> {
	
	private List<UNDONETXDATA> pageSearchList;
	
	private String filter_bat;
	private String startDate;
	private String endDate;
	private String clearingphase;
	private String bgbkId;
	private String businessTypeId;
	private String ostan;
	private String opbkId;
	private int pageNo;
	private int pageSize;

}
