package com.airbnb.inventoryservice.controller;


import com.airbnb.inventoryservice.dto.InventoryResponse;
import com.airbnb.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/inventory")
public class InventoryController
{

    private final InventoryService inventoryService;


    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getStock(@PathVariable Long productId)
    {

        return new ResponseEntity<>(inventoryService.checkStock(productId), HttpStatus.OK);

    }


}
