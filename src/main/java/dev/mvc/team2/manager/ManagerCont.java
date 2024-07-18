package dev.mvc.team2.manager;


import dev.mvc.admitperson.AdmitPersonProcInter;
import dev.mvc.allergy.AllergyVO;
import dev.mvc.customer.CustomerVO;
import dev.mvc.customerhistory.CustomerHistoryVO;
import dev.mvc.dto.ReserveDTO;
import dev.mvc.ingredient.IngredientVO;
import dev.mvc.reserve.Reserve;
import dev.mvc.reserve.ReserveProcInter;
import dev.mvc.restaurant.RestaurantProInter;
import dev.mvc.tool.CityUtils;
import dev.mvc.tool.ClientUtils;
import dev.mvc.tool.IpLocationService;
import dev.mvc.tool.Security;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Controller
@CrossOrigin("*")

@RequestMapping("/manager")
public class ManagerCont {

  @Autowired
  private Security security;

  @Autowired
  private ManagerService managerService;


  @Autowired
  private ManagerRepository managerRepository;

  @Autowired
  @Qualifier("dev.mvc.admitperson.AdmitPersonProc")
  private AdmitPersonProcInter admitPersonProc;

  @Autowired
  @Qualifier("dev.mvc.restaurant.RestaurantProc")
  private RestaurantProInter restaurantProc;

  @Autowired
  @Qualifier("reserveProc")
  private ReserveProcInter reserveProc;


  public ManagerCont() {
    System.out.println("--ManagerCont--");
  }



  @PostMapping("/check_id")
  public ResponseEntity<HashMap<String,Object>> checkID(@RequestParam String id) {
    int cnt = this.managerService.checkId(id);

    System.out.println("cnt- >" + cnt);
    HashMap<String,Object> map = new HashMap<String,Object>();
    if (cnt == 0) {
      map.put("cnt", 1);
    } else {
      map.put("cnt", 0);

    }


    return ResponseEntity.ok(map);
  }


  @GetMapping("")
  public String forward(HttpSession session, Model model) {

    String type = (String) session.getAttribute("type");
    model.addAttribute("accessType", type);

    if (type != null && type.equals("owner") ) {
      Integer ownerno = (Integer) session.getAttribute("ownerno");
      Integer restno = this.restaurantProc.foreign(ownerno);


      model.addAttribute("restno", restno);
    }
//    if (type == "manager") {
      return "/layout";
//    } else {
//      return "redirect:/";
//    }



  }

  @GetMapping("/create")

  public String createForm(Model model) {

    // 알러지 재료명 목록 추가



    return "/manager/create";
  }


  @PostMapping("/create")
  public String createcustomer(Model model, Manager manager,
                               @RequestParam(name = "ingredno[]", required = false) ArrayList<Integer> ingrednoList, RedirectAttributes rrtr) {

    int check_ID = this.managerService.checkId(manager.getId());
    System.out.println("->" + manager.getId());




    System.out.println("check_ID -> " + check_ID);
    if (check_ID == 0) {

      String regDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
      manager.setRegDate(regDate);

      manager.setPasswd(this.security.aesEncode(manager.getPasswd()));
      int count = managerService.save(manager);
      System.out.println("count -> " + count);
      if (count == 1) {
        rrtr.addFlashAttribute("success", 1);
        rrtr.addFlashAttribute("come", manager.getMname() + "님 회원가입 축하드립니다! ");
        return "redirect:/manager/login";
      } else {
        rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");
        return "redirect:/manager/create";
      }
    } else {
      rrtr.addFlashAttribute("fail", "아이디 중복입니다 다시 만들어주세요 ");
      return "redirect:/customer/create";
    }
  }


  @GetMapping("/login")

