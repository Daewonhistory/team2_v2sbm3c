package dev.mvc.tool;

import dev.mvc.dto.HistoryDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PublicTools {

  public boolean isCustomer(HttpSession session) {
    boolean sw = false; // 로그인하지 않은 것으로 초기화
    String type = (String) session.getAttribute("type");
//    System.out.println(grade);

    if (type != null) {
      if (type.equals("customer")) {
        sw = true;  // 로그인 한 경우
      }
    }
    return sw;
  }


  public boolean isManager(HttpSession session) {
    boolean sw = false; // 로그인하지 않은 것으로 초기화
    String type = (String) session.getAttribute("type");
//    System.out.println(grade);

    if (type != null) {
      if (type.equals("manager")) {
        sw = true;  // 로그인 한 경우
      }
    }
    return sw;
  }


  public String isOwner(String type) {
    String sw = ""; // 로그인하지 않은 것으로 초기화

//    System.out.println(grade);

    if (type != null) {
      if (type.equals("owner")) {
        sw = "owner";  // 로그인 한 경우
      }else if (type.equals("NotCerti")) {
        sw = "NotCerti";
      } else {
        sw = "ready";

      }
    }
    return sw;
  }



  public Map<String, List<HistoryDTO>> groupByLoginDate(List<HistoryDTO> loginHistoryList) {

    return loginHistoryList.stream().collect(Collectors.groupingBy(HistoryDTO::getLogin_date));


  }
}
