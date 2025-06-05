package com.microservices.order_service.stubs;
import static com.github.tomakehurst.wiremock.client.WireMock.*;


public class InventoryClientStubs {

    public static void stubInventoryCall(String skuCode, int quantity){
        stubFor(get(urlEqualTo("/api/v1/inventory?skuCode=" + skuCode + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true")));
    }
}
