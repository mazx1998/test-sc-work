package com.app.rest;

import com.app.model.ProvidedService;
import com.app.model.SeasonService;
import com.app.model.User;
import com.app.model.dto.GetUserProvidedServicesDto;
import com.app.model.dto.ProvidedServiceFullDto;
import com.app.model.dto.ProvidedServiceMinDto;
import com.app.model.dto.ToProvideServiceDto;
import com.app.model.factories.ProvidedServiceFactory;
import com.app.repository.SeasonServiceRepository;
import com.app.rest.factories.ResponseErrorEntityFactory;
import com.app.service.EmailService;
import com.app.service.ProvidedServicesService;
import com.app.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author Максим Зеленский
 */

@RestController
@RequestMapping(path = "/users/seasonServices/")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private ProvidedServicesService providedServicesService;
    private SeasonServiceRepository seasonServiceRepository;
    private UserService userService;
    private EmailService emailService;

    @Autowired
    public UserController(ProvidedServicesService providedServicesService,
                          SeasonServiceRepository seasonServiceRepository,
                          UserService userService, EmailService emailService) {
        this.providedServicesService = providedServicesService;
        this.seasonServiceRepository = seasonServiceRepository;
        this.userService = userService;
        this.emailService = emailService;
    }

    /*
    * Consume json example:
    *
      {
        "serviceName": "Выдача охотбилетов единого федерального образца",
        "userId": "c5fc8d63-4122-4ae2-892c-cbc781f1a923",
        "creationDateTime": 1589282732208
      }
    *
    * */
    @PostMapping("provide")
    public ResponseEntity<Object> provideService(@Valid @RequestBody ToProvideServiceDto requestProperties) {
        final String serviceName = requestProperties.getServiceName();
        final UUID userID = requestProperties.getUserId();
        final long creationDateTime = requestProperties.getCreationDateTime();

        // Get season service
        SeasonService service = seasonServiceRepository.findByName(serviceName);
        if (service == null) {
            log.error("IN provideService: Service {} not found", serviceName);

            return ResponseErrorEntityFactory.create(HttpStatus.BAD_REQUEST,
                    Collections.singletonList("Service " + serviceName + " not found"));
        }

        // Check how much services usage left
        Integer serviceUsed = service.getUsed();
        User user = userService.findById(userID);
        if (user == null) {
            log.error("IN provideService: User with id {} not found", userID);
            return ResponseErrorEntityFactory.create(HttpStatus.BAD_REQUEST,
                    Collections.singletonList("User with login " + userID + " not found"));
        }

        if (serviceUsed < service.getUsageLimit()) {
            serviceUsed++;
            service.setUsed(serviceUsed);
            seasonServiceRepository.save(service); // update used services count

            Integer serialNumber = serviceUsed;
            Timestamp creationDate = new Timestamp(creationDateTime);
            Timestamp provisionDate = new Timestamp(System.currentTimeMillis());

            providedServicesService.create(ProvidedServiceFactory.create(
                    service,
                    user,
                    creationDate,
                    provisionDate,
                    serialNumber
            ));

            log.info("IN provideService: Service {} was successfully provided", serviceName);

            emailService.sendServiceSuccessfullyProvidedMessage(user.getEmail());

            Map<String, Integer> serialNumberBody = new HashMap<>();
            serialNumberBody.put("serialNumber", serialNumber);
            return ResponseEntity.ok(serialNumberBody);
        } else {
            log.error("IN provideService: Service {} has limit of usage and it's ended.", serviceName);

            emailService.sendServiceSuccessfullyProvidedMessage(user.getEmail());

            return ResponseErrorEntityFactory.create(
                    HttpStatus.BAD_REQUEST,
                    Collections.singletonList("Service " + serviceName + " has limit of usage and it's ended"
                    ));
        }
    }

    /*
     * Consume json example:
     *
         {
            "userId": "c5fc8d63-4122-4ae2-892c-cbc781f1a923",
            "pageNumber": 0,
            "pageSize":  10
         }
     *
     * */
    @GetMapping("getAll")
    public ResponseEntity<Object> getProvidedServices(
            @Valid @RequestBody GetUserProvidedServicesDto requestProperties) {
        log.info("IN getProvidedServices");

        final UUID userId = requestProperties.getUserId();
        final Integer pageNumber = requestProperties.getPageNumber();
        final Integer pageSize = requestProperties.getPageSize();

        User user = userService.findById(userId);
        if (user == null) {
            log.error("IN User getProvidedServices: User with id {} not found", userId);

            return ResponseErrorEntityFactory.create(HttpStatus.BAD_REQUEST,
                    Collections.singletonList("User with login " + userId + " not found"));
        }

        // Get provided services with request parameters
        List<ProvidedService> providedServices = providedServicesService.findByUser(
                user,
                PageRequest.of(
                        pageNumber,
                        pageSize,
                        Sort.Direction.DESC,
                        "creationDate"
                )
        );

        // Convert ProvidedService entity to minimal version ProvidedServiceMinDto
        List<ProvidedServiceMinDto> providedServicesMin = new LinkedList<>();
        providedServices
                .stream()
                .map(service -> new ProvidedServiceMinDto(
                        service.getService().getName(),
                        service.getCreationDate().getTime(),
                        service.getSerialNumber(),
                        service.getId()))
                .forEach(providedServicesMin::add);

        log.info("IN User getProvidedServices: request for provided services" +
                " where pageNum = {}, pageSize = {}, user id = {}", pageNumber, pageSize, userId);

        return ResponseEntity.ok(providedServicesMin);
    }

    @GetMapping("getOne/{providedServiceId}")
    public ResponseEntity<Object> getOne(@Valid @NotNull @PathVariable UUID providedServiceId) {
        ProvidedService providedService = providedServicesService.findById(providedServiceId);
        if (providedService == null) {
            log.error("IN getOne: Provided service with id {} not found", providedServiceId);

            return ResponseErrorEntityFactory.create(HttpStatus.BAD_REQUEST,
                    Collections.singletonList("Provided service with id " + providedServiceId + " not found"));
        }

        ProvidedServiceFullDto providedServiceInfo = new ProvidedServiceFullDto(
                providedService.getService().getName(),
                providedService.getCreationDate().getTime(),
                providedService.getSerialNumber(),
                providedService.getUser().getFirstName(),
                providedService.getUser().getFamilyName(),
                providedService.getUser().getPatronymic(),
                providedService.getProvisionDate().getTime(),
                providedServiceId
        );

        return ResponseEntity.ok(providedServiceInfo);
    }

}
