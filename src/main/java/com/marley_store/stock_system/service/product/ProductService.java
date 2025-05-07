package com.marley_store.stock_system.service.product;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.marley_store.stock_system.config.userAuthenticationFilter.UserAuthenticationFilter;
import com.marley_store.stock_system.dto.product.CreateProductDTO;
import com.marley_store.stock_system.dto.product.GetProductDTO;
import com.marley_store.stock_system.dto.product.UpdateProductDTO;
import com.marley_store.stock_system.exceptions.product.ProductNotFoundException;
import com.marley_store.stock_system.exceptions.user.UserNotFoundException;
import com.marley_store.stock_system.model.product.Product;
import com.marley_store.stock_system.model.user.User;
import com.marley_store.stock_system.repository.product.ProductRepository;
import com.marley_store.stock_system.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    @Autowired
    private UserRepository userRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public GetProductDTO getProduct(Long codeBar){
        Optional<Product> product = productRepository.findByCodeBar(codeBar);

        if(product.isPresent()){
            return new GetProductDTO(product.get());
        }else{
            throw new ProductNotFoundException();
        }

//        return productRepository.findByCodeBar(codeBar);
    }

    public void createProduct(CreateProductDTO createProductDTO, HttpServletRequest request){

        String email = userAuthenticationFilter.getEmailToken(request);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());

        Product product = Product.builder()
                .name(createProductDTO.name())
                .quantity(createProductDTO.quantity())
                .costPrice(createProductDTO.costPrice())
                .codeBar(createProductDTO.codeBar())
                .sellingPrice(createProductDTO.sellingPrice())
                .user(user)
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
