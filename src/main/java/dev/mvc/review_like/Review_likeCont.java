package dev.mvc.review_like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.customer.CustomerVO;
import jakarta.servlet.http.HttpSession;

@Controller
public class Review_likeCont {

  @Autowired
  @Qualifier("dev.mvc.review_like.Review_likeProc")
  private Review_likeProcInter review_likeProc;

  @PostMapping("/review_like/review_like")
  @ResponseBody
  public Map<String, Object> toggleLike(HttpSession session, @RequestBody Map<String, String> map) {
    CustomerVO customerVO = (CustomerVO) session.getAttribute("login");
    Map<String, Object> response = new HashMap<>();
    int reviewno = Integer.parseInt(map.get("reviewno"));

    if (customerVO != null) {
      int custno = customerVO.getCustno();
      if (Boolean.parseBoolean(map.get("review_like"))) { // increase
        review_likeProc.increased_likes(reviewno, custno);
        response.put("success", "increase");
      } else { // decrease
        review_likeProc.decreased_likes(reviewno, custno);
        response.put("success", "decrease");
      }
    } else {
      response.put("fail", "login");
    }

    int likes_count = review_likeProc.likes_count(reviewno);
    response.put("likes_count", likes_count);
    return response;
  }
}
