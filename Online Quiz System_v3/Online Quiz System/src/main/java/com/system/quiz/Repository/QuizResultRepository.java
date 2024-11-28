package com.system.quiz.Repository;

import com.system.quiz.Model.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@Repository

public interface QuizResultRepository extends JpaRepository<QuizResult, Long>,CrudRepository<QuizResult, Long> {
    List<QuizResult> findByUserId(long userId);
}
