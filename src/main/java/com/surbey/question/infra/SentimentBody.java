package com.surbey.question.infra;

import lombok.Data;

@Data
public class SentimentBody {
    private String content;

    public SentimentBody(String content) {
        this.content = content;
    }
}
