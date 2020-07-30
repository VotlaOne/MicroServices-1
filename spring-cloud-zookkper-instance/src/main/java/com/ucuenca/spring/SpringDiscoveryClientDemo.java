package com.ucuenca.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import com.ucuenca.spring.GreetingServiceConsumerApplication.GreetingCleint;

@RestController
public class SpringDiscoveryClientDemo {

	@Autowired
	private DiscoveryClient discoveryClient;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	GreetingCleint greetingClient;

	@RequestMapping(value = "/discovery")
	public List<String> getGreetDiscovery() {

		RestTemplate restTemplate = new RestTemplate();

		List<ServiceInstance> list = discoveryClient.getInstances("GreetingMicroservice");

		if (list == null || list.size() == 0)
			return null;

		List<String> list2 = new ArrayList<>();

		for (ServiceInstance instance : list) {

			ResponseEntity<String> resp = restTemplate.getForEntity(instance.getUri(), String.class);

			list2.add("uri " + instance.getUri() + " &resp " + resp.getBody() + " ");

		}
		return list2;
	}

	//change this when testing fallback
	@RequestMapping(value = "/resttemplate")
	@HystrixCommand(fallbackMethod = "getFallBack", threadPoolKey = "greetingPool", threadPoolProperties = {
			@HystrixProperty(name = "coreSize", value = "10"),
			@HystrixProperty(name = "maxQueueSize", value = "5") }, commandProperties = {
					@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
					@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "75"),
					@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000"),
					@HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "60000"),
					@HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "5"),
					@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000") })
	public String getGreetResttemplate() {

		ResponseEntity<String> resp = restTemplate.exchange("http://GreetingMicroservice/", HttpMethod.GET, null,
				String.class);

		if (resp == null)
			return null;

		System.out.println("Response "+resp.getBody()+" from "+Thread.currentThread());
		
		return resp.getBody()+" from "+Thread.currentThread();
	}

	public String getFallBack() {
		return "this is fallback string from "+Thread.currentThread();
	}

	@RequestMapping(value = "/feignclient")
	public String getGreetFeignclient() {

		return greetingClient.getGreetFeignclient();
	}
}
