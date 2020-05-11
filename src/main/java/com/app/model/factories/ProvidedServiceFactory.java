package com.app.model.factories;

import com.app.model.ProvidedService;
import com.app.model.SeasonService;
import com.app.model.User;

import java.sql.Timestamp;

/**
 * @author Максим Зеленский
 */

public final class ProvidedServiceFactory {

    public static ProvidedService create(SeasonService service,
                                         User user,
                                         Timestamp creationDate,
                                         Timestamp provisionDate,
                                         Integer serialNumber) {
        ProvidedService providedService = new ProvidedService();
        providedService.setService(service);
        providedService.setUser(user);
        providedService.setCreationDate(creationDate);
        providedService.setProvisionDate(provisionDate);
        providedService.setSerialNumber(serialNumber);
        return providedService;
    }
}
