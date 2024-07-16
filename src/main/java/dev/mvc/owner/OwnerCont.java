package dev.mvc.owner;


import dev.mvc.category.CategoryVO;
import dev.mvc.dto.HistoryDTO;
import dev.mvc.dto.RestDTO;
import dev.mvc.dto.RestFullData;
import dev.mvc.emailAuth.EmailAuthVO;
import dev.mvc.midarea.MidAreaProcInter;
import dev.mvc.midarea.MidAreaVO;
import dev.mvc.ownerhistory.OwnerHistoryProcInter;
import dev.mvc.ownerhistory.OwnerHistoryVO;
import dev.mvc.phoneAuth.PhoneAuthVO;

import dev.mvc.restaurant.Restaurant;
import dev.mvc.restaurant.RestaurantProInter;
import dev.mvc.restaurant.RestaurantVO;
import dev.mvc.restimg.RestImgProInter;
import dev.mvc.restimg.RestimgVO;
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
@RequestMapping("/owner")
public class OwnerCont {


  @Autowired
  @Qualifier("dev.mvc.owner.OwnerProc")
  private OwnerProInter ownerProc;

  @Autowired
  @Qualifier("dev.mvc.restaurant.RestaurantProc")
  private RestaurantProInter restproC;


  @Autowired
  @Qualifier("dev.mvc.ownerhistory.OwnerHistoryProc")
  private OwnerHistoryProcInter ownerhisProc;

  @Autowired
  @Qualifier("dev.mvc.restaurant.RestaurantProc")
  private RestaurantProInter restaurantProc;

  @Autowired
  @Qualifier("dev.mvc.restimg.RestImgProC")
  private RestImgProInter restimgproc;

  @Autowired
  @Qualifier("dev.mvc.midarea.MidAreaProc")
  private MidAreaProcInter midAreaProc;

  @Autowired
  private Security security;

  @Autowired
  private SmsToolO smsTool;

  @Autowired
  private EmailToolO emailTool;


  @Autowired
  private PublicTools publicTools;


  public OwnerCont() {
//    System.out.println("CustomerCont created");
  }

