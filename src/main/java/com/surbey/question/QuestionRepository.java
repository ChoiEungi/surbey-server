package com.surbey.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findQuestionBySurveyId(UUID uuid);

    Optional<Question> findQuestionBySurveyIdAndId(UUID uuid, Long id);
}
