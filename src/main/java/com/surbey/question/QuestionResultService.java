package com.surbey.question;

import com.surbey.answer.Answer;
import com.surbey.answer.AnswerRepository;
import com.surbey.result.ResultRequest;
import com.surbey.survey.Survey;
import com.surbey.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionResultService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    @Transactional(readOnly = true)
    public void findResultBySurvey(UUID id) {
        Survey survey = surveyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return;
    }


    @Transactional
    public void answerTheQuestion(List<ResultRequest> request) {
        List<Answer> answerList = answerRepository.findAllById(request.stream().map(s -> s.getAnswerId()).collect(Collectors.toList()));
        answerList.forEach(s -> s.addResult());
    }
}
