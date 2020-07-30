package com.gateway;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class PostFilter extends ZuulFilter {

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

		return POST_TYPE;
	}

	@Override
	public Object run() {

		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

        List<Pair<String, String>> filteredResponseHeaders = new ArrayList<>();

        Pair<String, String> pair = new Pair<>("added_post_Filter_key", "added_post_Filter_value");
        filteredResponseHeaders.add(pair);
		ctx.put("zuulResponseHeaders", filteredResponseHeaders);

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
