package com.geo.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizData {

    private String countryName;
    private List<String> countryCapitalOptions;
    private String correctAnswer;
}
