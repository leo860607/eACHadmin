package com.fstop.infra.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "ONPENDINGTAB")
@Table(name = "MASTER_ONPENDINGTAB")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ONPENDINGTAB implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7521516122770873553L;
	private ONPENDINGTABPK id;
	private String PCODE;
	private String CLEARINGCODE;
	private String OBIZDATE;
	private String OCLEARINGPHASE;
	private String BIZDATE;
	private String CLEARINGPHASE;
	private String RESULTCODE;
	private String SENDERBANK;
	private String SENDERCLEARING;
	private String INCLEARING;
	private String OUTCLEARING;
	private String SENDERACQUIRE;
	private String INACQUIRE;
	private String OUTACQUIRE;
	private String SENDERHEAD;
	private String INHEAD;
	private String OUTHEAD;
	private BigDecimal SENDERFEE;
	private BigDecimal INFEE;
	private BigDecimal OUTFEE;
	private BigDecimal EACHFEE;
	private String TXAMT;
	private String SENDERBANKID;
	private String OUTBANKID;
	private String INBANKID;
	private String OUTACCT;
	private String INACCT;
	private String ACHFLAG;


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ONPENDINGTAB other = (ONPENDINGTAB) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}