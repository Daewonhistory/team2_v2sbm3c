package dev.mvc.review_like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.customer.CustomerVO;
import dev.mvc.review.ReviewProcInter;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/review_like")
@Controller
public class Review_likeCont {

  @Autowired
  @Qualifier("dev.mvc.review.ReviewProc")
  private ReviewProcInter reviewProc;

  @Autowired
  @Qualifier("dev.mvc.review_like.Review_likeProc")
  private Review_likeProcInter review_likeProc;

  @PostMapping(value = "/like")
  @ResponseBody
  public Map<String, Object> likes(HttpSession session, @RequestBody Map<String, Object> payload) {
    Integer custno = (Integer) session.getAttribute("custno");
    Map<String, Object> response = new HashMap<>();

    if (custno != null) {
      int reviewno = Integer.parseInt(payload.get("reviewno").toString());
      boolean liked = Boolean.parseBoolean(payload.get("liked").toString());

      if (liked) { // decrease
        review_likeProc.decreased_likes(reviewno, custno);
        response.put("success", "decreased");
      } else { // increase
        review_likeProc.increased_likes(reviewno, custno);
        response.put("success", "increased");
      }

      int likes_count = review_likeProc.likes_count(reviewno);
      response.put("likes_count", likes_count);
    } else {
      response.put("fail", "login");
      System.out.println("Login fail: No custno found in session");
    }
    return response;
  }

}
