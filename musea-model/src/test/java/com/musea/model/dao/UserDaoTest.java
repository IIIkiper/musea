package com.musea.model.dao;

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

@ContextConfiguration(classes = {ModelCfg.class, OrmCfg.class, DataCfgTest.class})
@TransactionConfiguration(defaultRollback = true)
public class UserDaoTest extends AbstractTransactionalTestNGSpringContextTests {
	
	@Autowired
	private UserDao usedDao;
	
	@Test
	public void testGetUserBySystemId() {
		User user = usedDao.getUserBySystemId(0);
		Assert.assertNull(user);
	}
}