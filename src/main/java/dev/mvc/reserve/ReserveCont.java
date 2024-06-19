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

import dev.mvc.admitperson.AdmitPersonProcInter;
import dev.mvc.admitperson.AdmitPersonVO;
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
  

  @PostMapping("/delete")
  public String delete(@RequestParam("reserveno") int reserveno) {
      int cnt = reserveProc.delete(reserveno);
      if (cnt == 1) {
          // 성공적으로 삭제된 경우 처리
          return "redirect:/reservation/list_all";
      } else {
          // 실패한 경우 처리
          return "error/500";
      }
  }
  
  
}
