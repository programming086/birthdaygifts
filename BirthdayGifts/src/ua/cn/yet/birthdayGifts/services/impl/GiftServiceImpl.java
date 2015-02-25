package ua.cn.yet.birthdayGifts.services.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ua.cn.yet.birthdayGifts.config.Messages;
import ua.cn.yet.birthdayGifts.domain.Gift;
import ua.cn.yet.birthdayGifts.domain.QueryNames;
import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.exceptions.GeneralServiceException;
import ua.cn.yet.birthdayGifts.services.IGiftService;
import ua.cn.yet.birthdayGifts.services.IUserService;

/**
 * Implementation of the gift service
 * 
 * @author Yuriy
 */
public class GiftServiceImpl extends GenericServiceImpl<Gift>
		implements IGiftService {
	
	/** User service to use in methods */
	protected IUserService userService;
	
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
	 * Constructor
	 */
	public GiftServiceImpl() {
		super(Gift.class);
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.impl.GenericServiceImpl#beforeEntityAddUpdate(ua.cn.yet.birthdayGifts.domain.DomainObject)
	 */
	@Override
	protected void beforeEntityAddUpdate(Gift entity)
			throws GeneralServiceException {
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.impl.GenericServiceImpl#beforeEntityDelete(ua.cn.yet.birthdayGifts.domain.DomainObject)
	 */
	@Override
	protected void beforeEntityDelete(Gift entity)
			throws GeneralServiceException {
	}
	
	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.impl.GenericServiceImpl#save(ua.cn.yet.birthdayGifts.domain.DomainObject)
	 */
	@Override
	public Gift save(Gift entity) throws IllegalArgumentException,
			GeneralServiceException, SecurityException {
		return super.save(entity);
	}
	
	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.impl.GenericServiceImpl#delEntity(ua.cn.yet.birthdayGifts.domain.DomainObject)
	 */
	@Override
	public void delEntity(Gift entity) throws IllegalArgumentException,
			GeneralServiceException, SecurityException {
		super.delEntity(entity);
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.IGiftService#assignPresenterToGift(ua.cn.yet.birthdayGifts.domain.Gift, ua.cn.yet.birthdayGifts.domain.User)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void assignPresenterToGift(Gift gift, User user)
			throws GeneralServiceException, IllegalArgumentException {
		if ((gift == null) || (user == null)) {
			throw new IllegalArgumentException(Messages.getString("GiftServiceImpl.ErrorArgumentsNull")); //$NON-NLS-1$
		}
		
		if (!gift.getOwner().equals(user)) {
		
			gift.changePresenter(user);
			save(gift);
			
			//TODO: Send email to owner of the gift :)
		} else {
			throw new GeneralServiceException(Messages.getString("GiftServiceImpl.ErrorCannotSelectOwnGift")); //$NON-NLS-1$
		}
	}
	
	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.IGiftService#assignPresenterToGift(java.lang.Long, java.lang.Long)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void assignPresenterToGift(Long giftId, Long userId)
		throws GeneralServiceException, IllegalArgumentException {
		if ((giftId == null) || (userId == null)) {
			throw new IllegalArgumentException(Messages.getString("GiftServiceImpl.ErrorArgumentsNull")); //$NON-NLS-1$
		}
		
		Gift gift = getEntityById(giftId);
		if (gift != null) {
			User user = userService.getEntityById(userId);
			if (user != null) {
				assignPresenterToGift(gift, user);
			} else {
				throw new GeneralServiceException(Messages.getString("GiftServiceImpl.ErrorUserNotFoundById")+ userId +Messages.getString("GiftServiceImpl.ErrorGiftWasNotAssigned")); //$NON-NLS-1$ //$NON-NLS-2$
			}
		} else {
			throw new GeneralServiceException(Messages.getString("GiftServiceImpl.ErrorGiftNotFoundById")+ giftId +Messages.getString("GiftServiceImpl.ErrorGiftWasNotAssigned")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.IGiftService#getGiftsByOwner(ua.cn.yet.birthdayGifts.domain.User)
	 */
	public Collection<Gift> getGiftsByOwner(User user)
			throws IllegalArgumentException {
		if (user == null) {
			throw new IllegalArgumentException(Messages.getString("GiftDaoJpa.ErrorUserObjectNull")); //$NON-NLS-1$
		}
		return getGiftsByOwner(user.getId());
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.IGiftService#getGiftsByPresenter(ua.cn.yet.birthdayGifts.domain.User)
	 */
	public Collection<Gift> getGiftsByPresenter(User user)
			throws IllegalArgumentException {
		if (user == null) {
			throw new IllegalArgumentException(Messages.getString("GiftDaoJpa.ErrorUserObjectNull")); //$NON-NLS-1$
		}
		return getGiftsByPresenter(user.getId());
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.IGiftService#getGiftsByOwner(java.lang.Long)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Collection<Gift> getGiftsByOwner(Long userId)
			throws IllegalArgumentException {
		if (userId == null) {
			throw new IllegalArgumentException(Messages.getString("GiftDaoJpa.ErrorUserIdNull")); //$NON-NLS-1$
		}
		return executeQuery(QueryNames.GIFT_GET_BY_OWNER, true, false, userId);
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.IGiftService#getGiftsByPresenter(java.lang.Long)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Collection<Gift> getGiftsByPresenter(Long userId)
			throws IllegalArgumentException {
		if (userId == null) {
			throw new IllegalArgumentException(Messages.getString("GiftDaoJpa.ErrorUserIdNull")); //$NON-NLS-1$
		}
		return executeQuery(QueryNames.GIFT_GET_BY_PRESENTER, true, false, userId);
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.IGiftService#addEntity(ua.cn.yet.birthdayGifts.domain.Gift, java.lang.Long)
	 */
	public Gift addEntity(Gift gift, Long ownerId)
			throws IllegalArgumentException, GeneralServiceException {
		if (gift == null) {
			throw new IllegalArgumentException("Entity for adding cannot be null.s");
		}
		
		User user = userService.getEntityById(ownerId);
		if (user != null) {
			gift.setOwner(user);
			return save(gift);
		} else {
			throw new GeneralServiceException(Messages.getString("GiftServiceImpl.ErrorUserNotFoundById") + ownerId); //$NON-NLS-1$
		}
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.IGiftService#getAvailableGiftsByOwner(java.lang.Long)
	 */
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Collection<Gift> getAvailableGiftsByOwner(Long userId)
			throws IllegalArgumentException {
		if (userId == null) {
			throw new IllegalArgumentException(Messages.getString("GiftDaoJpa.ErrorUserIdNull")); //$NON-NLS-1$
		}
		return executeQuery(QueryNames.GIFT_GET_AVAIL_BY_OWNER, true, false, userId);
	}

	/* (non-Javadoc)
	 * @see ua.cn.yet.birthdayGifts.services.IGiftService#removeSelectionFromGift(java.lang.Long)
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void removeSelectionFromGift(Long giftId)
			throws GeneralServiceException, IllegalArgumentException {
		if (giftId == null) {
			throw new IllegalArgumentException(Messages.getString("GiftServiceImpl.ErrorArgumentsNull")); //$NON-NLS-1$
		}
		
		Gift gift = getEntityById(giftId);
		if (gift != null) {
			gift.changePresenter(null);
			save(gift);
		} else {
			throw new GeneralServiceException(Messages.getString("GiftServiceImpl.ErrorGiftNotFoundById")+ giftId +Messages.getString("GiftServiceImpl.ErrorGiftWasNotDiscarded")); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

}
