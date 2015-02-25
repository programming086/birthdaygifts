package ua.cn.yet.birthdayGifts.exceptions;

/**
 * General exception for the whole project
 * 
 * @author Yuriy
 */
public class BirthdayGiftsException extends Exception {

	private static final long serialVersionUID = 1L;

	public BirthdayGiftsException() {
		super();
	}

	public BirthdayGiftsException(String message, Throwable cause) {
		super(message, cause);
	}

	public BirthdayGiftsException(String message) {
		super(message);
	}

	public BirthdayGiftsException(Throwable cause) {
		super(cause);
	}

}
