package com.factoryproduct.controller;

import com.factoryproduct.entity.Employee;
import com.factoryproduct.entity.Factory;
import com.factoryproduct.entity.Price;
import com.factoryproduct.entity.Product;
import com.factoryproduct.service.EmployeeService;
import com.factoryproduct.service.FactoryService;
import com.factoryproduct.service.PriceService;
import com.factoryproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    private EmployeeService employeeService;
    private FactoryService factoryService;


    @Autowired
    public EmployeeController(EmployeeService employeeService, FactoryService factoryService) {
        this.employeeService = employeeService;
        this.factoryService = factoryService;
    }



    @GetMapping
    public String getEmployees(Model model){
        List<Employee> employees = employeeService.getEmployees();
        model.addAttribute("employees", employees);
        return "employee/employees-list";
    }

    @GetMapping("add")
    public String addEmployee(Model model){
        List<Factory> factory = factoryService.getFactories();
        model.addAttribute("factories", factory);
        return "employee/employee-add";
    }

    @PostMapping("add")
    public String addEmployee(@RequestParam(required = false) Long factoryId,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String surname){
        employeeService.addEmployee(factoryService.getFactoryById(factoryId), name, surname);
        return "redirect:/employee";
    }

    @GetMapping("upd/{employeeId}")
    public String updateEmployee(@PathVariable("employeeId") Long employeeId, Model model){
        if(!employeeService.existsById(employeeId)){
            return "redirect:/employee";
        }
        List<Factory> factory = factoryService.getFactories();
        model.addAttribute("factories", factory);
        Employee employee = employeeService.getEmployeeById(employeeId);
        model.addAttribute("employee", employee);
        return "employee/employee-update";
    }

    @PostMapping("upd/{employeeId}")
    public String updateEmployee(@PathVariable("employeeId") Long employeeId,
                                 @RequestParam(required = false) Long factoryId,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String surname){
        employeeService.updateEmployee(employeeId, factoryService.getFactoryById(factoryId), name, surname);
        return "redirect:/employee";
    }

    @PostMapping("del/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") Long employeeId){
        employeeService.deleteEmployee(employeeId);
        return "redirect:/employee";
    }
}
