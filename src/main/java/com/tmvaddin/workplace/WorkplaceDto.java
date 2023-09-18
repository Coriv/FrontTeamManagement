package com.tmvaddin.workplace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class WorkplaceDto {
    private long workplaceId;
    private String name;
    private DifficultyLevel diffLvl;
    private double teamNumber;
    private double speedLine;

    @Override
    public String toString() {
        return name;
    }
}
