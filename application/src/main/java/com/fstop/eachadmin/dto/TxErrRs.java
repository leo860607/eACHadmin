package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxErrRs<VW_ONBLOCKTAB> {

	@JsonProperty("DATASUMLIST")
	private List<VW_ONBLOCKTAB> dataSumList;

	@Schema(description = "")
	@JsonProperty("TOTAL")
	private Integer total;

	@Schema(description = "")
	@JsonProperty("PAGE")
	private String page;

	@Schema(description = "")
	@JsonProperty("RECORDS")
	private Integer records;

	@Schema(description = "")
	@JsonProperty("ROWS")
	private List<VW_ONBLOCKTAB> rows;

}
