package com.factoryproduct.service;

import com.factoryproduct.entity.Factory;
import com.factoryproduct.repository.FactoryRepository;
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

    public void addFactory(String name, String region)  {
        Factory factory = new Factory();
        factory.setName(name);
        factory.setRegion(region);
        factoryRepository.save(factory);
    }

    public void updateFactory(Long factoryId, String name, String region) {
        Factory updatedFactory = factoryRepository.findById(factoryId).get();
        updatedFactory.setName(name);
        updatedFactory.setRegion(region);
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
