package com.airbnb.orderservice.client;

import inventory.InventoryGrpcServiceGrpc;
import inventory.stockRequest;
import inventory.stockResponse;
import net.devh.boot.grpc.client.inject.GrpcClient;

public class InventoryClient
{
    @GrpcClient("inventory")
    private InventoryGrpcServiceGrpc
            .InventoryGrpcServiceBlockingStub stub;

    public  boolean checkStock(Long productId)
    {
        stockResponse response = stub.checkStock(stockRequest.newBuilder().setProductId(productId).build());

        return response.getInStock();
    }
}
