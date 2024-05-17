package dev.mvc.team2;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeCont {

  public HomeCont() {

  }

  @RequestMapping("/")
  public String Home() {
    return "index";
  }
}