  @GetMapping()
  public String owner(Model model, HttpSession session) {
    String type = (String) session.getAttribute("type");
    System.out.println("type-> " + type);

    Integer ownerno = (Integer) session.getAttribute("ownerno");
    System.out.println("owner ownerno ->" + ownerno);

    model.addAttribute("accessType", type);


    if (type == null || ownerno == null) {
      return "redirect:/";
    }


    Integer restno = this.restproC.foreign(ownerno);


    model.addAttribute("restno", restno);

    if (publicTools.isOwner(type).equals("owner") || publicTools.isOwner(type).equals("NotCerti") || publicTools.isOwner(type).equals("ready")) {
      return "layout";
    } else {
      return "redirect:/owner/login";
    }

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


  @GetMapping("/rest_create")
  public String create(Model model, RestaurantVO restaurantVO, HttpSession session) {
    String type = (String) session.getAttribute("type");


    if (type.equals("Norest")) {
      model.addAttribute("accessType", type);

      ArrayList<MidAreaVO> midAreaVOS = midAreaProc.list_all();
      model.addAttribute("midAreaList", midAreaVOS);
      return "owner/rest_create";

    } else {
      return "redirect:/";
    }

  }

  /*
   * 식당 등록 처리 메서드
   *
   *
   */

  @PostMapping("/rest_create")
  public String restaurant(Model model, HttpSession session, RedirectAttributes redirectAttributes, RestaurantVO restaurantVO, RestimgVO restimgVO, RedirectAttributes ra) {
    Integer ownerno = (Integer) session.getAttribute("ownerno");
    restaurantVO.setOwnerno(ownerno);


    int count = this.restaurantProc.create(restaurantVO);

    int restno = this.restaurantProc.foreign(restaurantVO.getOwnerno());

    if (count == 1) {

      String upDir = Restaurant.getUploadDir(); // 파일을 업로드할 폴더 준비
      session.setAttribute("type", "owner");
      System.out.println("-> upDir: " + upDir);

      MultipartFile mf1 = restimgVO.getFile1MF();
      MultipartFile mf2 = restimgVO.getFile2MF();
      MultipartFile mf3 = restimgVO.getFile3MF(); // 파일 3 추가

      String[] fileNames = {mf1.getOriginalFilename(), mf2.getOriginalFilename(), mf3.getOriginalFilename()};
      MultipartFile[] files = {mf1, mf2, mf3};

      for (int i = 0; i < files.length; i++) {
        if (!fileNames[i].isEmpty()) {
          if (Tool.checkUploadFile(fileNames[i])) {
            long size = files[i].getSize();

            if (size > 0) {
              String exe = fileNames[i].split("\\.")[1];
              String newFileName = "rest_" + restaurantVO.getOwnerno() + "_" + (i + 1) + "." + exe;
              String fileSaved = Upload.saveFileSpring(files[i], upDir, newFileName);

              if (Tool.isImage(fileSaved)) {
                String thumb = Tool.preview(upDir, fileSaved, 200, 150);

                restimgVO.setRestno(restno);
                restimgVO.setImagefile(fileSaved); // 저장된 파일명 설정
                restimgVO.setThumbfile(thumb); // 저장된 썸네일 파일명 설정

                int saved = this.restimgproc.create(restimgVO);
                if (saved != 1) {
                  return "redirect:/schedule/create?restno=" + restno;
                }
              } else {
                return "redirect:/schedule/create?restno=" + restno; // 파일이 이미지가 아닐 경우 리다이렉트
              }
            } else {
              return "redirect:/schedule/create?restno=" + restno; // 파일 크기가 0일 경우 리다이렉트
            }
          } else {
            ra.addFlashAttribute("cnt", 0);
            ra.addFlashAttribute("code", "check_upload_file_fail");
            ra.addFlashAttribute("url", "/contents/msg"); // 메시지 페이지 URL 설정
            return "redirect:/owner/msg";
          }
        }
      }

      return "redirect:/schedule/create?restno=" + restno;
    } else {
      return "redirect:/schedule/create?restno=" + restno;
    }


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
        rrtr.addFlashAttribute("come", ownerVO.getOname() + "님 회원가입 축하드립니다! 사업자가 인증 완료되면 식당 관리 이용 가능합니다 ");
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
      return "redirect:/owner/login";
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
                      RedirectAttributes rttr,
                      OwnerHistoryVO historyVO) {

    HashMap<String, Object> map = new HashMap<>();
    map.put("id", id);
    map.put("passwd", this.security.aesEncode(passwd));

    int cnt = this.ownerProc.login(map);
    System.out.println(cnt);
    if (cnt == 1) {
      OwnerVO ownerVO = this.ownerProc.readById(id);

      historyVO.setOwnerno(ownerVO.getOwnerno());

      String ipAddress = ClientUtils.getRemoteIP(request);
      System.out.println(ipAddress);
      historyVO.setIp(ipAddress);
      historyVO.setLogininfo(request.getHeader("User-Agent"));

      IpLocationService ipLocationService = new IpLocationService();
      Map<String, Object> location = ipLocationService.getLocation(ipAddress);
      String city = (String) location.get("city");

      CityUtils cityUtils = new CityUtils();
      String cityConvert = cityUtils.cityConvert(city);

      historyVO.setCity(cityConvert);
      int history = ownerhisProc.create(historyVO);

      if (id_save.equals("Y")) {
        Cookie ck_id = new Cookie("ck_id", id);
        ck_id.setPath("/");
        ck_id.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(ck_id);
      } else {
        Cookie ck_id = new Cookie("ck_id", "");
        ck_id.setPath("/");
        ck_id.setMaxAge(0);
        response.addCookie(ck_id);
      }

      Cookie ck_id_save = new Cookie("ck_id_save", id_save);
      ck_id_save.setPath("/");
      ck_id_save.setMaxAge(60 * 60 * 24 * 30);
      response.addCookie(ck_id_save);

      if (passwd_save.equals("Y")) {
        Cookie ck_passwd = new Cookie("ck_passwd", passwd);
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(60 * 60 * 24 * 30);
        response.addCookie(ck_passwd);
      } else {
        Cookie ck_passwd = new Cookie("ck_passwd", "");
        ck_passwd.setPath("/");
        ck_passwd.setMaxAge(0);
        response.addCookie(ck_passwd);
      }

      Cookie ck_passwd_save = new Cookie("ck_passwd_save", passwd_save);
      ck_passwd_save.setPath("/");
      ck_passwd_save.setMaxAge(60 * 60 * 24 * 30);
      response.addCookie(ck_passwd_save);

      System.out.println("->" + ownerVO.getGrade());

      switch (ownerVO.getGrade()) {
        case 1:
          session.setAttribute("restcount", this.restaurantProc.restaurantCount(ownerVO.getOwnerno()));
          Integer restcount = this.restaurantProc.restaurantCount(ownerVO.getOwnerno());
          if (restcount == 0) {
            session.setAttribute("type", "Norest");
            session.setAttribute("ownerno", ownerVO.getOwnerno());
            session.setAttribute("id", ownerVO.getId());
            session.setAttribute("oname", ownerVO.getOname());
            session.setAttribute("grade", "Norest");
            session.setAttribute("ownerVO", ownerVO);
            return "redirect:/";
          } else {
            session.setAttribute("type", "owner");
            System.out.println("ownerno" + ownerVO.getOwnerno());
            session.setAttribute("ownerno", ownerVO.getOwnerno());
            session.setAttribute("id", ownerVO.getId());
            session.setAttribute("oname", ownerVO.getOname());
            session.setAttribute("grade", "owner");
            session.setAttribute("ownerVO", ownerVO);
            return "redirect:/";
          }
        case 10:
          session.setAttribute("ownerno", ownerVO.getOwnerno());
          System.out.println("ownerno" + ownerVO.getOwnerno());
          session.setAttribute("id", ownerVO.getId());
          session.setAttribute("oname", ownerVO.getOname());
          session.setAttribute("grade", "ready");
          session.setAttribute("type", "ready");
          session.setAttribute("ownerVO", ownerVO);
          rttr.addFlashAttribute("login", ownerVO.getOname() + "님 안녕하세요 사업자 인증 대기중입니다.");
          return "redirect:/owner";
        case 20:
          session.setAttribute("type", "NotCerti");
          System.out.println("ownerno" + ownerVO.getOwnerno());
          session.setAttribute("ownerno", ownerVO.getOwnerno());
          session.setAttribute("id", ownerVO.getId());
          session.setAttribute("oname", ownerVO.getOname());
          session.setAttribute("grade", "NotCerti");
          session.setAttribute("ownerVO", ownerVO);
          rttr.addFlashAttribute("login", ownerVO.getOname() + "님 안녕하세요 사업자가 인증되면 식당을 생성할 수 있습니다.");
          return "redirect:/owner";
        case 30:
          session.setAttribute("type", "Unauth");
          System.out.println("ownerno" + ownerVO.getOwnerno());
          session.setAttribute("ownerno", ownerVO.getOwnerno());
          session.setAttribute("id", ownerVO.getId());
          session.setAttribute("oname", ownerVO.getOname());
          session.setAttribute("grade", "Unauth");
          session.setAttribute("ownerVO", ownerVO);
          rttr.addFlashAttribute("login", ownerVO.getOname() + "님 안녕하세요 사업자가 심사에서 반려되었습니다");
          return "redirect:/owner";
        default:
          session.setAttribute("type", "?");
          System.out.println("ownerno" + ownerVO.getOwnerno());
          session.setAttribute("ownerno", ownerVO.getOwnerno());
          session.setAttribute("id", ownerVO.getId());
          session.setAttribute("oname", ownerVO.getOname());
          session.setAttribute("grade", "?");
          session.setAttribute("ownerVO", ownerVO);
          rttr.addFlashAttribute("login", ownerVO.getOname() + "님 알 수 없는 정보입니다.");
          return "redirect:/owner";
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

  @GetMapping("/my_info_update")
  public String myinfo(Model model, HttpSession session, RedirectAttributes rttr) {


//    if (this.customerProc.isCustomer(session)) {
    String id = (String) session.getAttribute("id");
    OwnerVO ownerVO = this.ownerProc.readById(id);

    model.addAttribute("ownerVO", ownerVO);

    return "/owner/my_info_update";

//    } else {
//      rttr.addFlashAttribute("Abnormal", "비정상적인 접근입니다 홈으로 돌아갑니다");
//      return "redirect:/";
//    }
  }

  /**
   * 프로필 업데이트 처리 메서드
   *
   * @param session
   * @param ra
   * @param ownerVO
   * @return
   */

  @PostMapping("/update_profile")
  public String updateProfile(HttpSession session, RedirectAttributes ra, OwnerVO ownerVO) {


    String id = (String) session.getAttribute("id");
    OwnerVO owner_old = this.ownerProc.readById(id);

    // -------------------------------------------------------------------
    // 파일 삭제 시작
    // -------------------------------------------------------------------
    String file1saved = owner_old.getImage();  // 실제 저장된 파일명

    String modifiedFileName = file1saved.replace(".png", "_t.png");


    long size1 = 0;

    String upDir = Owner.getUploadDir(); // C:/kd/deploy/resort_v2sbm3c/contents/storage/

    Tool.deleteFile(upDir, file1saved);  // 실제 저장된 파일삭제

    Tool.deleteFile(upDir, modifiedFileName);
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
    MultipartFile mf = ownerVO.getFile1MF();

    file1 = mf.getOriginalFilename(); // 원본 파일명
    size1 = mf.getSize();  // 파일 크기

    if (size1 > 0) { // 폼에서 새롭게 올리는 파일이 있는지 파일 크기로 체크 ★
      // 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
      String exe = file1.split("\\.")[1];
      String newFileName = "owner_" + owner_old.getOwnerno() + "." + exe;
      file1saved = Upload.saveFileSpring(mf, upDir, newFileName);

      if (Tool.isImage(file1saved)) { // 이미지인지 검사
        // thumb 이미지 생성후 파일명 리턴됨, width: 250, height: 200
        file1 = Tool.preview(upDir, file1saved, 150, 150);
      } else {
        ra.addFlashAttribute("success", "이미지가 아닙니다!");
        return "redirect:/owner/my_page";
      }

    } else { // 파일이 삭제만 되고 새로 올리지 않는 경우
      file1 = "";
      file1saved = "";
      size1 = 0;
    }

    ownerVO.setImage(file1);
    ownerVO.setId(id);
    // -------------------------------------------------------------------
    // 파일 전송 코드 종료
    // -------------------------------------------------------------------

    int count = this.ownerProc.updateProfile(ownerVO);// Oracle 처리

    if (count == 1) {
      ra.addFlashAttribute("success", "프로필 수정이 완료되었습니다.");
      return "redirect:/owner/my_page";
    } else {
      return "redirect:/";
    }
  }

  @GetMapping("/restread")
  public String restread(HttpSession session, Model model) {
    // 메뉴 정보
    String type = (String) session.getAttribute("type");
    Integer ownerno = (Integer) session.getAttribute("ownerno");
    if (type == "owner") {
      model.addAttribute("accessType", type);
      RestFullData restFullData = this.restaurantProc.readFullDataOwner(ownerno);
      model.addAttribute("restFullData", restFullData);
      // 메뉴의 재료 목록
      return "owner/restread";
    } else {
      return "redirect:/";
    }

  }


  @GetMapping("/update_map")
  public String update_map(Model model, HttpSession session) {
    // 메뉴 정보
    String type = (String) session.getAttribute("type");
    Integer ownerno = (Integer) session.getAttribute("ownerno");
    if (type == "owner") {
      model.addAttribute("accessType", type);
      ArrayList<MidAreaVO> midAreaVOS = midAreaProc.list_all();
      model.addAttribute("midAreaList", midAreaVOS);
      RestFullData restFullData = this.restaurantProc.readFullDataOwner(ownerno);
      model.addAttribute("restFullData", restFullData);
      // 메뉴의 재료 목록


      return "owner/update_map";
    } else {
      return "redirect:/";

    }

  }

  @PostMapping("/update_map")
  public String update_map(RestFullData restFullData, RedirectAttributes ra, String restno, HttpSession session) {

    String type = (String) session.getAttribute("type");
    if (type == "owner") {
      int count = restaurantProc.update_map(restFullData);

      if (count == 1) {
        return "redirect:/owner/update_map";

      } else {
        return "redirect:/";
      }
    } else {
      return "redirect:/";

    }


  }

  @GetMapping("/update_imgs")
  public String update_imgs(Model model, HttpSession session) {
    // 메뉴 정보
    String type = (String) session.getAttribute("type");
    Integer ownerno = (Integer) session.getAttribute("ownerno");
    if (type == null) {
      return "redirect:/";
    }
    model.addAttribute("accessType", type);

    RestFullData restFullData = this.restaurantProc.readFullDataOwner(ownerno);
    model.addAttribute("restFullData", restFullData);
    // 메뉴의 재료 목록


    return "owner/update_imgs";
  }

  @PostMapping("/update_imgs")
  public String updateRestaurant(Model model, HttpSession session, RedirectAttributes redirectAttributes, RestFullData restFullData, RestimgVO restimgVO, @RequestParam("deletedImageFiles") String deletedImageFiles) {
    Integer ownerno = (Integer) session.getAttribute("ownerno");
    restFullData.setOwnerno(ownerno);
    String upDir = Restaurant.getUploadDir(); // C:/kd/deploy/resort_v2sbm3c/contents/storage/


    int restno = restFullData.getRestno();  // 업데이트할 식당 번호

    // 삭제할 이미지 처리
    if (deletedImageFiles != null && !deletedImageFiles.isEmpty()) {
      String[] deletedFileNames = deletedImageFiles.split(",");
      for (String fileName : deletedFileNames) {
        if (!fileName.isEmpty()) {
          List<RestimgVO> imagesToDelete = this.restimgproc.findByFileName(fileName.trim());

          for (RestimgVO imageToDelete : imagesToDelete) {
            System.out.println("->" + imageToDelete.getImagefile());
            String fileToDelete = imageToDelete.getImagefile(); // Assuming this is the file name

            System.out.println("이미지 이름->" + fileToDelete);
            int dotIndex = fileToDelete.lastIndexOf(".");
            if (dotIndex > 0 && dotIndex < fileToDelete.length() - 1) {
              String baseName = fileToDelete.substring(0, dotIndex);
              String extension = fileToDelete.substring(dotIndex);

              // 확장자 앞에 '_t'를 추가합니다.
              String modifiedFileName = baseName + "_t" + extension;

              Tool.deleteFile(upDir, fileToDelete); // 원본 파일 삭제
              Tool.deleteFile(upDir, modifiedFileName); // '_t'가 추가된 파일 이름으로 삭제
              int delete = this.restimgproc.delete(imageToDelete.getRest_imgno());
              System.out.println("delete:" + delete);
            } else {
              System.out.println("-> 유효하지 않은 파일 이름: " + fileToDelete);
            }
          }
        }
      }
    }


    MultipartFile mf1 = restimgVO.getFile1MF();
    MultipartFile mf2 = restimgVO.getFile2MF();
    MultipartFile mf3 = restimgVO.getFile3MF(); // 파일 3 추가

    String[] fileNames = {mf1.getOriginalFilename(), mf2.getOriginalFilename(), mf3.getOriginalFilename()};
    MultipartFile[] files = {mf1, mf2, mf3};

    for (int i = 0; i < files.length; i++) {
      if (!fileNames[i].isEmpty()) {
        if (Tool.checkUploadFile(fileNames[i])) {
          long size = files[i].getSize();

          if (size > 0) {
            String exe = fileNames[i].split("\\.")[1];
            String newFileName = "rest_" + restFullData.getOwnerno() + "_" + (i + 1) + "." + exe;
            String fileSaved = Upload.saveFileSpring(files[i], upDir, newFileName);

            if (Tool.isImage(fileSaved)) {
              String thumb = Tool.preview(upDir, fileSaved, 200, 150);

              restimgVO.setRestno(restno);
              restimgVO.setImagefile(fileSaved); // 저장된 파일명 설정
              restimgVO.setThumbfile(thumb); // 저장된 썸네일 파일명 설정

              int saved = this.restimgproc.create(restimgVO);
              if (saved != 1) {
                return "redirect:/owner/restread?restno=" + restno;
              }
            } else {
              return "redirect:/owner/restread?restno=" + restno;
            }
          } else {
            return "redirect:/owner/restread?restno=" + restno;
          }
        } else {
          redirectAttributes.addFlashAttribute("cnt", 0);
          redirectAttributes.addFlashAttribute("code", "check_upload_file_fail");
          redirectAttributes.addFlashAttribute("url", "/contents/msg"); // 메시지 페이지 URL 설정
          return "redirect:/owner/msg";
        }
      }
    }

    return "redirect:/owner/restread?restno=" + restno;
  }


  /**
   * 로그인  접속 기록 메서드
   *
   * @param model
   * @param loginHistoryVO
   * @param session
   * @return
   */
  @GetMapping("/restaurant")
  public String byOwner(HttpSession session, Model model) {
    String type = (String) session.getAttribute("type");
    if (type == null) {
      return "redirect:/owner/login";
    }
    model.addAttribute("accessType", type);
    Integer ownerno = (Integer) session.getAttribute("ownerno");
    RestDTO restDTO = this.restaurantProc.restaurant_ownerno(ownerno);
    model.addAttribute("searchlist", restDTO);
    return "owner/restaurant_by_owner";


  }

  @GetMapping("/logininfo")
  public String moble(Model model, OwnerHistoryVO loginHistoryVO, HttpSession session) {

    Integer ownerno = (Integer) session.getAttribute("ownerno");

    if (ownerno == null) {
      ownerno = 15;
    }
    ArrayList<HistoryDTO> selecthistory = this.ownerhisProc.selecthistory(ownerno);


    PublicTools publicTools = new PublicTools();


    Map<String, List<HistoryDTO>> groupedLoginHistory = publicTools.groupByLoginDate(selecthistory);


    model.addAttribute("loginHistoryList", groupedLoginHistory);


    return "mobile_login_info";
  }


  @PostMapping("/logout")
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
  public String searchownerno(HttpSession session, Model model, @RequestParam(name = "type", defaultValue = "100") String type, String word, CategoryVO
          categoryVO, @RequestParam(name = "now_page", defaultValue = "1") int now_page) {


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


    int count = this.ownerProc.list_search_count(word, type);
    // 일련 번호 생성
    int num = count - ((now_page - 1) * Owner.RECORD_PER_PAGE);
    ArrayList<OwnerVO> ownerList = this.ownerProc.list_search_paging(word, type, now_page, Owner.RECORD_PER_PAGE);
    String paging = this.ownerProc.pagingBox(now_page, word, type, "/customer/list", count, Owner.RECORD_PER_PAGE, Owner.PAGE_PER_BLOCK);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("count", count);

    model.addAttribute("num", num);
    if (ownerList.isEmpty()) {
      model.addAttribute("word", word);
      model.addAttribute("type", type);
      model.addAttribute("nulllist", "결과가 없습니다.");
    } else if (!ownerList.isEmpty() && !word.equals("")) {
      model.addAttribute("word", word);
      model.addAttribute("type", type);
      model.addAttribute("search", types + ":" + word + "에  대한 검색 결과 : 총 " + ownerList.size() + "건");
    }

    model.addAttribute("word", word);
    model.addAttribute("type", type);
    model.addAttribute("searchlist", ownerList);


    return "owner/ownerList"; // Assuming "search_result" is the name of the view to display the search results


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


    String id = (String) session.getAttribute("id");
    String grade = (String) session.getAttribute("grade");
    String type = (String) session.getAttribute("type");

    model.addAttribute("accessType", type);

    if (id != null && grade.equals("NotCerti")) {

      OwnerVO read = this.ownerProc.readById(id);
      if (read != null) {
        model.addAttribute("ownerVO", read);

        return "owner/certifi";
      } else {
        return "redirect:/";

      }


    } else {
      return "redirect:/";
    }


  }

  /**
   * 사업자 등록 처리 메서드
   *
   * @param model
   * @param ownerVO
   * @param ra
   * @return
   */
  @PostMapping("/certifi")

  public String certicreate(Model model, OwnerVO ownerVO, RedirectAttributes ra, HttpSession session) {
    String file1 = ""; // 원본 파일명 image
    String file1saved = ""; // 저장된 파일명, image
    String thumb1 = ""; // preview image

    String file2 = ""; // 원본 파일명 image
    String file2saved = ""; // 저장된 파일명, image
    String thumb2 = ""; // preview image

    String upDir = Owner.getUploadDir(); // 파일을 업로드할 폴더 준비
    System.out.println("-> upDir: " + upDir);
    System.out.println("파일명" + ownerVO.getCertifi_image());
    System.out.println("파일명" + ownerVO.getIdenti_card_image());
    // 전송 파일이 없어도 file1MF 객체가 생성됨.
    // <input type='file' class="form-control" name='file1MF' id='file1MF'
    // value='' placeholder="파일 선택">
    MultipartFile mf1 = ownerVO.getFile1MF();
    MultipartFile mf2 = ownerVO.getFile2MF();

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

        System.out.println(size1);

        if (size1 > 0 && size2 > 0) { // 파일 크기가 0보다 큰지 확인
          String exe1 = file1.split("\\.")[1]; // 확장자 추출
          String exe2 = file2.split("\\.")[1]; // 확장자 추출
          String new_file_name1 = "onwer_" + ownerVO.getOwnerno() + "_b" + ownerVO.getCertifi_image() + "." + exe1;
          String new_file_name2 = "onwer_" + ownerVO.getOwnerno() + "_i" + "w" + "." + exe2;
          file1saved = Upload.saveFileSpring(mf1, upDir, new_file_name1); // 파일 저장 후 업로드된 파일명 리턴
          file2saved = Upload.saveFileSpring(mf2, upDir, new_file_name2); // 파일 저장 후 업로드된 파일명 리턴

          if (Tool.isImage(file1saved) && Tool.isImage(file2saved)) { // 이미지 파일인지 검사
            thumb1 = Tool.preview(upDir, file1saved, 200, 150); // 썸네일 생성
            thumb2 = Tool.preview(upDir, file2saved, 200, 150); // 썸네일 생성
            ownerVO.setCertifi_image(file1saved); // 저장된 파일명 설정
            ownerVO.setIdenti_card_image(file2saved); // 저장된 파일명 설정
            Integer ownerno = (Integer) session.getAttribute("ownerno");
            session.setAttribute("grade", "ready");
            session.setAttribute("type", "ready");
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ownerno", ownerno);
            map.put("grade", 10);
            ownerProc.update_grade(map);
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

    int cnt = this.ownerProc.updateCertifi(ownerVO);
    System.out.println("-> update cnt" + cnt);

    if (cnt == 0) {
      model.addAttribute("cnt", cnt);
      model.addAttribute("code", "create_fail");
      return "content/msg";
    } else {
      return "redirect:/";
    }
  }

  @PostMapping("/send_phone")

  public ResponseEntity<CompletableFuture<Boolean>> PhoneAuth(@RequestBody PhoneAuthVO phoneVO) throws NurigoMessageNotReceivedException, NurigoEmptyResponseException, NurigoUnknownException {

    System.out.println("phone ->" + phoneVO.getPhone());
    return ResponseEntity.ok((smsTool.send_message((java.lang.String) phoneVO.getPhone())));
  }

  @PostMapping("/send_email")

  public ResponseEntity<CompletableFuture<Boolean>> EmailAuth(@RequestBody EmailAuthVO emailAuthVO) throws Exception {

    System.out.println("->" + emailAuthVO.getEmail());
    return ResponseEntity.ok((emailTool.sendAuthenticationCode(emailAuthVO.getEmail())));
  }

  @PostMapping("/check_phone")
  public ResponseEntity<HashMap<String, Object>> auth_phone_check(@RequestBody PhoneAuthVO phoneVO) throws Exception {

    return ResponseEntity.ok((smsTool.checkAuthentication(phoneVO.getPhone(), phoneVO.getAuth())));

  }


  @PostMapping("/check_email")
  public ResponseEntity<HashMap<String, Object>> auth_email_check(@RequestBody EmailAuthVO emailAuthVO) throws Exception {

    return ResponseEntity.ok((emailTool.checkAuthentication(emailAuthVO.getEmail(), emailAuthVO.getAuth())));

  }

  @PostMapping("/delete_rest")
  public String delete_rest(HttpSession session, RedirectAttributes ra) {
    Integer ownerno = (Integer) session.getAttribute("ownerno");
    if (ownerno == null) {
      return "redirect:/";
    } else {
      int count = this.restaurantProc.delete_rest(ownerno);

      if (count == 1) {
        session.setAttribute("type", "Norest");

        ra.addFlashAttribute("cnt", 1);
        return "redirect:/owner/restaurant";
      } else {
        return "redirect:/owner/restaurant";
      }
    }
  }
}



