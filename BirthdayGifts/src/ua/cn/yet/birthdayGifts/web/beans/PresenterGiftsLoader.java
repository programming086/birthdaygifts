package ua.cn.yet.birthdayGifts.web.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;

import ua.cn.yet.birthdayGifts.config.Messages;
import ua.cn.yet.birthdayGifts.domain.Gift;
import ua.cn.yet.birthdayGifts.domain.User;
import ua.cn.yet.birthdayGifts.services.IGiftService;

/**
 * Bean that loads gifts selected by presenter, that is currently logged in
 * 
 * @author Yuriy
 */
public class PresenterGiftsLoader extends BeanSuper {

	/** */
	private static final long serialVersionUID = 1L;
	
	/** Gift service for loading gifts */
	private IGiftService giftService;
	
	/** User bean to get current user information */
	private UserBean userBean;
	
	/**
	 * Map of selected gifts, where key is the owner, and
	 * value is the collection of the gifts of that owner,
	 * that current presenter selected
	 */
	private Map<User, Collection<Gift>> selectedGifts;

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
	 * <p>
	 * Returning collection of entries from the map of selected gifts
	 * </p>
	 * <p>
	 * Here is one of the most strange things that I've seen in java: if we simply
	 * return <code>selectedGifts.entrySet()</code>, then it won't work, throwing exception
	 * that property <code>key</code> not found on <code>java.util.TreeMap$EntrySet</code>.
	 * That is when we iterate through that collection of entries.
	 * But, there <b>is</b> such property! And the interesting <b>solution</b> that I found:
	 * I just create an array list of entries and do <code>addAll</code> of all entries
	 * from the set. It works.
	 * </p>
	 * <p>
	 * I didn't investigate this problem further, but as far as I think, method
	 * <code>entrySet()</code> doesn't return simple set. Yes, it is backed by the map, 
	 * so changes in that set are reflected in the map. So, maybe that is why, when we
	 * iterate through that set, it returns some strange objects as entries, where 
	 * properties cannot be discovered by EL engine. Well, some day, I'll dig into the
	 * code, but now I want to sleep :)
	 * </p>
	 * @return the selectedGifts
	 */
	public Collection<Entry<User, Collection<Gift>>> getSelectedGifts() {
		Collection<Entry<User, Collection<Gift>>> rez = new ArrayList<Entry<User,Collection<Gift>>>();
		rez.addAll(selectedGifts.entrySet());
		return rez;
	}
	
	/**
	 * Loading selected gifts and creating map from it
	 */
	@PostConstruct
	public void postConstruct(){
		if (userBean.getId() != null) {
			Collection<Gift> allSelectedGifts = giftService.getGiftsByPresenter(userBean.getId());
			
			selectedGifts = new TreeMap<User, Collection<Gift>>();
			
			if (CollectionUtils.isNotEmpty(allSelectedGifts)) {
				for (Gift gift : allSelectedGifts) {
					
					Collection<Gift> selectedGiftsOfOwner = selectedGifts.get(gift.getOwner());
					if (selectedGiftsOfOwner == null) {
						selectedGiftsOfOwner = new ArrayList<Gift>();
						selectedGifts.put(gift.getOwner(), selectedGiftsOfOwner);
					}
					selectedGiftsOfOwner.add(gift);
				}
			}
		}
	}
	
	/**
	 * Discarding gift by removing selected info from it.
	 * 
	 * @return <code>null</code> to stay on current page
	 */
	public String doDiscardGift() {
		Long giftId = getParameterFromRequestAsLong("giftId"); //$NON-NLS-1$
		if (giftId != null) {
			try {
				giftService.removeSelectionFromGift(giftId);
				addGlobalINFOMessage(Messages.getString("PresenterGiftsLoader.SuccessGiftDiscard"), null); //$NON-NLS-1$
				//reloading list of selected gifts
				postConstruct();
			} catch (Exception e) {
				addGlobalErrorMessage(Messages.getString("PresenterGiftsLoader.ErrorGiftDiscard"), e); //$NON-NLS-1$
			}		
		} else {
			addGlobalErrorMessage(Messages.getString("PresenterGiftsLoader.ErrorGiftFindInRequest"), null); //$NON-NLS-1$
		}
		return null;
	}

}
