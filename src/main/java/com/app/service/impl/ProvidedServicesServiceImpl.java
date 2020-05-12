package com.app.service.impl;

import com.app.model.ProvidedService;
import com.app.model.User;
import com.app.repository.ProvidedServiceRepository;
import com.app.service.ProvidedServicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Максим Зеленский
 */

@Service
public class ProvidedServicesServiceImpl implements ProvidedServicesService {
    private Logger log = LoggerFactory.getLogger(ProvidedServicesServiceImpl.class);

    private ProvidedServiceRepository providedServiceRepository;

    @Autowired
    public ProvidedServicesServiceImpl(ProvidedServiceRepository providedServiceRepository) {
        this.providedServiceRepository = providedServiceRepository;
    }

    @Override
    public List<ProvidedService> findAll(Pageable pageable) {
        log.info("IN findAll: finding providing services with offset: {} , page size: {}, sort: {}",
                pageable.getOffset(), pageable.getPageSize(), pageable.getSort());
        return providedServiceRepository.findAll(pageable).getContent();
    }

    @Override
    public List<ProvidedService> findByUser(User user, Pageable pageable) {
        log.info("IN findByUser: finding providing services with offset: {} " +
                        ", page size: {}, sort: {} and user {}",
                pageable.getOffset(), pageable.getPageSize(),
                pageable.getSort(), user.getLogin());
        return providedServiceRepository.findByUser(user, pageable);
    }

    @Override
    public ProvidedService create(ProvidedService providedService) {
        log.info("IN create: creating providedService {}", providedService.getService().getName());
        return providedServiceRepository.save(providedService);
    }

    @Override
    public ProvidedService findById(UUID id) {
        log.info("IN getById: getting user by id {}", id);
        return providedServiceRepository.findById(id).orElse(null);
    }
}
