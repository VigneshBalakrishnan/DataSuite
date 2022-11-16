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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dfx.reverseproxy.security.ntlm.RolesManager;
import com.dfx.reverseproxy.security.userauthorizationException.UserauthorizationException;

@RestController
public class SecurityConfigRestServices {

	Logger log = LoggerFactory.getLogger(SecurityConfigRestServices.class);

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST }, value = "/roles")
	public Map<String, List> roles(HttpSession session) {

		Map<String, List> user_roles = new HashMap();

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		String username = auth.getPrincipal().toString();
		List<String> user = new ArrayList<String>();
		user.add(username);
		user_roles.put("username", user);
		try {
			user_roles.put("roles", RolesManager.getRoles(username));
			session.setAttribute("ntlmusername", user); // client side post request is not posting ntlm

		} catch (UserauthorizationException e) {

			log.error(e.getErrorMsg());

		}
		return user_roles;
	}

}
