package com.surbey.question.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionRequest {
    private final String questionContent;
    private final List<String> answerDescription;
    private final int time;
    private final int questionOrder;
}
