package com.back.service;

import com.back.config.TokenConventor;
import com.back.entity.Employee;
import com.back.entity.Factory;
import com.back.entity.User;
import com.back.repository.EmployeeRepository;
import com.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private EmployeeRepository employeeRepository;
    private FactoryService factoryService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenConventor tokenConventor;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository, FactoryService factoryService) {
        this.employeeRepository = employeeRepository;
        this.factoryService = factoryService;
    }

    public List<Employee> getEmployees() {
        return employeeRepository.findAll();
    }
    public Employee getEmployeeById(Long employeeId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Employee employee = employeeRepository.findById(employeeId).get();

        if(!user.getEmail().equals(employee.getUser().getEmail())){
            throw new Exception();
        }

        return employeeRepository.findById(employeeId).get();
    }

    public void addEmployee(Employee employee, Long factoryId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        employee.setUser(user);

        employee.setFactory(factoryService.getFactoryById(factoryId, token));
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Employee deletedEmployee = employeeRepository.findById(employeeId).get();

        if(!user.getEmail().equals(deletedEmployee.getUser().getEmail())){
            throw new Exception();
        }

        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Long employeeId, Employee employee, Long factoryId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Employee updatedEmployee = employeeRepository.findById(employeeId).get();

        if(!user.getEmail().equals(updatedEmployee.getUser().getEmail())){
            throw new Exception();
        }

        updatedEmployee.setFactory(factoryService.getFactoryById(factoryId, token));
        updatedEmployee.setName(employee.getName());
        updatedEmployee.setSurname(employee.getSurname());
        employeeRepository.save(updatedEmployee);
    }

    public boolean existsById(Long employeeId) {
        return employeeRepository.existsById(employeeId);
    }
}
