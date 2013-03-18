package com.musea.service;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.transaction.annotation.Transactional;

import com.musea.model.dao.Dao;
import com.musea.model.domain.Domain;

/**
 * @author Roman Zaripov
 */
public abstract class GenericService<T extends Domain> {
	
	protected abstract Dao<T> getDao();
	
	@Transactional(readOnly = true)
	public T getById(Serializable id) {
		return getDao().getById(id);
	}
	
	/**
	 * This method updates persisted ORM collection without replacing all it elements. Doing so makes ORM
	 * execute very few queries to database (comparing with full collection replacement).
	 * Attention: both collections should contain persisted elements.
	 * equals() method for collection elements should be overriden.
	 * @param collection - collection to change
	 * @param actualCollection - target collection.
	 */
	protected <X extends Domain> void updateCollection(Collection<X> collection, Collection<X> actualCollection) {
		// Deleting elements, that not present in actual collection
		Iterator<X> iter = collection.iterator(); // TODO retainAll ?
		while(iter.hasNext()) {
			X item = iter.next();
			if (!actualCollection.contains(item)) {
				iter.remove();
			}
		}
		
		// Adding new elements to collection.
		for (X item : actualCollection) {
			if (!collection.contains(item) && item != null) {
				collection.add(item);
			}
		}		
	}
	
	/**
	 * The same as {@link #updateCollection(Collection, Collection)}, but returns added elements.
	 * @param collection - collection to change
	 * @param actualCollection - reference collection.
	 * @return elements added to modified collection.
	 */
/*	protected <X extends Domain> Collection<X> updateCollectionAndGetNew(Collection<X> collection, Collection<X> actualCollection) {		
		// Deleting elements, that not present in actual collection
		Iterator<X> iter = collection.iterator();
		while(iter.hasNext()) {
			X item = iter.next();
			if (!actualCollection.contains(item)) {
				iter.remove();
			}
		}
		
		// Adding new elements to collection.
		Collection<X> added = new HashSet<>();
		for (X item : actualCollection) {
			if (!collection.contains(item) && item != null) {
				collection.add(item);
				added.add(item);
			}
		}
		return added;		
	}*/
	
	protected <X,Y extends Domain> Collection<X> getPropertyCollection(Collection<Y> collection, String propertyName, Class<X> propertyClass, Class<Y> beanClass) {
		Collection<X> result = new HashSet<>();
		if (collection != null) {
			try {
				PropertyDescriptor pd = new PropertyDescriptor(propertyName, beanClass);
				for (Domain d : collection) {
					if (d != null) {
						result.add(propertyClass.cast(pd.getReadMethod().invoke(d)));
					}
				}
			} catch (Exception ex) {
				System.out.println("property collection error");
				ex.printStackTrace();			
			}
		}
		return result;
	}
	
	protected <X,Y extends Domain> Collection<X> getPropertyCollection(Y[] collection, String propertyName, Class<X> propertyClass, Class<Y> beanClass) {
		return getPropertyCollection(Arrays.asList(collection), propertyName, propertyClass, beanClass);
	}
}