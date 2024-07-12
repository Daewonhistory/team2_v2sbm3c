package dev.mvc.restaurant;

import dev.mvc.category.CategoryProcInter;
import dev.mvc.category.CategoryVO;
import dev.mvc.dto.RestDTO;
import dev.mvc.dto.RestFullData;
import dev.mvc.favorite.FavoriteProcInter;
import dev.mvc.midarea.MidAreaProcInter;
import dev.mvc.midarea.MidAreaVO;
import dev.mvc.notice.NoticeProcInter;
import dev.mvc.notice.NoticeVO;
import dev.mvc.restimg.RestImgProInter;
import dev.mvc.restimg.RestimgVO;
import dev.mvc.tool.Security;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/restaurant/api")
public class RestaurantContR {


  @Autowired
  @Qualifier("dev.mvc.restaurant.RestaurantProc")
  private RestaurantProInter restaurantProc;

  @Autowired
  @Qualifier("dev.mvc.favorite.FavoriteProc")
  private FavoriteProcInter favoriteProc;


  @Autowired
  @Qualifier("dev.mvc.restimg.RestImgProC")
  private RestImgProInter restimgproc;

  @Autowired
  @Qualifier("dev.mvc.midarea.MidAreaProc")
  private MidAreaProcInter midAreaProc;

  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc")
  private CategoryProcInter categoryProc;

  @Autowired
  @Qualifier("dev.mvc.notice.NoticeProc")
  private NoticeProcInter noticeProc;

  @Autowired
  private Security security;

  String today = LocalDate.now().toString();
  public RestaurantContR() {
//    System.out.println("RestaurantCont created");
  }


  /*
   * 식당 등록 폼 메서드
   *
   *
   */
  @ResponseBody
  @GetMapping("/Best_Restaurant_list")
  public ResponseEntity<ArrayList<RestFullData>> bestRestaurantList() {
    return new ResponseEntity<>(restaurantProc.ranking_rate_select(), HttpStatus.OK);
  }

}



