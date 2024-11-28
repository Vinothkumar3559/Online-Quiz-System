package com.system.quiz.Model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuizAnswer {
    private Long questionId;
    private String selectedOption;
}
