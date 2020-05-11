package com.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author Максим Зеленский
 */

public class ProvidedServiceDto {

    @NotBlank(message = "Please provide a service name")
    private String ServiceName;

    @NotNull(message = "Please provide a user login")
    private String userLogin;

    @NotNull(message = "Please provide a creation date time")
    private long creationDateTime;

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public long getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(long creationDateTime) {
        this.creationDateTime = creationDateTime;
    }
}
