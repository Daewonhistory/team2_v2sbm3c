package dev.mvc.team2;

import dev.mvc.category.CategoryProcInter;
import dev.mvc.category.CategoryVO;
import dev.mvc.customer.CustomerVO;
import dev.mvc.dto.RestFullData;
import dev.mvc.midarea.MidAreaProcInter;
import dev.mvc.midarea.MidAreaVO;
import dev.mvc.owner.OwnerVO;
import dev.mvc.restaurant.RestaurantProInter;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;


@Controller
public class HomeCont {
  @Autowired
  @Qualifier("dev.mvc.midarea.MidAreaProc")
  MidAreaProcInter midAreaProc;

  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc")
  CategoryProcInter categoryProc;

  @Autowired
  @Qualifier("dev.mvc.restaurant.RestaurantProc")
  private RestaurantProInter restaurantProc;


  public HomeCont() {

  }

//  @GetMapping("/")
//  public String Home(Model model) {
//    ArrayList<MidAreaVO> midAreaList = this.midAreaProc.list_all();
//    ArrayList<CategoryVO> categoryList = this.categoryProc.list();
//    ArrayList<RestFullData> restFullData = this.restaurantProc.ranking_rate_select();
//    model.addAttribute("restFullData", restFullData);
//    model.addAttribute("midAreaList", midAreaList);
//    model.addAttribute("categoryList", categoryList);
//    return "mobile";
//  }

  @GetMapping("/")
  public String home(Model model) {
    return "forward:/index.html";
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
  public String modal(Model model) {
    ArrayList<CategoryVO> list = this.categoryProc.list();
    model.addAttribute("list", list);
    return "/modal";
  }

//  @GetMapping("/error")
//  public String handleError() {
//    // 에러 페이지로 이동
//    return "error"; // error.html로 이동
//  }

}