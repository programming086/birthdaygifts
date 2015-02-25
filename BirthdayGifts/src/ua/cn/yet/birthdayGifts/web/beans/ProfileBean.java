package ua.cn.yet.birthdayGifts.web.beans;

import javax.annotation.PostConstruct;

import ua.cn.yet.birthdayGifts.config.Messages;
import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.exceptions.GeneralServiceException;
import ua.cn.yet.birthdayGifts.services.IUserService;


/**
 * Bean for performing operations with user "profile".
 * Well, actually these are the operation with user object itself,
 * but they are all performed from the profile page :)
 * 
 * @author Yuriy
 */
public class ProfileBean extends BeanSuper {

	/** */
	private static final long serialVersionUID = 1L;
	
	/** User that is currently logged in */
	private User user;
	
	/** User bean to use */
	private UserBean userBean;
	
	/** User service to use */
	private IUserService userService;
	
	/** User new password */
	private String password;
	
	/** User new password retyped */
	private String rePassword;

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the rePassword
	 */
	public String getRePassword() {
		return rePassword;
	}

	/**
	 * @param rePassword the rePassword to set
	 */
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the userBean
	 */
	public UserBean getUserBean() {
		return userBean;
	}

	/**
	 * @param userBean the userBean to set
	 */
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	/**
	 * @return the userService
	 */
	public IUserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Loading current user
	 */
	@PostConstruct
	public void postConstruct() {
		user = userService.getEntityById(userBean.getId());
	}
	
	/**
	 * Action to update user information
	 * @return null
	 */
	public String doUpdateUserInfo() {
		try {
			user = userService.save(user);
			addGlobalINFOMessage(Messages.getString("ProfileBean.SuccessProfileUpdate"), null); //$NON-NLS-1$
		} catch (GeneralServiceException e) {
			addGlobalErrorMessage(e.getLocalizedMessage(), e);
		}
		return null;
	}
	
	/**
	 * Changing user's password
	 * @return null if error, or "profile" to stay on current page
	 */
	public String doUpdateUserPassword() {
		if (password.compareTo(rePassword) != 0) {
			addGlobalErrorMessage(Messages.getString("ProfileBean.ErrorPasswordsMatch"), null); //$NON-NLS-1$
		} else {
			user.setPassword(password);
			try {
				user = userService.save(user);
				addGlobalINFOMessage(Messages.getString("ProfileBean.SuccessProfileUpdate"), null); //$NON-NLS-1$
			} catch (GeneralServiceException e) {
				addGlobalErrorMessage(e.getLocalizedMessage(), e);
			}
		}
		return null;
	}

}
