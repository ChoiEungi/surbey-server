package com.surbey.question;

import com.surbey.question.dto.QuestionRequest;
import com.surbey.survey.Survey;
import com.surbey.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SurveyRepository surveyRepository;


    @Transactional
    public List<Question> findQuestionsById(UUID uuid) {
        List<Question> questions = questionRepository.findQuestionBySurveyId(uuid);
        return questions;
    }

    @Transactional
    public Long createQuestion(QuestionRequest request) {
        Survey survey = surveyRepository.findById(request.getSurveyId()).orElseThrow(IllegalArgumentException::new);
        Long questionId = questionRepository.save(new Question(request.getQuestionContent(), request.getLeftQuestion(), request.getRightQuestion(),
                request.getTime(), request.getQuestionOrder(), survey)).getId();
        return questionId;
    }

    @Transactional
    public List<Long> createQuestions(List<QuestionRequest> requests) {
        Survey survey = surveyRepository.findById(requests.get(0).getSurveyId()).orElseThrow(IllegalArgumentException::new);
        List<Long> resultsId = requests.stream()
                .map(s -> questionRepository.save(new Question(s.getQuestionContent(), s.getLeftQuestion(), s.getRightQuestion(),
                        s.getTime(), s.getQuestionOrder(), survey)).getId())
                .collect(Collectors.toList());
        return resultsId;
    }

    @Transactional
    public void updateMainQuestion(String questionContent, Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
        question.setQuestionContent(questionContent);
    }

    @Transactional
    public void deleteMainQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
        questionRepository.delete(question);
    }


}
