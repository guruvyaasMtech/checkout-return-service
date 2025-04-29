package com.libManager.checkoutreturnservice.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventory-service") // The name used in the Inventory Service's spring.application.name
public interface InventoryServiceClient {

    @GetMapping("/api/inventory/code/{itemCode}")
    InventoryItemInfo getItemByItemCode(@PathVariable String itemCode);

    @PatchMapping("/api/inventory/code/{itemCode}/status")
    void updateItemStatus(@PathVariable String itemCode, @RequestParam String status);
}