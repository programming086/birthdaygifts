package ua.cn.yet.birthdayGifts.domain;


/**
 * Class that defines constants for query names
 * @author Yuriy
 */
public abstract class QueryNames {
	
	public static final String USER_GET_BY_LOGIN = "getByLogin"; //$NON-NLS-1$
	public static final String USER_GET_BY_LOGIN_PASSWD = "getByLoginAndPassword"; //$NON-NLS-1$
	public static final String USER_GET_BY_ROLE_SORTED_BIRTHDAY = "getByRoleSorted"; //$NON-NLS-1$
	
	public static final String GIFT_GET_BY_OWNER = "getGiftsByOwnerId"; //$NON-NLS-1$
	public static final String GIFT_GET_BY_PRESENTER = "getGiftsByPresenterId"; //$NON-NLS-1$
	public static final String GIFT_GET_AVAIL_BY_OWNER = "getAvailableGiftsByOwnerId"; //$NON-NLS-1$
	

}
