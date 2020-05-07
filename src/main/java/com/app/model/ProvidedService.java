package com.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author Максим Зеленский
 */

@Entity
@Table(name = "provided_services")
public class ProvidedService extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    @NotNull(message = "Please provide a service")
    private SeasonService service;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "Please provide a user")
    private User user;

    @Column(name = "creation_date")
    @NotNull(message = "Please provide a creation date")
    private Timestamp creationDate;

    @Column(name = "provision_date")
    private Timestamp provisionDate;

    @Column(name = "serial_number")
    @NotNull(message = "Please provide a serial number")
    private Integer serialNumber;

    public SeasonService getService() {
        return service;
    }

    public void setService(SeasonService service) {
        this.service = service;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getProvisionDate() {
        return provisionDate;
    }

    public void setProvisionDate(Timestamp provisionDate) {
        this.provisionDate = provisionDate;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public String toString() {
        return "ProvidedService{" +
                "service=" + service +
                ", user=" + user +
                ", creationDate=" + creationDate +
                ", provisionDate=" + provisionDate +
                ", serialNumber=" + serialNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProvidedService that = (ProvidedService) o;
        return service.equals(that.service) &&
                user.equals(that.user) &&
                creationDate.equals(that.creationDate) &&
                Objects.equals(provisionDate, that.provisionDate) &&
                serialNumber.equals(that.serialNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(service, user, creationDate, provisionDate, serialNumber);
    }
}
