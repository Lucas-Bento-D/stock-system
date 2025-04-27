package com.marley_store.stock_system.service.product;

import com.marley_store.stock_system.dto.product.CreateProductDTO;
import com.marley_store.stock_system.model.product.Product;
import com.marley_store.stock_system.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAll(Long codeBar){

        return productRepository.findByCodeBar(codeBar);
    }

    public void createProduct(CreateProductDTO createProductDTO){
        Product product = Product.builder()
                .name(createProductDTO.name())
                .quantity(createProductDTO.quantity())
                .costPrice(createProductDTO.costPrice())
                .codeBar(createProductDTO.codeBar())
                .sellingPrice(createProductDTO.sellingPrice())
                .build();

        productRepository.save(product);
    }

}
