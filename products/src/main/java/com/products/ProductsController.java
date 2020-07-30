package com.products;

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
public class ProductsController {

    @RequestMapping("/")
    @ResponseBody
    String home() {return  "From Products /";}

    @RequestMapping(value="/productsList")
    public List<String> getProducts(){
    	
    	List<String> list=new ArrayList<>();
    	
    	list.add("Product_a");
    	list.add("Product_b");
    	list.add("Product_c");
    	
    	return list;
    }
    public static void main(String[] args) throws Exception {
        SpringApplication.run(ProductsController.class, args);
    }
}
