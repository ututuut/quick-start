package com.sougn.admin.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sougn.admin.entity.User;
import com.sougn.admin.entity.ViewLog;

public interface ViewLogDao extends JpaRepository<ViewLog, Integer> {
	
	Page<ViewLog> findAll(Specification<ViewLog> viewLog,Pageable pageable);
}
