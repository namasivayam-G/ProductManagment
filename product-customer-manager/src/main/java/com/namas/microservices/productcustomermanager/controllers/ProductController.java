package com.namas.microservices.productcustomermanager.controllers;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/order")
public class ProductController {

	private static final Logger LOG = Logger.getLogger(ProductController.class.getName());

//	@Autowired
//	RestTemplate rt;
	
	@Autowired
	@Qualifier("nibba")
	WebClient webClient;

	@GetMapping("/item01")
	//@CircuitBreaker(name="orderItem", fallbackMethod = "fallbackOrderItem")
	@Retry(name="orderItem",fallbackMethod = "fallbackOrderItem")
	public String orderitem() {
	  LOG.info("Inside Microservice A: Product Controller Mono mono");
	  String result = webClient.get()
	  	.uri("http://localhost:9202/checkrepo/order01")
	  	.retrieve()
	  	.bodyToMono(String.class).block();
		/* rt.getForObject("http://localhost:9202/checkrepo/order01", String.class); */
	  LOG.info("Output of MicroService A : "+ "response of B");
	  return result+" Success";
	}
	
	@GetMapping("/item02")
	//@CircuitBreaker(name="orderItem", fallbackMethod = "fallbackOrderItem")
	//@Retry(name="orderItem",fallbackMethod = "fallbackOrderItem")
	public String orderitem2() {
	  LOG.info("Inside Microservice A2: Product Controller Mono mono");
	  String result = webClient.get()
	  	.uri("http://localhost:9202/checkrepo/order01")
	  	.retrieve()
	  	.bodyToMono(String.class).block();
		/* rt.getForObject("http://localhost:9202/checkrepo/order01", String.class); */
	  LOG.info("Output of MicroService A2: "+ "response of B");
	  return result+" Success2";
	}
	
	public String fallbackOrderItem(Exception e) {
		return "This is a dummy response";
	}

}
