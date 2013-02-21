package com.musea.service.vk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musea.model.dao.UserDao;
import com.musea.model.dao.vk.VKUserDao;
import com.musea.model.domain.vk.VKUser;
import com.musea.service.UserService;

@Service
public class VKUserService extends UserService<VKUser> {
	
	@Autowired
	private VKUserDao userDao;

	@Override
	protected UserDao<VKUser> getDao() {
		return userDao;
	}

	@Override
	protected VKUser getUserInstance() {
		return new VKUser();
	}	
}