package com.app.repository;

import com.app.model.ProvidedService;
import com.app.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

/**
 * @author Максим Зеленский
 */
public interface ProvidedServiceRepository extends JpaRepository<ProvidedService, UUID> {
    List<ProvidedService> findByUser(User user, Pageable pageable);
}
