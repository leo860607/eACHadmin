package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name= "WK_DATE_CALENDAR")
@Table(name="MASTER_WK_DATE_CALENDAR")
@Getter
@Setter
public class WK_DATE_CALENDAR implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2790938525294469301L;
	@Id
	@OrderBy("TXN_DATE ASC")
	private String TXN_DATE;
	private Integer WEEKDAY;
	private String IS_TXN_DATE;
	private String CDATE;
	private String UDATE;
	


}
