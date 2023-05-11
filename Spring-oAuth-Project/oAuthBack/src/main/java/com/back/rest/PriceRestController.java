package com.back.rest;

import com.back.entity.Employee;
import com.back.entity.Price;
import com.back.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("price")
public class PriceRestController {

    private PriceService priceService;

    @Autowired
    public PriceRestController(PriceService priceService) {
        this.priceService = priceService;
    }

    @CrossOrigin
    @GetMapping
    public List<Price> getPrices(){
        return priceService.getPrices();
    }

    @CrossOrigin
    @GetMapping("{priceId}")
    public ResponseEntity<?> getPriceById(@PathVariable("priceId") Long priceId, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            Price price = priceService.getPriceById(priceId, token);
            return new ResponseEntity<Price>(price,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<HttpStatus>(HttpStatus.UNAUTHORIZED,HttpStatus.UNAUTHORIZED);
        }
    }

    @CrossOrigin
    @PostMapping("add")
    public HttpStatus addPrice(@RequestParam Long productId, @RequestParam Long factoryId, @RequestBody Price price, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            priceService.addPrice(price, productId, factoryId, token);
        } catch (Exception e) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.CREATED;
    }

    @CrossOrigin
    @PutMapping("upd/{priceId}")
    public HttpStatus updatePrice(@PathVariable("priceId") Long priceId, @RequestParam Long productId, @RequestParam Long factoryId,
                                  @RequestBody Price price, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            priceService.updatePrice(priceId, price, productId, factoryId, token);
        } catch (Exception e) {
            return HttpStatus.UNAUTHORIZED;
        }
        return HttpStatus.OK;
    }

    @CrossOrigin
    @DeleteMapping ("del/{priceId}")
    public HttpStatus deletePrice(@PathVariable("priceId") Long priceId, HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization");

            priceService.deletePrice(priceId, token);
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
