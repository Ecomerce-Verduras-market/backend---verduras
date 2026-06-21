package com.verduritas.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.verduritas.product.infrastructure.entity.ProductEntity;
import com.verduritas.product.infrastructure.repository.ProductRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class ProductServiceTest {
    private final ProductRepository repository = mock(ProductRepository.class);
    private final ProductService productService = new ProductService(repository);

    @Test
    void createsProduct() {
        when(repository.existsBySku("CARROT-001")).thenReturn(false);
        when(repository.save(any(ProductEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var response = productService.create(new CreateProductRequest(
                "Organic carrots",
                "CARROT-001",
                BigDecimal.valueOf(3.50),
                "https://example.com/carrots.jpg"));

        assertThat(response.name()).isEqualTo("Organic carrots");
        assertThat(response.sku()).isEqualTo("CARROT-001");
        assertThat(response.price()).isEqualByComparingTo("3.50");
        assertThat(response.imageUrl()).isEqualTo("https://example.com/carrots.jpg");
        assertThat(response.active()).isTrue();
    }
}
