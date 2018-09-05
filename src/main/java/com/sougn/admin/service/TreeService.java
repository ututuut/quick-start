package com.sougn.admin.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sougn.admin.dao.TreeDao;
import com.sougn.admin.entity.Tree;

@Service
public class TreeService {

	@Autowired
	private TreeDao treeDao;

	public void deleteById(Integer id) {
		treeDao.deleteById(id);
	}

	public Tree getOne(Integer id) {
		return treeDao.getOne(id);
	}

	public void save(Tree tree) {
		treeDao.save(tree);
	}

	public List<Tree> getTree(Integer id) {
		List<Tree> treeList = null;
		if (id == null) {
			treeList = treeDao.findByParentIdIsNull();
			for (Tree tree : treeList) {
				getChild(tree);
			}
		} else {
			Tree tree = treeDao.getOne(id);
			getChild(tree);
			treeList.add(tree);
		}
		return treeList;
	}
	
	private Tree getChild(Tree tree) {
		List<Tree> treeList = treeDao.findByParentId(tree.getId());
		for(Tree treeEl : treeList) {
			getChild(treeEl);
		}
		tree.setTreeList(treeList);
		return tree;
	}

}
