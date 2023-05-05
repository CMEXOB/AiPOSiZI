package com.back.service;

import com.back.entity.Factory;
import com.back.repository.FactoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FactoryService {

    private FactoryRepository factoryRepository;

    @Autowired
    public FactoryService(FactoryRepository factoryRepository) {
        this.factoryRepository = factoryRepository;
    }

    public List<Factory> getFactories() {
        return factoryRepository.findAll();
    }
    public Factory getFactoryById(Long factoryId) {
        return factoryRepository.findById(factoryId).get();
    }

    public void addFactory(Factory factory)  {
        factoryRepository.save(factory);
    }

    public void updateFactory(Long factoryId, Factory factory) {
        Factory updatedFactory = factoryRepository.findById(factoryId).get();
        updatedFactory.setName(factory.getName());
        updatedFactory.setRegion(factory.getRegion());
        factoryRepository.save(updatedFactory);
    }

    public void deleteFactory(Long factoryId) {
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
