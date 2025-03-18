package com.geo.model;

import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Country {

  private Name name;
  private Map<String, Currency> currencies;
  private List<String> capital;
  private Map<String, String> languages;
  private Double population;
  private String formattedPopulation;
}
