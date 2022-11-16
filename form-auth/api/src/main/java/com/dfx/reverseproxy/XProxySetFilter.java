package com.dfx.reverseproxy;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.dfx.reverseproxy.security.userauthorization.RolesManager;
import com.dfx.reverseproxy.security.userauthorizationException.UserauthorizationException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;


@Component
public class XProxySetFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(XProxySetFilter.class);

	@Override
	public String filterType() {
		return PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	public Object runInMemory() throws ZuulException {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		StringBuffer odf_roles = new StringBuffer();
		userDetails.getAuthorities().forEach(role -> {
			String role_truncated = role.getAuthority().replace("ROLE_", "");
			odf_roles.append(role_truncated.toLowerCase() + ",");
		});
		log.info("Setting proxy roles " + odf_roles);
		RequestContext ctx = RequestContext.getCurrentContext();
		// ctx.addZuulRequestHeader("x-forwarded-for", "127.0.0.1");
		ctx.addZuulRequestHeader("x-proxy-user", userDetails.getUsername());
		ctx.addZuulRequestHeader("x-proxy-roles", odf_roles + "readall");
		log.trace(ctx.getZuulRequestHeaders().toString());
		return null;
	}

	@Override
	public Object run() throws ZuulException {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		RequestContext ctx = RequestContext.getCurrentContext();
		String username = auth.getPrincipal().toString();
		Object usernamefromsession = ctx.getRequest().getSession().getAttribute("ntlmusername");
		if (username == null || username.isEmpty()) {
			if (usernamefromsession == null) {
				System.out.println("Both username from ntlm and session is null..........");
			} else {
				username = usernamefromsession.toString();
			}
		} else {
			if (usernamefromsession == null) {
				ctx.getRequest().getSession().setAttribute("ntlmusername", username);
			}
		}
		// ctx.addZuulRequestHeader("x-forwarded-for", "127.0.0.1");
		ctx.addZuulRequestHeader("x-proxy-user", username); // auth.getPrincipal().toString()
		
		List<String> roles = null;
		try {
			roles = RolesManager.getRoles(username);
			// auth.getPrincipal().toString()
			String odf_roles = String.join(",", roles);
			ctx.addZuulRequestHeader("x-proxy-roles", odf_roles + ",readall");
			ctx.addZuulRequestHeader("x-forwarded-for", "127.0.0.1");
			log.info("logged in user is " + auth.getPrincipal().toString());
			log.info("logged in user's role " + odf_roles);
			log.trace(ctx.getZuulRequestHeaders().toString());
		} catch (UserauthorizationException e) {
		// TODO Auto-generated catch block
		e.getErrorMsg();
		}
		return null;
		/*
		List<String> roles = RolesManager.getRoles(username); // auth.getPrincipal().toString()
		String odf_roles = String.join(",", roles);
		ctx.addZuulRequestHeader("x-proxy-roles", odf_roles + ",readall");
		ctx.addZuulRequestHeader("x-forwarded-for", "127.0.0.1");
		log.info("logged in user is " + auth.getPrincipal().toString());
		log.info("logged in user's role " + odf_roles);
		log.trace(ctx.getZuulRequestHeaders().toString());
		return null;
		*/

	}

}

