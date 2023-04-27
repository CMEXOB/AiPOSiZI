package com.factoryproduct.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Getter
@Setter
public class Price {
    @Id
    @SequenceGenerator(
            name = "price_sequence",
            sequenceName = "price_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "price_sequence"
    )
    private Long id;


    @ManyToOne
    @JoinColumn(name = "factoryId", referencedColumnName = "id")
    private Factory factory;

    @ManyToOne
    @JoinColumn(name = "productId", referencedColumnName = "id")
    private Product product;

    private Date date;
    private Double purchasePrice;
    private Double sellingPrice;

    public Price(Long id, Factory factory, Product product, Date date, Double purchasePrice, Double sellingPrice) {


        this.id = id;
        this.date = date;
        this.factory = factory;
        this.product =product;
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
