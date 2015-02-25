/**
 * 
 */
package ua.cn.yet.birthdayGifts.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;

import org.junit.Test;

import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.domain.UserRole;
import ua.cn.yet.birthdayGifts.exceptions.GeneralServiceException;
import ua.cn.yet.birthdayGifts.services.IGenericService;
import ua.cn.yet.birthdayGifts.services.IUserService;

/**
 * This test case will test the {@link IUserService} methods. As it extends from
 * {@link TestGenericServiceAbstract} it will be automatically tested for
 * general methods. This class will test additional aspects of service behavior
 * as well as additional methods.
 * 
 * @author Yuriy Tkach
 * 
 * @see IUserService, TestGenericServiceAbstract, User
 */
public class TestUserService extends TestGenericServiceAbstract<User> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ua.cn.yet.birthdayGifts.service.TestGenericServiceAbstract#getFirstEntity
	 * ()
	 */
	@Override
	protected User getFirstEntity() {
		User user = new User("aaa", "aaa", UserRole.VIP);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ua.cn.yet.birthdayGifts.service.TestGenericServiceAbstract#getSecondEntity
	 * ()
	 */
	@Override
	protected User getSecondEntity() {
		User user = new User("bbb", "bbb", UserRole.PRESENTER);
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ua.cn.yet.birthdayGifts.service.TestGenericServiceAbstract#getService()
	 */
	@Override
	protected IGenericService<User> getService() {
		return getUserService();
	}

	/**
	 * Getting user service from context
	 * 
	 * @return IUserService implementation
	 */
	protected IUserService getUserService() {
		return (IUserService) appContext.getBean("userService");
	}

	/**
	 * Test method for
	 * {@link ua.cn.yet.birthdayGifts.services.impl.UserServiceImpl#save(ua.cn.yet.birthdayGifts.domain.User)}
	 * . Testing that we can't save user with existent login
	 * 
	 * @throws GeneralServiceException
	 * @throws IllegalArgumentException
	 */
	@Test(expected = GeneralServiceException.class)
	public void testSaveUser() throws IllegalArgumentException,
			GeneralServiceException {
		getUserService().save(getFirstEntity());
	}

	/**
	 * Test method for
	 * {@link ua.cn.yet.birthdayGifts.services.impl.UserServiceImpl#getUserByLoginAndPassword(java.lang.String, java.lang.String)}
	 * .
	 * 
	 * @throws GeneralServiceException
	 * @throws IllegalArgumentException
	 */
	@Test
	public void testGetUserByLoginAndPassword()
			throws IllegalArgumentException, GeneralServiceException {
		User foundUser = getUserService().getUserByLoginAndPassword(
				entity1.getLogin(), entity1.getPassword());

		assertNotNull(foundUser);
		assertNotNull(foundUser.getId());
		assertEquals(entity1.getLogin(), foundUser.getLogin());
		assertEquals(entity1.getPassword(), foundUser.getPassword());

		foundUser = getUserService().getUserByLoginAndPassword(
				entity2.getLogin(), entity2.getPassword());

		assertNotNull(foundUser);
		assertNotNull(foundUser.getId());
		assertEquals(entity2.getLogin(), foundUser.getLogin());
		assertEquals(entity2.getPassword(), foundUser.getPassword());
	}

	/**
	 * Test method for
	 * {@link ua.cn.yet.birthdayGifts.services.impl.UserServiceImpl#getUserByLoginAndPassword(java.lang.String, java.lang.String)}
	 * .
	 * 
	 * @throws GeneralServiceException
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetUserByLoginAndPasswordIllegalLogin()
			throws IllegalArgumentException, GeneralServiceException {
		getUserService().getUserByLoginAndPassword(null, "asdf");
	}

	/**
	 * Test method for
	 * {@link ua.cn.yet.birthdayGifts.services.impl.UserServiceImpl#getUserByLoginAndPassword(java.lang.String, java.lang.String)}
	 * .
	 * 
	 * @throws GeneralServiceException
	 * @throws IllegalArgumentException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGetUserByLoginAndPasswordIllegalPassword()
			throws IllegalArgumentException, GeneralServiceException {
		getUserService().getUserByLoginAndPassword("asdf", null);
	}

	/**
	 * Test method for
	 * {@link ua.cn.yet.birthdayGifts.services.impl.UserServiceImpl#getVIPUsers()}
	 * .
	 */
	@Test
	public void testGetVIPUsers() {
		Collection<User> collection = getUserService().getVIPUsers();
		assertNotNull(collection);
		assertFalse(collection.isEmpty());

		assertTrue(collection.contains(entity1));
		assertFalse(collection.contains(entity2));
	}

	/**
	 * Test method for
	 * {@link ua.cn.yet.birthdayGifts.services.impl.UserServiceImpl#isUserExists(java.lang.String)}
	 * .
	 */
	@Test
	public void testIsUserExists() {
		assertTrue(getUserService().isUserExists(entity1.getLogin()));
		assertTrue(getUserService().isUserExists(entity2.getLogin()));

		assertFalse(getUserService().isUserExists(null));
		assertFalse(getUserService().isUserExists(""));
	}

}
