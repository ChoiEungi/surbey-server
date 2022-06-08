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

        Question question2 = new Question("강용석이 이번 경기도지사 선거에 긍정적인 영향을 주었나요?", 0, 2, survey);
        List<Answer> answerList2 = List.of(new Answer("yes", question2), new Answer("no", question2));
        question2.addAnswer(answerList2);
        questionRepository.save(question2);
        answerRepository.saveAll(answerList2);

        Survey courseSurvey = surveyRepository.save(new Survey("수강평 관련 설문", "2022년도 1학기 GIST 강의평가 관련 설문입니다.", Instant.now(), Instant.now().plusSeconds(100L), "pw"));

        Question courseQuestion = new Question("강의 전반적인 만족도는 높으셨나요?", 0, 1, survey);
        List<Answer> courseAnswerList = List.of(new Answer("yes", courseQuestion), new Answer("no", courseQuestion));
        question1.addAnswer(courseAnswerList);
        questionRepository.save(courseQuestion);
        answerRepository.saveAll(courseAnswerList);

        Question courseQuestion2 = new Question("강의 자료 제공은 만족스러웠나요?", 0, 2, courseSurvey);
        List<Answer> courseAnswerList2 = List.of(new Answer("yes", courseQuestion2), new Answer("no", courseQuestion2));
        question2.addAnswer(courseAnswerList2);
        questionRepository.save(courseQuestion2);
        answerRepository.saveAll(courseAnswerList2);

        Question courseQuestion3 = new Question("강의 영어 사용 비율이 높았나요?", 0, 2, courseSurvey);
        List<Answer> courseAnswerList3 = List.of(new Answer("yes", courseQuestion3), new Answer("no", courseQuestion3));
        question2.addAnswer(courseAnswerList3);
        questionRepository.save(courseQuestion3);
        answerRepository.saveAll(courseAnswerList3);

    }
}
