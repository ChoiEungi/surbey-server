package com.surbey.question;

import com.surbey.answer.Answer;
import com.surbey.answer.AnswerRepository;
import com.surbey.question.dto.QuestionRequest;
import com.surbey.question.dto.QuestionResponse;
import com.surbey.question.dto.SentimentQuestionResponse;
import com.surbey.question.dto.SentimentQuestionResponse.Document;
import com.surbey.question.infra.SentimentVerifier;
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
    private final SentimentVerifier sentimentVerifier;


    @Transactional(readOnly = true)
    public List<QuestionResponse> findQuestionsById(UUID uuid) {
        List<Question> questions = questionRepository.findQuestionBySurveyId(uuid);
//        List<Question> questions = questionRepository.findAll();
        return QuestionResponse.listOf(questions);
    }

    @Transactional(readOnly = true)
    public QuestionResponse findOneQuestions(UUID id, Long questionId) {
        Question question = questionRepository.findQuestionBySurveyIdAndId(id, questionId).orElseThrow(IllegalArgumentException::new);
        return QuestionResponse.of(question);
    }

    @Transactional
    public Long createQuestion(UUID id, QuestionRequest request) {
        Survey survey = surveyRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        Question question = new Question(request.getQuestionContent(), request.getTime(), request.getQuestionOrder(), survey);
        List<Answer> answers = request.getAnswerDescription().stream().map(s -> new Answer(s, question)).collect(Collectors.toList());
        question.addAnswer(answers);
        Question savedQuestion = questionRepository.save(question);
        answerRepository.saveAll(answers);
        return savedQuestion.getId();
    }

    @Transactional
    public List<Long> createQuestions(UUID id, List<QuestionRequest> requests) {
        Survey survey = surveyRepository.findById(id).orElseThrow(IllegalArgumentException::new);

        List<Question> questions = requests.stream()
                .map(s -> new Question(s.getQuestionContent(), s.getTime(), s.getQuestionOrder(), survey))
                .collect(Collectors.toList());

        IntStream.range(0, requests.size())
                .forEach(i -> questions.get(i).addAnswer(requests.get(i).getAnswerDescription().stream().map(s -> new Answer(s, questions.get(i))).collect(Collectors.toList())));

        List<Question> savedQuestions = questions.stream().map(s -> questionRepository.save(s)).collect(Collectors.toList());

        return savedQuestions.stream().map(Question::getId).collect(Collectors.toList());
    }

    @Transactional
    public SentimentQuestionResponse getSentimentResponse(QuestionRequest request) {
        Document sentimentResult = sentimentVerifier.getSentimentResult(request.getQuestionContent());

        SentimentQuestionResponse sentimentQuestionResponse = new SentimentQuestionResponse(request.getQuestionContent(), request.getTime(), request.getQuestionOrder(), sentimentResult.getSentiment(), sentimentResult.getConfidence());
        sentimentQuestionResponse.getAnswerDescription().addAll(request.getAnswerDescription());
        return sentimentQuestionResponse;
    }

    @Transactional(readOnly = true)
    public List<SentimentQuestionResponse> getSentimentResponseList(List<QuestionRequest> requestList) {
        List<Document> sentimentResultList = requestList.stream().map(s -> sentimentVerifier.getSentimentResult(s.getQuestionContent())).collect(Collectors.toList());
        return SentimentQuestionResponse.listOf(requestList, sentimentResultList);
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
