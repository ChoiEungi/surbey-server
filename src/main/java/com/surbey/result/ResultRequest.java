package com.surbey.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Data
public class ResultRequest {
    private final List<Long> answerIdList = new ArrayList<>();

}
