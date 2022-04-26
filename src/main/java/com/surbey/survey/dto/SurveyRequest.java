package com.surbey.survey.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyRequest {
    private String title;
    private String purpose;
    private Instant startDate;
    private Instant endDate;
    private String password;

}
