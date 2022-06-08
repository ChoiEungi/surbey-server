package com.surbey.result;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Result {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
