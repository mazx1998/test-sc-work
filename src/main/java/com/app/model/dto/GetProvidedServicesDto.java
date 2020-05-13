package com.app.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Максим Зеленский
 */
public class GetProvidedServicesDto {

    @NotNull(message = "Please provide a page number")
    @Min(value = 0, message = "Minimal page number value = 0")
    Integer pageNumber;

    @NotNull(message = "Please, provide a page size")
    Integer pageSize;

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
