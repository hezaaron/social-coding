package com.iplusplus.gateway;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class OktaOAuth2SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
    protected void configure(HttpSecurity httpSec) throws Exception {
		httpSec
            .authorizeRequests()
            .anyRequest().authenticated()
            .and()
            .oauth2Login()
            .and().oauth2ResourceServer().jwt();
    }
}