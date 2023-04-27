package com.factoryproduct.controller;

import com.factoryproduct.entity.Factory;
import com.factoryproduct.service.FactoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("factory")
public class FactoryController {

    private FactoryService factoryService;

    @Autowired
    public FactoryController(FactoryService factoryService) {
        this.factoryService = factoryService;
    }

    @GetMapping
    public String getFactories(Model model){
        List<Factory> factory = factoryService.getFactories();
        model.addAttribute("factories", factory);
        return "factory/factories-list";
    }

    @GetMapping("add")
    public String addFactory(){
        return "factory/factory-add";
    }

    @PostMapping("add")
    public String addFactory(@RequestParam(required = false) String name,
                             @RequestParam(required = false) String region){
        factoryService.addFactory(name, region);
        return "redirect:/factory";
    }

    @GetMapping("upd/{factoryId}")
    public String updateFactory(@PathVariable("factoryId") Long factoryId, Model model){
        if(!factoryService.existsById(factoryId)){
            return "redirect:/factory";
        }
        Factory updatedFactory = factoryService.getFactoryById(factoryId);
        model.addAttribute("factory", updatedFactory);
        return "factory/factory-update";
    }

    @PostMapping("upd/{factoryId}")
    public String updateFactory(@PathVariable("factoryId") Long factoryId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String region){
        factoryService.updateFactory(factoryId, name, region);
        return "redirect:/factory";
    }

    @PostMapping("del/{factoryId}")
    public String deleteFactory(@PathVariable("factoryId") Long factoryId, RedirectAttributes redirectAttributes){
        try {
            factoryService.deleteFactory(factoryId);
        }
        catch (Exception e){
            redirectAttributes.addAttribute("errorMessage",e.getMessage());
            return "redirect:/errormessage";
        }
        return "redirect:/factory";
    }
}
