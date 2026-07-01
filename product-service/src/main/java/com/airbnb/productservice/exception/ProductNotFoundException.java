package com.airbnb.productservice.exception;

public class ProductNotFoundException extends RuntimeException
{
    public ProductNotFoundException(Long id){
        super("Product Not Found "+id);
    }
}
