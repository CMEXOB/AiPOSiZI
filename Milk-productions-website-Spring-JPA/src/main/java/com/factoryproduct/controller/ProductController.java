package com.factoryproduct.controller;

import com.factoryproduct.entity.Product;
import com.factoryproduct.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("product")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getProducts(Model model){
        List<Product> products = productService.getProducts();
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
        productService.addProduct(name, sort, productGroup);
        return "redirect:/product";
    }

    @GetMapping("upd/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId, Model model){
        if(!productService.existsById(productId)){
            return "redirect:/product";
        }
        Product updatedProduct = productService.getProductById(productId);

        model.addAttribute("product", updatedProduct);
        return "product/product-update";
    }

    @PostMapping("upd/{productId}")
    public String updateProduct(@PathVariable("productId") Long productId,
                           @RequestParam(required = false) String name,
                           @RequestParam(required = false) String sort,
                           @RequestParam(required = false) String productGroup){
        productService.updateProduct(productId, name, sort, productGroup);
        return "redirect:/product";
    }

    @PostMapping("del/{productId}")
    public String deleteProduct(@PathVariable("productId") Long productId, RedirectAttributes redirectAttributes){
        try {
            productService.deleteProduct(productId);
        }
        catch (Exception e){
            redirectAttributes.addAttribute("errorMessage",e.getMessage());
            return "redirect:/errormessage";
        }
        return "redirect:/product";
    }
}
