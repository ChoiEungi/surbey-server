package com.surbey.survey;

import com.surbey.survey.dto.SurveyRequest;
import com.surbey.survey.dto.SurveyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SurveyServices {

    private final SurveyRepository surveyRepository;

    @Transactional(readOnly = true)
    public List<SurveyResponse> findSurveyList() {
        return SurveyResponse.listOf(surveyRepository.findAll());
    }

    @Transactional(readOnly = true)
    public SurveyResponse findSurveyById(UUID id) {
        Survey survey = surveyRepository.findById(id).orElseThrow(IllegalAccessError::new);
        return SurveyResponse.of(survey);
    }

    @Transactional
    public UUID createSurvey(SurveyRequest request) {
        UUID id = surveyRepository.save(
                new Survey(request.getTitle(), request.getPurpose(),
                        request.getStartDate(), request.getEndDate(), request.getPassword())).getId();
        return id;
    }

}
