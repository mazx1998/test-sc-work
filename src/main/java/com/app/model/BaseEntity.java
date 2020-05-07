package com.app.model;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Максим Зеленский
 */

@MappedSuperclass
public class BaseEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
