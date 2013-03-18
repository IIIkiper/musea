package com.musea.service;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.musea.model.dao.vk.VKUserDao;
import com.musea.model.domain.User;
import com.musea.model.domain.vk.VKUser;
import com.musea.service.vk.VKUserService;

import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {
	
	@Mock
	private VKUserDao userDao;
	
	private AbstractUserService<? extends User> userService;
	
	@BeforeTest
	public void beforeClass() {
		initMocks(this);
		
	}
		
	@BeforeMethod
	public void setUp() {
		//initMocks(this);
		userService = new VKUserService(userDao);
	}
	
	@Test
	public void testCreateUser() {		
		Mockito.when(userDao.getEntityClass()).thenReturn(VKUser.class);
		
		
		
		User user = userService.getOrCreateUserBySystemId(2L);
		Assert.assertNotNull(user);
		Assert.assertEquals(2L, user.getSystemId());
	}

}
