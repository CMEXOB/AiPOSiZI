package com.back.service;

import com.back.config.TokenConventor;
import com.back.entity.Factory;
import com.back.entity.Product;
import com.back.entity.User;
import com.back.repository.FactoryRepository;
import com.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryService {

    private FactoryRepository factoryRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenConventor tokenConventor;

    @Autowired
    public FactoryService(FactoryRepository factoryRepository) {
        this.factoryRepository = factoryRepository;
    }

    public List<Factory> getFactories() {
        return factoryRepository.findAll();
    }
    public Factory getFactoryById(Long factoryId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Factory factory = factoryRepository.findById(factoryId).get();

        if(!user.getEmail().equals(factory.getUser().getEmail())){
            throw new Exception();
        }
        return factoryRepository.findById(factoryId).get();
    }

    public void addFactory(Factory factory, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        factory.setUser(user);
        factoryRepository.save(factory);
    }

    public void updateFactory(Long factoryId, Factory factory, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Factory updatedFactory = factoryRepository.findById(factoryId).get();

        if(!user.getEmail().equals(updatedFactory.getUser().getEmail())){
            throw new Exception();
        }
        updatedFactory.setName(factory.getName());
        updatedFactory.setRegion(factory.getRegion());
        factoryRepository.save(updatedFactory);
    }

    public void deleteFactory(Long factoryId, String token) throws Exception {
        String userEmail = tokenConventor.getUserEmail(token);
        if(!userRepository.existsByEmail(userEmail)){
            throw new Exception();
        }
        User user = userRepository.findUserByEmail(userEmail);
        Factory deletedFactory = factoryRepository.findById(factoryId).get();

        if(!user.getEmail().equals(deletedFactory.getUser().getEmail())){
            throw new Exception();
        }
        try {
            factoryRepository.deleteById(factoryId);
        }
        catch (DataIntegrityViolationException e){
            throw new DataIntegrityViolationException("Данный фабрика является внешним ключом! Фабрика можно будет удалить только после того, как будут удалены все связанные с ним цены");
        }
    }

    public boolean existsById(Long factoryId) {
        return factoryRepository.existsById(factoryId);
    }
}
