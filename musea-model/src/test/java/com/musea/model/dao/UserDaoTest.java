package com.musea.model.dao;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.musea.model.DataCfgTest;
import com.musea.model.ModelCfg;
import com.musea.model.OrmCfg;
import com.musea.model.domain.User;
import com.musea.model.domain.vk.VKUser;

@ContextConfiguration(classes = {ModelCfg.class, OrmCfg.class, DataCfgTest.class})
@TransactionConfiguration(defaultRollback = true)
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private UserDao userDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Test
	public void testGetById() {
		long systemId = 43L;
		
		VKUser vkUser = new VKUser();
		vkUser.setSystemId(systemId);
		entityManager.persist(vkUser);
		
		entityManager.flush();
		
		long id = vkUser.getId();
		
		User user = userDao.getById(id);
		Assert.assertNotNull(user);
		Assert.assertEquals(user.getSystemId(), systemId);
		Assert.assertEquals(user.getId(), id);
	}
	
//	@Test
//	public void testGetUsersBySystemIds() {		
//		VKUser user1 = new VKUser();
//		user1.setSystemId(1l);
//		entityManager.persist(user1);
//		
//		VKUser user2 = new VKUser();
//		user2.setSystemId(2l);
//		entityManager.persist(user2);
//		
//		VKUser user3 = new VKUser();
//		user3.setSystemId(3l);
//		entityManager.persist(user3);
//		
//		List<? extends User> users = userDao.getUsersBySystemIds(Arrays.asList(1L ,2L), "friends");
//		Assert.assertEquals(2, users.size());
//	}
}