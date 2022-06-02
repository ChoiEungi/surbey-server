package com.surbey.question.dto;

import com.surbey.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Getter
@NoArgsConstructor
public class QuestionRequest {
    private String questionContent;
    private List<Answer> answer;
    private int time;
    private int questionOrder;
    private UUID surveyId;
}
