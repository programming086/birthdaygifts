/**
 * 
 */
package ua.cn.yet.birthdayGifts.service;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;

import ua.cn.yet.birthdayGifts.domain.Gift;
import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.domain.UserRole;
import ua.cn.yet.birthdayGifts.exceptions.GeneralServiceException;
import ua.cn.yet.birthdayGifts.services.IGenericService;
import ua.cn.yet.birthdayGifts.services.IGiftService;
import ua.cn.yet.birthdayGifts.services.IUserService;

/**
 * @author yuriy
 *
 */
public class TestGiftService extends TestGenericServiceAbstract<Gift> {
	
	/** User entity to use in tests */
	protected User userVip;
	/** User entity to use in tests */
	protected User userPresenter;
	
	/**
	 * Adding users to database, then calling superclass method for adding gifts
	 * @see ua.cn.yet.birthdayGifts.service.TestGenericServiceAbstract#saveEntitiesToDatabase()
	 */
	@Override
	public void saveEntitiesToDatabase() throws IllegalArgumentException,
			GeneralServiceException {
		IUserService userService = (IUserService) appContext.getBean("userService");
		
		userVip = new User("userVip", "userVip", UserRole.VIP);
		userPresenter = new User("userPresenter", "userPresenter", UserRole.PRESENTER);
		
		userService.save(userVip);
		userService.save(userPresenter);
		
		super.saveEntitiesToDatabase();
	}
	
