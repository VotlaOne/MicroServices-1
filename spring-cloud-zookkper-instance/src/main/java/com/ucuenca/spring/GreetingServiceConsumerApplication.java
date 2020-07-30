package com.ucuenca.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.ribbon.proxy.annotation.Hystrix;


@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
@EnableCircuitBreaker
public class GreetingServiceConsumerApplication {

    @Autowired
    private ServiceConsumerClient serviceConsumer;


    @GetMapping("/get")
    public String helloWorld() {

        return "Hello Consumer";

    }

    @GetMapping("/get-greeting")
    public String greeting() {

        return serviceConsumer.helloWorld();

    }

    public static void main(String[] args) {
        SpringApplication.run(GreetingServiceConsumerApplication.class, args);
    }
    
	@LoadBalanced
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@FeignClient(name="GreetingMicroservice")
	public interface GreetingCleint{
		
		@RequestMapping(value="/")
		public String getGreetFeignclient();
	}

	
}
