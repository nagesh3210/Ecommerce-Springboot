package com.airbnb.productservice.repository;

import com.airbnb.productservice.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long>
{

}
