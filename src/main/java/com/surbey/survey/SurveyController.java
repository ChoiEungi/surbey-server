package com.surbey.survey;

import com.surbey.survey.dto.SurveyRequest;
import com.surbey.survey.dto.SurveyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/surveys")
public class SurveyController {

    private final SurveyServices surveyServices;

    @PostMapping("")
    public ResponseEntity<Void> createSurvey(@RequestBody SurveyRequest request){
        UUID survey = surveyServices.createSurvey(request);
        return ResponseEntity.created(URI.create("/surveys" + survey)).build();
    }

    @GetMapping("")
    public ResponseEntity<List<SurveyResponse>> retrieveSurveyList(){
        List<SurveyResponse> surveyList = surveyServices.findSurveyList();
        return ResponseEntity.ok(surveyList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SurveyResponse> retrieveSurvey(@PathVariable UUID id){
        SurveyResponse survey = surveyServices.findSurveyById(id);
        return ResponseEntity.ok(survey);
    }
}
