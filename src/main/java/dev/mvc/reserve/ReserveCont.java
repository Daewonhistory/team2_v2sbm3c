package dev.mvc.reserve;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.admitperson.AdmitPersonProcInter;
import dev.mvc.admitperson.AdmitPersonVO;
import dev.mvc.dto.ReserveDTO;
import dev.mvc.dto.RestDTO;
import dev.mvc.restaurant.RestaurantProInter;
import dev.mvc.restaurant.RestaurantVO;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/reservation")
@Controller
public class ReserveCont {
  
  @Autowired
  @Qualifier("dev.mvc.admitperson.AdmitPersonProc")
  private AdmitPersonProcInter admitPersonProc;
  
  @Autowired
  @Qualifier("dev.mvc.restaurant.RestaurantProc")
  private RestaurantProInter restaurantProc;

  @Autowired
  @Qualifier("reserveProc")
  private ReserveProcInter reserveProc;
    
  @GetMapping("/create")
  public String create(Model model, HttpSession session, int admitpersonno, int person) {
    // 예약 가능한 정보 가져오기
    AdmitPersonVO admitPerson = admitPersonProc.read(admitpersonno);
    RestaurantVO restaurant = restaurantProc.read(admitPerson.getRestno());
    int custno = (int)session.getAttribute("custno");
    
    //System.out.println("custno:" + custno);
    model.addAttribute("admitPerson", admitPerson);
    model.addAttribute("restaurant", restaurant);
    model.addAttribute("person", person);
    model.addAttribute("custno", custno);
    
    return "reservation/create_mobile";
  }

  @PostMapping("/create")
  public String create(ReserveVO reserveVO) {
    // 예약 정보 설정
    reserveVO.setSub_date(new Date(System.currentTimeMillis())); // 현재 날짜를 예약 신청 날짜로 설정
    System.out.println(reserveVO.getSub_date());
    int cnt = reserveProc.create(reserveVO);
    if( cnt == 1) {
      admitPersonProc.update_curr_person(reserveVO.getAdmitpersonno());
    }

    // 예약 생성 후 성공 메시지를 포함하여 list_all 페이지로 리다이렉트
    return "redirect:/reservation/list_all?message=success";
  }
  
  @GetMapping("/list_all")
  public String listAll(Model model, HttpSession session, @RequestParam(required = false) String reserve_date) {
    int custno = (int) session.getAttribute("custno");
    ArrayList<ReserveVO> reserveList;
      if (reserve_date == null || reserve_date.isEmpty()) {
        reserveList = reserveProc.list_search_by_custno(custno);
    } else {
        Map<String, Object> params = new HashMap<>();
        params.put("custno", custno);
        params.put("reserveDate", reserve_date);
        reserveList = reserveProc.list_search_by_reserve_date(params);
    }
    ArrayList<Map<String, Object>> reserveDetails = new ArrayList<>();

    for (ReserveVO reserve : reserveList) {
        AdmitPersonVO admitPerson = admitPersonProc.read(reserve.getAdmitpersonno());
        RestaurantVO restaurant = restaurantProc.read(admitPerson.getRestno());

        Map<String, Object> details = new HashMap<>();
        details.put("reserve", reserve);
        details.put("restaurantName", restaurant.getName());
        details.put("reserveDate", admitPerson.getReserve_date()); // 예약 날짜 추가
        details.put("reserveTime", admitPerson.getTime());
        reserveDetails.add(details);
    }

    // reserveDetails를 reserveDate를 기준으로 정렬 (내림차순)
    reserveDetails.sort((d1, d2) -> ((Date) d2.get("reserveDate")).compareTo((Date) d1.get("reserveDate")));

    model.addAttribute("reserveDetails", reserveDetails);
    return "reservation/list_all";
  }
  
