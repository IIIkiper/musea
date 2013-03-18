package com.musea.model.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.musea.model.domain.User;

public abstract class AbstractUserDao<T extends User> extends Dao<T> {
		
	public List<T> getUsersBySystemIds(Collection<Long> systemIDs, String... associations) {	
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(entityClass);
		Root<T> from = query.from(entityClass);
		
		initializeAssociations(from, associations);
		
		return list(
			query.select(from)
				.where(from.get("systemId").in(systemIDs))
		);
	}
}