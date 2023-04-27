package com.factoryproduct.repository;

import com.factoryproduct.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT pr FROM Product pr WHERE pr.name = ?1")
    Product findProductByName(String userName) ;
}
