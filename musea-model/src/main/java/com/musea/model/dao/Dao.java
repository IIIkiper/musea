package com.musea.model.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.FetchParent;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import com.musea.model.domain.Domain;

/**
 * Superclass for DAO objects.
 * @author Roman Zaripov
 * @param <T> - type of domain object, managed with this DAO.
 */

@SuppressWarnings("unchecked")
public abstract class Dao <T extends Domain> {
	
	@PersistenceContext
	protected EntityManager entityManager;
	
	protected final Class<T> entityClass;
	
    {
        Type type = this.getClass().getGenericSuperclass();
        try {
        	entityClass = (Class<T>) ((type instanceof ParameterizedType) ? ((ParameterizedType) type).getActualTypeArguments()[0] : type);
        } catch (ClassCastException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    protected CriteriaBuilder getCriteriaBuilder() {
    	return entityManager.getCriteriaBuilder();
    }
    
    protected <X> List<X> list(CriteriaQuery<X> criteria) {
    	return entityManager.createQuery(criteria).getResultList();
    }
    
    protected <X> List<X> list(CriteriaQuery<X> criteria, int limit, Order... orderBy) {
    	return list(criteria, 0, limit, orderBy);
    }
    
    protected <X> List<X> list(CriteriaQuery<X> criteria, int start, int limit, Order... orderBy) {
    	if (orderBy.length == 0) {
    		throw new IllegalArgumentException("Order must be specified for paging");
    	}
    	criteria.orderBy(orderBy);
    	return entityManager.createQuery(criteria)
	    	.setFirstResult(start)
	    	.setMaxResults(limit)
	    	.getResultList();  	
    }
    
    protected <X> X single(CriteriaQuery<X> criteria) {
    	try {
    		return entityManager.createQuery(criteria).getSingleResult();
    	} catch (Exception ex) {
    		return null;
    	}
    }
    
    protected void initializeAssociations(FetchParent<?, ?> fetchParent, String... associations) {
    	for (String association : associations) {
    		// TODO Hibernate performance warnings - memory consumption.
    		fetchParent.fetch(association, JoinType.LEFT);
    	}    	
    }
    
    public long count() {
    	CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    	CriteriaQuery<Long> criteria = criteriaBuilder.createQuery(Long.class);
    	Root<T> root = criteria.from(entityClass);
    	criteria.select(criteriaBuilder.count(root));
    	return single(criteria);
    }
        
    public T getByID(Serializable id) {
    	return entityManager.find(entityClass, id);
    }
    
    public <X extends Domain> void persist(X entity) {
    	if (entity != null) {
    		entityManager.persist(entity);
    	}
    }
    
    public <X extends Domain> void merge(X entity) {
    	if (entity != null) {
    		entityManager.merge(entity);
    	}
    }
    
    /*
    public void remove(Object entity) {
    	entityManager.remove(entity);
    }
    
    public void refresh(Object entity) {
    	entityManager.refresh(entity);
    }
    */
}