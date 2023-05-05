package com.back.service;

import com.back.entity.Employee;
import com.back.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private FactoryService factoryService;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, FactoryService factoryService) {
        this.employeeRepository = employeeRepository;
        this.factoryService = factoryService;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(Long employeeId) {
        return employeeRepository.findById(employeeId).get();
    }

    public void addEmployee(Employee employee, Long factoryId) {
        employee.setFactory(factoryService.getFactoryById(factoryId));
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Long employeeId, Employee employee, Long factoryId) {
        Employee updatedEmployee = employeeRepository.findById(employeeId).get();

        updatedEmployee.setFactory(factoryService.getFactoryById(factoryId));
        updatedEmployee.setName(employee.getName());
        updatedEmployee.setSurname(employee.getSurname());
        employeeRepository.save(updatedEmployee);
    }

    public boolean existsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}
