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
public class BANKAPSTATUSTAB implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7521516122770873553L;
	@EmbeddedId
	private BANKAPSTATUSTAB_PK id;
	private String MBAPSTATUS;
	

}
