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

    private int time;

    private int questionOrder;

    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private final List<Answer> answerList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Survey survey;

    public Question(String questionContent, int time, int questionOrder, Survey survey) {
        this(null, questionContent, time, questionOrder, survey);
    }

    private Question(Long id, String questionContent, int time, int questionOrder, Survey survey) {
        this.id = id;
        this.questionContent = questionContent;
        this.time = time;
        this.questionOrder = questionOrder;
        this.survey = survey;
    }

    public void setQuestionContent(String mainQuestion) {
        this.questionContent = mainQuestion;
    }

    public void addAnswer(List<Answer> answerList) {
        answerList.forEach(s -> s.setQuestion(this));
        this.answerList.addAll(answerList);
    }
}
