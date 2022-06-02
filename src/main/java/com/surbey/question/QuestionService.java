package com.surbey.question;

import com.surbey.answer.AnswerRepository;
import com.surbey.question.dto.QuestionRequest;
import com.surbey.question.dto.QuestionResponse;
import com.surbey.survey.Survey;
import com.surbey.survey.SurveyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final SurveyRepository surveyRepository;
    private final AnswerRepository answerRepository;


    @Transactional(readOnly = true)
    public List<QuestionResponse> findQuestionsById(UUID uuid) {
        List<Question> questions = questionRepository.findQuestionBySurveyId(uuid);
//        List<Question> questions = questionRepository.findAll();
        return QuestionResponse.listOf(questions);
    }

    @Transactional(readOnly = true)
    public QuestionResponse findOneQuestions(UUID uuid, Long questionId) {
        Question question = questionRepository.findQuestionBySurveyIdAndId(uuid, questionId).orElseThrow(IllegalArgumentException::new);
        return QuestionResponse.of(question);
    }

    @Transactional
    public Long createQuestion(QuestionRequest request) {
        Survey survey = surveyRepository.findById(request.getSurveyId()).orElseThrow(IllegalArgumentException::new);
        Question question = new Question(request.getQuestionContent(), request.getTime(), request.getQuestionOrder(), survey);
        question.addAnswer(request.getAnswer());
        Question savedQuestion = questionRepository.save(question);
//        answerRepository.saveAll(request.getAnswer());

        return savedQuestion.getId();
    }

    @Transactional
    public List<Long> createQuestions(List<QuestionRequest> requests) {
        Survey survey = surveyRepository.findById(requests.get(0).getSurveyId()).orElseThrow(IllegalArgumentException::new);

        List<Question> questions = requests.stream()
                .map(s -> new Question(s.getQuestionContent(), s.getTime(), s.getQuestionOrder(), survey))
                .collect(Collectors.toList());

        IntStream.range(0, requests.size())
                .forEach(i -> questions.get(i).addAnswer(requests.get(i).getAnswer()));

        List<Question> savedQuestions = questions.stream().map(s -> questionRepository.save(s)).collect(Collectors.toList());

        return savedQuestions.stream().map(Question::getId).collect(Collectors.toList());
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
