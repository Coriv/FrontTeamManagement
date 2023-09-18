package com.tmvaddin.planning;

import com.tmvaddin.employee.EmployeeService;
import com.tmvaddin.workplace.WorkplaceDto;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class PlanService {
    private final RestTemplate rest;

    private List<DailyPlanDto> plans = new ArrayList<>();
    private EmployeeService employeeService = EmployeeService.getInstance();
    public static PlanService instance;
    private PlanService() {
        this.rest = new RestTemplate();
    }

    public static PlanService getInstance() {
        if (instance == null) {
            instance = new PlanService();
        }
        return instance;
    }

    public List<DailyPlanDto> preparePlans() {
        return employeeService.getEmployee().stream()
                .map(empl -> {
                    var dailyPlan = new DailyPlanDto();
                    dailyPlan.setDayPlanId(new Random().nextInt(1000));
                    dailyPlan.setEmployeeDto(empl);
                    for (int i = 0; i < 4; i++) {
                        dailyPlan.getWorkplaces().add(new WorkplaceDto());
                    }
                    return dailyPlan;
                })
                .collect(Collectors.toList());
    }

    public List<DailyPlanDto> callForPlan(List<DailyPlanDto> plans) {
        var uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/api/v1/plan")
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
        DailyPlanDto[] result  = rest.postForObject(uri, plans, DailyPlanDto[].class);
        return Arrays.asList(result);
    }
}
