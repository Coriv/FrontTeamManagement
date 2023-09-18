package com.tmvaddin.planning;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlanMapper {

    public PlanDto mapToPlanDto(DailyPlanDto dailyPlanDto) {
        var planDto = new PlanDto();
        planDto.setEmployeeDto(dailyPlanDto.getEmployeeDto());
        planDto.setPostOne(dailyPlanDto.getWorkplaces().get(0));
        planDto.setPostTwo(dailyPlanDto.getWorkplaces().get(1));
        planDto.setPostThree(dailyPlanDto.getWorkplaces().get(2));
        planDto.setPostFour(dailyPlanDto.getWorkplaces().get(3));
        return planDto;
    }

    public List<PlanDto> mapToPlanDtoList(List<DailyPlanDto> list) {
        return list.stream()
                .map(this::mapToPlanDto)
                .collect(Collectors.toList());
    }
}
