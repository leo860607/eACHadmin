package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PageSearchRs<UNDONE_TXDATA> {

	@JsonProperty("DATASUMLIST")
	private List<UNDONE_TXDATA> dataSumList;

	@Schema(description = "")
	@JsonProperty("TOTAL")
	private Integer total;

	@Schema(description = "")
	@JsonProperty("PAGE")
	private Integer page;

	@Schema(description = "")
	@JsonProperty("RECORDS")
	private Integer records;

	@Schema(description = "")
	@JsonProperty("ROWS")
	private List<UNDONE_TXDATA> rows;

}
