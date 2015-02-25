package ua.cn.yet.birthdayGifts.web.tags;

import javax.faces.validator.ValidatorException;

import org.junit.Test;

public class TestValidateEmail {

	/**
	 * Testing validation of valid emails
	 */
	@Test
	public void testValidate() {
		ValidateEmail ve = new ValidateEmail();
		ve.validate(null, null, "aaa@aaa.com");
	}
	
	/**
	 * Testing validation of invalid email
	 */
	@Test(expected=ValidatorException.class)
	public void testValidateInvalid1() {
		ValidateEmail ve = new ValidateEmail();
		ve.validate(null, null, "");
	}
	
	/**
	 * Testing validation of invalid email
	 */
	@Test(expected=ValidatorException.class)
	public void testValidateInvalid2() {
		ValidateEmail ve = new ValidateEmail();
		ve.validate(null, null, "aaa");
	}
	
	/**
	 * Testing validation of invalid email
	 */
	@Test(expected=ValidatorException.class)
	public void testValidateInvalid3() {
		ValidateEmail ve = new ValidateEmail();
		ve.validate(null, null, "aaa@.com");
	}
	
	/**
	 * Testing validation of invalid email
	 */
	@Test(expected=ValidatorException.class)
	public void testValidateInvalid4() {
		ValidateEmail ve = new ValidateEmail();
		ve.validate(null, null, "a@a.a");
	}

}
