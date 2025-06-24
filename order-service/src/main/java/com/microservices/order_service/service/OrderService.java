package com.microservices.order_service.service;

import com.microservices.order_service.client.InventoryClient;
import com.microservices.order_service.dto.OrderRequest;
import com.microservices.order_service.event.OrderPlacedEvent;
import com.microservices.order_service.model.Order;
import com.microservices.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

        private final OrderRepository orderRepository;

        private final InventoryClient inventoryClient;

        private final KafkaTemplate<String,OrderPlacedEvent> kafkaTemplate;

        public void placeOrder(OrderRequest orderRequest){
            var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());
            if(isProductInStock){
                Order order = new Order();
                order.setOrderNumber(UUID.randomUUID().toString());
                order.setQuantity(orderRequest.quantity());
                order.setPrice(orderRequest.price());
                order.setSkuCode(orderRequest.skuCode());
                orderRepository.save(order);

                //kafka event starts
                OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
                orderPlacedEvent.setOrderNumber(order.getOrderNumber());
                orderPlacedEvent.setEmail(orderRequest.userDetails().email());
                orderPlacedEvent.setFirstName(orderRequest.userDetails().firstName());
                orderPlacedEvent.setLastName(orderRequest.userDetails().lastName());

                log.info("sending order placed event to kafka for orderNumber {}",orderRequest.orderNumber());
                kafkaTemplate.send("order-placed",orderPlacedEvent);
                log.info("sent order placed event to kafka for orderNumber{}",order.getOrderNumber());
            }else{
                throw new RuntimeException("the product with skucode" + orderRequest.skuCode() + " is not in stock");
            }

        }
}
