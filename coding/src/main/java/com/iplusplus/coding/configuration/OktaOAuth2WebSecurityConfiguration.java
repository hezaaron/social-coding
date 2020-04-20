package com.iplusplus.coding.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.okta.spring.boot.oauth.Okta;

@Configuration
public class OktaOAuth2WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSec) throws Exception {
		httpSec.authorizeRequests()
			   .anyRequest().authenticated()
			   .and()
			   .oauth2ResourceServer().jwt();

        // force a non-empty response body for 401's to make the response more browser friendly
        Okta.configureResourceServer401ResponseBody(httpSec);
	}
}
