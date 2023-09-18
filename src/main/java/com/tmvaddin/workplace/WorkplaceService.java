package com.tmvaddin.workplace;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkplaceService {
    private final RestTemplate rest;
    private final String URL = "http://localhost:8080/api/v1/workplace/";
    private static WorkplaceService instance;

    private WorkplaceService() {
        rest = new RestTemplate();
    }

    public static WorkplaceService getInstance() {
        if (instance == null) {
            instance = new WorkplaceService();
        }
        return instance;
    }

    public List<WorkplaceDto> getWorkplaces(int teamNumber) {
        String callForWorkplaces = URL + "/" + teamNumber;
        WorkplaceDto[] workplaces = rest.getForObject(uriBuilder(callForWorkplaces), WorkplaceDto[].class);
        return Arrays.asList(workplaces);
    }

    public void saveWorkplace(WorkplaceDto workplaceDto) {
        rest.postForEntity(uriBuilder(URL), workplaceDto, WorkplaceDto.class);
    }

    private URI uriBuilder(String url) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .encode(StandardCharsets.UTF_8)
                .build()
                .toUri();
    }
}
