package com.musea.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.musea.model.dao.Dao;
import com.musea.model.dao.GenreDao;
import com.musea.model.dao.UserDao;
import com.musea.model.domain.Genre;
import com.musea.model.domain.User;
import com.musea.service.dto.UserGenreCollection;

//@Service
//@Transactional
public class GenreService extends GenericService<Genre> {
	
	@Autowired private GenreDao genreDao;
	@Autowired private UserDao userDao;

	@Override
	protected Dao<Genre> getDao() {
		return genreDao;
	}

	/**
	 * @param id internal ID of current user. Uses to limit genre info by user itself and his friends.
	 * @param systemIds system IDs of users for genre statics.
	 * @return Genre statistics for user with specified systemIds.
	 */
	@Deprecated
	public Collection<UserGenreCollection> getGenres(Long id, Collection<Long> systemIds) {
		User user = userDao.getById(id, "friends");
		Collection<Long> allowedIds = new HashSet<>();
		
		Iterator<User> iter = user.getFriends().iterator();
		while (iter.hasNext()) {
			long userId = iter.next().getId();
			if (systemIds.contains(userId) || userId == id) { //only self ID and friends IDs are allowed
				allowedIds.add(userId);
			}
		}
		
		if (allowedIds.isEmpty()) {
			return null;
		}
		
		// Fetching users with song genres
		Collection<UserGenreCollection> userGenreCollections = new ArrayList<UserGenreCollection>();
		UserGenreCollection userGenreCollection = null;
		Long currentUserId = -1L; // none of the users can have negative system ID.
		for (Object[] item : userDao.getUsersGenres(allowedIds)) {
			Long userId = (Long) item[0];
			
			if (currentUserId != userId) {
				if (userGenreCollection != null) {
					userGenreCollections.add(userGenreCollection);
				}
				userGenreCollection = new UserGenreCollection();
				userGenreCollection.setSystemId(userId);
				currentUserId = userId;
			}
			
			userGenreCollection.addGenre(new Object[] {item[1], item[2], item[3]});			
		}
		if (userGenreCollection != null) {
			userGenreCollections.add(userGenreCollection); // adding last element if exist.
		}
		
		return userGenreCollections;
	}
}
