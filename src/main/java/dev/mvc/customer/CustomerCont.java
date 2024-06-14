package dev.mvc.customer;

import dev.mvc.allergy.AllergyProcInter;
import dev.mvc.allergy.AllergyVO;
import dev.mvc.category.CategoryVO;
import dev.mvc.customerhistory.CustomerHistoryProcInter;
import dev.mvc.customerhistory.CustomerHistoryVO;
import dev.mvc.dto.HistoryDTO;
import dev.mvc.emailAuth.EmailAuthVO;
import dev.mvc.ingredient.IngredientProcInter;
import dev.mvc.ingredient.IngredientVO;
import dev.mvc.phoneAuth.PhoneAuthVO;
import dev.mvc.restaurant.Restaurant;
import dev.mvc.tool.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import net.nurigo.sdk.message.exception.NurigoEmptyResponseException;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.exception.NurigoUnknownException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;
import java.util.concurrent.CompletableFuture;


@Controller
@RequestMapping("/customer")
public class CustomerCont {

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



  public CustomerCont() {
//    System.out.println("CustomerCont created");
  }


  /**
   * 회원가입시 아이디 중복확인
   *
   * @param id
   * @return
   */
  @PostMapping("/checkID")  //http:localhost:9091/meber/checkId?id=admin
  @ResponseBody
  public String checkId(String id) {
    System.out.println("-> id  " + id);

    int cnt = this.customerProc.checkID(id);

    //{"cnt": cnt}
    return "{\"cnt\":" + cnt + "}";
  }

  /**
   * 회원가입시 이름 중복확인
   *
   * @param nickname
   * @return
   */
  @PostMapping("/checkNickName")  //http:localhost:9091/meber/checkId?id=admin
  @ResponseBody
  public String checkNickname(String nickname) {
    System.out.println("-> nickname  " + nickname);

    int cnt = this.customerProc.checkNickName(nickname);

    //{"cnt": cnt}
    return "{\"cnt\":" + cnt + "}";
  }

  @PostMapping("/checkNamePhone")  //http:localhost:9091/meber/checkId?id=admin
  @ResponseBody
  public ResponseEntity<HashMap<String,Object>> checkNamePhone(@RequestBody CustomerVO customerVO) {

    HashMap<String,Object> map = new HashMap<String,Object>();
    System.out.println(customerVO.getPhone() + "?");
    int count = this.customerProc.checkNamePhone(customerVO.getCname(), customerVO.getPhone());

    if (count == 1) {
      map.put("cnt", 1);
    } else {
      map.put("cnt", 0);
    }


    return ResponseEntity.ok(map);
  }
  @PostMapping("/checkNameid")  //http:localhost:9091/meber/checkId?id=admin
  @ResponseBody
  public ResponseEntity<HashMap<String,Object>> checkNameEmail(@RequestBody CustomerVO customerVO) {

    HashMap<String,Object> map = new HashMap<String,Object>();
    System.out.println(customerVO.getId());
    System.out.println(customerVO.getCname());
    int count = this.customerProc.checkNameEmail(customerVO.getCname(), customerVO.getId());
    System.out.println("countsss->" + count);
    if (count == 1) {
      map.put("cnt", 1);
    } else {
      map.put("cnt", 0);
    }



    return ResponseEntity.ok(map);
  }



  /**
   * 회원가입 폼 메서드
   *
   * @param model
   * @param customerVO
   * @return
   */
  @GetMapping("/create")

  public String createForm(Model model, CustomerVO customerVO) {
      
    // 알러지 재료명 목록 추가
    ArrayList<IngredientVO> ingredient_list = this.ingredientProc.list_all();
    model.addAttribute("ingredient_list", ingredient_list);


    return "/customer/create";
  }


  /**
   * 회원가입 처리 메소드
   *
   * @param model
   * @param customerVO
   * @param rrtr
   * @return
   */

