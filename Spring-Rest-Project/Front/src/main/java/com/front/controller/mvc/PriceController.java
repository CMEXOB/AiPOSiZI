package com.front.controller.mvc;

import com.front.entity.Factory;
import com.front.entity.Price;
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

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("price")
public class PriceController {

    @Value("${rest.server.host}")
    private String basicUrl;

    @GetMapping
    public String getPrices(Model model){
        String getAll = basicUrl+"/price";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Price[]> response = restTemplate.getForEntity(getAll, Price[].class);
        Price[] prices = response.getBody();
        model.addAttribute("prices", prices);
        return "price/prices-list";
    }

    @GetMapping("add")
    public String addPrice(Model model){
        String getAll = basicUrl+"/factory";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Factory[]> response = restTemplate.getForEntity(getAll, Factory[].class);
        Factory[] factory = response.getBody();
        model.addAttribute("factories", factory);

        String getAllProduct = basicUrl+"/product";
        ResponseEntity<Product[]> responseProduct = restTemplate.getForEntity(getAllProduct, Product[].class);
        Product[] products = responseProduct.getBody();
        model.addAttribute("products", products);
        return "price/price-add";
    }

    @PostMapping("add")
    public String addPrice(@RequestParam(required = false) Long factoryId,
                           @RequestParam(required = false) Long productId,
                           @RequestParam(required = false) Date date,
                           @RequestParam(required = false) Double purchasePrice,
                           @RequestParam(required = false) Double sellingPrice){
        RestTemplate restTemplate = new RestTemplate();
        String add = basicUrl+"/price/add?factoryId={factoryId}&productId={productId}";
        Map<String, String> params = new HashMap<>();
        params.put("factoryId",factoryId.toString());
        params.put("productId",productId.toString());

        Price price = new Price(date, purchasePrice, sellingPrice);
        ResponseEntity<HttpStatus> response = restTemplate.postForEntity(add, price, HttpStatus.class, params);


        return "redirect:/price";
    }

    @GetMapping("upd/{priceId}")
    public String updatePrice(@PathVariable("priceId") Long priceId, Model model){
        String getId = basicUrl+"/price/{id}";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Price> response = restTemplate.getForEntity(getId, Price.class,priceId);
        Price price = response.getBody();
        if(price == null){
            return "redirect:/price";
        }
        model.addAttribute("price", price);

        String getAll = basicUrl+"/factory";
        ResponseEntity<Factory[]> responseFactory = restTemplate.getForEntity(getAll, Factory[].class);
        Factory[] factory = responseFactory.getBody();
        model.addAttribute("factories", factory);

        String getAllProduct = basicUrl+"/product";
        ResponseEntity<Product[]> responseProduct = restTemplate.getForEntity(getAllProduct, Product[].class);
        Product[] products = responseProduct.getBody();
        model.addAttribute("products", products);
        return "price/price-update";
    }

    @PostMapping("upd/{priceId}")
    public String updatePrice(@PathVariable("priceId") Long priceId,
                           @RequestParam(required = false) Long factoryId,
                           @RequestParam(required = false) Long productId,
                           @RequestParam(required = false) Date date,
                           @RequestParam(required = false) Double purchasePrice,
                           @RequestParam(required = false) Double sellingPrice){
        RestTemplate restTemplate = new RestTemplate();
        String update = basicUrl+"/price/upd/{id}?factoryId={factoryId}&productId={productId}";
        Map<String, String> params = new HashMap<>();
        params.put("id",priceId.toString());
        params.put("productId",productId.toString());
        params.put("factoryId",factoryId.toString());
        Price price = new Price(date, purchasePrice, sellingPrice);
        HttpStatus status = restTemplate.exchange(update, HttpMethod.PUT, new HttpEntity<>(price),HttpStatus.class, params).getBody();

        return "redirect:/price";
    }

    @PostMapping("del/{priceId}")
    public String deletePrice(@PathVariable("priceId") Long priceId){
        String delete = basicUrl+"/price/del/{id}";
        RestTemplate restTemplate = new RestTemplate();
        HttpStatus status = restTemplate.exchange(delete, HttpMethod.DELETE, null,HttpStatus.class, priceId).getBody();

        return "redirect:/price";
    }
}
