package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "BUSINESSTYPE")
@Table(name = "MASTER_BUSINESS_TYPE")
@Getter
@Setter
public class BUSINESSTYPE implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1788986625072345048L;
	/**
	 * 
	 */
	
	/**
	 *
	 *  21XX-帳單扣款類
		22XX-入帳類
		23XX-數位錢包類
		24XX-中華QR扣類
		25XX-網路購物類
		26XX-晶片卡轉帳類
		27XX-繳費類交易
		28XX-自動充值
		41XX-整批交易
		51XX-結帳類
		61XX-授權類
	 */
	@Id
	private	String	BUSINESS_TYPE_ID	;
	private	String	BUSINESS_TYPE_NAME	;
	private	String	CDATE	;
	private	String	UDATE	;
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((BUSINESS_TYPE_ID == null) ? 0 : BUSINESS_TYPE_ID.hashCode());
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
		BUSINESSTYPE other = (BUSINESSTYPE) obj;
		if (BUSINESS_TYPE_ID == null) {
			if (other.BUSINESS_TYPE_ID != null)
				return false;
		} else if (!BUSINESS_TYPE_ID.equals(other.BUSINESS_TYPE_ID))
			return false;
		return true;
	}
	
	
	
}
