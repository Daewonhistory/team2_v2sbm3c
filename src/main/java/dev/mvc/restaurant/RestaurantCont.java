package dev.mvc.restaurant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import dev.mvc.review.ReviewProcInter;
import dev.mvc.tool.Security;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/restaurant")
public class RestaurantCont {


  @Autowired
  @Qualifier("dev.mvc.restaurant.RestaurantProc")
  private RestaurantProInter restaurantProc;
  
  @Autowired
  @Qualifier("dev.mvc.favorite.FavoriteProc")
  private FavoriteProcInter favoriteProc;
  
  @Autowired
  @Qualifier("dev.mvc.review.ReviewProc")
  private ReviewProcInter reviewProc;


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
  public RestaurantCont() {
//    System.out.println("RestaurantCont created");
  }


  /*
   * 식당 등록 폼 메서드
   *
   *
   */
  @GetMapping("/create")
  public String create(Model model, RestaurantVO restaurantVO, HttpSession session) {
    String type = "s";

    ArrayList<MidAreaVO> midAreaVOS = midAreaProc.list_all();
    model.addAttribute("midAreaList",midAreaVOS);

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
  public String restaurant(Model model, HttpSession session,RedirectAttributes redirectAttributes, RestaurantVO restaurantVO, RestimgVO restimgVO, RedirectAttributes ra) {
    Integer ownerno = (Integer) session.getAttribute("ownerno");
    restaurantVO.setOwnerno(ownerno);


    int count = this.restaurantProc.create(restaurantVO);

    int restno = this.restaurantProc.foreign(restaurantVO.getOwnerno());

    if (count == 1) {

      String upDir = Restaurant.getUploadDir(); // 파일을 업로드할 폴더 준비
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
              String newFileName = "rest_" +restaurantVO.getOwnerno()+"_"+(i + 1) + "." + exe;
              String fileSaved = Upload.saveFileSpring(files[i], upDir, newFileName);

              if (Tool.isImage(fileSaved)) {
                String thumb = Tool.preview(upDir, fileSaved, 200, 150);

                restimgVO.setRestno(restno);
                restimgVO.setImagefile(fileSaved); // 저장된 파일명 설정
                restimgVO.setThumbfile(thumb); // 저장된 썸네일 파일명 설정

                int saved = this.restimgproc.create(restimgVO);
                if (saved != 1) {
                  return "redirect:/restaurant/search_b";
                }
              } else {
                return "redirect:/restaurant/search_b"; // 파일이 이미지가 아닐 경우 리다이렉트
              }
            } else {
              return "redirect:/restaurant/search_b"; // 파일 크기가 0일 경우 리다이렉트
            }
          } else {
            ra.addFlashAttribute("cnt", 0);
            ra.addFlashAttribute("code", "check_upload_file_fail");
            ra.addFlashAttribute("url", "/contents/msg"); // 메시지 페이지 URL 설정
            return "redirect:/contents/msg";
          }
        }
      }

      return "redirect:/restaurant/search_b";
    } else {
      return "redirect:/restaurant/create";
    }


  }
  @GetMapping("/read")
  public String read(Model model, String word, int now_page, int restno) {
    // 메뉴 정보

    RestFullData restFullData = this.restaurantProc.readFullData(restno);
    model.addAttribute("restFullData", restFullData);
    // 메뉴의 재료 목록


    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    return "restaurant/read";
  }


  @GetMapping("/update_map")
  public String update_map(Model model, String word, int now_page, int restno) {
    // 메뉴 정보
    ArrayList<MidAreaVO> midAreaVOS = midAreaProc.list_all();
    model.addAttribute("midAreaList",midAreaVOS);
    RestFullData restFullData = this.restaurantProc.readFullData(restno);
    model.addAttribute("restFullData", restFullData);
    // 메뉴의 재료 목록


    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    return "restaurant/update_map";
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
    ArrayList<RestDTO> restlist = this.restaurantProc.list_search_paging(word, type, now_page, Restaurant.RECORD_PER_PAGE);
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
    model.addAttribute("restDTO", restlist);

    return "restaurant/search_all"; // Assuming "search_result" is the name of the view to display the search results


  }

  @GetMapping("/search")
  public String search(Model model) {
    ArrayList<MidAreaVO> midAreaList = this.midAreaProc.list_all();
    model.addAttribute("midAreaList", midAreaList);
    ArrayList<CategoryVO> cateList = this.categoryProc.list();
    model.addAttribute("cateList", cateList);
    return "/search";
  }

