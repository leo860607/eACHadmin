package com.fstop.infra.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fstop.infra.entity.AGENT_FEE_CODE;
import com.fstop.infra.entity.AGENT_FEE_CODE.AGENT_FEE_CODE_PK;


@Repository
public interface AGENT_FEE_CODE_Dao extends JpaRepository<AGENT_FEE_CODE, AGENT_FEE_CODE_PK>{

}
