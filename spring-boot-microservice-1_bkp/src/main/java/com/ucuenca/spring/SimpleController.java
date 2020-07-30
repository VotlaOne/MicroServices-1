package com.ucuenca.spring;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.*;

import java.net.InetAddress;
import java.net.UnknownHostException;



@Configuration
@EnableAutoConfiguration
@EnableDiscoveryClient
@RestController
public class SimpleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
    	long t1=System.currentTimeMillis();
        String message = "Hello World from Greeting-1_bkp  located at %s!";
        try {
            String address = InetAddress.getLocalHost().getHostAddress();
            message = String.format(message, address);
        } catch (UnknownHostException e) {
            message = String.format(message, "Unknown Host");
        }

        try {
			//Thread.sleep(3000);
		} catch (Exception e) {
			// TODO: handle exception
		}
        long t2=System.currentTimeMillis();
        return message +" after "+(t2-t1)+" ms";
        
       

    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SimpleController.class, args);
    }
}
