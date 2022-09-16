package com.fstop.infra.entity;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fstop.infra.entity.RponBlockTabPk;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RponBlockTabPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8175489765163321102L;
	private String TXDATE;
	private String STAN;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((STAN == null) ? 0 : STAN.hashCode());
		result = prime * result + ((TXDATE == null) ? 0 : TXDATE.hashCode());
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
		RponBlockTabPk other = (RponBlockTabPk) obj;
		if (STAN == null) {
			if (other.STAN != null)
				return false;
		} else if (!STAN.equals(other.STAN))
			return false;
		if (TXDATE == null) {
			if (other.TXDATE != null)
				return false;
		} else if (!TXDATE.equals(other.TXDATE))
			return false;
		return true;
	}
	
	
}
