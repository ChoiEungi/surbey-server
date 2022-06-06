package com.surbey.result;

import com.surbey.answer.Answer;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"id", "petition_id"}))
public class Result {

    @Id
    @GeneratedValue()
    private Long id;

    private Long answerId;

    public Result(Long answerId) {
        this(null, answerId);
    }

    private Result(Long id, Long answerId) {
        this.id = id;
        this.answerId = answerId;
    }

}
