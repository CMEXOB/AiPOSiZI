package com.back.rest;

import com.back.entity.Employee;
import com.back.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeRestController {

    private EmployeeService employeeService;


    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getEmployees(){
        return employeeService.getEmployees();
    }

    @GetMapping("{factoryId}")
    public Employee getEmployeeById(@PathVariable("factoryId") Long factoryId){
        return employeeService.getEmployeeById(factoryId);
    }

    @PostMapping("add")
    public HttpStatus addEmployee(@RequestParam(required = false) Long factoryId, @RequestBody Employee employee){
        employeeService.addEmployee(employee, factoryId);
        return HttpStatus.CREATED;
    }

    @PutMapping("upd/{employeeId}")
    public HttpStatus updateEmployee(@PathVariable("employeeId") Long employeeId, @RequestParam(required = false) Long factoryId, @RequestBody Employee employee){
        employeeService.updateEmployee(employeeId, employee, factoryId);
        return HttpStatus.OK;
    }

    @DeleteMapping("del/{employeeId}")
    public HttpStatus deleteEmployee(@PathVariable("employeeId") Long employeeId){
        try {
            employeeService.deleteEmployee(employeeId);
            return HttpStatus.NO_CONTENT;
        }
        catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
