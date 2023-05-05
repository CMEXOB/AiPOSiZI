package com.back.service;

import com.back.entity.Product;
import com.back.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId).get();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public Product updateProduct(Long productId, Product product) {
        Product updatedProduct = productRepository.findById(productId).get();
        updatedProduct.setName(product.getName());
        updatedProduct.setSort(product.getSort());
        updatedProduct.setProductGroup(product.getProductGroup());
        productRepository.save(updatedProduct);
        return updatedProduct;
    }

    public void deleteProduct(Long productId) {
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
