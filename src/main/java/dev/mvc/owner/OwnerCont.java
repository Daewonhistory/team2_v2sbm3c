package dev.mvc.owner;

import dev.mvc.certifi.Certifi;
import dev.mvc.certifi.CertifiProInter;
import dev.mvc.certifi.CertifiVO;
import dev.mvc.menu.Menu;
import dev.mvc.tool.Security;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

@Controller
@RequestMapping("/owner")
public class OwnerCont {


  @Autowired
  @Qualifier("dev.mvc.owner.OwnerProc")
  private OwnerProInter ownerProc;

  @Autowired
  @Qualifier("dev.mvc.certifi.CertifiProc")
  private CertifiProInter certifiProc;


  @Autowired
  private Security security;

  public OwnerCont() {
//    System.out.println("CustomerCont created");
  }


  /*
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
   *
   * @param model
   * @param ownerVO
   * @return
   */
  @GetMapping("/create")

  public String createForm(Model model, OwnerVO ownerVO) {


    return "/owner/create";
  }

  /**
   * 사업자 회원가입 처리 메서드
   *
   * @param model
   * @param ownerVO
   * @param rrtr
   * @return
   */
  @PostMapping("/create")

  public String createowner(Model model, OwnerVO ownerVO, RedirectAttributes rrtr) {


    int check_ID = this.ownerProc.checkID(ownerVO.getId());


    System.out.println("check_ID -> " + check_ID);
    if (check_ID == 0) {
      Random random = new Random();
      int randomNumber = random.nextInt(5) + 1; // 1부터 5까지의 랜덤한 숫자 생성
      String imageName = "ownerbasic" + randomNumber + ".jpg";
      ownerVO.setImage(imageName);

      ownerVO.setGrade(20);
      int count = ownerProc.create(ownerVO);
      System.out.println("count -> " + count);
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
   *
   * @param model
   * @param ownerVO
   * @param session
   * @param request
   * @return 로그인 경로
   */
  @GetMapping("/login")

  public String loginForm(Model model, OwnerVO ownerVO, HttpSession session, HttpServletRequest request) {

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

  public String login(Model model,
                      String id, String passwd,
                      @RequestParam(value = "id_save", defaultValue = "") String id_save,
                      @RequestParam(value = "passwd_save", defaultValue = "") String passwd_save,
                      HttpSession session,
                      HttpServletResponse response,
                      HttpServletRequest request,
                      RedirectAttributes rttr
  ) {


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


      if (ownerVO.getGrade() == 1) {
        session.setAttribute("type", "owner");
        session.setAttribute("ownerno", ownerVO.getOwnerno());
        session.setAttribute("id", ownerVO.getId());
        session.setAttribute("oname", ownerVO.getOname());
        session.setAttribute("grade", "owner");
        session.setAttribute("ownerVO", ownerVO);
        return "redirect:/";

      } else {
        session.setAttribute("ownerno", ownerVO.getOwnerno());
        session.setAttribute("id", ownerVO.getId());
        session.setAttribute("oname", ownerVO.getOname());
        session.setAttribute("grade", "NotCerti");
        session.setAttribute("ownerVO", ownerVO);
        rttr.addFlashAttribute("login", ownerVO.getOname() + "님 안녕하세요 사업자가 인증되면 컨텐츠에 접근할 수 있습니다");

        return "redirect:/owner/certifi";
      }
    } else {
      rttr.addFlashAttribute("error", "아이디 또는 비밀번호 오류입니다.");
      return "redirect:/owner/login";
    }

  }


  @GetMapping("/my_page")
  public String mypage(Model model, HttpSession session, RedirectAttributes rttr) {


    String id = (String) session.getAttribute("id");
    System.out.println(id);

    if (id == null) {
      return "redirect:/";
    } else {
      OwnerVO ownerVO = this.ownerProc.readById(id);
      if (ownerVO == null) {
        return "redirect:/";
      } else {
        model.addAttribute("ownerVO", ownerVO);

        return "/owner/my_page";
      }


    }


  }

  @GetMapping("/logout")
  public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
    // 세션을 무효화합니다.
    session.invalidate();

    // 로그아웃 성공 메시지(선택 사항)
    redirectAttributes.addFlashAttribute("logout", "로그아웃 되었습니다.");

    // 홈 페이지로 리다이렉트합니다.
    return "redirect:/";

  }


  @PostMapping("/update")

  public String updateowner(Model model, OwnerVO ownerVO, RedirectAttributes rrtr, HttpSession session) {


    int check_ID = this.ownerProc.checkID(ownerVO.getId());


    int count = this.ownerProc.update(ownerVO);
    System.out.println(check_ID);
    System.out.println(ownerVO.getId());
    if (check_ID == 1) {
      if (count == 1) {


        rrtr.addFlashAttribute("success", 1);
        rrtr.addFlashAttribute("come", ownerVO.getOname() + "님 수정 완료 되었습니다! ");
        session.setAttribute("cname", ownerVO.getOname());
        return "redirect:/owner/read?ownerno=" + ownerVO.getOwnerno();
      } else {
        rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");


        return "redirect:/owner/read?ownerno=" + ownerVO.getOwnerno();
      }
    } else {
      rrtr.addFlashAttribute("fail", "아이디 중복입니다 다시 만들어주세요 ");
      return "redirect:/owner/create";
    }


  }

  @PostMapping("/update-mypage")

  public String updateMypage(Model model, OwnerVO ownerVO, RedirectAttributes rrtr, HttpSession session) {


    int check_ID = this.ownerProc.checkID(ownerVO.getId());


    int count = ownerProc.update(ownerVO);
    System.out.println(check_ID);
    System.out.println(ownerVO.getId());
    if (check_ID == 1) {
      if (count == 1) {


        rrtr.addFlashAttribute("success", 1);
        rrtr.addFlashAttribute("come", ownerVO.getOname() + "님 수정 완료 되었습니다! ");

        return "redirect:/owner/my_page";
      } else {
        rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");

        return "redirect:/owner/my_page";
      }
    } else {
      rrtr.addFlashAttribute("fail", "아이디 중복입니다 다시 만들어주세요 ");
      return "redirect:/owner/create";
    }
  }

  @GetMapping("/list")
  public String list_member(Model model, OwnerVO ownerVO, HttpSession session, RedirectAttributes rttr) {

    String id = (String) session.getAttribute("id");


    ArrayList<OwnerVO> list = this.ownerProc.list();
    model.addAttribute("list", list);
    return "owner/ownerList";


  }

  /**
   * 조히
   *
   * @param model
   * @param ownerno 회원 번호
   * @return
   */
  @GetMapping("/read")
  public String read(Model model, @RequestParam(name = "ownerno") Integer ownerno, HttpSession session,
                     RedirectAttributes rttr) {
    System.out.println(ownerno);
    OwnerVO read = this.ownerProc.read(ownerno);

    String id = (String) session.getAttribute("id");


    if (read != null) {

      model.addAttribute("ownerVO", read);
      return "owner/read";
    } else {
      rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
      return "redirect:/owner/list";
    }
  }

  /**
   * 회원 탈퇴
   *
   * @param model
   * @param ownerno
   * @param cname
   * @param session
   * @param rttr
   * @return
   */
  @PostMapping("/delete")
  public String delete(Model model, @RequestParam("ownerno") Integer ownerno,
                       @RequestParam("cname") String cname, HttpSession session,
                       RedirectAttributes rttr) {

    int count = this.ownerProc.delete(ownerno);


    if (count == 1) {
      rttr.addFlashAttribute("delete", cname + "'이 삭제되었습니다.");
      return "redirect:/owner/list";
    } else {
      rttr.addFlashAttribute("delete", "삭제 실패");
      return "redirect:/owner/list";
    }
  }

  @GetMapping("/update_grade")
  public String update_gradeForm(Model model, Integer ownerno, HttpSession session, RedirectAttributes rttr) {

    if (ownerno == null) {
      rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
      return "redirect:/";
    }

    OwnerVO read = this.ownerProc.read(ownerno);
    if (read == null) {
      return "redirect:/owner/list";
    } else {
      model.addAttribute("ownerVO", read);
      ArrayList<CertifiVO> list = this.certifiProc.findByOnwer(ownerno);
      model.addAttribute("list", list);

      return "owner/update_grade";
    }

  }


  @PostMapping("update_grade")
  public String updategrade(Model model, OwnerVO ownerVO,
                            Integer grade, Integer ownerno,

                            RedirectAttributes rrtr,
                            HttpSession session
  ) {


    HashMap<String, Object> map = new HashMap<String, Object>();

    System.out.println("custno->" + ownerno);
    map.put("grade", grade);
    map.put("ownerno", ownerno);
    int count = this.ownerProc.update_grade(map);


    if (count == 1) {


      rrtr.addFlashAttribute("success", 1);
      rrtr.addFlashAttribute("update", "등급  수정이 완료되었습니다 ");

      return "redirect:/owner/list";
    } else {
      rrtr.addFlashAttribute("fail", "다시 시도해주세요 ");
      return "redirect:/owner/update_grade";
    }

  }


  /**
   * 사업자 인증 등록 폼 메서드
   *
   * @param ownerVO
   * @return
   */
  @GetMapping("/certifi")
  public String certi(HttpSession session, OwnerVO ownerVO, Model model) {


//    OwnerVO ownerVO1 = (OwnerVO) session.getAttribute("ownerVO");
//    if (ownerVO1 != null && ownerVO1.getGrade() == 20) {

      OwnerVO read = this.ownerProc.read(8);
      if (read != null) {
        model.addAttribute("ownerVO", read);
        return "owner/certifi";
      } else {
        return "redirect:/";

      }


//    } else {
//      return "redirect:/";
//    }


  }

  /**
   * 사업자 등록 처리 메서드
   *
   * @param model
   * @param certifiVO
   * @param rrtr
   * @return
   */
  @PostMapping("/certifi")

  public String certicreate(Model model, CertifiVO certifiVO, RedirectAttributes ra) {
    String file1 = ""; // 원본 파일명 image
    String file1saved = ""; // 저장된 파일명, image
    String thumb1 = ""; // preview image

    String file2 = ""; // 원본 파일명 image
    String file2saved = ""; // 저장된 파일명, image
    String thumb2 = ""; // preview image

    String upDir = Certifi.getUploadDir(); // 파일을 업로드할 폴더 준비
    System.out.println("-> upDir: " + upDir);
    System.out.println("파일명" + certifiVO.getCertifi_image());
    System.out.println("파일명" + certifiVO.getIdenti_card_image());
    // 전송 파일이 없어도 file1MF 객체가 생성됨.
    // <input type='file' class="form-control" name='file1MF' id='file1MF'
    // value='' placeholder="파일 선택">
    MultipartFile mf1 = certifiVO.getFile1MF();
    MultipartFile mf2 = certifiVO.getFile2MF();

    // file1 처리
    file1 = mf1.getOriginalFilename(); // 원본 파일명 산출
    System.out.println("-> 원본 파일명 산출 file1: " + file1);

    // file2 처리
    file2 = mf2.getOriginalFilename(); // 원본 파일명 산출
    System.out.println("-> 원본 파일명 산출 file2: " + file2);

    // 두 파일 모두 처리
    if (!file1.isEmpty() && !file2.isEmpty()) { // 두 파일 모두가 비어 있지 않은 경우
      if (Tool.checkUploadFile(file1) && Tool.checkUploadFile(file2)) { // 두 파일 모두 업로드 가능한지 검사
        long size1 = mf1.getSize(); // 파일 크기
        long size2 = mf2.getSize(); // 파일 크기

        if (size1 > 0 && size2 > 0) { // 파일 크기가 0보다 큰지 확인
          String exe1 = file1.split("\\.")[1]; // 확장자 추출
          String exe2 = file2.split("\\.")[1]; // 확장자 추출
          String new_file_name1 = "onwer_" + certifiVO.getOwnerno() + "_b"+certifiVO.getCertifino()+"."+exe1;
          String new_file_name2 = "onwer_" + certifiVO.getOwnerno() + "_i"+certifiVO.getCertifino()+"."+exe2;
          file1saved = Upload.saveFileSpring(mf1, upDir, new_file_name1); // 파일 저장 후 업로드된 파일명 리턴
          file2saved = Upload.saveFileSpring(mf2, upDir, new_file_name2); // 파일 저장 후 업로드된 파일명 리턴

          if (Tool.isImage(file1saved) && Tool.isImage(file2saved)) { // 이미지 파일인지 검사
            thumb1 = Tool.preview(upDir, file1saved, 200, 150); // 썸네일 생성
            thumb2 = Tool.preview(upDir, file2saved, 200, 150); // 썸네일 생성
            certifiVO.setCertifi_image(file1saved); // 저장된 파일명 설정
            certifiVO.setIdenti_card_image(file2saved); // 저장된 파일명 설정
          } else {
            return "redirect:/"; // 파일이 이미지가 아닐 경우 리다이렉트
          }
        } else {
          return "redirect:/"; // 파일 크기가 0일 경우 리다이렉트
        }
      } else {
        // 업로드가 불가능한 파일일 경우 리다이렉트
        ra.addFlashAttribute("cnt", 0);
        ra.addFlashAttribute("code", "check_upload_file_fail");
        ra.addFlashAttribute("url", "/contents/msg"); // 메시지 페이지 URL 설정
        return "redirect:/contents/msg";
      }
    } else {
      return "redirect:/"; // 전송된 파일이 없을 경우 리다이렉트
    }

    int cnt = this.certifiProc.createCertifi(certifiVO);

    if (cnt == 0) {
      model.addAttribute("cnt", cnt);
      model.addAttribute("code", "create_fail");
      return "content/msg";
    } else {
      return "redirect:/";
    }
  }

}



