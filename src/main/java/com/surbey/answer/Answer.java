package com.surbey.answer;

import com.surbey.question.Question;
import com.surbey.result.Result;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    private String answerQuestion;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Result> results = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    public Answer(String answerQuestion, Question question) {
        this(null, answerQuestion, question);
    }

    private Answer(Long answerId, String answerQuestion, Question question) {
        this.answerId = answerId;
        this.answerQuestion = answerQuestion;
        this.question = question;
    }


    public Long getAnswerId(){
        return this.answerId;
    }
    public String getAnswerQuestion() {
        return answerQuestion;
    }
    public Long getQuestionId() {
        return this.question.getId();
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void addResult(){
//        this.results.add(new Result(this.answerId));
    }



}