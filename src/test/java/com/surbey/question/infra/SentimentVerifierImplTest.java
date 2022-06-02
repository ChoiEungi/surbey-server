package com.surbey.question.infra;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SentimentVerifierImplTest {

    @Autowired
    private SentimentVerifier sentimentVerifier;

    @Test
    void getSentimentResult() {
        System.out.println(sentimentVerifier.getSentimentResult("강용석이 경기도지사 선거에 영향을 준 부분이 무엇이라고 생각합니까? 그의 욕심때문인가요?"));
    }
}