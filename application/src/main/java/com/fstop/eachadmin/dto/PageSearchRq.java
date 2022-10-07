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

public class PageSearchRq {
	
	
	@Length(min = 1, max = 20)
	@Schema(description = "是否過濾整批資料")
	@JsonProperty("FILTER_BAT")
	private String filter_bat;

	@NotEmpty
	@Length(min = 8, max = 8)
	@Schema(description = "營業日")
	@JsonProperty("STARTDATE")
	private String startDate;
	
	@NotEmpty
	@Length(min = 8, max = 8)
	@Schema(description = "營業日")
	@JsonProperty("ENDDATE")
	private String endDate;
	
	@NotEmpty
	@Length(min = 1, max = 4)
	@Schema(description = "清算階段")
	@JsonProperty("CLEARINGPHASE")
	private String clearingphase;

	@NotEmpty
	@Length(min = 1, max = 4)
	@Schema(description = "總行代號")
	@JsonProperty("BGBKID")
	private String bgbkId;

	@NotEmpty
	@Length(min = 1, max = 4)
	@Schema(description = "業務類別")
	@JsonProperty("BUSINESSTYPEID")
	private String businessTypeId;

	
	@Schema(description = "系統追蹤序號")
	@JsonProperty("OSTAN")
	private String ostan;
	
	@NotEmpty
	@Length(min = 1, max = 4)
	@Schema(description = "處理情形")
	@JsonProperty("RESULTCODE")
	private String ResultCode;
	
	@NotEmpty
	@Length(min = 1, max = 4)
	@Schema(description = "操作行")
	@JsonProperty("OPBKID")
	private String opbkId;

	
	@Schema(description = "")
	@JsonProperty("PAGENO")
	private Integer pageNo;

	
	@Schema(description = "")
	@JsonProperty("PAGESIZE")
	private Integer pageSize;

	@NotEmpty
	@Length(min = 1, max = 4)
	@Schema(description = "交易金額")
	@JsonProperty("SORD")
	private String sord;
	
	@NotEmpty
	@Length(min = 1, max = 4)
	@Schema(description = "查詢結果總筆數")
	@JsonProperty("SIDX")
	private String sidx;
	
	


}
