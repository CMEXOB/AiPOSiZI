package com.back.rest;

import com.back.entity.Product;
import com.back.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("product")
public class ProductRestController {

    private ProductService productService;

    @Autowired
    public ProductRestController(ProductService productService) {
        this.productService = productService;
    }

    @CrossOrigin
    @GetMapping
    public List<Product> getProducts(){
        return productService.getProducts();
    }

    @CrossOrigin
    @GetMapping("{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Long productId, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            Product product = productService.getProductById(productId, token);
            return new ResponseEntity<Product>(product,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED,HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin
    @PostMapping("add")
    public HttpStatus addProduct(@RequestBody Product product, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            productService.addProduct(product, token);
        } catch (Exception e) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.CREATED;
    }

    @CrossOrigin
    @PutMapping("upd/{productId}")
    public HttpStatus updateProduct(@PathVariable("productId") Long productId, @RequestBody Product product, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            productService.updateProduct(productId, product, token);
        } catch (Exception e) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.OK;
    }

    @CrossOrigin
    @DeleteMapping("del/{productId}")
    public HttpStatus deleteProduct(@PathVariable("productId") Long productId, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            productService.deleteProduct(productId, token);
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
