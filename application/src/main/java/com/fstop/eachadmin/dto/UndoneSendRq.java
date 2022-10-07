package com.fstop.eachadmin.dto;


import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UndoneSendRq {
	
	@NotEmpty
	@Length(min = 1, max = 10)
	@Schema(description = "系統追蹤序號")
	@JsonProperty("stan")
	private String stan;
	
	@NotEmpty
	@Length(min = 1, max = 10)
	@Schema(description = "交易日期")
	@JsonProperty("txdate")
	private String txdate;

	
}
