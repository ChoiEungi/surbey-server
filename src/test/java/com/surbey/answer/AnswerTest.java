package com.surbey.answer;

import com.surbey.question.Question;
import com.surbey.question.QuestionRepository;
import com.surbey.question.QuestionResultService;
import com.surbey.survey.Survey;
import com.surbey.survey.SurveyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class AnswerTest {

    @Autowired private SurveyRepository surveyRepository;
    @Autowired private AnswerRepository answerRepository;
    @Autowired private QuestionRepository questionRepository;

    @Test
    void answerBulkInsertTest(){
        Survey survey = surveyRepository.save(new Survey("지방 선거 관련 설문", "2022 6월 1일에 시행되는 지방선거 관련 설문입니다.", Instant.now(), Instant.now().plusSeconds(100L), "pw"));
        Question question1 = new Question("윤석열 정부에 대해 긍정적이십니까?", 5, 1, survey);

        List<Answer> answerList = new ArrayList<>();
        for (int i=0; i<100; i++){
            Answer answer = new Answer("ans", question1);
            answerList.add(answer);
        }

        question1.addAnswer(answerList);
        questionRepository.save(question1);
        answerRepository.saveAll(answerList);
    }
}
