package com.front.controller.mvc;

import com.front.entity.Product;
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
@RequestMapping("product")
public class ProductController {

    @Value("${rest.server.host}")
    private String basicUrl;

    @GetMapping
    public String getProducts(Model model){
        String getAll = basicUrl+"/product";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product[]> response = restTemplate.getForEntity(getAll, Product[].class);
        Product[] products = response.getBody();
        model.addAttribute("products", products);
        return "product/products-list";
    }

    @GetMapping("add")
    public String addProduct(){
        return "product/product-add";
    }

    @PostMapping("add")
    public String addProduct(@RequestParam String name,
                             @RequestParam String sort,
                             @RequestParam String productGroup){
        RestTemplate restTemplate = new RestTemplate();
        String add = basicUrl+"/product/add";
        Product product = new Product(name, sort, productGroup);
        ResponseEntity<HttpStatus> response = restTemplate.postForEntity(add, product, HttpStatus.class);
        System.out.println(response.getBody().name());
        return "redirect:/product";
    }

    @GetMapping("upd/{productId}")
    public String updateFactory(@PathVariable("productId") Long productId, Model model){
        String getId = basicUrl+"/product/{id}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> response = restTemplate.getForEntity(getId, Product.class,productId);
        Product products = response.getBody();
        if(products == null){
            return "redirect:/product";
        }
        model.addAttribute("product", products);
        return "product/product-update";
    }

    @PostMapping("upd/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String sort,
                           @RequestParam(required = false) String productGroup){
        String update = basicUrl+"/product/upd/{id}";
        RestTemplate restTemplate = new RestTemplate();
        Product product = new Product(name, sort, productGroup);
        HttpStatus status = restTemplate.exchange(update, HttpMethod.PUT, new HttpEntity<>(product),HttpStatus.class, productId).getBody();
        return "redirect:/product";
    }

    @PostMapping("del/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId, RedirectAttributes redirectAttributes){
        String delete = basicUrl+"/product/del/{id}";
        RestTemplate restTemplate = new RestTemplate();
        HttpStatus status = restTemplate.exchange(delete, HttpMethod.DELETE, null,HttpStatus.class, productId).getBody();
        if(status.equals(HttpStatus.INTERNAL_SERVER_ERROR)){
            String mes = "Данный продукт является внешним ключом! Продукт можно будет удалить только после того, как будут удалены все связанные с ним цены";
            redirectAttributes.addAttribute("errorMessage",mes);
            return "redirect:/errormessage";
        }
        else {
            return "redirect:/product";
        }
    }
}
