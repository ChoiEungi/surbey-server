package com.surbey.question;

import com.surbey.survey.Survey;
import com.surbey.survey.SurveyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private Survey survey;

    @BeforeEach
    void setup() {
        survey = surveyRepository.save(new Survey("title", "purpose", Instant.now(), Instant.now().plusSeconds(100L), "pw"));
        questionRepository.save(new Question("question", "yes", "no", 10, 1, survey));
    }

    @Test
    void findQuestionsById() {
        List<Question> questions = questionService.findQuestionsById(survey.getId());
        Assertions.assertThat(questions.size()).isEqualTo(1);
    }
}