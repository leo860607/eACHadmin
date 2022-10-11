package com.fstop.eachadmin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UserlogTchUserIdRs {

	@Schema(description = "票交端操作紀錄查詢使用者Id")
	@JsonProperty("UserId")
	private String UserId;
}
