package com.airbnb.inventoryservice.grpcService;

import com.airbnb.inventoryservice.entity.Inventory;
import com.airbnb.inventoryservice.repository.InventoryRepository;
import inventory.InventoryGrpcServiceGrpc;
import inventory.stockRequest;
import inventory.stockResponse;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Optional;

@RequiredArgsConstructor
@GrpcService
public class InventoryGrpcServiceImpl extends InventoryGrpcServiceGrpc.InventoryGrpcServiceImplBase
{

    private final InventoryRepository inventoryRepository;


    @Override
    public void checkStock(stockRequest request, StreamObserver<stockResponse> observer)
    {

        Inventory inventory = inventoryRepository.findByProductId(request.getProductId()).orElseThrow();

        stockResponse response = stockResponse.newBuilder().setProductId(inventory.getProductId()).setAvaiableQuantity(inventory.getAvailableQuantity()).setInStock(inventory.getAvailableQuantity() > 0).build();


        observer.onNext(response);

        observer.onCompleted();

    }
}
