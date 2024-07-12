package dev.mvc.owner;

import dev.mvc.allergy.AllergyProcInter;
import dev.mvc.customer.CustomerProInter;
import dev.mvc.customer.CustomerVO;
import dev.mvc.customerhistory.CustomerHistoryProcInter;
import dev.mvc.ingredient.IngredientProcInter;
import dev.mvc.tool.EmailTool;
import dev.mvc.tool.Security;
import dev.mvc.tool.SmsTool;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;


@RestController
@RequestMapping("/owner/api")
public class OwnerContR {

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



  public OwnerContR() {
//    System.out.println("CustomerCont created");
  }


  @PostMapping("/check-login-status")
  public ResponseEntity<HashMap<String,Boolean>> logincheck(HttpSession session) {
    OwnerVO ownerVO = (OwnerVO) session.getAttribute("ownerVO");
    HashMap<String,Boolean> map = new HashMap<>();
    if (ownerVO == null) {
      map.put("isOwnerLoggedIn", false);
    }else {
      map.put("isOwnerLoggedIn", true);
    }

    return ResponseEntity.ok(map);

  }



}

