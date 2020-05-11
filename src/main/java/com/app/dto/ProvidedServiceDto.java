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

}
