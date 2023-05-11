package com.front.entity;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Product {

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
