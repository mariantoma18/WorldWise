package com.geo.controller;

import com.geo.model.QuizData;
import com.geo.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/quiz")
@RequiredArgsConstructor
public class QuizController {

  private final QuizService quizService;

  @GetMapping()
  public String getQuizPage(Model model) {
    QuizData quizData = quizService.getQuizData();

    model.addAttribute("quiz", quizData.getCountryName());
    model.addAttribute("options", quizData.getCountryCapitalOptions());
    model.addAttribute("correctAnswer", quizData.getCorrectAnswer());
    model.addAttribute("currentQuestion", 1);
    model.addAttribute("score", 0);
    model.addAttribute("totalQuestions", quizService.getTOTAL_QUESTIONS());
    return "quiz";
  }

    @PostMapping
    public String processQuizAnswer(@RequestParam("selectedCapital") String selectedCapital,
                                    @RequestParam("correctAnswer") String correctAnswer,
                                    @RequestParam("currentQuestion") int currentQuestion,
                                    @RequestParam("score") int score,
                                    Model model) {
        return quizService.evaluateAnswer(selectedCapital, correctAnswer, currentQuestion, score, model);
    }
}
