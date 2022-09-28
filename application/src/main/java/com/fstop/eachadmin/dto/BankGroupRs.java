package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BankGroupRs {
	@Schema(description = "訊息")
	@JsonProperty("msg")
	private String msg;

	@Schema(description = "結果")
	@JsonProperty("result")
	private String result;
}
