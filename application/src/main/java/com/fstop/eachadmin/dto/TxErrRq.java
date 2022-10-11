package com.fstop.eachadmin.dto;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
public class TxErrRq<VW_ONBLOCKTAB> {

	@NotEmpty(message = "日期格式不正确，請輸入民國年，例如01091021")
	@Length(min = 8, max = 8)
	@Schema(description = "營業日期")
	@JsonProperty("BIZDATE")
	private String BIZDATE;

	@NotEmpty(message = "最小值為01")
	@Length(min = 01)
	@Schema(description = "清算階段")
	@JsonProperty("CLEARINGPHASE")
	private String CLEARINGPHASE;

	@NotEmpty(message = "最小值為0")
	@Length(min = 0)
	@Schema(description = "頁數")
	@JsonProperty("PAGE")
	private String page;

	@Schema(description = "列表")
	@JsonProperty("ROWS")
	private List<VW_ONBLOCKTAB> rows;

	@Schema(description = "查詢結果總筆數")
	@JsonProperty("SIDX")
	private String sidx;

	@Schema(description = "交易金額")
	@JsonProperty("SORD")
	private String sord;

}
