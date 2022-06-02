package com.surbey.question;

import com.surbey.question.dto.QuestionRequest;
import com.surbey.question.dto.QuestionResponse;
import com.surbey.question.dto.SentimentQuestionResponse;
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

    @PostMapping("survey/{id}/questions")
    public ResponseEntity<Void> createQuestionLists(@PathVariable UUID id, @RequestBody List<QuestionRequest> questionListRequest) {
        List<Long> questions = questionService.createQuestions(questionListRequest);
        return ResponseEntity.created(URI.create("/survey/" + id + "/questions")).build();
    }

    @PostMapping("survey/{id}/question")
    public ResponseEntity<Void> createQuestion(@PathVariable UUID id, @RequestBody QuestionRequest questionRequest) {
        Long questionId = questionService.createQuestion(questionRequest);
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
    public ResponseEntity<SentimentQuestionResponse> retrieveSentimentQuestion(@PathVariable UUID id, @PathVariable Long questionId, @RequestBody QuestionRequest questionRequest){
        SentimentQuestionResponse sentimentQuestionResponse = questionService.getSentimentList(questionRequest);
        return ResponseEntity.ok(sentimentQuestionResponse);
    }


}
