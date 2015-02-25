package ua.cn.yet.birthdayGifts.domain;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

/**
 * Object for representing gift
 * 
 * @author Yuriy
 */
@Entity
@Table(name = "GIFT")
@NamedQueries( {
		@NamedQuery(name = QueryNames.GIFT_GET_BY_OWNER, query = "SELECT x FROM Gift x WHERE x.owner.id = ?1"),
		@NamedQuery(name = QueryNames.GIFT_GET_BY_PRESENTER, query = "SELECT x FROM Gift x WHERE x.presenter.id = ?1"),
		@NamedQuery(name = QueryNames.GIFT_GET_AVAIL_BY_OWNER, query = "SELECT x FROM Gift x WHERE x.owner.id = ?1 AND x.presenter = NULL") })
public class Gift extends DomainObject {

	/** */
	private static final long serialVersionUID = 1L;

	/** Name of the gift */
	@NotNull(message = "Name of the gift cannot be null")
	@NotEmpty(message = "Name of the gift cannot be empty")
	private String name;

	/** Url of the gift image */
	private String imgUrl;

	/** Url of the gift page */
	private String url;

	/** Description of the gift */
	@Lob
	private String description;

	/** Date the gift was added */
	@Temporal(TemporalType.DATE)
	@NotNull(message = "Date the gift was added cannot be null")
	private Calendar dateAdd = Calendar.getInstance(getDefLocale());

	/** Date and time the gift was selected by presenter */
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateTimeSelected;

	/**
	 * User that requests a gift
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@NotNull
	private User owner;

	/**
	 * User that will present a gift
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	private User presenter;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the imgUrl
	 */
	public String getImgUrl() {
		return imgUrl;
	}

	/**
	 * @param imgUrl
	 *            the imgUrl to set
	 */
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		if (StringUtils.isNotBlank(url)) {
			try {
				new URL(url);
			} catch (MalformedURLException e) {
				url = "http://" + url; //$NON-NLS-1$
			}
		}

		this.url = url;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the dateAdd
	 */
	public Calendar getDateAdd() {
		return dateAdd;
	}

	/**
	 * @param dateAdd
	 *            the dateAdd to set
	 */
	protected void setDateAdd(Calendar dateAdd) {
		this.dateAdd = dateAdd;
	}

	/**
	 * @return the dateTimeSelected
	 */
	public Calendar getDateTimeSelected() {
		return dateTimeSelected;
	}

	/**
	 * @param dateTimeSelected
	 *            the dateTimeSelected to set
	 */
	protected void setDateTimeSelected(Calendar dateTimeSelected) {
		this.dateTimeSelected = dateTimeSelected;
	}

	/**
	 * @return the owner
	 */
	public User getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(User owner) {
		this.owner = owner;
	}

	/**
	 * @return the presenter
	 */
	public User getPresenter() {
		return presenter;
	}

	/**
	 * @param presenter
	 *            the presenter to set
	 */
	protected void setPresenter(User presenter) {
		this.presenter = presenter;
	}

	/**
	 * Methods sets {@link #dateTimeSelected} with {@link #presenter}
	 * 
	 * @param presenter
	 *            New presenter to set
	 */
	public void changePresenter(User presenter) {
		if (presenter == null) {
			this.dateTimeSelected = null;
		} else {
			this.dateTimeSelected = Calendar.getInstance();
		}
		this.presenter = presenter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ua.cn.yet.birthdayGifts.domain.DomainObject#toString()
	 */
	@Override
	public String toString() {
		ToStringBuilder sb = new ToStringBuilder(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
		sb.append("id", getId()); //$NON-NLS-1$
		sb.append("name", getName()); //$NON-NLS-1$
		sb.append("url", getUrl()); //$NON-NLS-1$
		sb.append("imageUrl", getImgUrl()); //$NON-NLS-1$
		sb.append("dateAdd", getDateAdd().getTime()); //$NON-NLS-1$
		sb.append("description", StringUtils.abbreviate(getDescription(), 10)); //$NON-NLS-1$
		sb.append("owner", owner); //$NON-NLS-1$
		sb.append("dateSelected", //$NON-NLS-1$
				(getDateTimeSelected() != null) ? getDateTimeSelected()
						.getTime() : "null"); //$NON-NLS-1$
		sb.append("presenter", getPresenter()); //$NON-NLS-1$

		return sb.toString();
	}
}
