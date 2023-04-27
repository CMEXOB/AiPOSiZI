package com.factoryproduct.controller;

import com.factoryproduct.entity.Factory;
import com.factoryproduct.entity.Price;
import com.factoryproduct.entity.Product;
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
@RequestMapping("price")
public class PriceController {

    private PriceService priceService;
    private FactoryService factoryService;
    private ProductService productService;

    @Autowired
    public PriceController(PriceService priceService, FactoryService factoryService, ProductService productService) {
        this.priceService = priceService;
        this.factoryService = factoryService;
        this.productService = productService;
    }

    @GetMapping
    public String getPrices(Model model){
        List<Price> prices = priceService.getPrices();
        model.addAttribute("prices", prices);
        return "price/prices-list";
    }

    @GetMapping("add")
    public String addPrice(Model model){
        List<Factory> factory = factoryService.getFactories();
        model.addAttribute("factories", factory);
        List<Product> product = productService.getProducts();
        model.addAttribute("products", product);
        return "price/price-add";
    }

    @PostMapping("add")
    public String addPrice(@RequestParam(required = false) Long factoryId,
                           @RequestParam(required = false) Long productId,
                           @RequestParam(required = false) Date date,
                           @RequestParam(required = false) Double purchasePrice,
                           @RequestParam(required = false) Double sellingPrice){
        priceService.addPrice(factoryService.getFactoryById(factoryId), productService.getProductById(productId),
                date, purchasePrice, sellingPrice);
        return "redirect:/price";
    }

    @GetMapping("upd/{priceId}")
    public String updatePrice(@PathVariable("priceId") Long priceId, Model model){
        if(!priceService.existsById(priceId)){
            return "redirect:/price";
        }
        Price updatedPrice = priceService.getPriceById(priceId);
        model.addAttribute("price", updatedPrice);
        List<Factory> factory = factoryService.getFactories();
        model.addAttribute("factories", factory);
        List<Product> product = productService.getProducts();
        model.addAttribute("products", product);
        return "price/price-update";
    }

    @PostMapping("upd/{priceId}")
    public String updatePrice(@PathVariable("priceId") Long priceId,
                           @RequestParam(required = false) Long factoryId,
                           @RequestParam(required = false) Long productId,
                           @RequestParam(required = false) Date date,
                           @RequestParam(required = false) Double purchasePrice,
                           @RequestParam(required = false) Double sellingPrice){
        priceService.updatePrice(priceId, factoryId, productId, date, purchasePrice, sellingPrice);
        return "redirect:/price";
    }

    @PostMapping("del/{priceId}")
    public String deletePrice(@PathVariable("priceId") Long priceId){
        priceService.deletePrice(priceId);
        return "redirect:/price";
    }
}
