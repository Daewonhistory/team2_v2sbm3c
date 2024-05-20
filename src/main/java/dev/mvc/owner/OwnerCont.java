package dev.mvc.owner;

import dev.mvc.customer.CustomerVO;
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

import java.util.HashMap;
import java.util.Random;

@Controller
@RequestMapping("/owner")
public class OwnerCont {


  @Autowired
  @Qualifier("dev.mvc.owner.OwnerProc")
  private OwnerProInter ownerProc;


  @Autowired
  private Security security;

  public OwnerCont() {
//    System.out.println("CustomerCont created");
  }


  /**
   * 사업자 ID 중복확인 메서드
   * @param id
   * @return cnt 성공여부
   */
  @PostMapping("/checkID")  //http:localhost:9091/meber/checkId?id=admin
  @ResponseBody
  public String checkId(String id) {
    System.out.println("-> id  " + id);

    int cnt = this.ownerProc.checkID(id);

    //{"cnt": cnt}
    return "{\"cnt\":" + cnt + "}";
  }



  /**
   * 회원 가입 폼 메서드
   * @param model
   * @param ownerVO
   * @return
   */
  @GetMapping("/create")

  public String createForm(Model model, OwnerVO ownerVO) {


    return "/owner/create";
  }

  /**
   *
   * 사업자 회원가입 처리 메서드
   * @param model
   * @param ownerVO
   * @param rrtr
   * @return
   */
  @PostMapping("/create")

  public String createowner(Model model, OwnerVO ownerVO, RedirectAttributes rrtr) {


    int check_ID = this.ownerProc.checkID(ownerVO.getId());


    System.out.println("check_ID -> "+check_ID);
    if (check_ID == 0) {
      Random random = new Random();
      int randomNumber = random.nextInt(5) + 1; // 1부터 5까지의 랜덤한 숫자 생성
      String imageName = "ownerbasic" + randomNumber + ".jpg";
      ownerVO.setImage(imageName);

      ownerVO.setGrade(20);
      int count = ownerProc.create(ownerVO);
      System.out.println("count -> "+count);
      if (count == 1) {

        rrtr.addFlashAttribute("success", 1);
        rrtr.addFlashAttribute("come", ownerVO.getOname() + "님 회원가입 축하드립니다! 사업자가 인증 완료되면 이용 가능합니다 ");
        return "redirect:/owner/login";
      } else {
        rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");
        return "redirect:/owner/create";
      }
    } else {
      rrtr.addFlashAttribute("fail", "아이디 중복입니다 다시 만들어주세요 ");
      return "redirect:/owner/create";
    }

  }


  /**
   * 로그인 폼
   * @param model
   * @param ownerVO
   * @param session
   * @param request
   * @return 로그인 경로
   */
  @GetMapping("/login")

    public String loginForm (Model model, OwnerVO ownerVO, HttpSession session, HttpServletRequest request){

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
        return "/owner/login_cookie";
      } else {
        return "redirect:/";
      }

      //-------------------------------------------------------------------
      // 쿠키 코드 종료
      //--------------------------------------  -----------------------------

    }

    /**
     * 쿠키 기반 로그인 처리
     *
     * @param model
     * @param id          아이디
     * @param passwd      패스워드
     * @param id_save     아이디 저장여부
     * @param passwd_save 패스워드 저장여부
     * @param session     세션
     * @param response    응답
     * @param request     요청
     * @param rttr        리아랙트
     * @return
     */
    @PostMapping("/login")

    public String login (Model model,
      String id, String passwd,
      @RequestParam(value = "id_save", defaultValue = "") String id_save,
      @RequestParam(value = "passwd_save", defaultValue = "") String passwd_save,
      HttpSession session,
      HttpServletResponse response,
      HttpServletRequest request,
      RedirectAttributes rttr
  ){


      HashMap<String, Object> map = new HashMap<String, Object>();
      map.put("id", id);
      map.put("passwd", this.security.aesEncode(passwd));

      int cnt = this.ownerProc.login(map);
      System.out.println(cnt);
      if (cnt == 1) {
        OwnerVO ownerVO = this.ownerProc.readById(id);
        if (id_save.equals("Y")) { // id를 저장할 경우, Checkbox를 체크한 경우
          Cookie ck_id = new Cookie("ck_id", id);
          ck_id.setPath("/");  // root 폴더에 쿠키를 기록함으로 모든 경로에서 쿠기 접근 가능
          ck_id.setMaxAge(60 * 60 * 24 * 30); // 30 day, 초단위
          response.addCookie(ck_id); // id 저장
        } else { // N, id를 저장하지 않는 경우, Checkbox를 체크 해제한 경우
          Cookie ck_id = new Cookie("ck_id", "");
          ck_id.setPath("/");
          ck_id.setMaxAge(0);
          response.addCookie(ck_id); // id 저장
        }

        // id를 저장할지 선택하는  CheckBox 체크 여부
        Cookie ck_id_save = new Cookie("ck_id_save", id_save);
        ck_id_save.setPath("/");
        ck_id_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
        response.addCookie(ck_id_save);
        // -------------------------------------------------------------------

        // -------------------------------------------------------------------
        // Password 관련 쿠기 저장
        // -------------------------------------------------------------------
        if (passwd_save.equals("Y")) { // 패스워드 저장할 경우
          Cookie ck_passwd = new Cookie("ck_passwd", passwd);
          ck_passwd.setPath("/");
          ck_passwd.setMaxAge(60 * 60 * 24 * 30); // 30 day
          response.addCookie(ck_passwd);
        } else { // N, 패스워드를 저장하지 않을 경우
          Cookie ck_passwd = new Cookie("ck_passwd", "");
          ck_passwd.setPath("/");
          ck_passwd.setMaxAge(0);
          response.addCookie(ck_passwd);
        }
        // passwd를 저장할지 선택하는  CheckBox 체크 여부
        Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
        ck_passwd_save.setPath("/");
        ck_passwd_save.setMaxAge(60 * 60 * 24 * 30); // 30 day
        response.addCookie(ck_passwd_save);
        // -------------------------------------------------------------------
        // id를 이용하여 회원 정보 조회

        // session.setAttribute("grade", memverVO.getGrade());


        System.out.println("->" + ownerVO.getGrade());
        if (ownerVO.getGrade() == 20) {
          session.setAttribute("grade","NotCerti");
          rttr.addFlashAttribute("login", ownerVO.getOname() + "님 안녕하세요 사업자가 인증되면 컨텐츠에 접근할 수 있습니다");

          return "redirect:/owner/certi";
        } else  {
          session.setAttribute("ownerno", ownerVO.getOwnerno());
          session.setAttribute("id", ownerVO.getId());
          session.setAttribute("oname", ownerVO.getOname());
          return "redirect:/";
        }
      } else {
        rttr.addFlashAttribute("error", "아이디 또는 비밀번호 오류입니다.");
        return "redirect:/owner/login";
      }

    }

  /**
   * 사업자 인증 등록 폼 메서드
   * @param ownerVO
   * @return
   */
  @GetMapping("/certi")
    public String certi(OwnerVO ownerVO){



      return "owner/certi";
    }
}



