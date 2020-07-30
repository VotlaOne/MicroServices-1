package com.ucuenca.spring;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;



@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@RestController
public class TokenController {

	@RequestMapping("/")
	@ResponseBody
	String home() {

		String message = "From Tenant Service";
		return message;

	}

	@RequestMapping(value = "/getTokens")
	public List<String> getTenantTokens() {
		List<String> list = new ArrayList<>();

		list.add("jwt-token1");
		list.add("jwt-token2");
		list.add("jwt-token3");
		list.add("jwt-token4");

		return list;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(TokenController.class, args);
	}
}
