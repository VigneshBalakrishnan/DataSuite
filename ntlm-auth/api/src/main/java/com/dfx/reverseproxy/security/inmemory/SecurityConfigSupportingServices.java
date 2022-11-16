package com.dfx.reverseproxy.security.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecurityConfigSupportingServices {

	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST} , value = "/inmemoryroles")
	public Map<String, List> greeting() {
		Map<String, List> user_roles = new HashMap();
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<String> odf_roles = new ArrayList<String>();
		odf_roles.add(userDetails.getUsername());
		List<String> user = new ArrayList();
		user.add(userDetails.getUsername());
		user_roles.put("username", user   );
		userDetails.getAuthorities().forEach(role -> {
			String role_truncated = role.getAuthority().replace("ROLE_", "");
			odf_roles.add(role_truncated.toLowerCase());
		});
		user_roles.put("roles", odf_roles   );
		return user_roles;
	}
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST} , value = "/logoutmessage")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {  
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();  
        HttpSession session = request.getSession(false); 
        if (session != null) { 
            System.out.println("session id : " + session.getId());
           session.invalidate();
           System.out.println("Session invalidated ....");
        } 
        if (auth != null){     
        	System.out.println("LOGOUT called ............................");
           new SecurityContextLogoutHandler().logout(request, response, auth);  
        }  
         return "Signed out";  
     }
	
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST} , value = "/invalidcredential")
    public String loginfailed(HttpServletRequest request, HttpServletResponse response) {  
         return "Invalid Username or Password";  
     } 
}
