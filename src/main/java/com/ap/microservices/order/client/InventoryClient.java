package com.ap.microservices.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

//@FeignClient(value = "inventory", url = "http://localhost:8082")
//removing this feign client usage as the it is now deprecated.

public interface InventoryClient {

//	@RequestMapping(method = RequestMethod.GET, value = "/api/inventory")
	@GetExchange("/api/inventory")
	boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
	
}
