package com.sougn.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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

import com.sougn.admin.dao.ViewLogDao;
import com.sougn.admin.entity.User;
import com.sougn.admin.entity.ViewLog;
import com.sougn.admin.entity.search.SearchViewLog;

@Service
public class ViewLogService {

	@Autowired
	private ViewLogDao viewLogDao;
	
	public void save(ViewLog viewLog) {
		viewLogDao.save(viewLog);
	}
	
	public void delete(Integer id) {
		viewLogDao.deleteById(id);
	}
	
	public Page<ViewLog> findAllList(SearchViewLog searchViewLog, Integer pageNumber, Integer pageSize, String sorts,String order) {
		Sort sort = null;
		if("DESC".equals(order)) {
			sort = new Sort(Sort.Direction.DESC, sorts);
		}else {
			sort = new Sort(Sort.Direction.ASC, sorts);
		}
		
		Pageable page = new PageRequest(pageNumber, pageSize, sort);
		return viewLogDao.findAll(new Specification<ViewLog>() {

			@Override
			public Predicate toPredicate(Root<ViewLog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				// TODO Auto-generated method stub
				List<Predicate> predicate = new ArrayList<>();
				if(!TextUtils.isEmpty(searchViewLog.getUserLoginId())) {
					//Join<User,ViewLog> join = root.join("user", JoinType.INNER);
					predicate.add(criteriaBuilder.like(root.join("user",JoinType.LEFT).get("loginId"),"%"+searchViewLog.getUserLoginId()+"%"));
				}else {
					//predicate.add(criteriaBuilder.(root.join("user",JoinType.LEFT).get("loginId")));
				}
				if(!TextUtils.isEmpty(searchViewLog.getUrl())) {
					predicate.add(criteriaBuilder.like(root.get("url").as(String.class),"%"+searchViewLog.getUrl()+"%"));
				}
				if(!TextUtils.isEmpty(searchViewLog.getParams())) {
					predicate.add(criteriaBuilder.like(root.get("params").as(String.class),"%"+searchViewLog.getParams()+"%"));
				}
				if(!TextUtils.isEmpty(searchViewLog.getLogName())) {
					predicate.add(criteriaBuilder.like(root.get("logName").as(String.class),"%"+searchViewLog.getLogName()+"%"));
				}
				if(!TextUtils.isEmpty(searchViewLog.getType())) {
					predicate.add(criteriaBuilder.like(root.get("type").as(String.class),"%"+searchViewLog.getType()+"%"));
				}
				if(searchViewLog.getSmallTtl() != null) {
					predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("ttl").as(Integer.class),searchViewLog.getSmallTtl()));
				}
				if(searchViewLog.getBigTtl() != null) {
					predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("ttl").as(Integer.class),searchViewLog.getBigTtl()));
				}
				if(!TextUtils.isEmpty(searchViewLog.getPlace())) {
					predicate.add(criteriaBuilder.like(root.get("place").as(String.class),searchViewLog.getPlace()));
				}
//				if(searchViewLog.getBeginTime() != null) {
//					predicate.add(criteriaBuilder.greaterThanOrEqualTo(root.get("beginTime").as(Date.class),searchViewLog.getBeginTime()));
//				}
//				if(searchViewLog.getEndTime() != null) {
//					predicate.add(criteriaBuilder.lessThanOrEqualTo(root.get("endTime").as(Date.class),searchViewLog.getEndTime()));
//				}
				/**
				 * 连接查询条件, 不定参数，可以连接0..N个查询条件
				 */
				//query.where(criteriaBuilder.like(namePath, "%李%"), criteriaBuilder.like(nicknamePath, "%王%")); // 这里可以设置任意条查询条件
				Predicate[] pre = new Predicate[predicate.size()];
				return query.where(predicate.toArray(pre)).getRestriction();
			}
		}, page);
	}
	
}
