package com.fstop.eachadmin.dto;

import java.util.Map;

import com.fstop.infra.entity.VW_ONBLOCKTAB;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TxErrDetailRs {
	
	private String NEWEXTENDFEE;
	private String TXN_TYPE;
	private String NEWSENDERFEE_NW;
	private String NEWINFEE_NW;
	private String NEWOUTFEE_NW;
	private String NEWWOFEE_NW;
	private String NEWEACHFEE_NW;
	private String NEWFEE_NW;
//	private Map DetailData;
	private VW_ONBLOCKTAB DetailMapData;

}
