package com.back.rest;

import com.back.entity.Factory;
import com.back.entity.Product;
import com.back.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("factory")
public class FactoryRestController {

    private FactoryService factoryService;

    @Autowired
    public FactoryRestController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    @CrossOrigin
    @GetMapping
    public List<Factory> getFactories(){
        return factoryService.getFactories();
    }

    @CrossOrigin
    @GetMapping("{factoryId}")
    public ResponseEntity<?> getFactoryById(@PathVariable("factoryId") Long factoryId, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            Factory factory = factoryService.getFactoryById(factoryId, token);
            return new ResponseEntity<Factory>(factory,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED,HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin
    @PostMapping("add")
    public HttpStatus addFactory(@RequestBody Factory factory, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            factoryService.addFactory(factory, token);
        } catch (Exception e) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.CREATED;
    }

    @CrossOrigin
    @PutMapping("upd/{factoryId}")
    public HttpStatus updateFactory(@PathVariable("factoryId") Long factoryId, @RequestBody Factory factory, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            factoryService.updateFactory(factoryId, factory, token);
        } catch (Exception e) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.OK;
    }

    @CrossOrigin
    @DeleteMapping("del/{factoryId}")
    public HttpStatus deleteFactory(@PathVariable("factoryId") Long factoryId, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            factoryService.deleteFactory(factoryId, token);
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
