package com.ucuenca.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

public class SpringDiscoveryClientEnabledRestTemplateDemo {

	
	@Autowired
	RestTemplate restTemplate;
	
	@RequestMapping(value="/resttemplate")
	public String getGreet() {
		

		ResponseEntity<String> resp= restTemplate.exchange("http://GreetingMicroservice/", HttpMethod.GET,null,String.class);
		
		if(resp==null) return null;
				
		return resp.getBody();
	}
	
}
