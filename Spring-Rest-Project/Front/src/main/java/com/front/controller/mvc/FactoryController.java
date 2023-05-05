package com.front.controller.mvc;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("factory")
public class FactoryController {

    @Value("${rest.server.host}")
    private String basicUrl;

    @GetMapping
    public String getFactories(Model model){
        String getAll = basicUrl+"/factory";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Factory[]> response = restTemplate.getForEntity(getAll, Factory[].class);
        Factory[] factory = response.getBody();
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
        RestTemplate restTemplate = new RestTemplate();
        String add = basicUrl+"/factory/add";
        Factory factory = new Factory(name, region);
        ResponseEntity<HttpStatus> response = restTemplate.postForEntity(add, factory, HttpStatus.class);

        return "redirect:/factory";
    }

    @GetMapping("upd/{factoryId}")
    public String updateFactory(@PathVariable("factoryId") Long factoryId, Model model){
        String getId = basicUrl+"/factory/{id}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Factory> response = restTemplate.getForEntity(getId, Factory.class,factoryId);
        Factory factory = response.getBody();
        if(factory == null){
            return "redirect:/factory";
        }
        model.addAttribute("factory", factory);
        return "factory/factory-update";
    }

    @PostMapping("upd/{factoryId}")
    public String updateFactory(@PathVariable("factoryId") Long factoryId,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String region){
        String update = basicUrl+"/factory/upd/{id}";
        RestTemplate restTemplate = new RestTemplate();
        Factory factory = new Factory(name, region);
        HttpStatus status = restTemplate.exchange(update, HttpMethod.PUT, new HttpEntity<>(factory),HttpStatus.class, factoryId).getBody();
        return "redirect:/factory";
    }

    @PostMapping("del/{factoryId}")
    public String deleteFactory(@PathVariable("factoryId") Long factoryId, RedirectAttributes redirectAttributes){
        String delete = basicUrl+"/factory/del/{id}";
        RestTemplate restTemplate = new RestTemplate();
        HttpStatus status = restTemplate.exchange(delete, HttpMethod.DELETE, null,HttpStatus.class, factoryId).getBody();
        if(status.equals(HttpStatus.INTERNAL_SERVER_ERROR)){
            String mes = "Данная фабрика является внешним ключом! Фабрику можно будет удалить только после того, как будут удалены все связанные с ней цены";
            redirectAttributes.addAttribute("errorMessage",mes);
            return "redirect:/errormessage";
        }
        else {
            return "redirect:/factory";
        }
    }
}
