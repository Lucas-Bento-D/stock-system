package com.marley_store.stock_system.repository.product;

import com.marley_store.stock_system.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
    Optional<Product> findByCodeBar(Long codeBar);
    <S extends Product> S saveAndFlush(S entity);
    boolean existsByName(String name);
    boolean existsByCodeBar(Long codeBar);

}
