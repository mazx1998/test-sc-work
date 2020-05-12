package com.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Максим Зеленский
 */

public class ToProvideServiceDto {

    @NotBlank(message = "Please provide a service name")
    private String serviceName;

    @NotNull(message = "Please provide a user id")
    private UUID userId;

    @NotNull(message = "Please provide a creation date time")
    private long creationDateTime;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public long getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(long creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
