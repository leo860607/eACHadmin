package com.fstop.eachadmin.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserlogTchFunRs {
	@Schema(description = "功能名稱")
	@JsonProperty("FUNC_NAME")
	private String FUNC_NAME;
	
	@JsonProperty("FUNC_TYPE")
	private String FUNC_TYPE;
	
	@JsonProperty("FUNC_ID")
	private String FUNC_ID;
	
	@JsonProperty("FUNC_URL")
	private String FUNC_URL;
	
	@JsonProperty("SUB_LIST")
	private List<FunSubList> SUB_LIST;
	
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class FunSubList{
		
		@JsonProperty("FUNC_NAME")
		private String FUNC_NAME;
		
		@JsonProperty("FUNC_TYPE")
		private String FUNC_TYPE;
		
		@JsonProperty("FUNC_ID")
		private String FUNC_ID;
		
		@JsonProperty("FUNC_URL")
		private String FUNC_URL;
	}
}
