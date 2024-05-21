package dev.mvc.restaurant;

import dev.mvc.certifi.CertifiProInter;
import dev.mvc.certifi.CertifiVO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@Controller
@RequestMapping("/restaurant")
public class RestaurantCont {


  @Autowired
  @Qualifier("dev.mvc.restaurant.RestaurantProc")
  private RestaurantProInter restaurantProc;

  @Autowired
  @Qualifier("dev.mvc.certifi.CertifiProc")
  private CertifiProInter certifiDAO;


  @Autowired
  private Security security;

  public RestaurantCont() {
//    System.out.println("RestaurantCont created");
  }


  /*
   * 식당 등록 폼 메서드
   *
   *
   */

  @GetMapping("/create")
  public String create(Model model,RestaurantVO restaurantVO,HttpSession session) {
    String type = (String) session.getAttribute("type");

    if (type == null) {
      return "redirect:/";
    } else {
      return "restaurant/create";
    }

  }


  @PostMapping("/create")
  public String restaurant(Model model,RedirectAttributes redirectAttributes,RestaurantVO restaurantVO) {


    int count = this.restaurantProc.create(restaurantVO);

    if (count ==1) {
      return "redirect:/";
    } else {
      return "redirect:/restaurant/create";
    }


  }
}



