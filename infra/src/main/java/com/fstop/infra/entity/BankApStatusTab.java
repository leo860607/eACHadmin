package com.fstop.infra.entity;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "tw.org.twntch.po.BANKAPSTATUSTAB")
@Table(name = "BANKAPSTATUSTAB")

@Getter
@Setter
public class BankApStatusTab implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7521516122770873553L;
	@EmbeddedId
	private BankApStatusTabPk id;
	private String MBAPSTATUS;
	

}
