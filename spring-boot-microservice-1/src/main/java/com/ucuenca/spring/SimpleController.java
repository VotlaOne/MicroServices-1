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
public class SimpleController {

    @RequestMapping("/")
    @ResponseBody
    String home() {
    	long t1=System.currentTimeMillis();
        String message = "Hello World from Greeting-1  located at %s!";
        try {
            String address = InetAddress.getLocalHost().getHostAddress();
            message = String.format(message, address);
        } catch (UnknownHostException e) {
            message = String.format(message, "Unknown Host");
        }

        try {
			//Thread.sleep(2000);
		} catch (Exception e) {
			// TODO: handle exception
		}
        long t2=System.currentTimeMillis();
        return message +" after "+(t2-t1)+" ms";

    }

    @RequestMapping(value="/products")
    public List<String> getProducts(){
    	
    	List<String> list=new ArrayList<>();
    	
    	list.add("Product_a");
    	list.add("Product_b");
    	list.add("Product_c");
    	
    	return list;
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(SimpleController.class, args);
    }
}
