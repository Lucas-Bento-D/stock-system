package com.marley_store.stock_system.model.products;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Entity
@AllArgsConstructor
@Table(name = "products")
public class products {

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

}
