package de.zottig.clean.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceApplicationConfiguration
		extends
			ResourceServerConfigurerAdapter {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	/**
	 * Allow "/" to be accessed without any permissions and anything else is
	 * protected by a valid Oauth2 token.
	 */
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll()
				.antMatchers("/api/tasks/**").authenticated();
	}

}