//  @GetMapping("/search_list")
//  public String search_list(Model model,
//                            int person,
//                            String date,
//                            int time,
//                            @RequestParam(defaultValue = "0") int categoryno,
//                            @RequestParam(defaultValue = "") String botarea,
//                            @RequestParam(name = "min_price", defaultValue = "0") int minPrice,
//                            @RequestParam(name = "max_price", defaultValue = "40") int maxPrice) {
//    int[] botareanos = null;
//    if (!botarea.equals("")) {
//      String[] splitedBotareas = botarea.split("_");
//      botareanos = new int[splitedBotareas.length];
//      for (int i = 0; i < splitedBotareas.length; i++) {
//        botareanos[i] = Integer.parseInt(splitedBotareas[i]);
//      }
//    } else {
//      botareanos = new int[0];
//    }
//
//
//    Map<String, Object> map = new HashMap<String, Object>();
//    map.put("person", person);
//    map.put("date", date + " 00:00:00");
//    map.put("time", time);
//    map.put("categoryno", categoryno);
//    map.put("botareanos", botareanos);
//    map.put("min_price", minPrice);
//    map.put("max_price", maxPrice);
//    System.out.println("person:" + person);
//    System.out.println("date:" + date);
//    System.out.println("time:" + time);
//    System.out.println("categoryno:" + categoryno);
//    System.out.println("min_price:" + minPrice);
//    System.out.println("max_price:" + maxPrice);
//
//    ArrayList<RestaurantVO> list = this.restaurantProc.condition_search_list(map);
//
//    String date1 = this.restaurantProc.test(date);
//    model.addAttribute("list", list);
//    
//    model.addAttribute("date", date);
//    model.addAttribute("categoryno", categoryno);
//    model.addAttribute("botareanos", botareanos);
//    model.addAttribute("min_price", minPrice);
//    model.addAttribute("max_price", maxPrice);
//    model.addAttribute("person", person);
//    model.addAttribute("reserve_date", date);
//    model.addAttribute("time", time);
//    return "/search_list";
//  }
  @GetMapping("/search_list")
  public String searchList(Model model,
					        int person,
					        String date,
					        int time,
					        @RequestParam(defaultValue = "0") int categoryno,
					        @RequestParam(defaultValue = "") String botarea,
					        @RequestParam(name = "min_price", defaultValue = "0") int minPrice,
					        @RequestParam(name = "max_price", defaultValue = "40") int maxPrice) {
	  model.addAttribute("person", person);
	  model.addAttribute("reserve_date", date);
	  model.addAttribute("time", time);
	  model.addAttribute("categoryno", categoryno);
	  model.addAttribute("botarea", botarea);
	  model.addAttribute("min_price", minPrice);
	  model.addAttribute("max_price", maxPrice);
	  
	  return "/search_list";
  }


  @PostMapping("/update_map")
  public String update_map(RestFullData restFullData, RedirectAttributes ra ,String restno, String word,  String now_page) {


    int count = restaurantProc.update_map(restFullData);

    if (count == 1) {
      return "redirect:/restaurant/update_map?restno=" + restno+"&now_page="+now_page+"&word="+word;

    } else {
      return "redirect:/";
    }



  }

  @PostMapping("/update")
  public String update(RestFullData restFullData, RedirectAttributes ra ,String restno, String word,  String now_page) {


    int count = restaurantProc.update(restFullData);

    if (count == 1) {
      return "redirect:/restaurant/read?restno=" + restno+"&now_page="+now_page+"&word="+word;

    } else {
      return "redirect:/";
    }



  }



  @PostMapping("/search_list")
  @ResponseBody
  public ResponseEntity<ArrayList<RestFullData>> searchList(@RequestBody Map<String, Object> requestBody){
	  int person = Integer.parseInt((String) requestBody.get("person"));
      String date = (String) requestBody.get("date");
      int time = Integer.parseInt((String) requestBody.get("time"));
      int categoryno = 0;
      if(!((String) requestBody.get("categoryno")).equals("")) {
    	  categoryno = Integer.parseInt((String) requestBody.get("categoryno"));
      }
      
      String botarea = (String) requestBody.get("botareas");
      System.out.println("aaa:" + botarea);
      int[] botareanos;
      if(!botarea.equals("")) {
    	  System.out.println("b");
		  String[] splitedBotareas = botarea.split("_");
		  
		  botareanos = new int[splitedBotareas.length];
		  for (int i = 0; i < splitedBotareas.length; i++) {
		    botareanos[i] = Integer.parseInt(splitedBotareas[i]);
		  }
	  } else {
		  botareanos = new int[0];
	  }

      int minPrice = Integer.parseInt((String) requestBody.get("minPrice"));
      int maxPrice = Integer.parseInt((String) requestBody.get("maxPrice"));
      System.out.println("b");
      Map<String, Object> map = new HashMap<String, Object>();
      map.put("person", person);
      map.put("date", date + " 00:00:00");
      map.put("time", time);
      map.put("categoryno", categoryno);
      map.put("botareanos", botareanos);
      map.put("min_price", minPrice);
      map.put("max_price", maxPrice);
      System.out.println("person:" + person);
      System.out.println("date:" + date);
      System.out.println("time:" + time);
      System.out.println("botareanos" + botareanos.length);
      System.out.println("categoryno:" + categoryno);
      System.out.println("min_price:" + minPrice);
      System.out.println("max_price:" + maxPrice);

      ArrayList<RestFullData> list = this.restaurantProc.SearchRestaurantWithImg(map);
      System.out.println("=>listSize" + list.size());
	  return new ResponseEntity<>(list, HttpStatus.OK);
  }
  
  @GetMapping("/main_page")
  public String mainPage(Model model, HttpSession session, 
                        int restno, @RequestParam(defaultValue="2")int person, 
                        String date) {
      String accessType = (String) session.getAttribute("type");
      
      int custno = -1; // 초기값 설정, 비로그인 상태
      boolean isFavorited = false;
      
      if (accessType != null && accessType.equals("customer")) {
          custno = (int) session.getAttribute("custno");
          isFavorited = favoriteProc.isFavorited(custno, restno);
          model.addAttribute("custno", custno);
      }

      RestFullData restFullData = this.restaurantProc.readFullData(restno);
      Float averageRate = reviewProc.avg_Rate(restno);
      String rateDisplay = (averageRate == 0) ? "식당 미평가" : String.format("%.1f", averageRate);
      
      model.addAttribute("restaurantVO", restFullData);
      model.addAttribute("rateDisplay", rateDisplay);

      ArrayList<NoticeVO> noticeList = this.noticeProc.list_by_restno(restno);
      model.addAttribute("noticeList", noticeList);
      
      model.addAttribute("accessType", accessType);
      model.addAttribute("restno", restno);
      model.addAttribute("person", person);
      model.addAttribute("date", date);
      model.addAttribute("isFavorited", isFavorited); 
      
      return "restaurant_page";
  }



  
  @PostMapping("/coordinate_search_list")
  @ResponseBody
  public ResponseEntity<ArrayList<RestFullData>> coordinateSearchList(@RequestBody Map<String, Object> requestBody){
	  double westLat = (double)requestBody.get("westLat");
	  double eastLat = (double)requestBody.get("eastLat");
	  double southLng = (double)requestBody.get("southLng");
	  double northLng = (double)requestBody.get("northLng");
	  System.out.println("test");
	  ArrayList<RestFullData> list = restaurantProc.coordinateSearchList(westLat, eastLat, southLng, northLng);
	  return new ResponseEntity<>(list, HttpStatus.OK);
  }

}



