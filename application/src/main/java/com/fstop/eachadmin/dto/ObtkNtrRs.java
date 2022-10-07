package com.fstop.eachadmin.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fstop.infra.entity.VW_ONBLOCKTAB;


@Getter
@Setter
public class ObtkNtrRs {

    private List OpbkIdList;
    private List BsTypeList;
    private String businessDate;
    private String START_DATE;
    private String END_DATE;
    private Map userData;
    private String OPBK_ID;
    
	@JsonProperty("detailData")
    private VW_ONBLOCKTAB detailData;
	
    private String IsUndone;
    
}
