package com.fstop.infra.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ONBKNOTTRADRES_SEARCH {

	@Schema(description = "交易金額")
	@JsonProperty("TXAMT")
	private BigDecimal TXAMT;
}
