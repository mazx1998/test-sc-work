package com.app.model.dto;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author Максим Зеленский
 */
public class GetUserProvidedServicesDto extends GetProvidedServicesDto {

    @NotNull(message = "Please provide a user id")
    private UUID userId;

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
