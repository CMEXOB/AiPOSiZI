package com.back.service;

import com.back.config.TokenConventor;
import com.back.entity.Product;
import com.back.entity.User;
import com.back.repository.ProductRepository;
import com.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenConventor tokenConventor;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Product product = productRepository.findById(productId).get();

        if(!user.getEmail().equals(product.getUser().getEmail())){
            throw new Exception();
        }

        return productRepository.findById(productId).get();
    }

    public void addProduct(Product product, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        product.setUser(user);
        productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product product, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Product updatedProduct = productRepository.findById(productId).get();

        if(!user.getEmail().equals(updatedProduct.getUser().getEmail())){
            throw new Exception();
        }

        updatedProduct.setName(product.getName());
        updatedProduct.setSort(product.getSort());
        updatedProduct.setProductGroup(product.getProductGroup());
        productRepository.save(updatedProduct);
        return updatedProduct;
    }

    public void deleteProduct(Long productId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Product deletedProduct = productRepository.findById(productId).get();

        if(!user.getEmail().equals(deletedProduct.getUser().getEmail())){
            throw new Exception();
        }
        try {
            productRepository.deleteById(productId);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Данный продукт является внешним ключом! Продукт можно будет удалить только после того, как будут удалены все связанные с ним цены");
        }
    }

    public boolean existsById(Long productId) {
        return productRepository.existsById(productId);
    }
}
