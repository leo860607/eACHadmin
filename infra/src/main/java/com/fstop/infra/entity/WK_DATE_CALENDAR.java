package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name= "WKDATECALENDAR")
@Table(name="MASTER_WKDATECALENDAR")
@Getter
@Setter
public class WKDATECALENDAR implements Serializable {
	
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
