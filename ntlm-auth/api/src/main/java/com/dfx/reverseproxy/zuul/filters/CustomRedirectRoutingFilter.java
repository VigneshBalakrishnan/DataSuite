package com.dfx.reverseproxy.zuul.filters;

import java.io.IOException;
import java.net.URL;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.pre.PreDecorationFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class CustomRedirectRoutingFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(CustomRedirectRoutingFilter.class);

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		if ((ctx.get("proxy") != null) && ctx.get("proxy").equals("superset")) {
			log.info("Executing CustomRedirectRoutingFilter");
			return true;
		}
		return false;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		String url = ctx.getRequest().getRequestURL().toString();
		log.info("Request URL:{}",url);
		String token=(String)ctx.getRequest().getSession().getAttribute("access_token");
		String requestParam="?token="+token;
		String redirectUrl=((URL) ctx.get("routeHost")).toString();
		redirectUrl=redirectUrl+requestParam;
		log.info("Redirecting to URL:{}",redirectUrl);
		ctx.setSendZuulResponse(false);
		ctx.put(FilterConstants.FORWARD_TO_KEY, redirectUrl);
		ctx.setResponseStatusCode(HttpStatus.SC_TEMPORARY_REDIRECT);
		try {
			ctx.getResponse().sendRedirect(redirectUrl);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return PreDecorationFilter.FILTER_ORDER + 1;
	}

}
