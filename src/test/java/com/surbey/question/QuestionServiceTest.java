package com.surbey.question;

import com.surbey.answer.Answer;
import com.surbey.answer.AnswerRepository;
import com.surbey.question.dto.QuestionRequest;
import com.surbey.question.dto.QuestionResponse;
import com.surbey.question.dto.SentimentQuestionResponse;
import com.surbey.survey.Survey;
import com.surbey.survey.SurveyRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
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

    @Autowired
    private AnswerRepository answerRepository;

    public QuestionRequest QUESTION_REQUEST;
    public List<String> answerList = new ArrayList<>();
    private Survey survey;

    public static final Survey SURVEY = new Survey("title", "purpose", Instant.now(), Instant.now().plusSeconds(100L), "pw");
    public static final Question QUESTION = new Question("question", 10, 1, SURVEY);


    @BeforeEach
    void setup() {
        survey = surveyRepository.save(SURVEY);
        answerList.add("yes");
        answerList.add("no");
        QUESTION_REQUEST = new QuestionRequest("content", answerList, 10, 2);
    }

    @Test
    void findQuestionsById() {
        Survey survey1 = surveyRepository.save(new Survey("title1", "purpose1", Instant.now(), Instant.now().plusSeconds(100L), "pw"));
        for (int i = 1; i <= 3; i++) {
            Question question = new Question("question123", 100, i, survey1);
            List<Answer> answerList = List.of(new Answer("yes", question), new Answer("no", question));
            question.addAnswer(answerList);
            questionRepository.save(question);
//            answerRepository.saveAll(answerList);
        }

        List<QuestionResponse> questions = questionService.findQuestionsById(survey1.getId());
        System.out.println(questions.get(0).getAnswer());
        assertThat(questions.size()).isEqualTo(3);
    }

    @Test
    void findOneQuestionById() {
        Survey survey = surveyRepository.save(new Survey("title1", "purpose1", Instant.now(), Instant.now().plusSeconds(100L), "pw"));
        Question question = questionRepository.save(new Question("question123", 100, 1, survey));
        Long questionid = questionService.createQuestion(survey.getId(), new QuestionRequest(question.getQuestionContent(), List.of("yes", "no"), question.getTime(), question.getQuestionOrder()));

        QuestionResponse response = questionService.findOneQuestions(survey.getId(), questionid);
        assertThat(response.getText()).isEqualTo(question.getQuestionContent());
//        System.out.println(questions.get(0).getAnswerQuestion());
//        assertThat(questions.size()).isEqualTo(3);
    }


    @Test
    void createQuestionTest() {
        Long questionId = questionService.createQuestion(survey.getId(), QUESTION_REQUEST);
        Question findedQuestion = questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
        assertThat(findedQuestion.getId()).isEqualTo(questionId);
    }

    @Test
    void createQuestionListTest() {
        List<QuestionRequest> questionRequestList = new ArrayList<>();
        questionRequestList.add(QUESTION_REQUEST);
        questionRequestList.add(QUESTION_REQUEST);
        questionRequestList.add(QUESTION_REQUEST);
        List<Long> questionIds = questionService.createQuestions(survey.getId(), questionRequestList);
        List<Question> findedQuestion = questionRepository.findAllById(questionIds);
        assertThat(findedQuestion.size()).isEqualTo(questionRequestList.size());
    }

    @Test
    void sentimentQuestionTest() {
        SentimentQuestionResponse sentimentList = questionService.getSentimentResponse(QUESTION_REQUEST);
        System.out.println(sentimentList.toString());
    }

    @Test
    void findAllQuestionTests() {

        for (int i = 0; i < 3; i++) {
            Question question = questionRepository.save(new Question(QUESTION_REQUEST.getQuestionContent(), QUESTION_REQUEST.getTime(), QUESTION_REQUEST.getQuestionOrder(), survey));
            answerRepository.saveAll(List.of(new Answer("yes", question), new Answer("no", question)));
        }

        // when
        List<QuestionResponse> findedQuestion = questionService.findQuestionsById(survey.getId());

        List<Answer> all = answerRepository.findAll();


        List<String> AnswerList = findedQuestion.get(1).getAnswer();
        System.out.println(AnswerList);

    }

    @Test
    void updateQuestionTest() {
        Long questionId = questionService.createQuestion(survey.getId(), QUESTION_REQUEST);
        Question questionSample = questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
        String updatecontent = "updated";
        questionService.updateMainQuestion(updatecontent, questionSample.getId());
        Question updatedQuestion = questionRepository.findById(questionSample.getId()).orElseThrow(IllegalArgumentException::new);
        assertThat(updatedQuestion.getQuestionContent()).isEqualTo(updatecontent);
    }

    @Test
    void deleteQuestionTest() {
        Long questionId = questionService.createQuestion(survey.getId(), new QuestionRequest("content", new ArrayList<>(), 1, 1));
        questionService.deleteMainQuestion(questionId);
        assertThatThrownBy(() -> questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new)).isInstanceOf(IllegalArgumentException.class);
    }

    @AfterEach
    void tearDown() {
        answerRepository.deleteAllInBatch();
        questionRepository.deleteAllInBatch();
        surveyRepository.deleteAllInBatch();
    }
}