package com.ap.microservices.order.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ap.microservices.order.client.InventoryClient;
import com.ap.microservices.order.dto.OrderRequest;
import com.ap.microservices.order.model.Order;
import com.ap.microservices.order.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;
	
	private final InventoryClient inventoryClient;
	
	public void placeOrder(OrderRequest orderRequest) {
		
		var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
		
		System.out.println(inventoryClient.getClass());
		
		if(isProductInStock) {
			Order order = new Order();
			
			order.setOrderNumber(UUID.randomUUID().toString());
			order.setQuantity(orderRequest.quantity());
			order.setPrice(orderRequest.price());
			order.setSkuCode(orderRequest.skuCode());
			orderRepository.save(order);
			log.info("Product Created Successfully");
		}
		
		else {
			throw new RuntimeException("product with SkuCode :- " + orderRequest.skuCode() + " is not in stock");
		}

	}
	
}
