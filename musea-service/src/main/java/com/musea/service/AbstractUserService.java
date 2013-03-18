package com.musea.service;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.musea.model.dao.AbstractUserDao;
import com.musea.model.domain.User;

public abstract class AbstractUserService<T extends User> extends GenericService<T> {
	
	protected abstract AbstractUserDao<T> getDao();
	
	/**
	 * Returns {@link User} with specufied system ID. If such user doesn't exist, one will be created.
	 * @param systemId system ID of user to return
	 * @return {@link User} with specified system ID.
	 */
	public T getOrCreateUserBySystemId(Long systemId) {
		List<T> users = getDao().getUsersBySystemIds(Collections.singleton(systemId));
		return users.isEmpty() ? createUser(systemId) : users.iterator().next();
	}
	
	/**
	 * Creates new {@link User}.
	 * @param systemId system ID of new user
	 * @return new {@link User} instance
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public T createUser(Long systemId) {
		if (systemId == null) {
			throw new IllegalArgumentException("System ID cannot be null");
		}
		T user = null;
		try {
			user = getDao().getEntityClass().newInstance();
		} catch (InstantiationException | IllegalAccessException ex) {
			// Cannot happen
		}
		user.setSystemId(systemId);
		getDao().persist(user);
		return user;
	}
	
	/**
	 * Manages user friends. Unbinds removed friends and links with added ones. User will be created in DB
	 * if friend with specified system ID doesn't exist. This method intended to be called every time 
	 * user enters the application.
	 * @param id current user ID
	 * @param systemIds system IDs of user friends
	 * @return IDs of user friends with expired song information or that doesn't exist in DB before.
	 */
	@SuppressWarnings("unchecked")
	public Collection<Long> manageUserFriends(Long id, Collection<Long> systemIds) {
		Collection<Long> newSystemIds = new HashSet<>(systemIds);
		Collection<T> friends = null;	
		
		if (systemIds != null && !systemIds.isEmpty()) {
			friends = getDao().getUsersBySystemIds(systemIds);
			Collection<Long> existSystemIDs = getPropertyCollection(friends, "systemId", Long.class, getDao().getEntityClass());

			newSystemIds.removeAll(existSystemIDs); // Defining user, that not exist in DB
			for (Long newSystemId : newSystemIds) {
				if (newSystemId != null) {
					friends.add(createUser(newSystemId));
				}
			}
		}
		
		T user = getDao().getById(id, "friends");
		if (friends == null || friends.isEmpty()) {
			user.getFriends().clear();
		} else {
			updateCollection((Collection<T>) user.getFriends(), friends);
			
			// Finding users with expired song information
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.WEEK_OF_YEAR, -1);
			Date lastWeek = cal.getTime();
			
			for (T friend : friends) {
				if (friend.getUpdateDate() == null || friend.getUpdateDate().before(lastWeek)) {
					newSystemIds.add(friend.getSystemId());
				}
			}
		}
		
		return newSystemIds;
	}
}