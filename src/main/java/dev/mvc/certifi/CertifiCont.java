package dev.mvc.certifi;

import dev.mvc.category.CategoryVO;
import dev.mvc.restaurant.RestaurantProInter;
import dev.mvc.restaurant.RestaurantVO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/certifi")
public class CertifiCont {


  @Autowired
  @Qualifier("dev.mvc.restaurant.RestaurantProc")
  private RestaurantProInter restaurantProc;

  @Autowired
  @Qualifier("dev.mvc.certifi.CertifiProc")
  private CertifiProInter certifiDAO;


  @Autowired
  private Security security;

  public CertifiCont() {
//    System.out.println("RestaurantCont created");
  }


  /*
   * 식당 등록 폼 메서드
   *
   *
   */

  @GetMapping("/create")
  public String create(Model model, RestaurantVO restaurantVO, HttpSession session) {
    String type = (String) session.getAttribute("type");

    if (type == null) {
      return "redirect:/";
    } else {
      return "owner/create";
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

  @PostMapping("/list")
  @ResponseBody
  public ResponseEntity<ArrayList<CertifiVO>> catelist(@RequestParam int ownerno) {

    ArrayList<CertifiVO> list = this.certifiDAO.businessnoList(ownerno);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }
}