	/**
	 * Calling superclass method first to delete gifts, then deleting users
	 * @see ua.cn.yet.birthdayGifts.service.TestGenericServiceAbstract#deleteEntitiesFromDatabase()
	 */
	@Override
	public void deleteEntitiesFromDatabase() throws IllegalArgumentException,
			GeneralServiceException {
		super.deleteEntitiesFromDatabase();
		
		IUserService userService = (IUserService) appContext.getBean("userService");
		
		if (userVip != null) {
			userService.delEntity(userVip);
		}
		if (userPresenter != null) {
			userService.delEntity(userPresenter);
		}
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.service.TestGenericServiceAbstract#getFirstEntity()
	 */
	@Override
	protected Gift getFirstEntity() {
		Gift gift = new Gift();
		gift.setName("aaa");
		gift.setOwner(userVip);
		return gift;
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.service.TestGenericServiceAbstract#getSecondEntity()
	 */
	@Override
	protected Gift getSecondEntity() {
		Gift gift = new Gift();
		gift.setName("ppp");
		gift.setOwner(userVip);
		return gift;
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.service.TestGenericServiceAbstract#getService()
	 */
	@Override
	protected IGenericService<Gift> getService() {
		return getGiftService();
	}
	
	/**
	 * Getting gift service for tests from application context
	 * @return IGiftService
	 */
	protected IGiftService getGiftService() {
		return (IGiftService) appContext.getBean("giftService");
	}

	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#assignPresenterToGift(ua.cn.yet.birthdayGifts.domain.Gift, ua.cn.yet.birthdayGifts.domain.User)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test
	public void testAssignPresenterToGiftGiftUser() throws GeneralServiceException, IllegalArgumentException {
		getGiftService().assignPresenterToGift(entity1, userPresenter);
		
		entity1 = getGiftService().getEntityById(entity1.getId());
		
		assertEquals(userPresenter, entity1.getPresenter());
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#assignPresenterToGift(ua.cn.yet.birthdayGifts.domain.Gift, ua.cn.yet.birthdayGifts.domain.User)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAssignPresenterToGiftGiftUserIllegalGiftArgument() throws GeneralServiceException, IllegalArgumentException {
		getGiftService().assignPresenterToGift(null, userPresenter);
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#assignPresenterToGift(ua.cn.yet.birthdayGifts.domain.Gift, ua.cn.yet.birthdayGifts.domain.User)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAssignPresenterToGiftGiftUserIllegalUserArgument() throws GeneralServiceException, IllegalArgumentException {
		getGiftService().assignPresenterToGift(entity1, null);
	}

	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#assignPresenterToGift(java.lang.Long, java.lang.Long)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test
	public void testAssignPresenterToGiftLongLong() throws GeneralServiceException, IllegalArgumentException {
		getGiftService().assignPresenterToGift(entity1.getId(), userPresenter.getId());
		
		entity1 = getGiftService().getEntityById(entity1.getId());
		
		assertEquals(userPresenter, entity1.getPresenter());
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#assignPresenterToGift(java.lang.Long, java.lang.Long)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAssignPresenterToGiftLongLongIllegalGiftId() throws GeneralServiceException, IllegalArgumentException {
		getGiftService().assignPresenterToGift(null, userPresenter.getId());
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#assignPresenterToGift(java.lang.Long, java.lang.Long)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAssignPresenterToGiftLongLongIllegalUserId() throws GeneralServiceException, IllegalArgumentException {
		getGiftService().assignPresenterToGift(entity1.getId(), null);
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#assignPresenterToGift(java.lang.Long, java.lang.Long)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test(expected=GeneralServiceException.class)
	public void testAssignPresenterToGiftLongLongNotSavedGift() throws GeneralServiceException, IllegalArgumentException {
		getGiftService().assignPresenterToGift(System.nanoTime(), userPresenter.getId());
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#assignPresenterToGift(java.lang.Long, java.lang.Long)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test(expected=GeneralServiceException.class)
	public void testAssignPresenterToGiftLongLongNotSavedUser() throws GeneralServiceException, IllegalArgumentException {
		getGiftService().assignPresenterToGift(entity1.getId(), System.nanoTime());
	}

	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#getGiftsByOwner(ua.cn.yet.birthdayGifts.domain.User)}.
	 */
	@Test
	public void testGetGiftsByOwnerUser() {
		Collection<Gift> list = getGiftService().getGiftsByOwner(userVip);
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertTrue(list.contains(entity1));
		assertTrue(list.contains(entity2));
		
		list = getGiftService().getGiftsByOwner(userPresenter);
		assertNotNull(list);
		assertTrue(list.isEmpty());
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#getGiftsByOwner(ua.cn.yet.birthdayGifts.domain.User)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetGiftsByOwnerUserIllegalArgument() {
		getGiftService().getGiftsByOwner((User)null);
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#getGiftsByOwner(java.lang.Long)}.
	 */
	@Test
	public void testGetGiftsByOwnerLong() {
		Collection<Gift> list = getGiftService().getGiftsByOwner(userVip.getId());
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertTrue(list.contains(entity1));
		assertTrue(list.contains(entity2));
		
		list = getGiftService().getGiftsByOwner(userPresenter.getId());
		assertNotNull(list);
		assertTrue(list.isEmpty());
		
		list = getGiftService().getGiftsByOwner(System.nanoTime());
		assertNotNull(list);
		assertTrue(list.isEmpty());
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#getGiftsByOwner(java.lang.Long)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetGiftsByOwnerLongIllegalArgument() {
		getGiftService().getGiftsByOwner((Long)null);
	}

	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#getGiftsByPresenter(ua.cn.yet.birthdayGifts.domain.User)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test
	public void testGetGiftsByPresenterUser() throws GeneralServiceException, IllegalArgumentException {
		Collection<Gift> list = getGiftService().getGiftsByPresenter(userPresenter);
		assertNotNull(list);
		assertTrue(list.isEmpty());
		
		list = getGiftService().getGiftsByPresenter(userVip);
		assertNotNull(list);
		assertTrue(list.isEmpty());
		
		getGiftService().assignPresenterToGift(entity1, userPresenter);
		
		list = getGiftService().getGiftsByPresenter(userPresenter);
		assertNotNull(list);
		assertEquals(1, list.size());
		assertTrue(list.contains(entity1));
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#getGiftsByPresenter(ua.cn.yet.birthdayGifts.domain.User)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetGiftsByPresenterUserIllegalArgument() throws GeneralServiceException, IllegalArgumentException {
		getGiftService().getGiftsByPresenter((User)null);
	}

	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#getGiftsByPresenter(java.lang.Long)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test
	public void testGetGiftsByPresenterLong() throws GeneralServiceException, IllegalArgumentException {
		Collection<Gift> list = getGiftService().getGiftsByPresenter(userPresenter.getId());
		assertNotNull(list);
		assertTrue(list.isEmpty());
		
		list = getGiftService().getGiftsByPresenter(userVip.getId());
		assertNotNull(list);
		assertTrue(list.isEmpty());
		
		list = getGiftService().getGiftsByPresenter(System.nanoTime());
		assertNotNull(list);
		assertTrue(list.isEmpty());
		
		getGiftService().assignPresenterToGift(entity1, userPresenter);
		
		list = getGiftService().getGiftsByPresenter(userPresenter.getId());
		assertNotNull(list);
		assertEquals(1, list.size());
		assertTrue(list.contains(entity1));
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#getGiftsByPresenter(java.lang.Long)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetGiftsByPresenterLongIllegalArgument() {
		getGiftService().getGiftsByPresenter((Long)null);
	}

	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#addEntity(ua.cn.yet.birthdayGifts.domain.Gift, java.lang.Long)}.
	 * @throws GeneralServiceException 
	 */
	@Test
	public void testAddEntity() throws GeneralServiceException {
		Gift gift = new Gift();
		gift.setName("ccc");
		
		gift = getGiftService().addEntity(gift, userVip.getId());
		
		Collection<Gift> list = getGiftService().getGiftsByOwner(userVip);
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertTrue(list.contains(entity1));
		assertTrue(list.contains(entity2));
		assertTrue(list.contains(gift));
		
		getGiftService().delEntity(gift);
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#addEntity(ua.cn.yet.birthdayGifts.domain.Gift, java.lang.Long)}.
	 * @throws GeneralServiceException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddEntityIllegalGiftArgument() throws GeneralServiceException {
		getGiftService().addEntity(null, userVip.getId());
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#addEntity(ua.cn.yet.birthdayGifts.domain.Gift, java.lang.Long)}.
	 * @throws GeneralServiceException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testAddEntityIllegalUserIdArgument() throws GeneralServiceException {
		Gift gift = new Gift();
		gift.setName("ccc");
		getGiftService().addEntity(gift, null);
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#addEntity(ua.cn.yet.birthdayGifts.domain.Gift, java.lang.Long)}.
	 * @throws GeneralServiceException 
	 */
	@Test(expected=GeneralServiceException.class)
	public void testAddEntityNotSavedUser() throws GeneralServiceException {
		Gift gift = new Gift();
		gift.setName("ccc");
		getGiftService().addEntity(gift, System.nanoTime());
	}

	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#getAvailableGiftsByOwner(java.lang.Long)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test
	public void testGetAvailableGiftsByOwner() throws GeneralServiceException, IllegalArgumentException {
		Collection<Gift> list = getGiftService().getAvailableGiftsByOwner(userVip.getId());
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertTrue(list.contains(entity1));
		assertTrue(list.contains(entity2));
		
		getGiftService().assignPresenterToGift(entity1, userPresenter);
		
		list = getGiftService().getAvailableGiftsByOwner(userVip.getId());
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertFalse(list.contains(entity1));
		assertTrue(list.contains(entity2));
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#getAvailableGiftsByOwner(java.lang.Long)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetAvailableGiftsByOwnerIllegalArgument() {
		getGiftService().getAvailableGiftsByOwner(null);
	}

	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#removeSelectionFromGift(java.lang.Long)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test
	public void testRemoveSelectionFromGift() throws GeneralServiceException, IllegalArgumentException {
		getGiftService().assignPresenterToGift(entity1, userPresenter);
		
		Collection<Gift> list = getGiftService().getAvailableGiftsByOwner(userVip.getId());
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertFalse(list.contains(entity1));
		assertTrue(list.contains(entity2));
		
		getGiftService().removeSelectionFromGift(entity1.getId());
		
		list = getGiftService().getAvailableGiftsByOwner(userVip.getId());
		assertNotNull(list);
		assertFalse(list.isEmpty());
		assertTrue(list.contains(entity1));
		assertTrue(list.contains(entity2));
	}
	
	/**
	 * Test method for {@link ua.cn.yet.birthdayGifts.services.impl.GiftServiceImpl#removeSelectionFromGift(java.lang.Long)}.
	 * @throws IllegalArgumentException 
	 * @throws GeneralServiceException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testRemoveSelectionFromGiftIllegalArgument() throws GeneralServiceException, IllegalArgumentException {
		getGiftService().removeSelectionFromGift(null);
	}

}
