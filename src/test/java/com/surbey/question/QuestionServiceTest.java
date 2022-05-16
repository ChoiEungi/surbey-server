package com.surbey.question;

import com.surbey.question.dto.QuestionRequest;
import com.surbey.survey.Survey;
import com.surbey.survey.SurveyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class QuestionServiceTest {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private Question questionSample;
    public QuestionRequest QUESTION_REQUEST;
    private Survey survey;

    public static final Survey SURVEY = new Survey("title", "purpose", Instant.now(), Instant.now().plusSeconds(100L), "pw");
    public static final Question QUESTION = new Question("question", "yes", "no", 10, 1, SURVEY);


    @BeforeEach
    void setup() {
        survey = surveyRepository.save(SURVEY);
        questionSample = questionRepository.save(QUESTION);
        QUESTION_REQUEST = new QuestionRequest("content", "yes", "no", 10, 2, survey.getId());
    }

    @Test
    void findQuestionsById() {
        Survey survey = surveyRepository.save(new Survey("title", "purpose", Instant.now(), Instant.now().plusSeconds(100L), "pw"));
        for (int i = 1; i <= 3; i++) {
            questionRepository.save(new Question("question", "yes", "no", 10, i, survey));
        }

        List<Question> questions = questionService.findQuestionsById(survey.getId());
        System.out.println(questions);
        assertThat(questions.size()).isEqualTo(3);
    }

    @Test
    void createQuestionTest() {
        Long questionId = questionService.createQuestion(QUESTION_REQUEST);
        Question findedQuestion = questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
        assertThat(findedQuestion.getId()).isEqualTo(questionId);
    }

    @Test
    void updateQuestionTest() {
        String updatecontent = "updated";
        questionService.updateMainQuestion(updatecontent, questionSample.getId());
        Question updatedQuestion = questionRepository.findById(questionSample.getId()).orElseThrow(IllegalArgumentException::new);
        assertThat(updatedQuestion.getQuestionContent()).isEqualTo(updatecontent);
    }

    @Test
    void deleteQuestionTest() {
        Long questionId = questionRepository.save(QUESTION).getId();
        questionService.deleteMainQuestion(questionId);
        assertThatThrownBy(() -> questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new)).isInstanceOf(IllegalArgumentException.class);
    }
}