  @PostMapping("/create")
  public String createcustomer(Model model, CustomerVO customerVO,
      @RequestParam(name = "ingredno[]", required = false) ArrayList<Integer> ingrednoList, RedirectAttributes rrtr) {

      int check_ID = this.customerProc.checkID(customerVO.getId());
      System.out.println("->" + customerVO.getId());
      System.out.println("닉네임->" + customerVO.getNickname());

      if (ingrednoList != null) {
          System.out.println("ingred : " + ingrednoList.size());
          for (Integer ingredno : ingrednoList) {
              AllergyVO allergyVO = new AllergyVO();
              allergyVO.setCustno(customerVO.getCustno());
              allergyVO.setIngredno(ingredno);
              this.allergyProc.create(allergyVO);
          }
      }

      System.out.println("check_ID -> " + check_ID);
      if (check_ID == 0) {
          Random random = new Random();
          int randomNumber = random.nextInt(5) + 1;
          String imageName = "basic" + randomNumber + ".jpg";
          customerVO.setImage(imageName);
          customerVO.setGrade(1);
          int count = customerProc.create(customerVO);
          System.out.println("count -> " + count);
          if (count == 1) {
              rrtr.addFlashAttribute("success", 1);
              rrtr.addFlashAttribute("come", customerVO.getCname() + "님 회원가입 축하드립니다! ");
              return "redirect:/customer/login";
          } else {
              rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");
              return "redirect:/customer/create";
          }
      } else {
          rrtr.addFlashAttribute("fail", "아이디 중복입니다 다시 만들어주세요 ");
          return "redirect:/customer/create";
      }
  }

  /**
   * 조히
   *
   * @param model
   * @param custno 회원 번호
   * @return
   */
  @GetMapping("/read")
  public String read(Model model, @RequestParam(name = "custno") Integer custno, HttpSession session, RedirectAttributes rttr) {
    System.out.println(custno);
    CustomerVO read = this.customerProc.read(custno);

    String id = (String) session.getAttribute("id");
    
    // 알러지 재료 목록 추가
    ArrayList<AllergyVO> allergies = this.allergyProc.list_by_custno(custno);
    model.addAttribute("allergies", allergies);
    
    // 알러지 재료명 목록 추가
    ArrayList<IngredientVO> ingredient_list = this.ingredientProc.list_all();
    model.addAttribute("ingredient_list", ingredient_list);


    if (read != null) {

      model.addAttribute("customerVO", read);
      return "customer/read";
    } else {
      rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
      return "redirect:/customer/list";
    }
  }

  /**
   * 마이페이지
   *
   * @param model
   * @param session
   * @param rttr
   * @return
   */
  @GetMapping("/my_page")
  public String mypage(Model model, HttpSession session, RedirectAttributes rttr) {


	if (this.customerProc.isCustomer(session)) {
    String id = (String) session.getAttribute("id");


    CustomerVO customerVO = this.customerProc.readById(id);

    model.addAttribute("customerVO", customerVO);
    
    ArrayList<IngredientVO> ingredient_list = this.ingredientProc.list_all();
    model.addAttribute("ingredient_list", ingredient_list);

    List<AllergyVO> allergies = this.allergyProc.list_by_custno(customerVO.getCustno());
    model.addAttribute("allergies", allergies);

    return "/customer/my_page";

    } else {
      rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
      return "redirect:/";
    }
  }

  @PostMapping("/update")

  public String updatecustomer(Model model, CustomerVO customerVO, RedirectAttributes rrtr, @RequestParam(name="ingredno[]", required=false) ArrayList<Integer> ingrednoList,HttpSession session) {


    int check_ID = this.customerProc.checkID(customerVO.getId());


    int count = this.customerProc.update(customerVO);
    
    // 기존 알러지 재료 삭제
    this.allergyProc.delete_by_custno(customerVO.getCustno());

    // 새로운 알러지 재료 추가
    if (ingrednoList != null) {
        for (Integer ingredno : ingrednoList) {
            AllergyVO allergyVO = new AllergyVO();
            allergyVO.setCustno(customerVO.getCustno());
            allergyVO.setIngredno(ingredno);
            this.allergyProc.create(allergyVO);
        }
    }
        
    System.out.println(check_ID);
    System.out.println(customerVO.getId());
    if (check_ID == 1) {
      if (count == 1) {


        rrtr.addFlashAttribute("success", 1);
        rrtr.addFlashAttribute("come", customerVO.getCname() + "님 수정 완료 되었습니다! ");
        session.setAttribute("cname", customerVO.getCname());
        return "redirect:/customer/read?custno=" + customerVO.getCustno();
      } else {
        rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");


        return "redirect:/customer/read?custno=" + customerVO.getCustno();
      }
    } else {
      rrtr.addFlashAttribute("fail", "아이디 중복입니다 다시 만들어주세요 ");
      return "redirect:/customer/create";
    }
   

  }

