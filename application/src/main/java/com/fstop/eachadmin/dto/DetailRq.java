package com.fstop.eachadmin.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class DetailRq {
	@NotEmpty
	@Length(min = 1, max = 4)
	@JsonProperty("ac_key")
	private String ac_key;

	@NotNull
	@Length(min = 8, max = 8)
	@Schema(description = "交易日期")
	@JsonProperty("txDate")
	private String TXDATE;

	@NotNull
	@Length(min = 1, max = 10)
	@Schema(description = "系統追蹤序號")
	@JsonProperty("stan")
	private String STAN;

}
