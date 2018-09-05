package com.sougn.admin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sougn.admin.entity.Tree;

public interface TreeDao extends JpaRepository<Tree, Integer> {
	
	public List<Tree> findByParentIdIsNull();
	
	public List<Tree> findByParentId(Integer parentId);
}
