package com.surbey.question.infra;

import com.surbey.question.dto.SentimentQuestionResponse.Confidence;
import com.surbey.question.dto.SentimentQuestionResponse.Document;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SentimentVerifierImpl implements SentimentVerifier {

    @Value("${SENTIMENT_URL}")
    private String url;

    @Value("${API_KEY_ID}")
    private String apiKeyId;

    @Value("${API_KEY}")
    private String apiKey;

    @Override
    public Document getSentimentResult(String content) {
        JsonNode json = getSentiment(content);
        JSONObject document = json.getObject().getJSONObject("document");

        String sentiment = document.getString("sentiment");

        JSONObject confidence = document.getJSONObject("confidence");
        double negative = confidence.getDouble("negative");
        double positive = confidence.getDouble("positive");
        double neutral = confidence.getDouble("neutral");

        return new Document(sentiment, new Confidence(negative, positive, neutral));
    }

    private JsonNode getSentiment(String content){
        HttpResponse<JsonNode> jsonNodeHttpResponse = Unirest.post(url)
                .header("X-NCP-APIGW-API-KEY-ID", apiKeyId)
                .header("X-NCP-APIGW-API-KEY", apiKey)
                .header("Content-Type", "application/json")
                .body(new SentimentBody(content))
                .asJson();

        return jsonNodeHttpResponse.getBody();
    }

}
