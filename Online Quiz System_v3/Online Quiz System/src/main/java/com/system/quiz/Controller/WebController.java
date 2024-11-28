package com.system.quiz.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/req/login")
    public String login(){
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup(){
        return "signup";
    }

    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/quiz")
    public String startQuiz(){
        return "quiz";
    }

    @GetMapping("/results")
    public String showResults() {
        return "results";
    }


}
