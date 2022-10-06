package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fstop.eachadmin.dto.TxErrRs.TxErrRsList;
import com.fstop.infra.entity.HR_TXP_TIME;
import com.fstop.infra.entity.TX_ERR_ONBLOCKTAB;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HrTxpTimeRs<HR_TXP_TIME> {
	
	@JsonProperty("COUNTANDSUMLIST")
	private List<HR_TXP_TIME> countAndSumList;
	
	@Schema(description = "")
	@JsonProperty("TOTAL")
	private String total;
	
	@Schema(description = "")
	@JsonProperty("PAGE")
	private String page;
	
	@Schema(description = "")
	@JsonProperty("RECORDS")
	private String records;
	
	@Schema(description = "")
	@JsonProperty("ROWS")
	private List<HR_TXP_TIME> rows;
}


