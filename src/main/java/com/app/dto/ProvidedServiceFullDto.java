package com.app.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.UUID;

/**
 * @author Максим Зеленский
 */

public class ProvidedServiceFullDto extends ProvidedServiceMinDto {

    private String firstName;

    private String familyName;

    private String patronymic;

    private Long provisionDate;

    public ProvidedServiceFullDto(String serviceName, Long creationDateTime,
                                  Integer serialNumber, String firstName,
                                  String familyName, String patronymic,
                                  Long provisionDate, UUID id) {
        super(serviceName, creationDateTime, serialNumber, id);
        this.firstName = firstName;
        this.familyName = familyName;
        this.patronymic = patronymic;
        this.provisionDate = provisionDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Long getProvisionDate() {
        return provisionDate;
    }

    public void setProvisionDate(Long provisionDate) {
        this.provisionDate = provisionDate;
    }
}
