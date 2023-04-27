package com.factoryproduct.service;

import com.factoryproduct.entity.Employee;
import com.factoryproduct.entity.Factory;
import com.factoryproduct.entity.Employee;
import com.factoryproduct.entity.Product;
import com.factoryproduct.repository.EmployeeRepository;
import com.factoryproduct.repository.FactoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    public void addEmployee(Factory factory, String name, String surname) {
        Employee employee = new Employee();

        employee.setFactory(factory);
        employee.setName(name);
        employee.setSurname(surname);

        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Long employeeId, Factory factory, String name, String surname) {
        Employee updatedEmployee = employeeRepository.findById(employeeId).get();

        updatedEmployee.setFactory(factory);
        updatedEmployee.setName(name);
        updatedEmployee.setSurname(surname);
        employeeRepository.save(updatedEmployee);
    }

    public boolean existsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}
