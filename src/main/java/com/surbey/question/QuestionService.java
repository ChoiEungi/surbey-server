package com.surbey.question;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;


    @Transactional
    public List<Question> findQuestionsById(UUID uuid) {
        List<Question> questionBySurveyId = questionRepository.findQuestionBySurveyId(uuid);
        return questionBySurveyId;
    }


    public Long createQuestion() {
        return 1L;

    }


}
