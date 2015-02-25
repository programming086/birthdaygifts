package ua.cn.yet.birthdayGifts.security.aspects;

import javax.faces.context.FacesContext;

import ua.cn.yet.birthdayGifts.domain.Gift;
import ua.cn.yet.birthdayGifts.services.IGiftService;
import ua.cn.yet.birthdayGifts.web.beans.UserBean;
import ua.cn.yet.birthdayGifts.domain.UserRole;

/**
 * This aspect connects to all methods that edit gifts and checks current user
 * to be the owner of the gift. Aspect disallows operations for non-owners that
 * are in {@link UserRole#VIP} role.
 * 
 * @author Yuriy Tkach
 */
public aspect CheckOwner {
	
	/**
	 * Connecting to save and delete methods
	 * @param gift Gift argument that is passed into the methods
	 */
	pointcut editGift(Gift gift):
		args(gift) && (
				(execution(public * IGiftService.save(Gift)) &&
						withincode(public * IGiftService.assignPresenterToGift(..)))
						||
		execution(public * IGiftService.delEntity(Gift)) ) ;
	
	/**
	 * Checking gift owner to be the current user. If not, then throwing security exception.
	 */
	before(Gift gift): editGift(gift) {
		try {
			Long id = getCurrentUser();
			if (!gift.getOwner().getId().equals(id)) {
				throw new SecurityException("Only owner of the gift can delete it.");
			}
		} catch (IllegalStateException e) {
			// Calling method from test, because no web context was created. Just exit from aspect. 
		}
	}
	
	/**
	 * Getting current user id from web session
	 * @return Id of the current user
	 * @throws IllegalStateException If no faces context is created
	 */
	protected Long getCurrentUser() throws IllegalStateException {
		if (FacesContext.getCurrentInstance() == null) {
			throw new IllegalStateException("No web context");
		} else {
			UserBean bean = (UserBean)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userBean");
			if (bean != null) {
				return bean.getId();
			} else {
				return null;
			}
		}
	}

}
