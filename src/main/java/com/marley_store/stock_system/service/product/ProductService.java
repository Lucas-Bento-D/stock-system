package com.marley_store.stock_system.service.product;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

        ObjectMapper objectMapper = new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ObjectNode productObject = objectMapper.valueToTree(product);
        ObjectNode productDTOObject = objectMapper.valueToTree(updateProductDTO);

        Product updateProduct = Product.builder()
                .id(productObject.get("id").longValue())
                .name(productDTOObject.get("name").asText())
                .quantity(productObject.get("quantity").longValue())
                .codeBar(productObject.get("codeBar").longValue())
                .costPrice(productDTOObject.get("costPrice").floatValue())
                .sellingPrice(productDTOObject.get("sellingPrice").floatValue())
                .build();

        productRepository.save(updateProduct);

    }

}
