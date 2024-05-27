package dev.mvc.review;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/review")
@Controller
public class ReviewCont {
    @Autowired
    @Qualifier("dev.mvc.review.ReviewProc")
    private ReviewProcInter reviewProc;

    public ReviewCont() {
        System.out.println("-> ReviewCont created.");
    }

    @GetMapping("/create")
    public String create(ReviewVO reviewVO) {

        return "/review/create";
    }

    @PostMapping(value = "/create")
    public String create(Model model, @Valid ReviewVO reviewVO, BindingResult bindingResult,
                                RedirectAttributes ra) {

        int cnt = this.reviewProc.create(reviewVO);
        System.out.println("-> cnt: " + cnt);

        if (cnt == 1) {
            
            return "redirect:/review/list_all";
        } else {
            model.addAttribute("code", "code");
            return "review/msg";
        }
    }


    
    /**
     * 목록
     * @param model
     * @param reviewVO
     * @return
     */
    @GetMapping(value = "/list_all")
    public String review_list(Model model, ReviewVO reviewVO) {
      ArrayList<ReviewVO> list = this.reviewProc.list_all();
      model.addAttribute("list", list);
      System.out.println("size:" + list.size());
      return "review/list_all";
    }

    
    @GetMapping("read")
    public String read(Model model, int reviewno) {
        ReviewVO reviewVO = this.reviewProc.read(reviewno);
        model.addAttribute("reviewVO", reviewVO);

        return "review/read";
    }




    @GetMapping("/update")
    public String update(Model model,@RequestParam("reviewno") int reviewno, String word) {
      ReviewVO reviewVO = this.reviewProc.read(reviewno);
      model.addAttribute("reviewVO",reviewVO);

      return "review/update";
    }
    
    @PostMapping("/update")
    public String update_process(Model model, ReviewVO reviewVO) {
      ReviewVO reviewVO_1 = this.reviewProc.read(reviewVO.getReviewno());
      
      int cnt = this.reviewProc.update_review(reviewVO);
      if(cnt == 1) {
        return "redirect:/review/list_all";
      }else {
        model.addAttribute("code" , "update_fail");
        return "review/msg";
      }
      }


    /**
     * 삭제
     * @param model
     * @param reviewVO
     * @return
     */
    @PostMapping(value = "/delete")
    public String review_delete(Model model,@RequestParam("reviewno") int reviewno) {
      
      
      int cnt = this.reviewProc.delete_review(reviewno);
      if( cnt == 1) {
        return "redirect:/review/list_all";
      }

      return "review/list_all";
     
    }
  }

