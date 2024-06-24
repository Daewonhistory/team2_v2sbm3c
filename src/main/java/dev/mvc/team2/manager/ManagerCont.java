package dev.mvc.team2.manager;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@CrossOrigin("*")

@RequestMapping("/manager")
public class ManagerCont {

  public ManagerCont() {
    System.out.println("--ManagerCont--");
  }




  @GetMapping(value = {"", "/manager/**"})
  public String forward() {
    return "/layout";
  }

}
