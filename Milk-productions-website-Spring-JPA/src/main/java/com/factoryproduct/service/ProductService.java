package com.factoryproduct.service;

import com.factoryproduct.entity.Product;
import com.factoryproduct.repository.ProductRepository;
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

    public void addProduct(String name, String sort, String productGroup) {
        Product product = new Product();
        product.setName(name);
        product.setSort(sort);
        product.setProductGroup(productGroup);
        productRepository.save(product);
    }

    public void updateProduct(Long productId, String name, String sort, String productGroup) {
        Product updatedProduct = productRepository.findById(productId).get();
        updatedProduct.setName(name);
        updatedProduct.setSort(sort);
        updatedProduct.setProductGroup(productGroup);
        productRepository.save(updatedProduct);
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
