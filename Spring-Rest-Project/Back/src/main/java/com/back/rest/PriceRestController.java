package com.back.rest;

import com.back.entity.Price;
import com.back.service.PriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("price")
public class PriceRestController {

    private PriceService priceService;

    @Autowired
    public PriceRestController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping
    public List<Price> getPrices(){
        return priceService.getPrices();
    }

    @GetMapping("{factoryId}")
    public Price getPriceById(@PathVariable("factoryId") Long factoryId){
        return priceService.getPriceById(factoryId);
    }

    @PostMapping("add")
    public HttpStatus addPrice(@RequestParam Long productId, @RequestParam Long factoryId, @RequestBody Price price){
        priceService.addPrice(price, productId, factoryId);
        return HttpStatus.CREATED;
    }

    @PutMapping("upd/{priceId}")
    public HttpStatus updatePrice(@PathVariable("priceId") Long priceId, @RequestParam Long productId, @RequestParam Long factoryId, @RequestBody Price price){
        priceService.updatePrice(priceId, price, productId, factoryId);
        return HttpStatus.OK;
    }

    @DeleteMapping ("del/{priceId}")
    public HttpStatus deletePrice(@PathVariable("priceId") Long priceId){
        try {
            priceService.deletePrice(priceId);
            return HttpStatus.NO_CONTENT;
        }
        catch (Exception e) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
