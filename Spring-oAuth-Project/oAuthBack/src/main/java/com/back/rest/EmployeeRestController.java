package com.back.rest;

import com.back.entity.Employee;
import com.back.entity.Factory;
import com.back.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("employee")
public class EmployeeRestController {

    private EmployeeService employeeService;


    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @CrossOrigin
    @GetMapping
    public List<Employee> getEmployees(){
        return employeeService.getEmployees();
    }

    @CrossOrigin
    @GetMapping("{factoryId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("factoryId") Long factoryId, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            Employee employee = employeeService.getEmployeeById(factoryId, token);
            return new ResponseEntity<Employee>(employee,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED,HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin
    @PostMapping("add")
    public HttpStatus addEmployee(@RequestParam(required = false) Long factoryId, @RequestBody Employee employee, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            employeeService.addEmployee(employee, factoryId, token);
        } catch (Exception e) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.CREATED;
    }

    @CrossOrigin
    @PutMapping("upd/{employeeId}")
    public HttpStatus updateEmployee(@PathVariable("employeeId") Long employeeId, @RequestParam(required = false) Long factoryId,
                                     @RequestBody Employee employee, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            employeeService.updateEmployee(employeeId, employee, factoryId, token);
        } catch (Exception e) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.OK;
    }

    @CrossOrigin
    @DeleteMapping("del/{employeeId}")
    public HttpStatus deleteEmployee(@PathVariable("employeeId") Long employeeId, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            employeeService.deleteEmployee(employeeId, token);
            return HttpStatus.NO_CONTENT;
        }
        catch (DataIntegrityViolationException e){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        catch (Exception e) {
            return HttpStatus.UNAUTHORIZED;
        }
    }
}
