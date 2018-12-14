package com.tcs.ifact.securityconfig;

import java.util.Arrays;

import org.apache.poi.util.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.tcs.ifact.dao.IUserDao;
import com.tcs.ifact.model.UserInfo;

@Service
public class MyAppUserDetailsService implements UserDetailsService {
	@Autowired
	private IUserDao userDao;
	@Override
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		UserInfo activeUserInfo = userDao.getActiveUser(userName);
		GrantedAuthority authority = new SimpleGrantedAuthority(activeUserInfo.getRole());
		UserDetails userDetails = (UserDetails)new User(activeUserInfo.getUser(),
				activeUserInfo.getPassword(), Arrays.asList(authority));
		return userDetails;
	}
}

