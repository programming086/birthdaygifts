package ua.cn.yet.birthdayGifts.web.beans;

import java.util.Calendar;
import java.util.Date;

import ua.cn.yet.birthdayGifts.config.Messages;
import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.domain.UserRole;
import ua.cn.yet.birthdayGifts.exceptions.GeneralServiceException;
import ua.cn.yet.birthdayGifts.services.IGiftService;
import ua.cn.yet.birthdayGifts.services.IUserService;

/**
 * Bean that holds actions that perform login, logout, and new user register.
 * 
 * @author Yuriy
 */
public class LoginBean extends BeanSuper {

	/** */
	private static final long serialVersionUID = 1L;

	/** Login field */
	private String login;

	/** Password field */
	private String password;

	/** Retyped password */
	private String rePassword;

	/** Email of the user */
	private String email;

	/** User bean */
	private UserBean userBean;
	
	/** Admin bean */
	private AdminBean adminBean;

	/** User service to use */
	private IUserService userService;
	
	/** Gift service to assign gift after logging in */
	private IGiftService giftService;
	
	/** Bean where selected gift id is stored */
	private GiftSessionBean giftSessionBean;
	
	/**
	 * @return the giftSessionBean
	 */
	public GiftSessionBean getGiftSessionBean() {
		return giftSessionBean;
	}

	/**
	 * @param giftSessionBean the giftSessionBean to set
	 */
	public void setGiftSessionBean(GiftSessionBean giftSessionBean) {
		this.giftSessionBean = giftSessionBean;
	}

	/**
	 * @return the giftService
	 */
	public IGiftService getGiftService() {
		return giftService;
	}

	/**
	 * @param giftService the giftService to set
	 */
	public void setGiftService(IGiftService giftService) {
		this.giftService = giftService;
	}

	/**
	 * @return the userService
	 */
	public IUserService getUserService() {
		return userService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
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
	 * @param rePassword
	 *            the rePassword to set
	 */
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the userBean
	 */
	public UserBean getUserBean() {
		return userBean;
	}

	/**
	 * @param userBean
	 *            the userBean to set
	 */
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
	
	/**
	 * @return the adminBean
	 */
	public AdminBean getAdminBean() {
		return adminBean;
	}

	/**
	 * @param adminBean the adminBean to set
	 */
	public void setAdminBean(AdminBean adminBean) {
		this.adminBean = adminBean;
	}

	/**
	 * @return date that is now
	 */
	public Date getNow() {
		return Calendar.getInstance().getTime();
	}
	
	/**
	 * Doing nothing
	 */
	public void setNow(Date date) {
	}

	/**
	 * Performing user login
	 * 
	 * @return action where to go next
	 */
	public String doLogin() {
		User user = userService.getUserByLoginAndPassword(login, password);
		if (user == null) {
			addGlobalErrorMessage(Messages.getString("LoginBean.ErrorIncorrectLoginPassword"), null); //$NON-NLS-1$
			return null;
		} else {

			userBean.setUserInfo(user);

			if (user.getRole().equals(UserRole.ADMIN)) {
				adminBean.doLoadUserList();
				return "admin"; //$NON-NLS-1$
			} else {
				return "regUser"; //$NON-NLS-1$
			}
		}
	}
	
	/**
	 * Logging user in and then assigning selected gift to it.
	 * Then moving to the page of gift owner
	 * @return <code>null</code> if error or user page
	 */
	public String doLoginAndSelectGift() {
		User user = userService.getUserByLoginAndPassword(login, password);
		if (user == null) {
			addGlobalErrorMessage(Messages.getString("LoginBean.ErrorIncorrectLoginPassword"), null); //$NON-NLS-1$
			return null;
		} else {
			return assignGiftToUser(user);
		}
	}
	
	/**
	 * Logging user in and then assigning selected gift to it.
	 * Then moving to the page of gift owner
	 * @return <code>null</code> if error or user page
	 */
	public String doRegisterAndSelectGift() {
		
		if (!password.equals(rePassword)) {
			addGlobalErrorMessage(Messages.getString("LoginBean.ErrorPasswordsMatch"), null); //$NON-NLS-1$
			return null;
		}
		
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setEmail(email);
		user.setRole(UserRole.PRESENTER);
		
		try {
			user = userService.save(user);
			return assignGiftToUser(user);
		} catch (GeneralServiceException e) {
			addGlobalErrorMessage(Messages.getString("LoginBean.ErrorRegister"), e); //$NON-NLS-1$
			return null;
		}
	}

	/**
	 * Assigning selected gift to user
	 * @param user User to assign gift to
	 * @return page id where to go next
	 */
	private String assignGiftToUser(User user) {
		userBean.setUserInfo(user);

		if (user.getRole().equals(UserRole.ADMIN)) {
			return "admin"; //$NON-NLS-1$
		} else {
			
			try {
				giftService.assignPresenterToGift(giftSessionBean.getGiftId(), user.getId());
				addGlobalINFOMessage(Messages.getString("LoginBean.SuccessGiftAssign"), null); //$NON-NLS-1$
			} catch (Exception e) {
				addGlobalErrorMessage(Messages.getString("LoginBean.ErrorGiftAssign"), e); //$NON-NLS-1$
			}
			
			return "regUser"; //$NON-NLS-1$
		}
	}

	/**
	 * Performing user logout
	 * 
	 * @return action where to go next
	 */
	public String doLogout() {
		userBean.setUserInfo(null);
		return "home"; //$NON-NLS-1$
	}
}
