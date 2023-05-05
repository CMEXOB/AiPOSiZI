package com.back.rest;

import com.back.entity.Factory;
import com.back.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("factory")
public class FactoryRestController {

    private FactoryService factoryService;

    @Autowired
    public FactoryRestController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    @GetMapping
    public List<Factory> getFactories(){
        return factoryService.getFactories();
    }

    @GetMapping("{factoryId}")
    public Factory getFactoryById(@PathVariable("factoryId") Long factoryId){
        return factoryService.getFactoryById(factoryId);
    }

    @PostMapping("add")
    public HttpStatus addFactory(@RequestBody Factory factory){
        factoryService.addFactory(factory);
        return HttpStatus.CREATED;
    }

    @PutMapping("upd/{factoryId}")
    public HttpStatus updateFactory(@PathVariable("factoryId") Long factoryId, @RequestBody Factory factory){
        factoryService.updateFactory(factoryId, factory);
        return HttpStatus.OK;
    }

    @DeleteMapping("del/{factoryId}")
    public HttpStatus deleteFactory(@PathVariable("factoryId") Long factoryId){
        try {
            factoryService.deleteFactory(factoryId);
            return HttpStatus.NO_CONTENT;
        }
        catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
