package com.musea.model.dao;

import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.musea.model.domain.Audio;

public abstract class AudioDao<T extends Audio> extends Dao<T> {
	
	public List<T> getAudiosBySystemIds(Collection<Long> ids) {
		CriteriaBuilder cb = getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(entityClass);
		Root<T> from = query.from(entityClass);
		
		return list(
			query.select(from)
			.where(from.get("systemId").in(ids))
		);
	}
}