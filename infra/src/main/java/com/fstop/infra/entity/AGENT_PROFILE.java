package com.fstop.infra.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Table(name="AGENT_PROFILE")
@Entity(name="tw.org.twntch.po.AGENT_PROFILE")
@Getter
@Setter
public class AGENT_PROFILE {
	private static final long serialVersionUID = -5228277355761735095L;
	@Id
	private String COMPANY_ID;
	private String COMPANY_ABBR_NAME;
	private String COMPANY_NAME;
	private String ACTIVE_DATE;
	private String STOP_DATE;
	private String WS_URL;
	private String WS_NAME_SPACE;
	private String KEY_ID;
	private String COMPANY_NO;
	private String KEY_FLAG;
}
