package ua.cn.yet.birthdayGifts.services;

import java.util.Collection;

import ua.cn.yet.birthdayGifts.domain.User;

/**
 * User service
 * 
 * @author Yuriy
 */
public interface IUserService extends IGenericService<User> {

	/**
	 * Getting user by its login and password
	 * 
	 * @param login
	 *            User login
	 * @param password
	 *            User password
	 * @return Found user or <code>null</code>
	 * @throws IllegalArgumentException
	 *             if params are <code>null</code>
	 */
	public User getUserByLoginAndPassword(String login, String password)
			throws IllegalArgumentException;
	
	/**
	 * Checking if user with specified login exists
	 * @param login Login to find user
	 * @return True if user with such login exists, false otherwise
	 */
	public boolean isUserExists(String login);
	
	
	/**
	 * Getting all vip users that have gifts
	 * sorted by their birthdays with the nearest one first.
	 * @return sorted collection of users 
	 */
	public Collection<User> getVIPUsers();
	

}
