package com.front.controller.mvc;

import com.front.entity.Employee;
import com.front.entity.Factory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("employee")
public class EmployeeController {

    @Value("${rest.server.host}")
    private String basicUrl;

    @GetMapping
    public String getEmployees(Model model){
        String getAll = basicUrl+"/employee";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Employee[]> response = restTemplate.getForEntity(getAll, Employee[].class);
        Employee[] employees = response.getBody();
        model.addAttribute("employees", employees);
        return "employee/employees-list";
    }

    @GetMapping("add")
    public String addEmployee(Model model){
        String getAll = basicUrl+"/factory";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Factory[]> response = restTemplate.getForEntity(getAll, Factory[].class);
        Factory[] factory = response.getBody();
        model.addAttribute("factories", factory);
        return "employee/employee-add";
    }

    @PostMapping("add")
    public String addEmployee(@RequestParam(required = false) Long factoryId,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String surname){
        RestTemplate restTemplate = new RestTemplate();
        String add = basicUrl+"/employee/add?factoryId={factoryId}";
        Employee employee = new Employee(name, surname);

        Map<String, String> params = new HashMap<>();
        params.put("factoryId",factoryId.toString());
        ResponseEntity<HttpStatus> response = restTemplate.postForEntity(add, employee, HttpStatus.class, params);

        return "redirect:/employee";
    }

    @GetMapping("upd/{employeeId}")
    public String updateEmployee(@PathVariable("employeeId") Long employeeId, Model model){
        String getId = basicUrl+"/employee/{id}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Employee> response = restTemplate.getForEntity(getId, Employee.class, employeeId);
        Employee employee = response.getBody();
        if(employee == null){
            return "redirect:/employee";
        }
        model.addAttribute("employee", employee);

        String getAll = basicUrl+"/factory";
        ResponseEntity<Factory[]> responseFactory = restTemplate.getForEntity(getAll, Factory[].class);
        Factory[] factory = responseFactory.getBody();
        model.addAttribute("factories", factory);
        return "employee/employee-update";
    }

    @PostMapping("upd/{employeeId}")
    public String updateEmployee(@PathVariable("employeeId") Long employeeId,
                                 @RequestParam(required = false) Long factoryId,
                                 @RequestParam(required = false) String name,
                                 @RequestParam(required = false) String surname){
        String update = basicUrl+"/employee/upd/{id}?factoryId={factoryId}";
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("id",employeeId.toString());
        params.put("factoryId",factoryId.toString());
        Employee employee = new Employee(name, surname);
        HttpStatus status = restTemplate.exchange(update, HttpMethod.PUT, new HttpEntity<>(employee),HttpStatus.class, params).getBody();

        return "redirect:/employee";
    }

    @PostMapping("del/{employeeId}")
    public String deleteEmployee(@PathVariable("employeeId") Long employeeId){
        String delete = basicUrl+"/employee/del/{id}";
        RestTemplate restTemplate = new RestTemplate();
        HttpStatus status = restTemplate.exchange(delete, HttpMethod.DELETE, null,HttpStatus.class, employeeId).getBody();
        return "redirect:/employee";
    }
}
