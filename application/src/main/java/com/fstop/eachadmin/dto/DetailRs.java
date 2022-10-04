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
	@JsonProperty("detailData")
	private VW_ONBLOCKTAB detailData;

	@JsonProperty("detailDataMap")
	private Map DETAILDATAMAP;
	
	@JsonProperty("start_date")
	private String START_DATE;
	
	@JsonProperty("end_date")
	private String END_DATE;
	
	
	
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
