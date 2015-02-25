package ua.cn.yet.birthdayGifts.web.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;

import ua.cn.yet.birthdayGifts.config.Messages;
import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.domain.UserRole;
import ua.cn.yet.birthdayGifts.exceptions.GeneralServiceException;
import ua.cn.yet.birthdayGifts.services.IUserService;

/**
 * Bean for admin operations
 * 
 * @author Yuriy
 */
public class AdminBean extends BeanSuper {

	/** */
	private static final long serialVersionUID = 1L;

	/** Page for the data scroller */
	private int usersPage = 1;

	/** Collection of users */
	private List<User> users;

	/** New user entity */
	protected User newUser = new User();

	/** Edit user entity */
	protected User editUser = null;

	/** User service for accessing operations for users */
	private IUserService userService;

	/**
	 * @return the usersPage
	 */
	public int getUsersPage() {
		return usersPage;
	}

	/**
	 * @param usersPage
	 *            the usersPage to set
	 */
	public void setUsersPage(int usersPage) {
		this.usersPage = usersPage;
	}

	/**
	 * @return the users
	 */
	public Collection<User> getUsers() {
		return users;
	}

	/**
	 * @param users
	 *            the users to set
	 */
	public void setUsers(List<User> users) {
		this.users = users;
	}

	/**
	 * @return the newUser
	 */
	public User getNewUser() {
		return newUser;
	}

	/**
	 * @param newUser
	 *            the newUser to set
	 */
	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	/**
	 * @return the editUser
	 */
	public User getEditUser() {
		return editUser;
	}

	/**
	 * @param editUser
	 *            the editUser to set
	 */
	public void setEditUser(User editUser) {
		this.editUser = editUser;
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
	 * @return list of user roles
	 */
	public List<UserRole> getRoles() {
		List<UserRole> rez = new ArrayList<UserRole>();
		UserRole[] roles = UserRole.values();
		for (int i = 0; i < roles.length; i++)
			rez.add(roles[i]);
		return rez;
	}

	/**
	 * Method that will be called, after bean was initialized
	 */
	@PostConstruct
	public void postConstruct() {
		log.debug("Loading all users"); //$NON-NLS-1$
		doLoadUserList();
	}

	/**
	 * Loading user list from service
	 */
	public String doLoadUserList() {
		users = userService.getAllEntites();
		return null;
	}

	/**
	 * Action that is performed, when adding new user.
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public String doAddUser() {
		try {
			userService.save(newUser);
		} catch (GeneralServiceException e) {
			addGlobalErrorMessage(e.getLocalizedMessage(), e);
			return null;
		}
		addGlobalINFOMessage(Messages.getString("AdminBean.User") + newUser.getLogin() //$NON-NLS-1$
				+ Messages.getString("AdminBean.SuccessAdd"), null); //$NON-NLS-1$

		newUser = new User();
		doLoadUserList();
		return null;
	}

	/**
	 * Action that is performed, when updating new user.
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public String doUpdateUser() {
		try {
			userService.save(editUser);
		} catch (GeneralServiceException e) {
			addGlobalErrorMessage(e.getLocalizedMessage(), e);
			return null;
		}
		addGlobalINFOMessage(Messages.getString("AdminBean.User") + editUser.getLogin() //$NON-NLS-1$
				+ Messages.getString("AdminBean.SuccessUpdate"), null); //$NON-NLS-1$

		editUser = null;
		newUser = new User();
		doLoadUserList();
		return null;
	}

	/**
	 * Action that is performed, when deleting user.
	 * <p>
	 * Getting the user to delete from row it was clicked.
	 * </p>
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public String doDeleteUser() {
		Long userId = getParameterFromRequestAsLong("uid"); //$NON-NLS-1$

		if (userId != null) {

			try {

				userService.delEntity(userId);

				// After successful deletion, updating again the users
				// list
				doLoadUserList();

				addGlobalINFOMessage(Messages.getString("AdminBean.SuccessDelete"), null); //$NON-NLS-1$

			} catch (GeneralServiceException e) {
				addGlobalErrorMessage(e.getLocalizedMessage(), e);
			}
		} else {
			addGlobalErrorMessage(Messages.getString("AdminBean.ErrorGetUserFromTableForDelete"), //$NON-NLS-1$
					null);
		}
		return null;
	}

	/**
	 * Action that is performed, when edit user is clicked.
	 * <p>
	 * Getting the user to edit from row it was clicked.
	 * </p>
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public String doSetEditUser() {

		Long userId = getParameterFromRequestAsLong("uid"); //$NON-NLS-1$

		for (User userEdit : users) {
			if (userEdit.getId().equals(userId)) {
				editUser = userEdit;
				break;
			}
		}
		return null;
	}

	/**
	 * Action that is performed, when canceling edit user .
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public String doCancelEditUser() {
		editUser = null;
		return null;
	}

}
