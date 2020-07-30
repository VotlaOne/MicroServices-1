package com.gateway;

import com.netflix.zuul.ZuulFilter;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;


import com.netflix.zuul.context.RequestContext;

public class RequestFilter extends ZuulFilter {

	@Autowired
	RestTemplate restTempplate;

	private static final String X_REQUESTED_WITH = "X-Requested-With";
	private static final String XML_HTTP_REQUEST = "XMLHttpRequest";

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub

		return PRE_TYPE;
	}

	@Override
	public Object run() {

		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		String jwt_token = request.getHeader("Authorization");
		if (jwt_token == null) {

			System.out.println("Null JWT Zuul Request Method : " + request.getMethod() + " Request URL : "
					+ request.getRequestURL().toString() + " JWT token " + jwt_token);

			forwardError(request, ctx.getResponse(), 403,
					String.format("Missing required %s=%s request header", X_REQUESTED_WITH, XML_HTTP_REQUEST));
			return null;

		} else {

			jwt_token = jwt_token.split(" ")[1].trim();

			ResponseEntity<List> resp = restTempplate.exchange("http://tenant-service/getTokens", HttpMethod.GET, null,
					List.class);

			if (resp.getBody().contains(jwt_token)) {
				System.out.println("Valid Zuul Request Method : " + request.getMethod() + " Request URL : "
						+ request.getRequestURL().toString() + " JWT token " + jwt_token);
			} else {
				System.out.println("InValid JWT Toen Zuul Request Method : " + request.getMethod() + " Request URL : "
						+ request.getRequestURL().toString() + " JWT token " + jwt_token);
			}

		}

		return null;
	}

	private void forwardError(ServletRequest request, HttpServletResponse response, int status, String message) {
		RequestDispatcher dispatcher = request.getRequestDispatcher("http://localhost:8004/autherror");
		request.setAttribute("javax.servlet.error.status_code", status);
		request.setAttribute("javax.servlet.error.message", message);
		if (dispatcher != null && !response.isCommitted()) {
			response.setStatus(status);
			try {
				dispatcher.forward(request, response);
			} catch (Exception e) {
				System.out.println("Exception during forwarding request" + e);
			}
		}
	}

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

}
