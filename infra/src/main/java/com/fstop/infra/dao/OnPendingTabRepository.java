package com.fstop.infra.dao;

import com.fstop.infra.entity.OnPendingTab;

import com.fstop.infra.entity.OnPendingTabPk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OnPendingTabRepository extends JpaRepository<OnPendingTab, OnPendingTabPk>{


}
