package com.geo.controller;

import com.geo.service.InfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
@RequiredArgsConstructor
public class InfoController {

  private final InfoService infoService;

  @GetMapping()
  public String getInfoPage(Model model) {
    model.addAttribute("info", infoService.getAllInfo());
    return "info";
  }
}
