package com.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Максим Зеленский
 */

@Entity
@Table(name = "season_services")
public class SeasonService extends Service {

    @Column(name = "start_date")
    @NotNull(message = "Please provide a start date")
    private Timestamp startDate;

    @Column(name = "end_date")
    @NotNull(message = "Please provide a end date")
    private Timestamp endDate;

    @Column(name = "usage_limit")
    @NotNull(message = "Please provide a usage limit")
    private Integer usageLimit;

    @Column(name = "used")
    @NotNull(message = "Please provide a 'used'")
    private Integer used;

    @OneToMany(mappedBy = "service", fetch = FetchType.LAZY)
    private List<ProvidedService> providedServices;

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Integer getUsed() {
        return used;
    }

    public void setUsed(Integer used) {
        this.used = used;
    }

    public List<ProvidedService> getProvidedServices() {
        return providedServices;
    }

    public void setProvidedServices(List<ProvidedService> providedServices) {
        this.providedServices = providedServices;
    }
}
