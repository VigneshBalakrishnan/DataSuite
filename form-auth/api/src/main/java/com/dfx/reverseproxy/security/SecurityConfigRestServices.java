package com.dfx.reverseproxy.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dfx.reverseproxy.security.userauthorization.RolesManager;
import com.dfx.reverseproxy.security.userauthorizationException.UserauthorizationException;

@RestController
public class SecurityConfigRestServices {

	Logger log = LoggerFactory.getLogger(SecurityConfigRestServices.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/roles")
	public Map<String, List> roles(HttpSession session) {

		Map<String, List> user_roles = new HashMap();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> user = new ArrayList<String>();
		user.add(userDetails.getUsername());
		user_roles.put("username", user);
		try {
			user_roles.put("roles", RolesManager.getRoles(userDetails.getUsername()));
			session.setAttribute("ntlmusername", user);
		} catch (UserauthorizationException e) {
			log.error(e.getMessage());
		}

		return user_roles;
	}

}
