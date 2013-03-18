package com.musea.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.musea.model.ModelCfg;
import com.musea.model.OrmCfg;
import com.musea.model.domain.User;
import com.musea.service.vk.VKUserService;

@ContextConfiguration(classes = {ServiceCfg.class, DataCfgTest.class, ModelCfg.class, OrmCfg.class})
public class UserServiceTxTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	private VKUserService userService;
	
	@PersistenceContext
	private EntityManager em;
	
	@Test
	public void test() {
		User user = userService.getOrCreateUserBySystemId(2L);
		Assert.assertNotNull(user);
		Assert.assertEquals(2L, user.getSystemId());
		
		user = em.find(User.class, 2l);
		Assert.assertNotNull(user);
	}

}
