package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name= "tw.org.twntch.po.WK_DATE_CALENDAR")
@Table(name="WK_DATE_CALENDAR")
@Getter
@Setter
public class WkDateCalendar implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2790938525294469301L;
	private String TXN_DATE;
	private Integer WEEKDAY;
	private String IS_TXN_DATE;
	private String CDATE;
	private String UDATE;
	
}
