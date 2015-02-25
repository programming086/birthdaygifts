package ua.cn.yet.birthdayGifts.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Class that loads application's config files and manipulates different configs
 * 
 * @author Yuriy
 */
public final class ApplicationConfig {
	
	private static final String SPRING_DAO_CONFIG_XML = "spring_config.xml"; //$NON-NLS-1$
	
	/** Spring context */
	private static ApplicationContext springAppContext;
	
	ApplicationConfig() {}

	/**
	 * Loading Spring configs and creating context
	 * 
	 * @return Spring application context
	 */
	public static ApplicationContext getSpringApplicationContext() {
		if (springAppContext == null) {
			springAppContext = getSpringApplicationContext(SPRING_DAO_CONFIG_XML);
		}
		return springAppContext;
	}
	
	/**
	 * Loading Spring configs and creating context
	 * 
	 * @return Spring application context
	 */
	public static ApplicationContext getSpringApplicationContext(String contextPath) {
		springAppContext = new ClassPathXmlApplicationContext(contextPath);
		return springAppContext;
	}

}
