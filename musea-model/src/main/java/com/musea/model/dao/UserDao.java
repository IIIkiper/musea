package com.musea.model.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.musea.model.domain.User;

@Repository
public class UserDao extends Dao<User> {
	
	public User getUserBySystemId(long systemId, String... associations) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<User> query = cb.createQuery(entityClass);
		Root<User> from = query.from(entityClass);
		
		initializeAssociations(from, associations);	
		
		query.select(from).where(cb.equal(from.get("systemId"), systemId));
		return single(query);
	}
}