  public String loginForm(Model model, Manager manager, HttpSession session, HttpServletRequest request) {

    // Cookie 관련 ----------------------------------------------
    Cookie[] cookies = request.getCookies();
    Cookie cookie = null;

    String ck_id = ""; // id 저장
    String ck_id_save = ""; // id 저장 여부를 체크
    String ck_passwd = ""; // passwd 저장
    String ck_passwd_save = ""; // passwd 저장 여부를 체크

    if (cookies != null) { // 쿠키가 존재한다면
      for (int i = 0; i < cookies.length; i++) {
        cookie = cookies[i]; // 쿠키 객체 추출

        if (cookie.getName().equals("ck_id")) {
          ck_id = cookie.getValue();
        } else if (cookie.getName().equals("ck_id_save")) {
          ck_id_save = cookie.getValue();  // Y, N
        } else if (cookie.getName().equals("ck_passwd")) {
          ck_passwd = cookie.getValue();         // 1234
        } else if (cookie.getName().equals("ck_passwd_save")) {
          ck_passwd_save = cookie.getValue();  // Y, N
        }
      }
    }
    // Cookie 관련 ----------------------------------------------


    //    <input type='text' class="form-control" name='id' id='id'
    //            value='${ck_id }' required="required"
    //            style='width: 30%;' placeholder="아이디" autofocus="autofocus">
    model.addAttribute("ck_id", ck_id);

    //    <input type='checkbox' name='id_save' value='Y'
    //            ${ck_id_save == 'Y' ? "checked='checked'" : "" }> 저장
    model.addAttribute("ck_id_save", ck_id_save);

    model.addAttribute("ck_passwd", ck_passwd);
    model.addAttribute("ck_passwd_save", ck_passwd_save);


    String id = (String) session.getAttribute("id");

    if (id == null) {
      return "manager/login_cookie";
    } else {
      return "redirect:/";
    }

    //-------------------------------------------------------------------
    // 쿠키 코드 종료
    //-------------------------------------------------------------------

  }


  @PostMapping("/login")
  public String login(
          String id,
          String passwd,
          @RequestParam(value = "id_save", defaultValue = "") String id_save,
          @RequestParam(value = "passwd_save", defaultValue = "") String passwd_save,
          HttpSession session,
          HttpServletResponse response,
          HttpServletRequest request,
          RedirectAttributes rttr


  ) {


    Integer cnt = this.managerService.login(id, this.security.aesEncode(passwd));

    System.out.println(cnt);
    if (cnt == 1) {
      Manager manager = this.managerService.readById(id);


      session.setAttribute("managerno", manager.getManagerno());
      session.setAttribute("Manager", manager);
      session.setAttribute("id", manager.getId());
      session.setAttribute("mname", manager.getMname());

      String type = "";
      String grade = "";

      switch (manager.getGrade()) {
        case 1:
          type = "Master"; //최고 관리자
          grade = "Master";
          break;
        case 10:
          type = "Admin"; // 회원 관리자
          grade = "Admin";
          break;
        case 20:
          type = "Manager"; // 카테고리 관리자
          grade = "Manager";
          break;
        case 99:
          type = "Stop"; // 정지 관리자
          grade = "Stop";
          break;
        default:
          type = "Unknown"; // 알 수 없음
          grade = "Unknown";
      }

      session.setAttribute("type", type);
      session.setAttribute("grade", grade);

      rttr.addFlashAttribute("login", manager.getMname() + "님 안녕하세요");

      // ID 관련 쿠키 저장
      Cookie ck_id = new Cookie("ck_id", id_save.equals("Y") ? id : "");
      ck_id.setPath("/");
      ck_id.setMaxAge(id_save.equals("Y") ? 60 * 60 * 24 * 30 : 0);
      response.addCookie(ck_id);

      Cookie ck_id_save = new Cookie("ck_id_save", id_save);
      ck_id_save.setPath("/");
      ck_id_save.setMaxAge(60 * 60 * 24 * 30);
      response.addCookie(ck_id_save);

      // Password 관련 쿠키 저장
      Cookie ck_passwd = new Cookie("ck_passwd", passwd_save.equals("Y") ? passwd : "");
      ck_passwd.setPath("/");
      ck_passwd.setMaxAge(passwd_save.equals("Y") ? 60 * 60 * 24 * 30 : 0);
      response.addCookie(ck_passwd);

      Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
      ck_passwd_save.setPath("/");
      ck_passwd_save.setMaxAge(60 * 60 * 24 * 30);
      response.addCookie(ck_passwd_save);

      return "redirect:/manager";
    } else {
      rttr.addFlashAttribute("error", "아이디 또는 비밀번호 오류입니다.");
      return "redirect:/manager/login";
    }
  }


  @PostMapping("/logout")
  public String logout(HttpSession session) {
    session.invalidate();
    return "redirect:/manager";
  }






}
