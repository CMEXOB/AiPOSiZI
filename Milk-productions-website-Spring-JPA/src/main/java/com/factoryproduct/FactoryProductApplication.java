package com.factoryproduct;

import com.factoryproduct.entity.Factory;
import com.factoryproduct.entity.Price;
import com.factoryproduct.entity.Product;
import com.factoryproduct.repository.FactoryRepository;
import com.factoryproduct.repository.PriceRepository;
import com.factoryproduct.repository.ProductRepository;
import com.factoryproduct.service.PriceService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import java.sql.Date;
import java.util.List;

@SpringBootApplication
@EnableJpaRepositories("com.factoryproduct.repository")
@EntityScan("com.factoryproduct.entity")
public class FactoryProductApplication {

    public static void main(String[] args) {
        ApplicationContext ctx  = SpringApplication.run(FactoryProductApplication.class, args);

//        ProductRepository productRepository = ctx.getBean(ProductRepository.class);
//        FactoryRepository factoryRepository = ctx.getBean(FactoryRepository.class);
//        PriceRepository priceRepository = ctx.getBean(PriceRepository.class);
//
//        PriceService priceService = ctx.getBean(PriceService.class);
//
//        Product product1 = new Product(1l,"Сырок с клубникой","1","Сырок");
//
//        Product product2 = new Product();
//        product2.setName("Пармезан");
//        product2.setSort("Высший");
//        product2.setProductGroup("Сыр");
//
//        Factory factory1 = new Factory(1l,"Молочная мечка","Минск");
//        Factory factory2 = new Factory(2l,"Сырная мечка","Пинск");
//
//        Price price1 = new Price(1l, factory1, product1, new Date(1212121212121L), 132.5, 15.5);
//        Price price2 = new Price();
//        price2.setFactory(factory2);
//        price2.setProduct(product2);
//        price2.setDate(Date.valueOf("2022-10-12"));
//        price2.setPurchasePrice(13.5);
//        price2.setSellingPrice(5.75);
//
//        productRepository.save(product1);
//        productRepository.save(product2);
//
//        factoryRepository.save(factory1);
//        factoryRepository.save(factory2);
//
//        priceRepository.save(price1);
//        priceRepository.save(price2);

    }

}
