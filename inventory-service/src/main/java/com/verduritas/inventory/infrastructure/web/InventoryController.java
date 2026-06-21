package com.verduritas.inventory.infrastructure.web;

import com.verduritas.inventory.application.InventoryResponse;
import com.verduritas.inventory.application.InventoryService;
import com.verduritas.inventory.application.UpsertStockRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    InventoryResponse upsert(@Valid @RequestBody UpsertStockRequest request) {
        return inventoryService.upsert(request);
    }

    @GetMapping
    List<InventoryResponse> findAll() {
        return inventoryService.findAll();
    }
}
