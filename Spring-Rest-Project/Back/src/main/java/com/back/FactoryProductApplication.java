package com.back;

import com.back.entity.Factory;
import com.back.entity.Price;
import com.back.entity.Product;
import com.back.repository.FactoryRepository;
import com.back.repository.PriceRepository;
import com.back.repository.ProductRepository;
import com.back.service.PriceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Date;

@SpringBootApplication
@EnableJpaRepositories("com.back.repository")
@EntityScan("com.back.entity")
public class FactoryProductApplication {

    public static void main(String[] args) {
        ApplicationContext ctx  = SpringApplication.run(FactoryProductApplication.class, args);

        ProductRepository productRepository = ctx.getBean(ProductRepository.class);
        FactoryRepository factoryRepository = ctx.getBean(FactoryRepository.class);
        PriceRepository priceRepository = ctx.getBean(PriceRepository.class);

        PriceService priceService = ctx.getBean(PriceService.class);

        Product product1 = new Product("Сырок с клубникой","1","Сырок");

        Product product2 = new Product();
        product2.setName("Пармезан");
        product2.setSort("Высший");
        product2.setProductGroup("Сыр");

        Factory factory1 = new Factory("Молочная мечка","Минск");
        Factory factory2 = new Factory("Сырная мечка","Пинск");

        Price price1 = new Price(factory1, product1, new Date(1212121212121L), 132.5, 15.5);
        Price price2 = new Price();
        price2.setFactory(factory2);
        price2.setProduct(product2);
        price2.setDate(Date.valueOf("2022-10-12"));
        price2.setPurchasePrice(13.5);
        price2.setSellingPrice(5.75);

        productRepository.save(product1);
        productRepository.save(product2);

        factoryRepository.save(factory1);
        factoryRepository.save(factory2);

        priceRepository.save(price1);
        priceRepository.save(price2);

    }

}
