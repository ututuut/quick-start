package com.sougn.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.http.util.TextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sougn.admin.dao.UserDao;
import com.sougn.admin.entity.User;

@Service
@SuppressWarnings("all")
public class UserService {

	@Autowired
	private UserDao userDao;

	public void save(User user) {
		userDao.save(user);
	}

	public User getOne(Integer id) {
		return userDao.getOne(id);
	}

	public void deleteById(Integer id) {
		userDao.deleteById(id);
	}

	public Page<User> findAllList(User user, Integer pageNumber, Integer pageSize, String sorts,String order) {
		Sort sort = null;
		if("DESC".equals(order)) {
			sort = new Sort(Sort.Direction.DESC, sorts);
		}else {
			sort = new Sort(Sort.Direction.ASC, sorts);
		}
		
		Pageable page = new PageRequest(pageNumber, pageSize, sort);
		return userDao.findAll(new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicate = new ArrayList<>();
				if(!TextUtils.isEmpty(user.getEmail())) {
					predicate.add(criteriaBuilder.like(root.get("email").as(String.class),"%"+user.getEmail()+"%"));
				}
				if(!TextUtils.isEmpty(user.getLoginId())) {
					predicate.add(criteriaBuilder.like(root.get("loginId").as(String.class),"%"+user.getLoginId()+"%"));
				}
				if(!TextUtils.isEmpty(user.getPhone())) {
					predicate.add(criteriaBuilder.like(root.get("phone").as(String.class),"%"+user.getPhone()+"%"));
				}
				if(!TextUtils.isEmpty(user.getTitle())) {
					predicate.add(criteriaBuilder.like(root.get("title").as(String.class),"%"+user.getTitle()+"%"));
				}
				if(!TextUtils.isEmpty(user.getName())) {
					predicate.add(criteriaBuilder.like(root.get("name").as(String.class),"%"+user.getName()+"%"));
				}
				/**
				 * 连接查询条件, 不定参数，可以连接0..N个查询条件
				 */
				//query.where(criteriaBuilder.like(namePath, "%李%"), criteriaBuilder.like(nicknamePath, "%王%")); // 这里可以设置任意条查询条件
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		}, page);
	}

	public User findByLoginIdAndPasswd(String loginId, String passwd) {
		return userDao.findByLoginIdAndPasswd(loginId, passwd);
	}

	public User findByLoginId(String loginId) {
		return userDao.findByLoginId(loginId);
	}

}
