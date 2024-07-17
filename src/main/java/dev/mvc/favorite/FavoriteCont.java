package dev.mvc.favorite;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/favorite")
@Controller
public class FavoriteCont {

  @Autowired
  @Qualifier("dev.mvc.favorite.FavoriteProc")
  private FavoriteProcInter favoriteProc;

  @PostMapping("/create")
  @ResponseBody
  public ResponseEntity<?> create(@RequestBody FavoriteVO favoriteVO) {
      int result = favoriteProc.create(favoriteVO);
      System.out.println("custno:" + favoriteVO.getCustno());
      System.out.println("restno:" + favoriteVO.getRestno());
      if (result > 0) {
          return ResponseEntity.ok().body("{\"message\": \"즐겨찾기가 저장되었습니다.\"}");
      } else {
          return ResponseEntity.status(500).body("{\"message\": \"즐겨찾기 저장에 실패했습니다.\"}");
      }
  }
  
  @PostMapping("/delete")
  @ResponseBody
  public ResponseEntity<?> delete(@RequestBody FavoriteVO favoriteVO) {
    int result = favoriteProc.delete(favoriteVO);
    if (result > 0) {
      return ResponseEntity.ok().body("{\"message\": \"즐겨찾기가 삭제되었습니다.\"}");
    } else {
      return ResponseEntity.status(500).body("{\"message\": \"즐겨찾기 삭제에 실패했습니다.\"}");
    }
  }
  
  @PostMapping("/toggle")
  @ResponseBody
  public ResponseEntity<?> toggle(@RequestBody FavoriteVO favoriteVO, HttpSession session) {
      String accessType = (String) session.getAttribute("type");
      if (accessType == null || !accessType.equals("customer")) {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"message\": \"로그인이 필요합니다.\"}");
      }
      
      int custno = (int) session.getAttribute("custno");
      favoriteVO.setCustno(custno);

      boolean isFavorited = favoriteProc.isFavorited(favoriteVO.getCustno(), favoriteVO.getRestno());
      if (isFavorited) {
          int result = favoriteProc.delete_favorite_rest(favoriteVO);
          if (result > 0) {
              return ResponseEntity.ok().body("{\"message\": \"즐겨찾기가 삭제되었습니다.\"}");
          } else {
              return ResponseEntity.status(500).body("{\"message\": \"즐겨찾기 삭제에 실패했습니다.\"}");
          }
      } else {
          int result = favoriteProc.create(favoriteVO);
          if (result > 0) {
              return ResponseEntity.ok().body("{\"message\": \"즐겨찾기가 저장되었습니다.\"}");
          } else {
              return ResponseEntity.status(500).body("{\"message\": \"즐겨찾기 저장에 실패했습니다.\"}");
          }
      }
  }
  
  @PostMapping("/delete_favorite_rest")
  @ResponseBody
  public ResponseEntity<?> delete_favorite_rest(@RequestBody FavoriteVO favoriteVO) {
      System.out.println("Trying to delete favorite by custno and restno: custno=" + favoriteVO.getCustno() + ", restno=" + favoriteVO.getRestno());
      int result = favoriteProc.delete_favorite_rest(favoriteVO);
      if (result > 0) {
          return ResponseEntity.ok().body("{\"message\": \"즐겨찾기가 삭제되었습니다.\"}");
      } else {
          return ResponseEntity.status(500).body("{\"message\": \"즐겨찾기 삭제에 실패했습니다.\"}");
      }
  }

  
  @GetMapping("/favorite_list")
  public String favorite_list(Model model) {
      List<FavoriteVO> list = favoriteProc.favorite_list();
      System.out.println("Retrieved favorite list: " + list); // 로그 추가
      model.addAttribute("list", list);
      return "favorite/favorite_list"; // 해당 HTML 파일 경로
  }
  
  @GetMapping("/favorite_list_mobile")
  public String favorite_list_mobile(Model model, HttpSession session,RedirectAttributes redirectAttributes) {
      String accessType = (String) session.getAttribute("type");
      if (accessType != null && accessType.equals("customer")) {
          int custno = (int) session.getAttribute("custno");
          System.out.println("custno :" + custno);
         
          List<FavoriteVO> list = favoriteProc.list_by_custno(custno);
          System.out.println("Favorite list for mobile: " + list);
          System.out.println("listsize : "+ list.size());
          model.addAttribute("list", list);
          model.addAttribute("custno", custno);
      } else {
        // 로그인되지 않은 상태일 경우 처리
        redirectAttributes.addFlashAttribute("alertMessage", "로그인 한 후 이용할 수 있습니다");
        return "redirect:/customer/login"; // 로그인 페이지로 리다이렉트
      }
      return "favorite/favorite_list_mobile"; // 해당 HTML 파일 경로
  }
  
  @GetMapping("/check_login")
  @ResponseBody
  public ResponseEntity<?> checkLogin(HttpSession session) {
      String accessType = (String) session.getAttribute("type");
      boolean loggedIn = accessType != null && accessType.equals("customer");
      return ResponseEntity.ok().body("{\"loggedIn\": " + loggedIn + "}");
  }


}
