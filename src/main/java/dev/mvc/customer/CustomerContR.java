package dev.mvc.customer;

import dev.mvc.allergy.AllergyProcInter;
import dev.mvc.allergy.AllergyVO;
import dev.mvc.category.CategoryVO;
import dev.mvc.customerhistory.CustomerHistoryProcInter;
import dev.mvc.customerhistory.CustomerHistoryVO;
import dev.mvc.dto.HistoryDTO;
import dev.mvc.ingredient.IngredientProcInter;
import dev.mvc.ingredient.IngredientVO;
import dev.mvc.tool.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;


@RestController
@RequestMapping("/customer/api")
public class CustomerContR {

  @Autowired
  @Qualifier("dev.mvc.customer.CustomerProc")
  private CustomerProInter customerProc;

  @Autowired
  @Qualifier("dev.mvc.customerhistory.CustomerHistoryProc")
  private CustomerHistoryProcInter historyproc;

  @Autowired
  @Qualifier("dev.mvc.allergy.AllergyProc")
  private AllergyProcInter allergyProc;

  @Autowired
  @Qualifier("dev.mvc.ingredient.IngredientProc")
  private IngredientProcInter ingredientProc;

  @Autowired
  private SmsTool smsTool;

  @Autowired
  private EmailTool emailTool;

  @Autowired
  private Security security;



  public CustomerContR() {
//    System.out.println("CustomerCont created");
  }


  @PostMapping("/check-login-status")
  public ResponseEntity<HashMap<String,Boolean>> logincheck(HttpSession session) {
    CustomerVO customerVO = (CustomerVO) session.getAttribute("customerVO");
    HashMap<String,Boolean> map = new HashMap<>();
    if (customerVO == null) {
      map.put("isLoggedIn", false);
    }else {
      map.put("isLoggedIn", true);
    }

    return ResponseEntity.ok(map);

  }



}

