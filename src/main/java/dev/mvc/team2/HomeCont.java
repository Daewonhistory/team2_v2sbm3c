package dev.mvc.team2;

import dev.mvc.customer.CustomerVO;
import dev.mvc.owner.OwnerVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
  @RequestMapping("/mobile")

  public String mobile() {
    return "mobile";
  }

  @GetMapping("/sign-up")

  public String publicCreateForm(Model model, CustomerVO customerVO) {


    return "/custowner/pubcreate";
  }


  @GetMapping("/register")

  public String publiclogin(Model model, CustomerVO customerVO) {


    return "/custowner/register";
  }




}
