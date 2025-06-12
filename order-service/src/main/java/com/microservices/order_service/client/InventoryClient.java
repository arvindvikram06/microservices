package com.microservices.order_service.client;



import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

//@FeignClient(name = "inventory-service",url = "${inventory.url}")
public interface InventoryClient {

    @GetExchange("/api/v1/inventory")
    boolean isInStock(@RequestParam String skuCode,@RequestParam int quantity);
}
