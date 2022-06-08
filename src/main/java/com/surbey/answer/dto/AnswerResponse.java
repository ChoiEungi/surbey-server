package com.surbey.answer.dto;

import com.surbey.answer.Answer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponse {
    private Long answerId;
    private String answerQuestion;

    public static List<AnswerResponse> listOf(List<Answer> answerList) {
        return answerList.stream()
                .map(AnswerResponse::of)
                .collect(Collectors.toList());
    }

    public static AnswerResponse of(Answer answer) {
        return new AnswerResponse(answer.getAnswerId(), answer.getAnswerQuestion());
    }
}
