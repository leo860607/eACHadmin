package com.fstop.infra.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HR_TXP_TIME {
	private String HOURLAP;
	private String HOURLAPNAME;
	private String TOTALCOUNT;
	private String OKCOUNT;
	private String FAILCOUNT;
	private String PENDCOUNT;
	private String PRCTIME;
	private String DEBITTIME;
	private String SAVETIME;
	private String ACHPRCTIME;
}
