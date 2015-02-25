package ua.cn.yet.birthdayGifts.web.beans;

import java.util.Collection;
import java.util.Iterator;

import javax.annotation.PostConstruct;

import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.services.IUserService;

/**
 * Bean that loads current vip users
 * 
 * @author Yuriy
 */
public class VipLoaderBean extends BeanSuper {

	/** */
	private static final long serialVersionUID = 1L;
	
	/** User service to access users */
	private IUserService userService;
	
	/** User bean to access logged user info */
	private UserBean userBean;
	
	/** Collection of all vip users */
	private Collection<User> vipUsers;

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
	 * @return the vipUsers
	 */
	public Collection<User> getVipUsers() {
		return vipUsers;
	}
	
	/**
	 * Loading all vip users lists 
	 */
	@PostConstruct
	public void postConstruct() {
		loadAllVipUsersList();
	}
	
	/**
	 * Loading list of all vip users, removing
	 * currently logged user from that list
	 */
	private void loadAllVipUsersList() {
		vipUsers = userService.getVIPUsers();
		for (Iterator<User> iterator = vipUsers.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			if (user.getId().equals(userBean.getId())) {
				iterator.remove();
				break;
			}
		}
	}

}
