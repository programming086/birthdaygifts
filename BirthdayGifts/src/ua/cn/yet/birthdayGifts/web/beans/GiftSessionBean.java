package ua.cn.yet.birthdayGifts.web.beans;

/**
 * This bean is used to transfer some params between requests when
 * processing beans. This is a session bean, so values of it are
 * updated in other bean's action and are saved in session
 * 
 * @author Yuriy
 */
public class GiftSessionBean extends BeanSuper {

	/** */
	private static final long serialVersionUID = 1L;
	
	/** Gift id */
	private Long giftId = null;

	/**
	 * @return the giftId
	 */
	public Long getGiftId() {
		return giftId;
	}

	/**
	 * @param giftId the giftId to set
	 */
	public void setGiftId(Long giftId) {
		this.giftId = giftId;
	}
}
