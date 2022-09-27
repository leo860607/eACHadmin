package com.fstop.eachadmin.dto;



import com.fasterxml.jackson.annotation.JsonProperty;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
@Getter
@Setter
public  class EachSysStatusTabRq  {

	@Schema(description = "")
	@JsonProperty("activeDate")
	private String activeDate;
	
	@Schema(description = " ")
	@JsonProperty("compareWay")
	private String compareWay;
}
