package com.fstop.infra.entity;
import java.math.BigDecimal;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class TX_ERR {
	private String ERR_TYPE;
	private String TXDATE;
	private String TXDT;
	private String STAN;
	private String SENDERBANKID;
	private String OUTBANKID;
	private String INBANKID;
	private String SENDERID;
	private String PCODE;
	private BigDecimal TXAMT;
	
}
