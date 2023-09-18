package com.tmvaddin.employee;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {

    private final RestTemplate rest;
    private final String URL = "http://localhost:8080/api/v1/employee";
    private static EmployeeService instance;


    public static EmployeeService getInstance() {
        if (instance == null) {
            instance = new EmployeeService();
        }
        return instance;
    }

    private EmployeeService() {
        this.rest = new RestTemplate();
    }

    public List<EmployeeDto> getEmployee() {
        EmployeeDto[] employees = rest.getForObject(uriBuilder(URL + "/all"), EmployeeDto[].class);
        return Arrays.asList(employees);
    }

    public void saveEmployee(EmployeeDto employeeDto) {
        rest.put(uriBuilder(URL), employeeDto);
    }

    private URI uriBuilder(String url) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }
}
