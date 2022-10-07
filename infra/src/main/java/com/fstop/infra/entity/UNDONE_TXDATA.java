package com.fstop.infra.entity;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//這支原生位置是bean，先放在entity裡面 20220916
public  class UNDONE_TXDATA  {
	private String PCODE;
	private String TXDT;
	private String TXDATE;
	private String STAN;	
	private String SENDERBANKID;	
	private String OUTBANKID;
	private String INBANKID;	
	private String OUTACCTNO;	
	private String INACCTNO;
	private String OUTACCT;	
	private String INACCT;
	private BigDecimal TXAMT;	
	private String CONRESULTCODE;	
	private String RESULTCODE;
	private String TXID;
	private String SENDERID;
	private String NEWTXDT;
	private String NEWTXAMT;

	

}
