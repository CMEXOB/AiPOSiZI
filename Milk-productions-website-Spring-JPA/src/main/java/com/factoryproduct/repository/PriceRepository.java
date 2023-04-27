package com.factoryproduct.repository;

import com.factoryproduct.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    @Query("SELECT pr FROM Price pr WHERE pr.date between ?1 and ?2 and pr.product.id in(SELECT p.id FROM Product p WHERE p.name = ?3)")
    List<Price> findPriceByProductName(Date dateAfter, Date dateBefore, String productName) ;

    @Query("SELECT pr FROM Price pr WHERE pr.date = ?1 and pr.factory.id in(SELECT f.id FROM Factory f WHERE f.name = ?2)")
    List<Price> findPriceByFactoryName(Date date, String factoryName) ;

    @Query("SELECT AVG(pr.purchasePrice), AVG(pr.sellingPrice) FROM Price pr WHERE pr.date = ?1 and pr.factory.id in(SELECT f.id FROM Factory f WHERE f.region = ?2)")
    List<List<Object>> findAvgPriceByFactoryRegion(Date date, String factoryRegion) ;
}
