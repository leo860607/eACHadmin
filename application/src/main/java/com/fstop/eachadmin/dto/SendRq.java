package com.fstop.eachadmin.dto;


import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendRq {
	
	@Schema(description = "")
	@JsonProperty("stan")
	private String stan;
	
	@Schema(description = "")
	@JsonProperty("txdate")
	private String txdate;

	
}