  @PostMapping("/update-mypage")

  public String updateMypage(Model model, CustomerVO customerVO, @RequestParam(name="ingredno[]", required=false) List<Integer> ingrednoList,RedirectAttributes rrtr, HttpSession session) {


    int check_ID = this.customerProc.checkID(customerVO.getId());
    System.out.println("->ingred: " + ingrednoList.size());


    int count = customerProc.update(customerVO);
    System.out.println(check_ID);
    System.out.println(customerVO.getId());
    if (check_ID == 1) {
      if (count == 1) {
        // 기존 알러지 재료 삭제
        this.allergyProc.delete_by_custno(customerVO.getCustno());

        // 새로운 알러지 재료 추가
        if (ingrednoList != null) {
            for (Integer ingredno : ingrednoList) {
                AllergyVO allergyVO = new AllergyVO();
                allergyVO.setCustno(customerVO.getCustno());
                allergyVO.setIngredno(ingredno);
                this.allergyProc.create(allergyVO);
            }
      }


        rrtr.addFlashAttribute("success", 1);
        rrtr.addFlashAttribute("come", customerVO.getCname() + "님 수정 완료 되었습니다! ");

        return "redirect:/customer/my_info_update";
      } else {
        rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");

        return "redirect:/customer/my_page";
      }
    } else {
      rrtr.addFlashAttribute("fail", "아이디 중복입니다 다시 만들어주세요 ");
      return "redirect:/customer/create";
    }
  }

  @GetMapping("/my_info_update")
  public String myinfo(Model model, HttpSession session,@RequestParam(name="ingredno[]", required=false) ArrayList<Integer> ingrednoList,
                        RedirectAttributes rttr) {


    if (this.customerProc.isCustomer(session)) {
    String id = (String) session.getAttribute("id");
    CustomerVO customerVO = this.customerProc.readById(id);

    model.addAttribute("customerVO", customerVO);
    
    ArrayList<IngredientVO> ingredient_list = this.ingredientProc.list_all();
    model.addAttribute("ingredient_list", ingredient_list);

    List<AllergyVO> allergies = this.allergyProc.list_by_custno(customerVO.getCustno());
    model.addAttribute("allergies", allergies);

    return "/customer/my_info_update";

    } else {
      rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
      return "redirect:/";
    }
  }


  @GetMapping("/update_grade")
  public String update_gradeForm(Model model, Integer custno, HttpSession session, RedirectAttributes rttr) {

    if (custno == null) {
      rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
      return "redirect:/";
    }

    CustomerVO read = this.customerProc.read(custno);
    if (read == null) {
      return "redirect:/customer/list";
    } else {
      model.addAttribute("customerVO", read);
      return "customer/update_grade";
    }

  }

  @PostMapping("update_grade")
  public String updategrade(Model model, CustomerVO customerVO, Integer grade, Integer custno,

                            RedirectAttributes rrtr, HttpSession session) {


    HashMap<String, Object> map = new HashMap<String, Object>();

    System.out.println("custno->" + custno);
    map.put("grade", grade);
    map.put("custno", custno);
    int count = this.customerProc.update_grade(map);


    if (count == 1) {


      rrtr.addFlashAttribute("success", 1);
      rrtr.addFlashAttribute("update", "등급  수정이 완료되었습니다 ");

      return "redirect:/customer/list";
    } else {
      rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");
      return "redirect:/customer/update_grade";
    }

  }

  /**
   * 프로필 업데이트 처리 메서드
   *
   * @param session
   * @param ra
   * @param customerVO
   * @return
   */

