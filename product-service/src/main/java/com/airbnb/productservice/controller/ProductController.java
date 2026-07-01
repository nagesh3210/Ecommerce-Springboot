package com.airbnb.productservice.controller;

import com.airbnb.productservice.dto.ProductRequest;
import com.airbnb.productservice.dto.ProductResponse;
import com.airbnb.productservice.entity.Product;
import com.airbnb.productservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(
        name = "Product API",
        description = "Operations related to product management")
public class ProductController
{
    private final ProductService productService;

    @PostMapping
    @Operation(
            summary = "Create Product",
            description = "Creates a new product in the inventory"
    )
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest)
    {
        ProductResponse product = productService.createProduct(productRequest);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Get Product By id",
            description = "Get a Product By its id"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long id)
    {
        return new ResponseEntity<>(productService.getProduct(id),HttpStatus.OK);
    }


}
