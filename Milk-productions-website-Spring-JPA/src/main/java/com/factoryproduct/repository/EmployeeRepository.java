package com.factoryproduct.repository;

import com.factoryproduct.entity.Employee;
import com.factoryproduct.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
