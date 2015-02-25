package ua.cn.yet.birthdayGifts.exceptions;

import ua.cn.yet.birthdayGifts.services.IGenericService;


public class GeneralServiceException extends BirthdayGiftsException {

	private static final long serialVersionUID = 1L;

	/** Service that caused exception */
	protected IGenericService<?> service;

	public GeneralServiceException() {
		super();
	}

	/**
	 * @param message
	 *            Message with exception
	 * @param cause
	 *            Cause of exception
	 */
	public GeneralServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 *            Message with exception
	 */
	public GeneralServiceException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 *            Cause of exception
	 */
	public GeneralServiceException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param service
	 *            {@link IGenericService} object
	 */
	public GeneralServiceException(IGenericService<?> service) {
		super();
		this.service = service;
	}

	/**
	 * @param message
	 *            Message with exception
	 * @param cause
	 *            Cause of exception
	 * @param service
	 *            {@link IGenericService} object
	 */
	public GeneralServiceException(String message, Throwable cause,
			IGenericService<?> service) {
		super(message, cause);
		this.service = service;
	}

	/**
	 * @param message
	 *            Message with exception
	 * @param service
	 *            {@link IGenericService} object
	 */
	public GeneralServiceException(String message, IGenericService<?> service) {
		super(message);
		this.service = service;
	}

	/**
	 * @param cause
	 *            Cause of exception
	 * @param service
	 *            {@link IGenericService} object
	 */
	public GeneralServiceException(Throwable cause,
			IGenericService<?> service) {
		super(cause);
		this.service = service;
	}

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see java.lang.Throwable#getLocalizedMessage()
//	 */
//	@Override
//	public String getLocalizedMessage() {
//		if (service != null) {
//			
//			StringBuilder mess = new StringBuilder(" occured in "); //$NON-NLS-1$
//			
//			mess.append(service.getClass().getSimpleName())
//				.append(": ") //$NON-NLS-1$
//				.append(super.getLocalizedMessage());
//			
//			return mess.toString();
//			
//		}else {
//			return super.getLocalizedMessage();
//		}
//	}

	/**
	 * @return the service
	 */
	public IGenericService<?> getService() {
		return service;
	}

}
