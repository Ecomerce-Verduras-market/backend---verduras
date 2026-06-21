package com.verduritas.product.application;

import com.verduritas.product.infrastructure.entity.ProductEntity;
import com.verduritas.product.infrastructure.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @CacheEvict(value = "products", allEntries = true)
    public ProductResponse create(CreateProductRequest request) {
        if (repository.existsBySku(request.sku())) throw new IllegalArgumentException("SKU already exists");
        var product = new ProductEntity();
        product.setName(request.name());
        product.setSku(request.sku());
        product.setPrice(request.price());
        product.setImageUrl(request.imageUrl());
        return toResponse(repository.save(product));
    }

    @Transactional(readOnly = true)
    @Cacheable("products")
    public List<ProductResponse> findAll() {
        return repository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "product", key = "#id")
    public ProductResponse findById(UUID id) {
        return repository.findById(id).map(this::toResponse).orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    private ProductResponse toResponse(ProductEntity entity) {
        return new ProductResponse(entity.getId(), entity.getName(), entity.getSku(), entity.getPrice(), entity.getImageUrl(), entity.isActive());
    }
}
