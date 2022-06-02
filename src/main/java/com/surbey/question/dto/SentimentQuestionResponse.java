package com.surbey.question.dto;

import com.surbey.question.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SentimentQuestionResponse {

    private String questionContent;
    private int time;
    private int questionOrder;
    private final List<String> answerQuestion = new ArrayList<>();
    private String sentiment;
    private Confidence confidence;

    public SentimentQuestionResponse(String questionContent, int time, int questionOrder, String sentiment, Confidence confidence) {
        this.questionContent = questionContent;
        this.time = time;
        this.questionOrder = questionOrder;
        this.sentiment = sentiment;
        this.confidence = confidence;
    }

    public static List<SentimentQuestionResponse> listOf(List<Question> questionList, List<Document> documentList){
        return IntStream.range(0, questionList.size())
                .mapToObj(i -> SentimentQuestionResponse.of(questionList.get(i), documentList.get(i)))
                .collect(Collectors.toList());
    }

    public static SentimentQuestionResponse of(Question question, Document document){
        List<String> answerList = question.getAnswerList().stream()
                .map(s -> s.getAnswerQuestion())
                .collect(Collectors.toList());
        SentimentQuestionResponse questionResponse = new SentimentQuestionResponse(question.getQuestionContent(), question.getTime(), question.getQuestionOrder(), document.getSentiment(), document.getConfidence());
        questionResponse.getAnswerQuestion().addAll(answerList);

        return questionResponse;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class Document {
        private String sentiment;
        private Confidence confidence;

        public Document(String sentiment, Confidence confidence) {
            this.sentiment = sentiment;
            this.confidence = confidence;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @ToString
    public static class Confidence {
        private Double negative;
        private Double positive;
        private Double neutral;

        public Confidence(double negative, double positive, double neutral) {
            this.negative = negative;
            this.positive = positive;
            this.neutral = neutral;
        }
    }
}
