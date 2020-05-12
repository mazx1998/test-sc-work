package com.app.dto;

import java.util.UUID;

/**
 * @author Максим Зеленский
 */

public class ProvidedServiceMinDto {

    private UUID id;

    private String serviceName;

    private Long creationDateTime;

    private Integer serialNumber;

    public ProvidedServiceMinDto(String serviceName, Long creationDateTime,
                                 Integer serialNumber,  UUID id) {
        this.serviceName = serviceName;
        this.creationDateTime = creationDateTime;
        this.serialNumber = serialNumber;
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Long getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Long creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }
}
