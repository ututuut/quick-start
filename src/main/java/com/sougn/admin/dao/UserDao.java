package com.sougn.admin.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.sougn.admin.entity.User;

public interface UserDao extends JpaSpecificationExecutor<User> ,JpaRepository<User, Integer>{
	 
	 public User findByLoginId(String loginId);
	 
	 public User findByLoginIdAndPasswd(String loginId , String passwd);
	 
	 Page<User> findAll(Specification<User> user,Pageable pageable);
}
