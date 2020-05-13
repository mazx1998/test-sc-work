package com.app.rest;

import com.app.model.dto.GetProvidedServicesDto;
import com.app.model.dto.ProvidedServiceMinDto;
import com.app.model.ProvidedService;
import com.app.service.ProvidedServicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Максим Зеленский
 */

@RestController
@RequestMapping("admin/seasonServices/")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    private ProvidedServicesService providedServicesService;

    @Autowired
    public AdminController(ProvidedServicesService providedServicesService) {
        this.providedServicesService = providedServicesService;
    }

    /*
     * Consume json example:
     *
     *   {
     *      "pageNumber": 0,                                   //not null
     *      "pageSize":  10                                    //not null
     *   }
     *
     * */
    @GetMapping("getAll")
    public ResponseEntity<Object> getProvidedServices(
            @Valid @RequestBody GetProvidedServicesDto requestProperties) {
        Integer pageNumber = requestProperties.getPageNumber();
        Integer pageSize = requestProperties.getPageSize();

        // Get provided services with request parameters
        List<ProvidedService> providedServices = providedServicesService.findAll(
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

        log.info("IN Admin getProvidedServices: request for provided services" +
                " where pageNum = {}, pageSize = {}", pageNumber, pageSize);

        return ResponseEntity.ok(providedServicesMin);
    }
}
