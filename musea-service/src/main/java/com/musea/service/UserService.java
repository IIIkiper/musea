package com.musea.service;

import org.springframework.transaction.annotation.Transactional;

import com.musea.model.dao.UserDao;
import com.musea.model.domain.User;

public abstract class UserService<T extends User> extends GenericService<T> {
	
	protected abstract UserDao<T> getDao();
	
	protected abstract T getUserInstance();
	
	@Transactional(readOnly = false)
	public T getOrCreateUserBySystemId(long systemId) {
		T user = getDao().getUserBySystemId(systemId);
		if (user == null) {
			user = getUserInstance();
			user.setSystemId(systemId);
			getDao().persist(user);
		}
		return user;
	}
}