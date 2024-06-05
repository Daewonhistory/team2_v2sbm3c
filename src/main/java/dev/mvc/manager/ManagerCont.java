package dev.mvc.manager;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manager")
public class ManagerCont {


  @GetMapping("")
  public String managerHome() {

    return "/layout";
  }
}
