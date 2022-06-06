package com.surbey.question;

import com.surbey.answer.Answer;
import com.surbey.answer.AnswerRepository;
import com.surbey.result.ResultRequest;
import com.surbey.survey.Survey;
import com.surbey.survey.SurveyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class QuestionResultServiceTest {

    @Autowired
    private QuestionResultService questionResultService;
    @Autowired
    private SurveyRepository surveyRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private AnswerRepository answerRepository;

    @Test
    void answerTheQuestion() {
        Survey survey = surveyRepository.save(new Survey("지방 선거 관련 설문", "2022 6월 1일에 시행되는 지방선거 관련 설문입니다.", Instant.now(), Instant.now().plusSeconds(100L), "pw"));

        Question question1 = new Question("윤석열 정부에 대해 긍정적이십니까?", 5, 1, survey);
        List<Answer> answerList = List.of(new Answer("yes", question1), new Answer("no", question1));
        question1.addAnswer(answerList);
        questionRepository.save(question1);
        answerRepository.saveAll(answerList);

        Question question2 = new Question("강용석이 이번 경기도지사 선거에 긍정적인 영향을 주었나요?", 0, 2, survey);
        List<Answer> answerList2 = List.of(new Answer("yes", question2), new Answer("no", question2));
        question2.addAnswer(answerList2);
        questionRepository.save(question2);
        answerRepository.saveAll(answerList2);

        List<ResultRequest> resultRequestList = answerRepository.findAll().
                stream().filter(s -> s.getAnswerId().intValue() < 2)
                .map(s -> new ResultRequest(s.getAnswerId()))
                .collect(Collectors.toList());

        questionResultService.answerTheQuestion(resultRequestList);
    }
}