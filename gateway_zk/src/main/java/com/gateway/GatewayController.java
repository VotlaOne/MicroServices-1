package com.gateway;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.stereotype.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@RestController
@EnableZuulProxy
public class GatewayController {

	@Autowired
	RestTemplate restTempplate;

	@RequestMapping("/")
	@ResponseBody
	String home() {

		return "This is API Gateway ZK";
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(GatewayController.class, args);
	}

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(value = "/get")
	public List<String> getTokens() {

		ResponseEntity<List> resp = restTempplate.exchange("http://tenant-service/getTokens", HttpMethod.GET, null,
				List.class);

		return resp.getBody();

	}

	@RequestMapping(value = "/autherror")
	public String error() {
		return "No JWT Token";

	}

	@Bean
	public RequestFilter requestFilter() {
		return new RequestFilter();
	}
	
	@Bean
	public PostFilter postFilter() {
		return new PostFilter();
	}
}
