package com.surbey.question;

import com.surbey.question.dto.QuestionRequest;
import com.surbey.question.dto.QuestionResponse;
import com.surbey.question.dto.SentimentQuestionResponse;
import com.surbey.result.ResultRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionResultService questionResultService;

    @PostMapping("survey/{id}/questions")
    public ResponseEntity<Void> createQuestionLists(@PathVariable UUID id, @RequestBody List<QuestionRequest> questionListRequest) {
        List<Long> questions = questionService.createQuestions(id, questionListRequest);
        return ResponseEntity.created(URI.create("/survey/" + id + "/questions")).build();
    }

    @PostMapping("survey/{id}/question")
    public ResponseEntity<Void> createQuestion(@PathVariable UUID id, @RequestBody QuestionRequest questionRequest) {
        Long questionId = questionService.createQuestion(id, questionRequest);
        return ResponseEntity.created(URI.create("/survey/" + id + "/questions/" + questionId)).build();
    }

    @GetMapping("/survey/{id}/questions")
    public ResponseEntity<List<QuestionResponse>> retrieveSurveyQuestions(@PathVariable UUID id) {
        List<QuestionResponse> questionsList = questionService.findQuestionsById(id);
        return ResponseEntity.ok(questionsList);
    }

    @GetMapping("/survey/{id}/questions/{questionId}")
    public ResponseEntity<QuestionResponse> retrieveSurveyQuestion(@PathVariable UUID id, @PathVariable Long questionId) {
        QuestionResponse question = questionService.findOneQuestions(id, questionId);
        return ResponseEntity.ok(question);
    }

    @PutMapping("/survey/{id}/questions/analysis/{questionId}")
    public ResponseEntity<SentimentQuestionResponse> retrieveSentimentQuestion(@PathVariable UUID id, @PathVariable Long questionId, @RequestBody QuestionRequest questionRequest) {
        SentimentQuestionResponse sentimentQuestionResponse = questionService.getSentimentResponse(questionRequest);
        return ResponseEntity.ok(sentimentQuestionResponse);
    }

    @PutMapping("/survey/{id}/questions/analysis")
    public ResponseEntity<List<SentimentQuestionResponse>> retrieveSentimentQuestionList(@PathVariable UUID id, @RequestBody List<QuestionRequest> questionRequestList) {
        List<SentimentQuestionResponse> sentimentQuestionResponse = questionService.getSentimentResponseList(questionRequestList);
        return ResponseEntity.ok(sentimentQuestionResponse);
    }

    @PostMapping("/survey/{id}/results")
    public ResponseEntity<Void> submitResult(@PathVariable UUID id, @RequestBody ResultRequest request) {
        questionResultService.answerTheQuestion(request);
        return ResponseEntity.created(URI.create(String.format("/survey/%s/results", id))).build();
    }

    @GetMapping("/survey/{id}/results")
    public void retrieveResults(@PathVariable UUID id) {
//        questionResultService.findResultBySurvey();
    }
}
