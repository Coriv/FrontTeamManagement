package com.tmvaddin.planning;

import com.tmvaddin.employee.EmployeeDto;
import com.tmvaddin.workplace.WorkplaceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlanDto {

    private EmployeeDto employeeDto;
    private WorkplaceDto postOne;
    private WorkplaceDto postTwo;
    private WorkplaceDto postThree;
    private WorkplaceDto PostFour;
}
