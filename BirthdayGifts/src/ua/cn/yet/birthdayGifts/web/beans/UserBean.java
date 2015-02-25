package ua.cn.yet.birthdayGifts.web.beans;

import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.domain.UserRole;

/**
 * Session bean that holds information about current user and provides some
 * actions for workings with users for admin
 * 
 * @author Yuriy
 */
public class UserBean extends BeanSuper {

	/** */
	private static final long serialVersionUID = 1L;

	/** User login */
	private String login = null;

	/** User role */
	private UserRole userRole = null;

	/** User id */
	private Long id = null;

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @return the userRole
	 */
	public UserRole getUserRole() {
		return userRole;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * @return True if user is in {@link UserRole#VIP} role
	 */
	public boolean isVip() {
		if (userRole != null) {
			return (userRole.equals(UserRole.VIP));
		} else {
			return false;
		}
	}

	/**
	 * Setting information from entity to the fields
	 * 
	 * @param user
	 *            User where to get information
	 */
	public void setUserInfo(User user) {
		if (user != null) {
			login = user.getLogin();
			userRole = user.getRole();
			id = user.getId();
		} else {
			login = null;
			userRole = null;
			id = null;
		}
	}

}