  @GetMapping("/list_reserve_paging")
  public String list_reserve_paging(HttpSession session, 
                                    Model model, 
                                    @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
      if (now_page < 1) {
          now_page = 1;
      }
      
      
      
      String accessType = (String) session.getAttribute("type");
      model.addAttribute("accessType", accessType);
       
      ArrayList<ReserveDTO> list = this.reserveProc.list_reserve_paging(now_page, Reserve.RECORD_PER_PAGE);
      System.out.println("Reserve List: " + list);
      model.addAttribute("list", list);

      int count = this.reserveProc.count_all();
      System.out.println("Total Count: " + count);
      model.addAttribute("count", count);

      String paging = this.reserveProc.pagingBox(now_page, "/reservation/list_reserve_paging", count, Reserve.RECORD_PER_PAGE, Reserve.PAGE_PER_BLOCK);
      model.addAttribute("paging", paging);

      model.addAttribute("now_page", now_page);

      return "reservation/list_reserve_paging";
  }
  
  @GetMapping("/list_owner_page")
  public String list_owner_page(HttpSession session, 
                                Model model, 
                                @RequestParam(name = "now_page", defaultValue = "1") int now_page,
                                @RequestParam(name = "reserve_date", required = false) String reserve_date,
                                @RequestParam(name = "restno", defaultValue = "0") int restno) {
      int ownerno = (int) session.getAttribute("ownerno");

      if (now_page < 1) {
          now_page = 1;
      }

      if (reserve_date == null || reserve_date.isEmpty()) {
          reserve_date = java.time.LocalDate.now().toString();
      }

      ArrayList<ReserveDTO> list = reserveProc.list_owner_page(now_page, Reserve.RECORD_PER_PAGE, reserve_date, ownerno, restno);
      model.addAttribute("list", list);

      int count = (restno == 0) ? reserveProc.count_by_owner(ownerno) : reserveProc.count_by_restno(restno);
      model.addAttribute("count", count);

      String paging = reserveProc.pagingBox(now_page, "/reservation/list_owner_page", count, Reserve.RECORD_PER_PAGE, Reserve.PAGE_PER_BLOCK);
      model.addAttribute("paging", paging);

      model.addAttribute("now_page", now_page);
      model.addAttribute("reserve_date", reserve_date);
      model.addAttribute("restno", restno);

      ArrayList<RestDTO> restList = restaurantProc.list_by_ownerno(ownerno);
      model.addAttribute("RestList", restList);

      return "reservation/list_owner_page";
  }
  

  
  @PostMapping("/delete")
  public String delete(@RequestParam("re=serveno") int reserveno) {
      int cnt = reserveProc.delete(reserveno);
      if (cnt == 1) {
          // 성공적으로 삭제된 경우 처리
          return "redirect:/reservation/list_all";
      } else {
          // 실패한 경우 처리
          return "error/500";
      }
  }
  
  @PostMapping("/delete_site")
  public String deleteSite(@RequestParam("reserveno") int reserveno, @RequestParam("now_page") int now_page, RedirectAttributes redirectAttributes) {
      int cnt = reserveProc.delete(reserveno);
      if (cnt == 1) {
          redirectAttributes.addAttribute("now_page", now_page); // 현재 페이지 번호를 전달
          return "redirect:/reservation/list_reserve_paging";
      } else {
          // 실패한 경우 처리
          return "error/500";
      }
  }
  
  @PostMapping("/delete_owner_page")
  public String deleteOwnerPage(@RequestParam("reserveno") int reserveno, @RequestParam("now_page") int now_page, @RequestParam("reserve_date") String reserve_date, RedirectAttributes redirectAttributes) {
      int cnt = reserveProc.delete(reserveno);
      if (cnt == 1) {
          redirectAttributes.addAttribute("now_page", now_page); // 현재 페이지 번호를 전달
          redirectAttributes.addAttribute("reserve_date", reserve_date); // 예약 날짜를 전달
          return "redirect:/reservation/list_owner_page";
      } else {
          // 실패한 경우 처리
          return "error/500";
      }
  }
  
  
}
