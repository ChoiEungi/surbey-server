package com.surbey.question.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class QuestionRequest {
    private String questionContent;
    private String leftQuestion;
    private String rightQuestion;
    private int time;
    private int questionOrder;
    private UUID surveyId;
}
