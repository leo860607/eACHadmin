package com.fstop.infra.entity;

import java.math.BigDecimal;

import javax.persistence.EmbeddedId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ONBLOCKNOTTRADRES_SEARCH {

	private BigDecimal TXAMT;
	
	private Integer num;
	
	@EmbeddedId

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((TXAMT == null) ? 0 : TXAMT.hashCode());
		result = prime * result
				+ ((num == null) ? 0 : num.hashCode());

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
		ONBLOCKNOTTRADRES_SEARCH other = (ONBLOCKNOTTRADRES_SEARCH) obj;
		if (TXAMT == null) {
			if (other.TXAMT != null)
				return false;
		} else if (!TXAMT.equals(other.TXAMT))
			return false;
		if (num == null) {
			if (other.num != null)
				return false;
		} else if (!num.equals(other.num))
			return false;
		
		return true;
	}
}
