package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fstop.infra.entity.VW_ONBLOCKTAB;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TxErrRs {

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
	private Long records;

	@Schema(description = "")
	@JsonProperty("ROWS")
	private List<TxErrRsList> rows;

	@Getter
	@Setter
	@AllArgsConstructor
	@NoArgsConstructor
	@Data
	public class TxErrRsList {
		private String ERR_TYPE;
		private String TXDATE;
		private String TXDT;
		private String STAN;
		private String SENDERBANKID;
		private String OUTBANKID;
		private String INBANKID;
		private String SENDERID;
		private String PCODE;
		private String TXAMT;
	}

}
