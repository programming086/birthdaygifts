package ua.cn.yet.birthdayGifts.web.beans;

import java.util.Collection;

import javax.annotation.PostConstruct;

import ua.cn.yet.birthdayGifts.config.Messages;
import ua.cn.yet.birthdayGifts.domain.Gift;
import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.services.IGiftService;
import ua.cn.yet.birthdayGifts.services.IUserService;

/**
 * Bean that contains methods that supplement operation for presenter user
 * 
 * @author Yuriy
 */
public class PresenterBean extends BeanSuper {

	/** */
	private static final long serialVersionUID = 1L;
	
	/** User service to access users */
	private IUserService userService;
	
	/** Gift service to access gifts */
	private IGiftService giftService;
	
	/** User bean to access logged user info */
	private UserBean userBean;
	
	/** Session bean to hold gift id between requests */
	private GiftSessionBean giftSessionBean;
	
	/**
	 * Vip user that is viewed right now
	 */
	private User vipUser;
	
	/**
	 * Gifts of the vip user that are available
	 */
	private Collection<Gift> vipGifts;
	
	/**
	 * @return the vipUser
	 */
	public User getVipUser() {
		return vipUser;
	}

	/**
	 * @return the vipGifts
	 */
	public Collection<Gift> getVipGifts() {
		return vipGifts;
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
	 * Loading info about selected users and gifts
	 */
	@PostConstruct
	public void postConstruct() {
		loadSelectedVipUserAndItsGifts();
	}

	/**
	 * Checking requests params for selected vipUser id and
	 * loading that user and its available gifts
	 */
	private void loadSelectedVipUserAndItsGifts() {
		Long vipId = getParameterFromRequestAsLong("vipId"); //$NON-NLS-1$
		if (vipId != null) {
			vipUser = userService.getEntityById(vipId);
			vipGifts = giftService.getAvailableGiftsByOwner(vipId);
		}
	}

	/**
	 * If user is logged in, then assigning gift to it and
	 * returning same page. If user is not logged in, then
	 * returning result to go for register.
	 * 
	 * @return where to go next
	 */
	public String doPresentGift() {
		
		Long giftId = getParameterFromRequestAsLong("giftId"); //$NON-NLS-1$
		if (giftId != null) {
			
			if (userBean.getId() != null) {
				
				try {
					giftService.assignPresenterToGift(giftId, userBean.getId());
					addGlobalINFOMessage(Messages.getString("PresenterBean.SuccessGiftAssign"), null); //$NON-NLS-1$
					
					loadSelectedVipUserAndItsGifts();
					
				} catch (Exception e) {
					addGlobalErrorMessage(Messages.getString("PresenterBean.ErrorGiftAssign"), e); //$NON-NLS-1$
				}
				
				return null;
			} else {
				giftSessionBean.setGiftId(giftId);
				return "singInToPresent"; //$NON-NLS-1$
			}
		} else {
			addGlobalErrorMessage(Messages.getString("PresenterBean.ErrorGiftFindInRequest"), null); //$NON-NLS-1$
			return null;
		}
	}
}
