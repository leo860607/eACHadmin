package com.fstop.infra.dao;

import java.util.List;

import com.fstop.infra.entity.AGENT_FEE_CODE;

public interface AGENT_FEE_CODE_CustomDao {
	public List<AGENT_FEE_CODE> getListDataBySql(String sql ,  List<String> values);
}
