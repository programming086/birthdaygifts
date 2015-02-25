package ua.cn.yet.birthdayGifts.domain;

import static javax.persistence.EnumType.STRING;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.validator.NotEmpty;
import org.hibernate.validator.NotNull;

/**
 * Class that holds information about user
 * 
 * @author Yuriy
 */
@Entity
@Table(name = "USR")
@NamedQueries( {
		@NamedQuery(name = QueryNames.USER_GET_BY_LOGIN_PASSWD, query = "SELECT x FROM User x WHERE x.login = ?1 AND x.password = ?2"),
		@NamedQuery(name = QueryNames.USER_GET_BY_LOGIN, query = "SELECT x FROM User x WHERE x.login = ?1"),
		@NamedQuery(name = QueryNames.USER_GET_BY_ROLE_SORTED_BIRTHDAY, query = "SELECT x FROM User x WHERE x.role = ?1 ORDER BY x.birthDate") })
public class User extends DomainObject implements Comparable<User>{

	/** */
	private static final long serialVersionUID = 1L;

	/**
	 * Login of the user
	 */
	@NotNull(message = "Login of the user cannot be null")
	@NotEmpty(message = "Login of the user cannot be empty")
	@Column(unique=true)
	private String login;

	/**
	 * Password of the user
	 */
	@NotNull(message = "Password of the user cannot be null")
	@NotEmpty(message = "Password of the user cannot be empty")
	private String password;

	/**
	 * Role of the user
	 */
	@Enumerated(STRING)
	@NotNull(message = "User role cannot be null")
	private UserRole role = UserRole.VIP;

	/** Email of the user */
	private String email;

	/** Url of the avatar image */
	private String avatarUrl;

	/**
	 * The birthday of the {@link UserRole#VIP} user
	 */
	@Temporal(TemporalType.DATE)
	private Calendar birthDate = Calendar.getInstance(getDefLocale());

	/**
	 * Welcome message of the user
	 */
	@Lob
	private String welcomeMessage;
	
	
	/**
	 * Full name of the user
	 */
	private String fullName;
	
	/**
	 * Default empty constructor
	 */
	public User() {	}
	
	/**
	 * Constructor with fields
	 * @param login Login
	 * @param password Password
	 * @param role Role
	 */
	public User(String login, String password, UserRole role) {
		super();
		this.login = login;
		this.password = password;
		this.role = role;
	}



	/**
	 * @return the fullName
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * @param fullName the fullName to set
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * @return the avatarUrl
	 */
	public String getAvatarUrl() {
		return avatarUrl;
	}

	/**
	 * @param avatarUrl
	 *            the avatarUrl to set
	 */
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the role
	 */
	public UserRole getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(UserRole role) {
		this.role = role;
	}

	/**
	 * @return the birthDate
	 */
	public Calendar getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate
	 *            the birthDate to set
	 */
	public void setBirthDate(Calendar birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * @return the welcomeMessage
	 */
	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	/**
	 * @param welcomeMessage
	 *            the welcomeMessage to set
	 */
	public void setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
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
		sb.append("login", getLogin()); //$NON-NLS-1$
		sb.append("email", getEmail()); //$NON-NLS-1$
		sb.append("role", getRole()); //$NON-NLS-1$
		if (getRole().equals(UserRole.VIP)) {
			sb.append("birthdate", (getBirthDate() != null) ? getBirthDate() //$NON-NLS-1$
					.getTime() : ""); //$NON-NLS-1$
			sb.append("avatar", getAvatarUrl()); //$NON-NLS-1$
			sb.append("welcomeMessage", StringUtils.abbreviate( //$NON-NLS-1$
					getWelcomeMessage(), 10));
		}
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(User arg0) {
		int rez = -1;
		if ((arg0 != null) && (this.getClass().isAssignableFrom(arg0.getClass()))) {
			User other = (User) arg0;
			CompareToBuilder cmp = new CompareToBuilder();
			cmp.append(this.getFullName(), other.getFullName());
			rez = cmp.toComparison();
		}
		return rez;
	}
}
