package com.arvind.microservices.inventory_service.service;

import com.arvind.microservices.inventory_service.Repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String skuCode,Integer quantity){
       return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode,quantity);
    }

}
