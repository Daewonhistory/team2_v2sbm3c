package dev.mvc.team2.manager;


import dev.mvc.category.CategoryVO;
import dev.mvc.customer.Customer;
import dev.mvc.customer.CustomerProInter;
import dev.mvc.customer.CustomerVO;
import dev.mvc.tool.CityUtils;
import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/manager")
public class ManagerRestCont {


  @Autowired
  private ManagerService managerService;

  @Autowired
  @Qualifier("dev.mvc.customer.CustomerProc")
  private CustomerProInter customerProc;

  public ManagerRestCont() {
    System.out.println("--ManagerCont--");
  }


  @GetMapping("/customer")
  public ResponseEntity<Map<String,Object>> selectCustomerlist(HttpSession session,
                                                               @RequestParam(name = "type", defaultValue = "100") String type,
                                                               @RequestParam(name = "word", defaultValue = "")   String word,
                                                               CategoryVO categoryVO,
                                                               @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
    String id = (String) session.getAttribute("id");
    String grade = (String) session.getAttribute("grade");   if (now_page < 1) {
      now_page = 1;
    }

    word = Tool.wordcheckNull(word);
    type = Tool.wordcheckNull(type);
    String types = "";

    if (type.equals("100")) {
      types = "이름";
    } else if (type.equals("200")) {
      types = "아이디";
    } else {
      types = "이름 + 아이디";
    }

    int count = this.customerProc.list_search_count(word, type);
    int num = count - ((now_page - 1) * Customer.RECORD_PER_PAGE);
    ArrayList<CustomerVO> custlist = this.customerProc.list_search_paging(word, type, now_page, Customer.RECORD_PER_PAGE);
    String paging = this.customerProc.pagingBox(now_page, word, type, "/customer/list", count, Customer.RECORD_PER_PAGE, Customer.PAGE_PER_BLOCK);
    int totalPages = (int) Math.ceil((double) count /  Customer.RECORD_PER_PAGE);
    Map<String, Object> response = new HashMap<>();
    response.put("users", custlist);
    response.put("totalPages", totalPages);

    if (custlist.isEmpty()) {
      return ResponseEntity.status(HttpStatus.OK)
              .body(null); // or any appropriate message or object
    } else {
      return ResponseEntity.status(HttpStatus.OK)
              .body(response);
    }
  }
}


