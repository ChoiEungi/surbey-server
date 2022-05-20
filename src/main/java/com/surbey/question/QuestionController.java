package com.surbey.question;

import com.surbey.question.dto.QuestionRequest;
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
    public ResponseEntity<List<Question>> retrieveSurveyQuestions(@PathVariable UUID id) {
        List<Question> questionsList = questionService.findQuestionsById(id);
        return ResponseEntity.ok(questionsList);
    }

    @GetMapping("/survey/{id}/questions/{questionId}")
    public ResponseEntity<Question> retrieveSurveyQuestion(@PathVariable UUID id, @PathVariable Long questionId) {
        Question question = questionService.findOneQuestions(id, questionId);
        return ResponseEntity.ok(question);
    }


}
