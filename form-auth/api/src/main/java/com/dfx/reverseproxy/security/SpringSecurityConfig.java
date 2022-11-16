/**
 * Waffle (https://github.com/Waffle/waffle)
 *
 * Copyright (c) 2010-2018 Application Security, Inc.
 *
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors: Application Security, Inc.
 */
package com.dfx.reverseproxy.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.session.SessionManagementFilter;



/**
 * Demo Spring Boot Security configuration that configures the Negotiate filter to require authentication for all
 * requests.
 */
@Configuration
@EnableWebSecurity(debug = true)
@PropertySource("classpath:userprovision.properties")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Value("#{'${userprovision.users}'.split(',')}")
	private List<String> username;
	
	@Value("${userprovision.password}")
	private String password;
	
	@Bean
    public UserDetailsService userDetailsService() {

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        
        for(int i=0;i<username.size();i++) {
        manager.createUser(users.username(username.get(i).trim()).password(password.trim()).roles().build());
        }
        return manager;

    }

    // Secure the endpoins with HTTP Basic authentication
	@Override
    protected void configure(HttpSecurity http) throws Exception {

		 http
         //HTTP Basic authentication
 		.addFilterAfter(new CorsFilter(), SessionManagementFilter.class)
         .authorizeRequests()
         .anyRequest().authenticated()
         .and()
         .formLogin()
         .defaultSuccessUrl("/roles", true) // dashboard
         .failureHandler(customAuthenticationFailureHandler())
         .and()
         .logout().deleteCookies("JSESSIONID").invalidateHttpSession(true)
         .logoutSuccessUrl("/logoutmessage")
 		.and()
 		.csrf().disable()
 		.headers().frameOptions().sameOrigin();

        }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

}
