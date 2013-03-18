package com.musea.service.vk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.musea.model.dao.AbstractUserDao;
import com.musea.model.dao.vk.VKUserDao;
import com.musea.model.domain.vk.VKUser;
import com.musea.service.AbstractUserService;

@Service
public class VKUserService extends AbstractUserService<VKUser> {
	
	@Autowired
	private VKUserDao userDao;
	
	public VKUserService() { }
	
	public VKUserService(VKUserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	protected AbstractUserDao<VKUser> getDao() {
		return userDao;
	}
}