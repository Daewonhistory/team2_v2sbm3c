package dev.mvc.team2;

import dev.mvc.customer.CustomerVO;
import dev.mvc.midarea.MidAreaProcInter;
import dev.mvc.midarea.MidAreaVO;
import dev.mvc.owner.OwnerVO;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeCont {
  @Autowired
  @Qualifier("dev.mvc.midarea.MidAreaProc")
  MidAreaProcInter midAreaProc;
  
  public HomeCont() {

  }

  @GetMapping("/")
  public String Home(Model model) {
    ArrayList<MidAreaVO> midAreaList = this.midAreaProc.list_all();
	model.addAttribute("midAreaList", midAreaList);
    return "mobile";
  }

  @RequestMapping("/chatbot")
  public String chatbot() {
    return "chatbot";
  }

  @RequestMapping("/mobile2")
  public String mob() {
    return "mobile_layout";
  }
  
  @RequestMapping("/mobilel")
  public String mobl() {
    return "mobile_login";
  }

  @RequestMapping("/search")
  public String search() {
    return "search";
  }
  @RequestMapping("/search_list")

  public String search_list() {
    return "search_list";
  }

  @GetMapping("/sign-up")

  public String publicCreateForm(Model model, CustomerVO customerVO) {


    return "/custowner/pubcreate";
  }


  @GetMapping("/register")

  public String publiclogin(Model model, CustomerVO customerVO) {


    return "/custowner/register";
  }
  
  @GetMapping("/map")

  public String map(Model model) {
	

    return "/map";
  }

  @GetMapping("/modal")
  public String modal() {
	  return "/modal";
  }

}