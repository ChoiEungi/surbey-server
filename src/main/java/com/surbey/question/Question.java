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
    private String question;

    private String leftQuestion;

    private String rightQuestion;

    private int time;

    private int questionOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    private Survey survey;

    public Question(String question, String leftQuestion, String rightQuestion, int time, int questionOrder, Survey survey) {
        this(null, question, leftQuestion, rightQuestion, time, questionOrder, survey);
    }

    private Question(Long id, String question, String leftQuestion, String rightQuestion, int time, int questionOrder, Survey survey) {
        this.id = id;
        this.question = question;
        this.leftQuestion = leftQuestion;
        this.rightQuestion = rightQuestion;
        this.time = time;
        this.questionOrder = questionOrder;
        this.survey = survey;
    }
}
