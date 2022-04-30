package com.surbey.survey.dto;


import com.surbey.survey.Survey;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResponse {
    private UUID id;
    private String title;
    private String purpose;
    private Instant startDate;
    private Instant endDate;

    public static List<SurveyResponse> listOf(List<Survey> surveyList) {
        return surveyList.stream()
                .map(SurveyResponse::of)
                .collect(Collectors.toList());
    }

    public static SurveyResponse of(Survey survey) {
        return new SurveyResponse(survey.getId(), survey.getTitle(), survey.getPurpose(), survey.getStartDate(), survey.getEndDate());
    }
}
