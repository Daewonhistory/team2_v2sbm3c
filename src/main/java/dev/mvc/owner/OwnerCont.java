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


  @PostMapping("/checkID")  //http:localhost:9091/meber/checkId?id=admin
  @ResponseBody
  public String checkId(String id) {
    System.out.println("-> id  " + id);

    int cnt = this.ownerProc.checkID(id);

    //{"cnt": cnt}
    return "{\"cnt\":" + cnt + "}";
  }

  @PostMapping("/checkNickName")  //http:localhost:9091/meber/checkId?id=admin
  @ResponseBody
  public String checkNickname(String nickname) {
    System.out.println("-> nickname  " + nickname);

    int cnt = this.ownerProc.checkNickName(nickname);

    //{"cnt": cnt}
    return "{\"cnt\":" + cnt + "}";
  }



  @GetMapping("/create")

  public String createForm(Model model, OwnerVO ownerVO) {


    return "/owner/create";
  }

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


  @GetMapping("/login")

    public String loginForm (Model model, CustomerVO customerVO, HttpSession session, HttpServletRequest request){

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


    @GetMapping("/certi")
    public String certi(OwnerVO ownerVO){



      return "owner/certi";
    }
}

//  @GetMapping("/list")
//  public String list_owner(Model model, CustomerVO ownerVO, HttpSession session, RedirectAttributes rttr) {
//
//    String id = (String) session.getAttribute("id");
//
//    if (this.ownerProc.isCustomerAdmin(session)) {
//      ArrayList<CustomerVO> list = this.ownerProc.list();
//      model.addAttribute("list", list);
//      return "owner/ownerList";
//    } else {
//      rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
//      return "redirect:/";
//    }
//
//
//  }
//
//  /**
//   * 조히
//   *
//   * @param model
//   * @param ownerno 회원 번호
//   * @return
//   */
//  @GetMapping("/read")
//  public String read(Model model, @RequestParam(name = "ownerno") Integer ownerno, HttpSession session,
//                     RedirectAttributes rttr) {
//    System.out.println(ownerno);
//    CustomerVO read = this.ownerProc.read(ownerno);
//
//    String id = (String) session.getAttribute("id");
//
//
//
//    if (this.ownerProc.isCustomerAdmin(session)) {
//      if (read != null) {
//
//        model.addAttribute("ownerVO", read);
//        return "owner/read";
//      } else {
//        rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
//        return "redirect:/owner/list";
//      }
//    }else{
//        rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
//        return "redirect:/";
//      }
//
//
//    }
//
//
//    @PostMapping("/update")
//
//    public String updateowner (Model model, CustomerVO ownerVO, RedirectAttributes rrtr,HttpSession session){
//
//      if (this.ownerProc.isCustomerAdmin(session)) {
//        int check_ID = this.ownerProc.checkID(ownerVO.getId());
//
//        if (ownerVO.getTel() == null || ownerVO.getTel().equals("")) {
//          ownerVO.setTel("010-0000-0000");
//        }
//        int count = ownerProc.update(ownerVO);
//        System.out.println(check_ID);
//        System.out.println(ownerVO.getId());
//        if (check_ID == 1) {
//          if (count == 1) {
//
//
//            rrtr.addFlashAttribute("success", 1);
//            rrtr.addFlashAttribute("come", ownerVO.getMname() + "님 수정 완료 되었습니다! ");
//
//            return "redirect:/owner/read?ownerno=" + ownerVO.getCustomerno();
//          } else {
//            rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");
//
//
//            return "redirect:/owner/read?ownerno=" + ownerVO.getCustomerno();
//          }
//        } else {
//          rrtr.addFlashAttribute("fail", "아이디 중복입니다 다시 만들어주세요 ");
//          return "redirect:/owner/create";
//        }
//      } else  {
//        rrtr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
//        return "redirect:/owner/list";
//      }
//
//
//    }
//
//    @PostMapping("/delete")
//    public String delete (Model model, @RequestParam("ownerno") Integer ownerno,
//      @RequestParam("mname") String mname,HttpSession session,
//      RedirectAttributes rttr){
//      if (this.ownerProc.isCustomerAdmin(session)) {
//        int count = this.ownerProc.delete(ownerno);
//
//
//        if (count == 1) {
//          rttr.addFlashAttribute("delete", mname + "'이 삭제되었습니다.");
//          return "redirect:/owner/list";
//        } else {
//          rttr.addFlashAttribute("delete", "삭제 실패");
//          return "redirect:/cate/search";
//        }
//      }else {
//        return "redirect:/owner/login";
//      }
//    }
//
//    /**
//     * 로그인 폼
//     *
//     * @param model
//     * @param ownerVO
//     * @param session
//     * @param request
//     * @return
//     */
//
//
//
//
//    @GetMapping("/update_password")
//    public String update_password (Model model, HttpSession session, RedirectAttributes rttr){
//      Integer ownerno = (Integer) session.getAttribute("ownerno");
//
//      if (ownerProc.isCustomer(session)) {
//        CustomerVO read = this.ownerProc.read(ownerno);
//        if (read == null) {
//          return "redirect:/owner/list";
//        } else {
//          model.addAttribute("ownerVO", read);
//          return "owner/update_password";
//        }
//      } else {
//        rttr.addFlashAttribute("Abnormal", "로그인이 필요합니다");
//        return "redirect:/owner/login";
//      }
//
//
//    }
//
//    @PostMapping("/checkpassword")
//    @ResponseBody
//    public ResponseEntity<Map<String, Object>> checkPassword (@RequestBody String json_src, HttpSession session){
//      HashMap<String, Object> map = new HashMap<>();
//      JSONObject src = new JSONObject(json_src);
//      String pastpasswd = src.getString("pastpasswd");
//      Integer ownerno = (Integer) session.getAttribute("ownerno");
//
//      map.put("passwd", this.security.aesEncode(pastpasswd));
//      map.put("ownerno", ownerno);
//
//      int check = this.ownerProc.passwd_check(map);
//
//      Map<String, Object> result = new HashMap<>();
//      result.put("check", check);
//
//      return new ResponseEntity<>(result, HttpStatus.OK);
//    }
//
//
//    // 여기서 비밀번호 일치 여부를 확인하고 결과를 반환합니다.
//
//
//    @PostMapping("/update_password")
//    public String updatepass (Model model, CustomerVO ownerVO,
//      String pastpasswd,
//      String passwd1,
//      String passwd2,
//      RedirectAttributes rrtr,
//      HttpSession session
//  ){
//      if (this.ownerProc.isCustomer(session)) {
//        HashMap<String, Object> mapcheck = new HashMap<String, Object>();
//
//
//        mapcheck.put("passwd", this.security.aesEncode(pastpasswd));
//        mapcheck.put("ownerno", ownerVO.getCustomerno());
//
//
//        int check_pass = this.ownerProc.passwd_check(mapcheck);
//
//
//        HashMap<String, Object> map = new HashMap<String, Object>();
//
//        map.put("passwd", this.security.aesEncode(passwd2));
//        map.put("ownerno", ownerVO.getCustomerno());
//        int count = ownerProc.passwd_update(map);
//
//
//        if (check_pass == 1 && passwd1.equals(passwd2) &&
//          !pastpasswd.equals(passwd1) && !pastpasswd.equals(passwd2) &&
//          passwd1.trim().length() > 3 && passwd2.trim().length() > 3) {
//          if (count == 1) {
//
//
//            String mname = (String) session.getAttribute("mname");
//            rrtr.addFlashAttribute("success", 1);
//            rrtr.addFlashAttribute("update", mname + "님 패스워드 수정이 완료되었습니다 ");
//            session.invalidate();
//            return "redirect:/";
//          } else {
//            rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");
//            return "redirect:/owner/update_password";
//          }
//        } else {
//          rrtr.addFlashAttribute("fail", "알수없는 오류 입니다. ");
//          return "redirect:/owner/update_password";
//        }
//      } else {
//        rrtr.addFlashAttribute("fail", "로그인이 필요합니다. ");
//        return "redirect:/owner/login";
//
//      }
//
//    }
//
//    @GetMapping("/my_page")
//    public String mypage (Model model, HttpSession session, RedirectAttributes rttr){
//
//
//      if (this.ownerProc.isCustomer(session)) {
//        String id = (String) session.getAttribute("id");
//        CustomerVO ownerVO = this.ownerProc.readById(id);
//
//        model.addAttribute("ownerVO", ownerVO);
//
//        return "/owner/my_page";
//
//      } else {
//        rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
//        return "redirect:/";
//      }
//    }
//
//    @GetMapping("/logout")
//    public String logout (HttpSession session, RedirectAttributes redirectAttributes){
//      // 세션을 무효화합니다.
//      session.invalidate();
//
//      // 로그아웃 성공 메시지(선택 사항)
//      redirectAttributes.addFlashAttribute("logout", "로그아웃 되었습니다.");
//
//      // 홈 페이지로 리다이렉트합니다.
//      return "redirect:/";
//
//    }
//
//
//    @GetMapping("/update_grade")
//    public String update_gradeForm (Model model, Integer ownerno, HttpSession session, RedirectAttributes rttr){
//
//      if (ownerno == null) {
//        rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
//        return "redirect:/";
//      }
//
//      CustomerVO read = this.ownerProc.read(ownerno);
//      if (read == null) {
//        return "redirect:/owner/list";
//      } else {
//        model.addAttribute("ownerVO", read);
//        return "owner/update_grade";
//      }
//
//    }
//
//    @PostMapping("update_grade")
//    public String updategrade (Model model, CustomerVO ownerVO,
//      Integer grade, Integer ownerno,
//
//      RedirectAttributes rrtr,
//      HttpSession session
//  ){
//
//
//      HashMap<String, Object> map = new HashMap<String, Object>();
//
//
//      map.put("grade", grade);
//      map.put("ownerno", ownerno);
//      int count = this.ownerProc.update_grade(map);
//
//
//      if (count == 1) {
//
//
//        rrtr.addFlashAttribute("success", 1);
//        rrtr.addFlashAttribute("update", "등급  수정이 완료되었습니다 ");
//
//        return "redirect:/owner/list";
//      } else {
//        rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");
//        return "redirect:/owner/update_grade";
//      }
//
//    }
//  }

//-------------------------------------------------------------------
// 코드 시작
//-------------------------------------------------------------------

