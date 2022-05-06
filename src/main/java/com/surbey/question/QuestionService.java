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
        List<Question> questions = questionRepository.findQuestionBySurveyId(uuid);
        return questions;
    }

    @Transactional
    public Long createQuestion() {
        return 1L;
    }

    @Transactional
    public void updateMainQuestion(String mainQuestion, Long questionId) {
        Question question = questionRepository.findById(questionId).orElseThrow(IllegalArgumentException::new);
        question.setQuestion(mainQuestion);
    }


}
