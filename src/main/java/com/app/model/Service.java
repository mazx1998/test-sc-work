package com.app.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotBlank;

/**
 * @author Максим Зеленский
 */

@MappedSuperclass
public class Service extends BaseEntity{
    @Column(name = "name")
    @NotBlank(message = "Please provide a name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
