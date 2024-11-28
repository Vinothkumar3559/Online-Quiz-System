package com.system.quiz.Service;

import com.system.quiz.Model.QuizQuestion;
import com.system.quiz.Model.QuizResult;
import com.system.quiz.Repository.QuizQuestionRepository;
import com.system.quiz.Repository.QuizResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class QuizService {

    @Autowired
    private QuizQuestionRepository questionRepository;

    @Autowired
    private QuizResultRepository quizResultRepository;

    public List<QuizQuestion> getAllQuestions() {
        return questionRepository.findAll();
    }

    public QuizResult calculateResult(Map<String, String> answers) {
        int score = 0;
        for (Map.Entry<String, String> entry : answers.entrySet()) {
            Long questionId = Long.parseLong(entry.getKey());
            String userAnswer = entry.getValue();

            QuizQuestion question = questionRepository.findById(questionId).orElse(null);
            if (question != null) {
                String correctOptionText = getCorrectOptionText(question);
                if (userAnswer.equals(correctOptionText)) {
                    score += 1;
                }
            }
        }

        QuizResult result = new QuizResult();
        result.setScore(score);
        return result;
    }

    private String getCorrectOptionText(QuizQuestion question) {
        if (question.getCorrectOption().equals("A")) return question.getOptionA();
        if (question.getCorrectOption().equals("B")) return question.getOptionB();
        if (question.getCorrectOption().equals("C")) return question.getOptionC();
        if (question.getCorrectOption().equals("D")) return question.getOptionD();
        return "";
    }

    public void saveResult(Long userId, int score) {
        QuizResult result = new QuizResult();
        result.setUserId(userId);
        result.setScore(score);
        quizResultRepository.save(result);
    }
}
