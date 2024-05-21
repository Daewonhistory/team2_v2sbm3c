package dev.mvc.team2;

import dev.mvc.customer.CustomerVO;
import dev.mvc.owner.OwnerVO;
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

  @GetMapping("/sign-up")

  public String publicCreateForm(Model model, CustomerVO customerVO) {


    return "/custowner/pubcreate";
  }


  @GetMapping("/register")

  public String publiclogin(Model model, CustomerVO customerVO) {


    return "/custowner/register";
  }




}
