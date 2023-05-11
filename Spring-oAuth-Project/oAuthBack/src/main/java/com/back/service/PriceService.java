package com.back.service;

import com.back.config.TokenConventor;
import com.back.entity.*;
import com.back.repository.FactoryRepository;
import com.back.repository.PriceRepository;
import com.back.repository.ProductRepository;
import com.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceService {

    private PriceRepository priceRepository;
    private FactoryService factoryService;
    private ProductService productService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenConventor tokenConventor;

    @Autowired
    public PriceService(PriceRepository priceRepository, FactoryService factoryService,
                        ProductService productService) {
        this.priceRepository = priceRepository;
        this.factoryService = factoryService;
        this.productService = productService;
    }

    public List<Price> getPrices() {
        return priceRepository.findAll();
    }

    public Price getPriceById(Long priceId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Price price = priceRepository.findById(priceId).get();

        if(!user.getEmail().equals(price.getUser().getEmail())){
            throw new Exception();
        }

        return priceRepository.findById(priceId).get();
    }

    public void addPrice(Price price, Long productId, Long factoryId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        price.setUser(user);

        price.setFactory(factoryService.getFactoryById(factoryId, token));
        price.setProduct(productService.getProductById(productId, token));
        priceRepository.save(price);
    }

    public void deletePrice(Long priceId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Price deletedPrice = priceRepository.findById(priceId).get();

        if(!user.getEmail().equals(deletedPrice.getUser().getEmail())){
            throw new Exception();
        }

        priceRepository.deleteById(priceId);
    }

    public void updatePrice(Long priceId, Price price, Long productId, Long factoryId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Price updatedPrice = priceRepository.findById(priceId).get();

        if(!user.getEmail().equals(updatedPrice.getUser().getEmail())){
            throw new Exception();
        }

        Factory factory = factoryService.getFactoryById(factoryId, token);
        Product product = productService.getProductById(productId, token);

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
