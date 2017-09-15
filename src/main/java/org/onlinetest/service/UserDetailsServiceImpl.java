package org.onlinetest.service;

import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.onlinetest.dao.UserAccountDao;
import org.onlinetest.model.User;
import org.onlinetest.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {

	private static final Logger logger = LogManager.getLogger(UserDetailsServiceImpl.class);
	
	@Autowired
	UserAccountDao userAccountDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userAccountDao.findByUserName(username);
		logger.info("User : {}", user);
		if(user == null){
			logger.info("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for(UserRole role : user.getRole()){
			logger.info("UserRole : {}", role);
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
		}
		logger.info("grantedAuthorities : {}", grantedAuthorities);
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), grantedAuthorities);
	}
}
