package com.sougn.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sougn.admin.entity.Resources;
import com.sougn.admin.entity.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {

}
