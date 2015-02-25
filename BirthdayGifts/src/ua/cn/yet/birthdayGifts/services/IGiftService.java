package ua.cn.yet.birthdayGifts.services;

import java.util.Collection;

import ua.cn.yet.birthdayGifts.domain.Gift;
import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.exceptions.GeneralServiceException;

/**
 * Gift service
 * 
 * @author Yuriy
 */
public interface IGiftService extends IGenericService<Gift> {

	/**
	 * Getting collection of gifts by owner user, the one who requested them
	 * 
	 * @param user
	 *            User that is owner of gifts
	 * @return Collection of gifts
	 * @throws IllegalArgumentException
	 *             If user is <code>null</code>
	 */
	public Collection<Gift> getGiftsByOwner(User user)
			throws IllegalArgumentException;

	/**
	 * Getting collection of gifts by owner user, the one who requested them
	 * 
	 * @param userId
	 *            User id that is owner of gifts
	 * @return Collection of gifts
	 * @throws IllegalArgumentException
	 *             If user is <code>null</code>
	 */
	public Collection<Gift> getGiftsByOwner(Long userId)
			throws IllegalArgumentException;

	/**
	 * Getting collection of available (that are not selected) gifts by owner
	 * user, the one who requested them
	 * 
	 * @param userId
	 *            User id that is owner of gifts
	 * @return Collection of gifts
	 * @throws IllegalArgumentException
	 *             If user is <code>null</code>
	 */
	public Collection<Gift> getAvailableGiftsByOwner(Long userId)
			throws IllegalArgumentException;

	/**
	 * Getting collection of gifts by presenter user, the one who will present
	 * them
	 * 
	 * @param user
	 *            User that will present gifts
	 * @return Collection of gifts
	 * @throws IllegalArgumentException
	 *             If user is <code>null</code>
	 */
	public Collection<Gift> getGiftsByPresenter(User user)
			throws IllegalArgumentException;

	/**
	 * Getting collection of gifts by presenter user, the one who will present
	 * them
	 * 
	 * @param userId
	 *            User id that will present gifts
	 * @return Collection of gifts
	 * @throws IllegalArgumentException
	 *             If user is <code>null</code>
	 */
	public Collection<Gift> getGiftsByPresenter(Long userId)
			throws IllegalArgumentException;

	/**
	 * Assign user as presenter of the gift
	 * 
	 * @param gift
	 *            Gift that will be presented
	 * @param user
	 *            User that will present a gift
	 * @throws GeneralServiceException
	 *             In case of error
	 * @throws IllegalArgumentException
	 *             If user or gift is <code>null</code>
	 */
	public void assignPresenterToGift(Gift gift, User user)
			throws GeneralServiceException, IllegalArgumentException;

	/**
	 * Assign user as presenter of the gift
	 * 
	 * @param giftId
	 *            Id of gift that will be presented
	 * @param userId
	 *            Id of user that will present a gift
	 * @throws GeneralServiceException
	 *             In case of error
	 * @throws IllegalArgumentException
	 *             If user or gift is <code>null</code>
	 */
	public void assignPresenterToGift(Long giftId, Long userId)
			throws GeneralServiceException, IllegalArgumentException;

	/**
	 * Adding gift to database and assign owner to it.
	 * 
	 * @param gift
	 *            Gift to add
	 * @param ownerId
	 *            User id of the owner
	 * @return Persistent added entity
	 * @throws GeneralServiceException
	 *             If service fails to add entity
	 * @throws IllegalArgumentException
	 *             if any of the arguments is null
	 */
	public Gift addEntity(Gift gift, Long ownerId)
			throws IllegalArgumentException, GeneralServiceException;

	/**
	 * Removing selection from gift, making it unselected
	 * 
	 * @param giftId
	 *            If of the gift to remove selection from
	 * 
	 * @throws GeneralServiceException
	 *             If service fails
	 * @throws IllegalArgumentException
	 *             if argument is null
	 */
	public void removeSelectionFromGift(Long giftId)
			throws GeneralServiceException, IllegalArgumentException;

	/**
	 * Deleting persistent entity from database.
	 * 
	 * @param entity
	 *            Persistent entity to delete
	 * @throws GeneralServiceException
	 *             If service fails to delete entity
	 * @throws IllegalArgumentException
	 *             If entity is <code>null</code>
	 * @throws SecurityException
	 *             If current user is not the owner of the entity
	 */
	public void delEntity(Gift entity) throws IllegalArgumentException,
			GeneralServiceException, SecurityException;
	
	/**
	 * Save new object or update old one in the DB.
	 * 
	 * @param entity
	 *            object to store
	 * @return stored object
	 * @throws ServiceException
	 *             If domain or DB error occurs
	 * @throws IllegalArgumentException
	 *             If entity is <code>null</code>
	 * @throws SecurityException
	 *             If current user is not the owner of the entity
	 */
	public Gift save(Gift entity) throws IllegalArgumentException,
			GeneralServiceException, SecurityException;

}
