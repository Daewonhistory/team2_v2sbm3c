package dev.mvc.favorite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequestMapping("/favorite")
@Controller
public class FavoriteCont {

  @Autowired
  @Qualifier("dev.mvc.favorite.FavoriteProc")
  private FavoriteProcInter favoriteProc;

  @PostMapping("/create")
  public ResponseEntity<?> create(@RequestBody FavoriteVO favoriteVO) {
      int result = favoriteProc.create(favoriteVO);
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
  
  @GetMapping("/favorite_list")
  public String favorite_list(Model model) {
    List<FavoriteVO> list = favoriteProc.favorite_list();
    System.out.println("Retrieved favorite list: " + list);
    model.addAttribute("list", list);
    return "favorite/favorite_list"; // 해당 HTML 파일 경로
  }
  
  @GetMapping("/favorite_list_mobile")
  public String favorite_list_mobile(Model model) {
    List<FavoriteVO> list = favoriteProc.favorite_list();
    System.out.println("Retrieved favorite list: " + list);
    model.addAttribute("list", list);
    return "favorite/favorite_list_mobile"; // 해당 HTML 파일 경로
  }
  
  
}
