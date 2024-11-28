package com.system.quiz.Repository;

import com.system.quiz.Model.QuizQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Long> {

    // Fetch all questions
    @Query("SELECT q FROM QuizQuestion q")
    List<QuizQuestion> findAllQuestions();
    // Fetch questions containing a specific keyword in the question text
    @Query("SELECT q FROM QuizQuestion q WHERE q.questionText LIKE %:keyword%")
    List<QuizQuestion> findQuestionsByKeyword(@Param("keyword") String keyword);

    // Fetch questions by their IDs (for specific selection)
    @Query("SELECT q FROM QuizQuestion q WHERE q.id IN :ids")
    List<QuizQuestion> findQuestionsByIds(@Param("ids") List<Long> ids);

    // Fetch a random set of questions (for quizzes with random questions)
    @Query(value = "SELECT * FROM quiz_question ORDER BY RAND() LIMIT :count", nativeQuery = true)
    List<QuizQuestion> findRandomQuestions(@Param("count") int count);
}
