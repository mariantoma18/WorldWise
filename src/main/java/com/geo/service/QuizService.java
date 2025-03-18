package com.geo.service;

import com.geo.model.Country;
import com.geo.model.QuizData;
import java.util.*;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

@Service
public class QuizService {

  Random random = new Random();
  @Getter
  private final int TOTAL_QUESTIONS = 10;
  @Getter
  private final int POINTS_FOR_CORRECT_ANSWER = 100;

  private final String QUIZ_API_URL = "https://restcountries.com/v3.1/all?fields=name,capital";

  public List<Country> getAllInfo() {
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<Country[]> response = restTemplate.getForEntity(QUIZ_API_URL, Country[].class);

    return Arrays.asList(response.getBody());
  }

  public QuizData getQuizData() {
    List<Country> countryList = getAllInfo();
    List<String> capitalOptions = new ArrayList<>();

    int randomValue = random.nextInt(countryList.size());
    Country country = countryList.get(randomValue);

    while (country.getCapital() == null || country.getCapital().isEmpty()) {
      randomValue = random.nextInt(countryList.size());
      country = countryList.get(randomValue);
    }

    String countryName = country.getName().getCommon();
    String correctAnswer = country.getCapital().get(0);

    capitalOptions.add(correctAnswer);

    addWrongCapitalOptionsFromList(capitalOptions, countryList, correctAnswer);

    Collections.shuffle(capitalOptions);
    return new QuizData(countryName, capitalOptions, correctAnswer);
  }

  public void addWrongCapitalOptionsFromList(
      List<String> capitalOptions, List<Country> countryList, String correctCapital) {
    while (capitalOptions.size() < 3) {
      int randomNumber = random.nextInt(countryList.size());
      Country country = countryList.get(randomNumber);

      validateCapitalOptions(capitalOptions, correctCapital, country);
    }
  }

  public void validateCapitalOptions(
      List<String> capitalOptions, String correctCapital, Country country) {
    if (country.getCapital() != null && !country.getCapital().isEmpty()) {
      String capital = country.getCapital().get(0);
      if (!capitalOptions.contains(capital) && !capital.equals(correctCapital)) {
        capitalOptions.add(capital);
      }
    }
  }

  public boolean checkAnswer(String userAnswer, String correctAnswer) {
    return userAnswer != null && userAnswer.equals(correctAnswer);
  }

  public String evaluateAnswer(String selectedCapital, String correctAnswer, int currentQuestion, int score, Model model) {

    boolean isCorrect = checkAnswer(selectedCapital, correctAnswer);

    score = verifyAnswer(score, model, isCorrect);

    return handleQuizProgress(currentQuestion, score, model);
  }

  public String handleQuizProgress(int currentQuestion, int score, Model model) {
    if(currentQuestion == TOTAL_QUESTIONS) {

      model.addAttribute("finalScore", score);
      return "quizFinalScore";
    } else {
      currentQuestion++;

      QuizData quizData = getQuizData();
      model.addAttribute("quiz", quizData.getCountryName());
      model.addAttribute("options", quizData.getCountryCapitalOptions());
      model.addAttribute("correctAnswer", quizData.getCorrectAnswer());
      model.addAttribute("currentQuestion", currentQuestion);
      model.addAttribute("score", score);
      model.addAttribute("totalQuestions", TOTAL_QUESTIONS);
      return "quiz";
    }
  }

  public int verifyAnswer(int score, Model model, boolean isCorrect) {
    if (isCorrect) {
      score += POINTS_FOR_CORRECT_ANSWER;
      model.addAttribute("message", "✅ Correct! Well done!");
    } else {
      model.addAttribute("message", "❌ Incorrect.");
    }
    return score;
  }
}
