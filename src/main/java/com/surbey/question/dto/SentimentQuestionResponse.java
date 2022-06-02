package com.surbey.question.dto;

import com.surbey.question.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SentimentQuestionResponse {

    private String questionContent;
    private String sentimentQuestionContent;
    private int time;
    private int questionOrder;
    private final List<String> answerQuestion = new ArrayList<>();

    public SentimentQuestionResponse(String questionContent, String sentimentQuestionContent, int time, int questionOrder) {
        this.questionContent = questionContent;
        this.sentimentQuestionContent = sentimentQuestionContent;
        this.time = time;
        this.questionOrder = questionOrder;
    }
}
