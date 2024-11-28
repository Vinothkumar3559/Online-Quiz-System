package com.system.quiz.Model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class QuizSubmission {

    private Long userId;
    private List<QuizAnswer> answers;  // Ensure 'answers' is defined
}
