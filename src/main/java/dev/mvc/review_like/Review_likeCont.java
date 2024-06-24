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
  public Map<String, Object> likes(HttpSession session, Model model, @RequestBody Map<String, String> map) {
    CustomerVO customerVO = (CustomerVO) session.getAttribute("login");
    Map<String, Object> response = new HashMap<>();
    int reviewno = Integer.parseInt(map.get("reviewno"));
    if (customerVO != null) {
      int custno = customerVO.getCustno();
      if (Boolean.parseBoolean(map.get("liked"))) { // decrease
        this.review_likeProc.decreased_likes(reviewno, custno);
        response.put("success", "decreased");
      } else {
        this.review_likeProc.increased_likes(reviewno, custno);
        response.put("success", "increased");
      }
    } else {
      response.put("fail", "login");
    }
    int likes_count = this.review_likeProc.likes_count(reviewno);
    response.put("likes_count", likes_count);
    return response;
  }

}
