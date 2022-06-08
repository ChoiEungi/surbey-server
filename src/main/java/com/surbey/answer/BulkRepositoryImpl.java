package com.surbey.answer;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BulkRepositoryImpl implements BulkRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void answerBatchInsert(List<Answer> answers){
        String sql = "INSERT INTO answer"
                + "(answer_question, question_id) values (?, ?)";

        jdbcTemplate.batchUpdate(sql, answers, 100, (ps, argument) -> {
            ps.setString(1, argument.getAnswerQuestion());
            ps.setLong(2, argument.getQuestionId());
        });
    };

}
