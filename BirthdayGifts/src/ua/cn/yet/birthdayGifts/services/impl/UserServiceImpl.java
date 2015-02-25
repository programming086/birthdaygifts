package ua.cn.yet.birthdayGifts.services.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.cn.yet.birthdayGifts.config.Messages;
import ua.cn.yet.birthdayGifts.domain.Gift;
import ua.cn.yet.birthdayGifts.domain.QueryNames;
import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.domain.UserRole;
import ua.cn.yet.birthdayGifts.exceptions.GeneralServiceException;
import ua.cn.yet.birthdayGifts.services.IGiftService;
import ua.cn.yet.birthdayGifts.services.IUserService;

/**
 * Implementation of the User service
 * 
 * @author Yuriy
 */
public class UserServiceImpl extends GenericServiceImpl<User> implements
		IUserService {

	private static String DEF_ADMIN_LOGIN = "admin"; //$NON-NLS-1$
	private static String DEF_ADMIN_PASSWD = "adminushka"; //$NON-NLS-1$

	{
		// Static block that initializes default admin props
		InputStream io = UserServiceImpl.class.getClassLoader()
				.getResourceAsStream("app.conf.properties"); //$NON-NLS-1$
		if (io != null) {
			try {
				Properties props = new Properties();
				props.load(io);

				String login = props.getProperty("login"); //$NON-NLS-1$
				if (StringUtils.isNotBlank(login)) {
					DEF_ADMIN_LOGIN = login;
				}
				String passwd = props.getProperty("passwd"); //$NON-NLS-1$
				if (StringUtils.isNotBlank(passwd)) {
					DEF_ADMIN_PASSWD = passwd;
				}
			} catch (Exception e) {

			} finally {
				IOUtils.closeQuietly(io);
			}
		}
	}

	/** Gift service to use in methods */
	protected IGiftService giftService;

	/**
	 * @return the giftService
	 */
	public IGiftService getGiftService() {
		return giftService;
	}

	/**
	 * @param giftService
	 *            the giftService to set
	 */
	public void setGiftService(IGiftService giftService) {
		this.giftService = giftService;
	}

	/**
	 * Constructor
	 */
	public UserServiceImpl() {
		super(User.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeua.cn.yet.birthdayGifts.services.impl.GenericServiceImpl#
	 * beforeEntityAddUpdate(ua.cn.yet.birthdayGifts.domain.DomainObject)
	 */
	@Override
	protected void beforeEntityAddUpdate(User entity)
			throws GeneralServiceException {
	}

	/**
	 * Checking if user exists with such login and then calling super method.
	 * 
	 * @see ua.cn.yet.birthdayGifts.services.impl.GenericServiceImpl#save(ua.cn.yet.birthdayGifts.domain.DomainObject)
	 */
	@Override
	public User save(User entity) throws IllegalArgumentException, GeneralServiceException {
		if (entity == null) {
			throw new IllegalArgumentException(
					"Entity for saving cannot be null!");
		}
		
		checkIfUserExists(entity);
		return super.save(entity);
	}

	/**
	 * <p>
	 * Checking if user already exists with login that <code>entity</code> has.
	 * </p>
	 * <p>
	 * If user is found, then throwing exception only if entity's id is
	 * <code>null</code>, which means that we are adding new user, or if found
	 * users's id is not equal to the entity's id, which means that there is a
	 * different record with such login.
	 * </p>
	 * 
	 * @param entity
	 *            user to check
	 * @throws GeneralServiceException
	 *             If user exists
	 */
	private void checkIfUserExists(User entity) throws GeneralServiceException {
		// Checking if such user already exists
		User user = executeQuery(QueryNames.USER_GET_BY_LOGIN, true, true,
				entity.getLogin());
		if (user != null) {
			if ((entity.getId() == null)
					|| (!user.getId().equals(entity.getId())))
				throw new GeneralServiceException(
						String
								.format(
										Messages
												.getString("UserServiceImpl.ErrorUserWithLoginExists"), entity.getLogin())); //$NON-NLS-1$
		}
	}

	/**
	 * <p>
	 * Changing presenter of all gifts that were selected by this user to
	 * <code>null</code>.
	 * </p>
	 * <p>
	 * Deleting all gifts that this user owns.
	 * </p>
	 * 
	 * @see ua.cn.yet.birthdayGifts.services.impl.GenericServiceImpl#beforeEntityDelete(ua.cn.yet.birthdayGifts.domain.DomainObject)
	 */
	@Override
	protected void beforeEntityDelete(User entity)
			throws GeneralServiceException {

		Collection<Gift> gifts = giftService.getGiftsByPresenter(entity);
		if (CollectionUtils.isNotEmpty(gifts)) {
			for (Gift gift : gifts) {
				gift.changePresenter(null);
			}
		}

		gifts = giftService.getGiftsByOwner(entity);
		if (CollectionUtils.isNotEmpty(gifts)) {
			Collection<Long> ids = new ArrayList<Long>();
			for (Gift gift : gifts) {
				ids.add(gift.getId());
			}
			giftService.delAllEntities(ids);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ua.cn.yet.birthdayGifts.services.IUserService#getUserByLoginAndPassword
	 * (java.lang.String, java.lang.String)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public User getUserByLoginAndPassword(String login, String password)
			throws IllegalArgumentException {

		if (StringUtils.isBlank(login)) {
			throw new IllegalArgumentException(Messages
					.getString("UserDaoJpa.ErrorLoginNull")); //$NON-NLS-1$
		}
		if (StringUtils.isBlank(password)) {
			throw new IllegalArgumentException(Messages
					.getString("UserDaoJpa.ErrorPasswordNull")); //$NON-NLS-1$
		}

		if (DEF_ADMIN_LOGIN.equals(login) && DEF_ADMIN_PASSWD.equals(password)) {
			User admin = new User();
			admin.setId(Long.valueOf(-1));
			admin.setLogin(login);
			admin.setRole(UserRole.ADMIN);
			return admin;
		}

		return executeQuery(QueryNames.USER_GET_BY_LOGIN_PASSWD, true, true,
				login, password);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ua.cn.yet.birthdayGifts.services.IUserService#getVIPUsers()
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Collection<User> getVIPUsers() {
		Collection<User> userList = executeQuery(
				QueryNames.USER_GET_BY_ROLE_SORTED_BIRTHDAY, true, false,
				UserRole.VIP);
		Calendar today = Calendar.getInstance();
		int todayDay = today.get(Calendar.DAY_OF_MONTH);
		int todayMonth = today.get(Calendar.MONTH);

		Collection<User> rez = new ArrayList<User>();
		for (Iterator<User> iterator = userList.iterator(); iterator.hasNext();) {
			User user = iterator.next();
			int userDay = user.getBirthDate().get(Calendar.DAY_OF_MONTH);
			int userMonth = user.getBirthDate().get(Calendar.MONTH);

			if ((todayMonth >= userMonth) && (todayDay > userDay)) {
				iterator.remove();
				rez.add(user);
			} else {
				break;
			}
		}
		userList.addAll(rez);

		return userList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ua.cn.yet.birthdayGifts.services.IUserService#isUserExists(java.lang.
	 * String)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isUserExists(String login) {
		if (StringUtils.isNotBlank(login)) {
			return (executeQuery(QueryNames.USER_GET_BY_LOGIN, true, true,
					login) != null);
		} else {
			return false;
		}
	}
}