  @PostMapping("/update_profile")
  public String updateProfile(HttpSession session, RedirectAttributes ra, CustomerVO customerVO) {


    String id = (String) session.getAttribute("id");
    CustomerVO customer_old = customerProc.readById(id);

    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    String file1saved = customer_old.getImage();  // 실제 저장된 파일명

    long size1 = 0;

    String upDir = Customer.getUploadDir(); // C:/kd/deploy/resort_v2sbm3c/contents/storage/

    Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제
    // -------------------------------------------------------------------
    // 파일 삭제 종료
    // -------------------------------------------------------------------

    // -------------------------------------------------------------------
    // 파일 전송 시작
    // -------------------------------------------------------------------
    String file1 = "";          // 원본 파일명 image

    // 전송 파일이 없어도 file1MF 객체가 생성됨.
    // <input type='file' class="form-control" name='file1MF' id='file1MF'
    //           value='' placeholder="파일 선택">
    MultipartFile mf = customerVO.getFile1MF();

    file1 = mf.getOriginalFilename(); // 원본 파일명
    size1 = mf.getSize();  // 파일 크기

    if (size1 > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
      // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
      String exe = file1.split("\\.")[1];
      String newFileName = "customer_" + customer_old.getCustno() + "." + exe;
      file1saved = Upload.saveFileSpring(mf, upDir, newFileName);

      if (Tool.isImage(file1saved)) { // 이미지인지 검사
        // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
        file1 = Tool.preview(upDir, file1saved, 150, 150);
      } else {
        ra.addFlashAttribute("success", "이미지가 아닙니다!");
        return "redirect:/customer/my_page";
      }

    } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
      file1 = "";
      file1saved = "";
      size1 = 0;
    }

    customerVO.setImage(file1);
    customerVO.setId(id);
    // -------------------------------------------------------------------
    // 파일 전송 코드 종료
    // -------------------------------------------------------------------

    int count = this.customerProc.updateProfile(customerVO);// Oracle 처리

