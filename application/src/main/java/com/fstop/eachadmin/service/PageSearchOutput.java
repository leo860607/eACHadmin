package com.fstop.eachadmin.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PageSearchOutput {

	String filter_bat;
	String startDate;
	String endDate;
	String clearingphase;
	String bgbkId;
	String businessTypeId;
	String ostan;
	String opbkId;
	int pageNo;
	int pageSize;

}
