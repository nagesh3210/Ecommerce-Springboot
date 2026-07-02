package com.airbnb.inventoryservice.exception;

public class InventoryNotAvailableException extends RuntimeException
{

    public InventoryNotAvailableException(Long productId)
    {
        super("Inventory not available for product : " + productId);
    }

    public InventoryNotAvailableException(Long productId, Integer requested, Integer available)
    {
        super(
                "Requested quantity : "
                        + requested
                        + ", Available : "
                        + available
                        + " for product : "
                        + productId
        );
    }



}
