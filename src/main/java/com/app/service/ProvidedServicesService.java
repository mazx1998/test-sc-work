package com.app.service;

import com.app.model.ProvidedService;
import com.app.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

/**
 * @author Максим Зеленский
 */

public interface ProvidedServicesService {
    List<ProvidedService> findAll(Pageable pageable);

    List<ProvidedService> findByUser(User user, Pageable pageable);

    ProvidedService create(ProvidedService providedService);

    ProvidedService findById(UUID id);
}
