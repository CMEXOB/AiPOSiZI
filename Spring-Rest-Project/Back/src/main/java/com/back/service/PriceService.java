package com.back.service;

import com.back.entity.Factory;
import com.back.entity.Price;
import com.back.entity.Product;
import com.back.repository.FactoryRepository;
import com.back.repository.PriceRepository;
import com.back.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    private PriceRepository priceRepository;
    private FactoryRepository factoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public PriceService(PriceRepository priceRepository, FactoryRepository factoryRepository,
                        ProductRepository productRepository) {
        this.priceRepository = priceRepository;
        this.factoryRepository = factoryRepository;
        this.productRepository = productRepository;
    }

    public List<Price> getPrices() {
        return priceRepository.findAll();
    }

    public Price getPriceById(Long priceId){
        return priceRepository.findById(priceId).get();
    }

    public void addPrice(Price price, Long productId, Long factoryId) {
        price.setFactory(factoryRepository.findById(factoryId).get());
        price.setProduct( productRepository.findById(productId).get());
        priceRepository.save(price);
    }

    public void deletePrice(Long priceId) {
        priceRepository.deleteById(priceId);
    }

    public void updatePrice(Long priceId, Price price, Long productId, Long factoryId) {
        Price updatedPrice = priceRepository.findById(priceId).get();
        Factory factory = factoryRepository.findById(factoryId).get();
        Product product = productRepository.findById(productId).get();

        updatedPrice.setFactory(factory);
        updatedPrice.setProduct(product);
        updatedPrice.setDate(price.getDate());
        updatedPrice.setPurchasePrice(price.getPurchasePrice());
        updatedPrice.setSellingPrice(price.getSellingPrice());
        priceRepository.save(updatedPrice);
    }

    public boolean existsById(Long priceId) {
        return priceRepository.existsById(priceId);
    }
}
