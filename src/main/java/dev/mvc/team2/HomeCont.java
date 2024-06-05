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

  @GetMapping("/")
  public String Home() {
    return "index";
  }



  @RequestMapping("/mobile")
  public String mobile() {
    return "mobile";
  }

  @RequestMapping("/mobile2")
  public String mob() {
    return "mobile_layout";
  }
  @RequestMapping("/mobilel")
  public String mobl() {
    return "mobile_login";
  }
  @RequestMapping("/mobile3")
  public String moble() {
    return "mobile_login_info";
  }
  @RequestMapping("/search")
  public String search() {
    return "search";
  }
  @RequestMapping("/search_list")

  public String search_list() {
    return "search_list";
  }
  @RequestMapping("/restaurant_page")

  public String restaurant_page() {
    return "restaurant_page";
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
