package com.surbey.answer;


import java.util.List;

public interface BulkRepository {

    void answerBatchInsert(List<Answer> answers);
}