    if (count == 1) {
      ra.addFlashAttribute("success", "프로필 수정이 완료되었습니다.");
      return "redirect:/customer/my_page";
    } else {
      return "redirect:/";
    }
  }

  @GetMapping("/my_password_update")
  public String passwordupdate(Model model, HttpSession session, RedirectAttributes rttr) {


    if (this.customerProc.isCustomer(session)) {
    String id = (String) session.getAttribute("id");
    CustomerVO customerVO = this.customerProc.readById("kksos28");

    model.addAttribute("customerVO", customerVO);

    return "/customer/my_password_update";

    } else {
      rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
      return "redirect:/";
    }
  }


  /**
   * 쿠키 로그인 폼 메소드
   *
   * @param model
   * @param customerVO
   * @param session
   * @param request
   * @return
   */

  @GetMapping("/login")

  public String loginForm(Model model, CustomerVO customerVO, HttpSession session, HttpServletRequest request) {

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
      return "/customer/login";
    } else {
      return "redirect:/";
    }

    //-------------------------------------------------------------------
    // 쿠키 코드 종료
    //-------------------------------------------------------------------

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

  public String login(Model model, String id, String passwd, @RequestParam(value = "id_save", defaultValue = "") String id_save, @RequestParam(value = "passwd_save", defaultValue = "") String passwd_save, HttpSession session, HttpServletResponse response, HttpServletRequest request, RedirectAttributes rttr, CustomerHistoryVO historyVO) {


    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("id", id);
    map.put("passwd", this.security.aesEncode(passwd));

    int cnt = this.customerProc.login(map);
    System.out.println(cnt);
    if (cnt == 1) {
      CustomerVO customerVO = this.customerProc.readById(id);
      // id를 이용하여 회원 정보 조회


      session.setAttribute("custno", customerVO.getCustno());


      session.setAttribute("customerVO", customerVO);
      session.setAttribute("id", customerVO.getId());
      session.setAttribute("cname", customerVO.getCname());
      // session.setAttribute("grade", memverVO.getGrade());
      System.out.println("grade" + customerVO.getGrade());
      if (customerVO.getGrade() == 1) {
        session.setAttribute("grade", "customer");

        System.out.println("grade ->" + session.getAttribute("grade"));
      }
      rttr.addFlashAttribute("login", customerVO.getCname() + "님 안녕하세요");


      // -------------------------------------------------------------------
      // id 관련 쿠기 저장
      // -------------------------------------------------------------------
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

      historyVO.setCustno(customerVO.getCustno());


      String ipAddress = ClientUtils.getRemoteIP(request);

      System.out.println(ipAddress);
      historyVO.setIp(ipAddress);
      IpLocationService ipLocationService = new IpLocationService();

      Map<String, Object> location = ipLocationService.getLocation(ipAddress);
      String city = (String) location.get("city");

      CityUtils cityUtils = new CityUtils();

      String cityConvert = cityUtils.cityConvert(city);

      historyVO.setLogininfo(request.getHeader("User-Agent"));
      historyVO.setCity(cityConvert);
      int history = historyproc.create(historyVO);

      return "redirect:/";


    } else {
      rttr.addFlashAttribute("error", "아이디 또는 비밀번호 오류입니다.");
      return "redirect:/customer/login";
    }


  }


  /**
   * 마이페이지  정보 수정
   *
   *
   * @param session
   * @param ra
   * @return
   */


  @PostMapping("/logout")
  public String logout(HttpSession session, RedirectAttributes ra ) {
    // 세션을 무효화합니다.
    session.invalidate();

    // 로그아웃 성공 메시지(선택 사항)
    ra.addFlashAttribute("logout", "로그아웃 되었습니다.");

    // 홈 페이지로 리다이렉트합니다.
    return "redirect:/";

  }




  @GetMapping("/list")
  public String searchownerno(HttpSession session, Model model, @RequestParam(name = "type", defaultValue = "100") String type, String word, CategoryVO categoryVO, @RequestParam(name = "now_page", defaultValue = "1") int now_page) {


    String id = (String) session.getAttribute("id");
    String grade = (String) session.getAttribute("grade");

    if (now_page < 1) {
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
    // 일련 번호 생성
    int num = count - ((now_page - 1) * Customer.RECORD_PER_PAGE);
    ArrayList<CustomerVO> custlist = this.customerProc.list_search_paging(word, type, now_page, Customer.RECORD_PER_PAGE);
    String paging = this.customerProc.pagingBox(now_page, word, type, "/customer/list", count, Customer.RECORD_PER_PAGE, Customer.PAGE_PER_BLOCK);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("count", count);

    model.addAttribute("num", num);
    if (custlist.isEmpty()) {
      model.addAttribute("word", word);
      model.addAttribute("type", type);
      model.addAttribute("nulllist", "결과가 없습니다.");
    } else if (!custlist.isEmpty() && !word.equals("")) {
      model.addAttribute("word", word);
      model.addAttribute("type", type);
      model.addAttribute("search", types + ":" + word + "에  대한 검색 결과 : 총 " + custlist.size() + "건");
    }

    model.addAttribute("word", word);
    model.addAttribute("type", type);
    model.addAttribute("searchlist", custlist);
    model.addAttribute("restDTO", custlist);

    return "customer/customerList"; // Assuming "search_result" is the name of the view to display the search results


  }


  /**
   * 회원 탈퇴
   *
   * @param model
   * @param custno
   * @param cname
   * @param session
   * @param rttr
   * @return
   */
  @PostMapping("/delete")
  public String delete(Model model, @RequestParam("custno") Integer custno, @RequestParam("cname") String cname, HttpSession session, RedirectAttributes rttr) {

    int count = this.customerProc.delete(custno);


    if (count == 1) {
      rttr.addFlashAttribute("delete", cname + "'이 삭제되었습니다.");
      return "redirect:/customer/list";
    } else {
      rttr.addFlashAttribute("delete", "삭제 실패");
      return "redirect:/customer/list";
    }
  }


  @GetMapping("/logininfo")
  public String moble(Model model, CustomerHistoryVO loginHistoryVO, HttpSession session) {

    Integer custno = (Integer) session.getAttribute("custno");

    if (custno == null) {
      custno = 19;
    }
    ArrayList<HistoryDTO> selecthistory = this.historyproc.selecthistory(custno);


    PublicTools publicTools = new PublicTools();


    Map<String, List<HistoryDTO>> groupedLoginHistory = publicTools.groupByLoginDate(selecthistory);


    model.addAttribute("loginHistoryList", groupedLoginHistory);


    return "/customer/login_info";
  }


}

