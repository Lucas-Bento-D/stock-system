package com.marley_store.stock_system.service.product;

import com.marley_store.stock_system.dto.product.CreateProductDTO;
import com.marley_store.stock_system.dto.product.UpdateProductDTO;
import com.marley_store.stock_system.exceptions.product.ProductNotFoundException;
import com.marley_store.stock_system.model.product.Product;
import com.marley_store.stock_system.repository.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProduct(Long codeBar){
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

    public void updateProduct(UpdateProductDTO updateProductDTO, Long codeBar){
        Product product = productRepository.findByCodeBar(codeBar).orElseThrow(() -> new ProductNotFoundException());

    }

}
