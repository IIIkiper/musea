package com.musea.model.dao;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.musea.model.domain.User;

public abstract class UserDao<T extends User> extends Dao<T> {
	
	public T getUserBySystemId(long systemId, String... associations) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(entityClass);
		Root<T> from = query.from(entityClass);
		
		initializeAssociations(from, associations);	
				
		return single(
			query.select(from)
			.where(cb.equal(from.get("systemId"), systemId))
		);
	}
}