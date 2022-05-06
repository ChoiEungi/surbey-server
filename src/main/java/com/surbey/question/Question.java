package com.surbey.question;

import com.surbey.survey.Survey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String questionContent;

    private String leftQuestion;

    private String rightQuestion;

    private int time;

    private int questionOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    private Survey survey;

    public Question(String questionContent, String leftQuestion, String rightQuestion, int time, int questionOrder, Survey survey) {
        this(null, questionContent, leftQuestion, rightQuestion, time, questionOrder, survey);
    }

    private Question(Long id, String questionContent, String leftQuestion, String rightQuestion, int time, int questionOrder, Survey survey) {
        this.id = id;
        this.questionContent = questionContent;
        this.leftQuestion = leftQuestion;
        this.rightQuestion = rightQuestion;
        this.time = time;
        this.questionOrder = questionOrder;
        this.survey = survey;
    }

    public void setQuestionContent(String mainQuestion) {
        this.questionContent = mainQuestion;
    }
}
