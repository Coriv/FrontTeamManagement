package com.tmvaddin.planning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tmvaddin.employee.EmployeeDto;
import com.tmvaddin.workplace.WorkplaceDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DailyPlanDto {

    private long dayPlanId;
    private EmployeeDto employeeDto;
    private List<WorkplaceDto> workplaces = new ArrayList<>();
}

