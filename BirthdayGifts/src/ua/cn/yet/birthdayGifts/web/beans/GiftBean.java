package ua.cn.yet.birthdayGifts.web.beans;

import java.util.Collection;

import javax.annotation.PostConstruct;

import ua.cn.yet.birthdayGifts.config.Messages;
import ua.cn.yet.birthdayGifts.domain.Gift;
import ua.cn.yet.birthdayGifts.exceptions.GeneralServiceException;
import ua.cn.yet.birthdayGifts.services.IGiftService;

/**
 * Bean for managing gifts
 * 
 * @author Yuriy
 */
public class GiftBean extends BeanSuper {

	/** */
	private static final long serialVersionUID = 1L;

	/** Service to use for gifts */
	private IGiftService giftService;

	/** User bean to access info about logged user */
	private UserBean userBean;

	/** Gift session bean to use for transfering some params */
	private GiftSessionBean giftSessionBean;

	/**
	 * Name of the that is deleted
	 */
	private String delGiftName;

	/**
	 * Id of the gift that is deleted
	 */
	private Long delGiftId;

	/** All gifts of the logged user */
	private Collection<Gift> gifts;

	/** Gift to edit or add */
	private Gift gift = new Gift();

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
	 * @return the giftSessionBean
	 */
	public GiftSessionBean getGiftSessionBean() {
		return giftSessionBean;
	}

	/**
	 * @param giftSessionBean
	 *            the giftSessionBean to set
	 */
	public void setGiftSessionBean(GiftSessionBean giftSessionBean) {
		this.giftSessionBean = giftSessionBean;
	}

	/**
	 * @return the delGiftName
	 */
	public String getDelGiftName() {
		return delGiftName;
	}

	/**
	 * @param delGiftName
	 *            the delGiftName to set
	 */
	public void setDelGiftName(String delGiftName) {
		this.delGiftName = delGiftName;
	}

	/**
	 * @return the delGiftId
	 */
	public Long getDelGiftId() {
		return delGiftId;
	}

	/**
	 * @param delGiftId
	 *            the delGiftId to set
	 */
	public void setDelGiftId(Long delGiftId) {
		this.delGiftId = delGiftId;
	}

	/**
	 * @return the gifts
	 */
	public Collection<Gift> getGifts() {
		return gifts;
	}

	/**
	 * @param gifts
	 *            the gifts to set
	 */
	public void setGifts(Collection<Gift> gifts) {
		this.gifts = gifts;
	}

	/**
	 * @return the gift
	 */
	public Gift getGift() {
		return gift;
	}

	/**
	 * @param gift
	 *            the gift to set
	 */
	public void setGift(Gift gift) {
		this.gift = gift;
	}

	/**
	 * Loading collection of all gifts and performing other initializations
	 */
	@PostConstruct
	public void postConstruct() {
		loadGiftList();

		Long giftId = giftSessionBean.getGiftId();

		if ((giftId != null) && (giftId != 0)) {
			gift = giftService.getEntityById(giftId);
		}
	}

	/**
	 * Loading all gifts that are owned by logged user
	 */
	private void loadGiftList() {
		gifts = giftService.getGiftsByOwner(userBean.getId());
	}

	/**
	 * Action that is performed, when deleting gift.
	 * <p>
	 * Getting the gift to delete from row it was clicked.
	 * </p>
	 * 
	 * @return <code>null</code> in all cases, so there will be no navigation to
	 *         other pages
	 */
	public String doDeleteGift() {
		Long giftId = getParameterFromRequestAsLong("uid"); //$NON-NLS-1$

		if (giftId != null) {

			try {

				giftService.delEntity(giftId);

				// After successful deletion, updating again the gift list
				loadGiftList();

				addGlobalINFOMessage(Messages.getString("GiftBean.SuccessGiftDelete"), null); //$NON-NLS-1$

			} catch (GeneralServiceException e) {
				addGlobalErrorMessage(e.getLocalizedMessage(), e);
			} catch (SecurityException se) {
				addGlobalErrorMessage("Security Exception: ", se);
			}
		} else {
			addGlobalErrorMessage(Messages.getString("GiftBean.ErrorGetGiftFromTableForDelete"), //$NON-NLS-1$
					null);
		}

		return null;
	}

	/**
	 * Adding gift to the owner
	 * 
	 * @return <code>null</code> in case of error, or "listGifts" if ok
	 */
	public String doAddGift() {
		try {
			giftService.addEntity(gift, userBean.getId());
			return "listGifts"; //$NON-NLS-1$
		} catch (GeneralServiceException e) {
			addGlobalErrorMessage(Messages.getString("GiftBean.ErrorAddGift"), e); //$NON-NLS-1$
			return null;
		}
	}

	/**
	 * Updating gift of the owner
	 * 
	 * @return <code>null</code> in case of error, or "listGifts" if ok
	 */
	public String doUpdateGift() {
		try {
			giftService.save(gift);
			return "listGifts"; //$NON-NLS-1$
		} catch (GeneralServiceException e) {
			addGlobalErrorMessage(Messages.getString("GiftBean.ErrorUpdateGift"), e); //$NON-NLS-1$
			return null;
		} catch (SecurityException se) {
			addGlobalErrorMessage("Security Exception: ", se);
			return null;
		}
	}
}
