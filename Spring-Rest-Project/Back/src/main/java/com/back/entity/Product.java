package com.back.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "product_sequence"
    )
    private Long id;
    private String name;
    private String sort;
    private String productGroup;

    public Product(String name, String sort, String productGroup) {
        this.name = name;
        this.sort = sort;
        this.productGroup = productGroup;
    }

    public Product() {

    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", sort='" + sort + '\'' +
                ", productGroup='" + productGroup + '\'' +
                '}';
    }
}
