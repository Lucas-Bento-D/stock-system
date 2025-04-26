package com.marley_store.stock_system.repository.product;

import com.marley_store.stock_system.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String name);
    List<Product> findByCodeBar(Long codeBar);
    <S extends Product> S saveAndFlush(S entity);
    boolean existsByName(String name);
    boolean existsByCodeBar(Long codeBar);

}
