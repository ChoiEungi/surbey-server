package com.surbey.question.dto;

import com.surbey.answer.dto.AnswerResponse;
import com.surbey.question.Question;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionResponse {
    private String text;
    private int time;
    private int questionOrder;
    private final List<AnswerResponse> answer = new ArrayList<>();

    public QuestionResponse(String text, int time, int questionOrder) {
        this.text = text;
        this.time = time;
        this.questionOrder = questionOrder;
    }

    public static List<QuestionResponse> listOf(List<Question> questionList) {
        return questionList.stream()
                .map(QuestionResponse::of)
                .collect(Collectors.toList());
    }

    public static QuestionResponse of(Question question) {
        QuestionResponse questionResponse = new QuestionResponse(question.getQuestionContent(), question.getQuestionOrder(), question.getSurveyTime());
        questionResponse.getAnswer().addAll(AnswerResponse.listOf(question.getAnswerList()));
        return questionResponse;
    }
}
