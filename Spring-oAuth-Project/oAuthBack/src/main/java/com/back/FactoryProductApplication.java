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
//        ProductRepository productRepository = ctx.getBean(ProductRepository.class);
//
//        Product product1 = new Product("Сырок с клубникой","1123","Сырок");
//
//        Product product2 = new Product();
//        product2.setName("Пармезан");
//        product2.setSort("12312323");
//        product2.setProductGroup("Сыр");
//
//        productRepository.save(product1);
//        productRepository.save(product2);


    }

}
