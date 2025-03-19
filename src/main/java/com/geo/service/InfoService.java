package com.geo.service;

import com.geo.model.Country;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class InfoService {

  private final String INFO_API_URL =
      "https://restcountries.com/v3.1/all?fields=name,currencies,capital,languages,population";

  public List<Country> getAllInfo() {
    RestTemplate restTemplate = new RestTemplate();

    ResponseEntity<Country[]> response = restTemplate.getForEntity(INFO_API_URL, Country[].class);

    return formatPopulationToMillions(response);
  }

  public List<Country> formatPopulationToMillions(ResponseEntity<Country[]> response){
      List<Country> countryList = Arrays.asList(response.getBody());

      countryList.stream()
              .filter(country -> country.getPopulation() != null)
              .forEach(country -> {
                  double populationInMillions = country.getPopulation() / 1000000;
                  country.setFormattedPopulation(String.format("%.2f M", populationInMillions));
              });
      return countryList;
  }
}
