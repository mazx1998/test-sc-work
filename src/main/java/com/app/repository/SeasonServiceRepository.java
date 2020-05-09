package com.app.repository;

import com.app.model.SeasonService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/**
 * @author Максим Зеленский
 */
public interface SeasonServiceRepository extends JpaRepository<SeasonService, UUID> {
    SeasonService findByName(String name);
}
