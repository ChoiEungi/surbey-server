package com.surbey;


import com.surbey.answer.Answer;
import com.surbey.answer.AnswerRepository;
import com.surbey.question.Question;
import com.surbey.question.QuestionRepository;
import com.surbey.survey.Survey;
import com.surbey.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Override
    public void run(String... args) throws Exception {
        Survey survey = surveyRepository.save(new Survey("지방 선거 관련 설문", "2022 6월 1일에 시행되는 지방선거 관련 설문입니다.", Instant.now(), Instant.now().plusSeconds(100L), "pw"));

        Question question1 = new Question("윤석열 정부에 대해 긍정적이십니까?", 5, 1, survey);
        List<Answer> answerList = List.of(new Answer("yes", question1), new Answer("no", question1));
        question1.addAnswer(answerList);
        questionRepository.save(question1);
        answerRepository.saveAll(answerList);

        Question question2 = new Question("강용석이 이번 경기도지사 선거에 긍정적인 영향을 주었나요?", 0, 1, survey);
        List<Answer> answerList2 = List.of(new Answer("yes", question2), new Answer("no", question2));
        question2.addAnswer(answerList2);
        questionRepository.save(question2);
        answerRepository.saveAll(answerList2);

    }
}
