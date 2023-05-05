package com.back.repository;

import com.back.entity.Factory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FactoryRepository extends JpaRepository<Factory, Long> {

    @Query("SELECT f FROM Factory f WHERE f.name = ?1")
    Factory findFactoryByName(String userName) ;

}
