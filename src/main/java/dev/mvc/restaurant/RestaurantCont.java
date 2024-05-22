package dev.mvc.restaurant;

import dev.mvc.category.CategoryVO;
import dev.mvc.certifi.Certifi;
import dev.mvc.certifi.CertifiProInter;
import dev.mvc.restimg.RestImgProInter;
import dev.mvc.restimg.RestimgVO;
import dev.mvc.tool.Security;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/restaurant")
public class RestaurantCont {


  @Autowired
  @Qualifier("dev.mvc.restaurant.RestaurantProc")
  private RestaurantProInter restaurantProc;

  @Autowired
  @Qualifier("dev.mvc.certifi.CertifiProc")
  private CertifiProInter certifipro;

  @Autowired
  @Qualifier("dev.mvc.restimg.RestImgProC")
  private RestImgProInter restimgproc;


  @Autowired
  private Security security;

  public RestaurantCont() {
//    System.out.println("RestaurantCont created");
  }


  /*
   * 식당 등록 폼 메서드
   *
   *
   */

  @GetMapping("/create")
  public String create(Model model,RestaurantVO restaurantVO,HttpSession session) {
    String type =  "s";

    if (type == null) {
      return "redirect:/";
    } else {
      return "restaurant/create";
    }

  }

  /*
   * 식당 등록 처리 메서드
   *
   *
   */

  @PostMapping("/create")
  public String restaurant(Model model, RedirectAttributes redirectAttributes, RestaurantVO restaurantVO, RestimgVO restimgVO, RedirectAttributes ra) {

    restaurantVO.setOwnerno(7);


    int count = this.restaurantProc.create(restaurantVO);


    if (count == 1) {
      int nextval = this.restaurantProc.nextval() - 1;
      String upDir = Certifi.getUploadDir(); // 파일을 업로드할 폴더 준비
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
              String newFileName = "owner_" + (i+1) + "_" + System.currentTimeMillis() + "." + exe;
              String fileSaved = Upload.saveFileSpring(files[i], upDir, newFileName);

              if (Tool.isImage(fileSaved)) {
                String thumb = Tool.preview(upDir, fileSaved, 200, 150);
                System.out.println(restaurantVO.getRestno());
                restimgVO.setRestno(nextval);
                restimgVO.setImagefile(fileSaved); // 저장된 파일명 설정
                restimgVO.setThumbfile(thumb); // 저장된 썸네일 파일명 설정

                int saved = this.restimgproc.create(restimgVO);
                if (saved != 1) {
                  return "redirect:/";
                }
              } else {
                return "redirect:/"; // 파일이 이미지가 아닐 경우 리다이렉트
              }
            } else {
              return "redirect:/"; // 파일 크기가 0일 경우 리다이렉트
            }
          } else {
            ra.addFlashAttribute("cnt", 0);
            ra.addFlashAttribute("code", "check_upload_file_fail");
            ra.addFlashAttribute("url", "/contents/msg"); // 메시지 페이지 URL 설정
            return "redirect:/contents/msg";
          }
        }
      }

      return "redirect:/";
    } else {
      return "redirect:/restaurant/create";
    }
  }




  @GetMapping("/search_b")
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
        types = "중분류";
      } else if (type.equals("200")) {
        types = "소분류";
      } else {
        types = "중분류 + 소분류";
      }


      int count = this.restaurantProc.list_search_count(word, type);
      // 일련 번호 생성
      int num = count - ((now_page - 1) * Restaurant.RECORD_PER_PAGE);
      ArrayList<RestaurantVO> restlist = this.restaurantProc.list_search_paging(word, type, now_page, Restaurant.RECORD_PER_PAGE);
      String paging = this.restaurantProc.pagingBox(now_page, word, type, "/restaurant/search_b", count, Restaurant.RECORD_PER_PAGE, Restaurant.PAGE_PER_BLOCK);
      model.addAttribute("paging", paging);
      model.addAttribute("now_page", now_page);
      model.addAttribute("count", count);

      model.addAttribute("num", num);
      if (restlist.isEmpty()) {
        model.addAttribute("word", word);
        model.addAttribute("type", type);
        model.addAttribute("nulllist", "결과가 없습니다.");
      } else if (!restlist.isEmpty() && !word.equals("")) {
        model.addAttribute("word", word);
        model.addAttribute("type", type);
        model.addAttribute("search", types + ":" + word + "에  대한 검색 결과 : 총 " + restlist.size() + "건");
      }

      model.addAttribute("word", word);
      model.addAttribute("type", type);
      model.addAttribute("searchlist", restlist);


      return "restaurant/search_all"; // Assuming "search_result" is the name of the view to display the search results


    }

}



