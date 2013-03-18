package com.musea.model.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.musea.model.domain.Genre;

@Repository
public class GenreDao extends Dao<Genre> {
	
	public Collection<Genre> getGenres(Date updateDate) {
		CriteriaBuilder cb = getCriteriaBuilder();		
		CriteriaQuery<Genre> c = cb.createQuery(Genre.class);
		Root<Genre> root = c.from(Genre.class);
		c.select(root);
		
		List<Predicate> predicates = new ArrayList<>();		
		if (updateDate != null) {
			predicates.add(cb.greaterThanOrEqualTo(root.<Date>get("updateDate"), updateDate));
		}
		predicates.add(cb.equal(root.get("manuallyChecked"), 1));

		c.where(cb.and(predicates.toArray(new Predicate[] {})));
		
		return list(c);
	}
}
