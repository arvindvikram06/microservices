package com.microservices.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service",url = "${inventory.url}")
public interface InventoryClient {

    @RequestMapping(method = RequestMethod.GET,value = "/api/v1/inventory")
    boolean isInStock(@RequestParam String skuCode,@RequestParam int quantity);
}
