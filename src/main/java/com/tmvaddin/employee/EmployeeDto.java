package com.tmvaddin.employee;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tmvaddin.workplace.WorkplaceDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class EmployeeDto {
    @EqualsAndHashCode.Include
    private long employeeId;
    @EqualsAndHashCode.Include
    private String name;
    private Set<WorkplaceDto> workplaces;
    @EqualsAndHashCode.Include
    private double teamNumber;
    private boolean temporaryWorker;

    @Override
    public String toString() {
        return name;
    }


}
