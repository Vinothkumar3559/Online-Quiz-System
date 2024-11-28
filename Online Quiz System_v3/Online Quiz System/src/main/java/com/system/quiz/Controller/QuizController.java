package com.system.quiz.Controller;

import com.system.quiz.Model.QuizQuestion;
import com.system.quiz.Model.QuizResult;
import com.system.quiz.Model.QuizSubmission;
import com.system.quiz.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/quiz")
public class QuizController {

    @Autowired
    private QuizService quizService;

    @GetMapping("/questions")
    public ResponseEntity<List<QuizQuestion>> getQuestions() {
        return ResponseEntity.ok(quizService.getAllQuestions());
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizResult> submitQuiz(@RequestBody QuizSubmission submission) {
        try {
            System.out.println("Received submission: " + submission);

            Map<String, String> answersMap = submission.getAnswers().stream()
                    .collect(Collectors.toMap(
                            answer -> String.valueOf(answer.getQuestionId()),
                            answer -> answer.getSelectedOption()
                    ));

            System.out.println("Processed answers map: " + answersMap);

            QuizResult result = quizService.calculateResult(answersMap);
            System.out.println("Calculated result: " + result.getScore());

            if (submission.getUserId() != null) {
                quizService.saveResult(submission.getUserId(), result.getScore());
            }

            return ResponseEntity.ok(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
