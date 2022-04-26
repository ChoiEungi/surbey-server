package com.surbey.survey;

import com.surbey.survey.dto.SurveyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurveyServices {

    private final SurveyRepository surveyRepository;

    public List<SurveyResponse> findSurveyList() {
        return SurveyResponse.listOf(surveyRepository.findAll());
    }

    public SurveyResponse findSurveyById(UUID id) {
        Survey survey = surveyRepository.findById(id).orElseThrow(IllegalAccessError::new);
        return SurveyResponse.of(survey);
    }

    public Long createSurvey() {
        //todo encdoe the password
        return 1L;
    }
}
