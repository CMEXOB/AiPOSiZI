package com.back.rest;

import com.back.entity.Product;
import com.back.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("product")
public class ProductRestController {

    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @GetMapping("{productId}")
    public Product getProductById(@PathVariable("productId") Long productId){
        return productService.getProductById(productId);
    }

    @PostMapping("add")
    public HttpStatus addProduct(@RequestBody Product product){
        productService.addProduct(product);
        return HttpStatus.CREATED;
    }

    @PutMapping("upd/{productId}")
    public HttpStatus updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product){
        productService.updateProduct(productId, product);
        return HttpStatus.OK;
    }

    @DeleteMapping("del/{productId}")
    public HttpStatus deleteProduct(@PathVariable("productId") Long productId){
        try {
            productService.deleteProduct(productId);
            return HttpStatus.NO_CONTENT;
        }
        catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
