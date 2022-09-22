package com.fstop.eachadmin.repository;

import com.fstop.infra.entity.ONPENDINGTAB;

import com.fstop.infra.entity.ONPENDINGTAB_PK;

//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OnPendingTabRepository extends CrudRepository<ONPENDINGTAB, ONPENDINGTAB_PK>{



}
