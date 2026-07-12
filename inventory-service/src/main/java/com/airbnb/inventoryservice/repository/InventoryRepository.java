package com.airbnb.inventoryservice.repository;

import com.airbnb.inventoryservice.entity.Inventory;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory,Long>
{
      @Lock(LockModeType.PESSIMISTIC_WRITE)
      Optional<Inventory> findByProductId(Long id);
}
