package ua.cn.yet.birthdayGifts.domain;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class TestGift {

	/**
	 * Testing that changing of presenter for gift works with clearing or
	 * setting required fields
	 */
	@Test
	public void testChangePresenter() {
		User user1 = new User();
		user1.setId(Long.valueOf(1));
		User user2 = new User();
		user2.setId(Long.valueOf(2));

		Gift gift = new Gift();
		gift.setDateTimeSelected(Calendar.getInstance());
		gift.setPresenter(user1);

		gift.changePresenter(null);
		assertNull(gift.getDateTimeSelected());
		assertNull(gift.getPresenter());

		gift.changePresenter(user2);
		assertNotNull(gift.getDateTimeSelected());
		assertNotNull(gift.getPresenter());
		assertEquals(user2, gift.getPresenter());
	}

}
