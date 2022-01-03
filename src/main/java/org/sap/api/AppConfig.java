package org.sap.api;

import org.hibersap.configuration.AnnotationConfiguration;
import org.hibersap.session.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/*
 * @Auth prasad
 * @Date 09/12/2021
 * @Desc HiberSap Configuration
 */
@Configuration
public class AppConfig {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AppConfig.class);
	
	@Bean
	public SessionManager createSessionManager() {
		 AnnotationConfiguration config=new AnnotationConfiguration("A12");
		 return config.buildSessionManager();
	}
}
