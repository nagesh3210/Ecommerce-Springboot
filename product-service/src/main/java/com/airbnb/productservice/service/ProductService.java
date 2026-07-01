package com.airbnb.productservice.service;

import com.airbnb.productservice.client.InventoryClient;
import com.airbnb.productservice.dto.InventoryResponse;
import com.airbnb.productservice.dto.ProductRequest;
import com.airbnb.productservice.dto.ProductResponse;
import com.airbnb.productservice.entity.Product;
import com.airbnb.productservice.exception.ProductNotFoundException;
import com.airbnb.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService
{

    private final ProductRepository repository;
    private final InventoryClient inventoryClient;


    public ProductResponse createProduct(ProductRequest request)
    {
        System.out.println(
                "Fetching from Database");

        Product product = Product.builder().name(request.name()).description(request.description()).price(request.price()).stockQuantity(request.stockQuantity()).build();


        Product save = repository.save(product);

        return new ProductResponse(save.getId(), save.getName(), save.getDescription(), save.getPrice(),save.getStockQuantity());
    }


//    @Cacheable(
//            value = "products",
//            key = "#id"
//    )
    public ProductResponse getProduct(Long id)
    {
        System.out.println("Inside getProduct");
        Product product = repository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        InventoryResponse response = inventoryClient.getInventory(id);

        log.info("info:{}",response);
        log.warn("info :{}",response);

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity()
        );
    }

    public InventoryResponse getInventory(
            Long productId) {

        return inventoryClient.getInventory(
                productId);
    }

}
