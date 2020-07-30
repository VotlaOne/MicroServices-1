package com.licenses;

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
public class LicenseController {

	@RequestMapping("/")
    @ResponseBody
    String home() {
    	
        return  "From Licenses /";
        
    }

	@RequestMapping(value = "/licensesList")
	public List<String> getLicenses() {

		List<String> list = new ArrayList<>();

		list.add("License_a");
		list.add("License_b");
		list.add("License_c");

		return list;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(LicenseController.class, args);
	}
}
