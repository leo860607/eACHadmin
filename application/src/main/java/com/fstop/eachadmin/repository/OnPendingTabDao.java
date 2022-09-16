package com.fstop.eachadmin.repository;


import tw.org.twntch.po.ONPENDINGTAB;
import tw.org.twntch.po.ONPENDINGTAB_PK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class OnPendingTabDao extends JpaRepository<ONPENDINGTAB, ONPENDINGTAB_PK> {

}
