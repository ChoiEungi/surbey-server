package com.surbey.question.infra;

import com.surbey.question.dto.SentimentQuestionResponse;
import com.surbey.question.dto.SentimentQuestionResponse.Document;

public interface SentimentVerifier {

    Document getSentimentResult(String content);
}
