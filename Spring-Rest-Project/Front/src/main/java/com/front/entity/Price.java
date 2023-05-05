package com.front.entity;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;


@Getter
@Setter
public class Price {
    private Long id;


    private Factory factory;

    private Product product;

    private Date date;
    private Double purchasePrice;
    private Double sellingPrice;

    public Price(Factory factory, Product product, Date date, Double purchasePrice, Double sellingPrice) {

        this.date = date;
        this.factory = factory;
        this.product =product;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public Price(Date date, Double purchasePrice, Double sellingPrice) {

        this.date = date;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public Price() {
    }

    @Override
    public String toString() {
        return "Price{" +
                "id=" + id +
                ", factory=" + factory.getName() +
                ", product=" + product.getName() +
                ", date=" + date +
                ", purchasePrice=" + purchasePrice +
                ", sellingPrice=" + sellingPrice +
                '}';
    }
}
