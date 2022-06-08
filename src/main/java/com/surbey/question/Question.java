package com.surbey.question;

import com.surbey.answer.Answer;
import com.surbey.survey.Survey;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String questionContent;

    private int surveyTime;

    private int questionOrder;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY)
    private final List<Answer> answerList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Survey survey;

    public Question(String questionContent, int surveyTime, int questionOrder, Survey survey) {
        this(null, questionContent, surveyTime, questionOrder, survey);
    }

    private Question(Long id, String questionContent, int surveyTime, int questionOrder, Survey survey) {
        this.id = id;
        this.questionContent = questionContent;
        this.surveyTime = surveyTime;
        this.questionOrder = questionOrder;
        this.survey = survey;
    }

    public void setQuestionContent(String mainQuestion) {
        this.questionContent = mainQuestion;
    }

    public void addAnswer(List<Answer> answerList) {
        this.answerList.addAll(answerList);
    }
}
