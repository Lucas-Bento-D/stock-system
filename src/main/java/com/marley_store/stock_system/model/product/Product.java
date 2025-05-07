package com.marley_store.stock_system.model.product;

import com.marley_store.stock_system.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;

    @Column(nullable = false)
    private Long quantity;

    @Column(unique = true, nullable = false)
    private Long codeBar;
    private Float costPrice;

    @Column(nullable = false)
    private Float sellingPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Product(){}

}
