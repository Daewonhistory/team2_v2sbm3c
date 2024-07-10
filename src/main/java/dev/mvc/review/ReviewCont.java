package dev.mvc.review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.customer.CustomerVO;
import dev.mvc.dto.ReviewDTO;
import dev.mvc.restaurant.RestaurantProC;
import dev.mvc.review_like.Review_likeProcInter;
import dev.mvc.reviewimg.ReviewImgProcInter;
import dev.mvc.reviewimg.ReviewimgVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RequestMapping("/review")
@Controller
public class ReviewCont {
    @Autowired
    @Qualifier("dev.mvc.review.ReviewProc")
    private ReviewProcInter reviewProc;

    @Autowired
    @Qualifier("dev.mvc.reviewimg.ReviewImgProc")
    private ReviewImgProcInter reviewimgproc;
    
    @Autowired
    @Qualifier("dev.mvc.restaurant.RestaurantProc")
    private RestaurantProC restaurantProc;
    
    @Autowired
    @Qualifier("dev.mvc.review_like.Review_likeProc")
    private Review_likeProcInter review_likeProc;

    public ReviewCont() {
        System.out.println("-> ReviewCont created.");
    }

    @GetMapping("/create")
    public String create(ReviewVO reviewVO) {
        return "/review/create";
    }
    
    @GetMapping("/create_mobile")
    public String create_mobile(HttpSession session, Model model, 
                                @RequestParam("restno") int restno, 
                                @RequestParam("person") int person, 
                                @RequestParam("date") String date) {
        // 현재 로그인한 사용자 확인
        Integer custno = (Integer) session.getAttribute("custno");
        if (custno == null) {
            return "redirect:/customer/login";
        }

        model.addAttribute("restno", restno);
        model.addAttribute("person", person);
        model.addAttribute("date", date);

        return "/review/create_mobile";
    }

    @PostMapping("/create_mobile")
    public String create_mobile(Model model, @Valid ReviewVO reviewVO, BindingResult bindingResult, 
                                ReviewimgVO reviewimgVO, RedirectAttributes ra, 
                                MultipartFile file1MF, MultipartFile file2MF, MultipartFile file3MF, HttpSession session) {
        Integer custno = (Integer) session.getAttribute("custno");
        if (custno == null) {
            return "redirect:/customer/login";
        }
        
        reviewVO.setCustno(custno);
        int cnt = this.reviewProc.create(reviewVO); 

        int restno = reviewVO.getRestno();
        int foreign = reviewProc.foreign(restno, custno);

        if (cnt == 1) {
            int reviewno = reviewVO.getReviewno();
            String upDir = Review.getUploadDir();

            MultipartFile[] files = {file1MF, file2MF, file3MF};
            for (MultipartFile mf : files) {
                if (!mf.isEmpty()) {
                    String originalFileName = mf.getOriginalFilename();
                    if (Tool.checkUploadFile(originalFileName)) {
                        long size = mf.getSize();
                        if (size > 0) {
                            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                            String newFileName = "review_" + System.currentTimeMillis() + ext;
                            String fileSaved = Upload.saveFileSpring(mf, upDir, newFileName);
                            if (Tool.isImage(fileSaved)) {
                                String thumb = Tool.preview(upDir, fileSaved, 200, 150);

                                reviewimgVO.setReviewno(foreign);
                                reviewimgVO.setImagefile(fileSaved);
                                reviewimgVO.setThumbfile(thumb);

                                int saved = this.reviewimgproc.create(reviewimgVO);
                                if (saved != 1) {
                                    return "redirect:/review/create_mobile";
                                }
                            } else {
                                return "redirect:/review/create_mobile";
                            }
                        } else {
                            return "redirect:/review/create_mobile";
                        }
                    } else {
                        ra.addFlashAttribute("cnt", 0);
                        ra.addFlashAttribute("code", "check_upload_file_fail");
                        ra.addFlashAttribute("url", "/review/msg");
                        return "redirect:/review/msg";
                    }
                }
            }

            // 여기서 식당의 평균 평점을 업데이트하는 로직을 추가
            Float averageRate = reviewProc.avg_Rate(restno);
            if (averageRate != 0) {
                restaurantProc.updateRate(restno, averageRate);
            }

            return "redirect:/customer/my_page";
        } else {
            return "redirect:/review/create_mobile";
        }
    }


    @PostMapping(value = "/create")
    public String create(Model model, @Valid ReviewVO reviewVO, BindingResult bindingResult, 
                         ReviewimgVO reviewimgVO, RedirectAttributes ra, 
                         MultipartFile file1MF, MultipartFile file2MF, MultipartFile file3MF,
                         HttpSession session) {
      
        // 세션에서 custno 가져오기
        if (session.getAttribute("custno") == null) {
            return "redirect:/customer/login";
        }
        int custno = (int) session.getAttribute("custno");
        reviewVO.setCustno(custno);

        reviewVO.setRestno(4); // 예시로 설정, 실제로는 선택된 식당의 ID를 설정

        int cnt = this.reviewProc.create(reviewVO); 
      
        int restno = reviewVO.getRestno();
        int foreign = reviewProc.foreign(restno, custno);

        if (cnt == 1) {
            int reviewno = reviewVO.getReviewno();
            String upDir = Review.getUploadDir();

            MultipartFile[] files = {file1MF, file2MF, file3MF};
            for (MultipartFile mf : files) {
                if (!mf.isEmpty()) {
                    String originalFileName = mf.getOriginalFilename();
                    if (Tool.checkUploadFile(originalFileName)) {
                        long size = mf.getSize();
                        if (size > 0) {
                            String ext = originalFileName.substring(originalFileName.lastIndexOf("."));
                            String newFileName = "review_" + System.currentTimeMillis() + ext;
                            String fileSaved = Upload.saveFileSpring(mf, upDir, newFileName);
                            if (Tool.isImage(fileSaved)) {
                                String thumb = Tool.preview(upDir, fileSaved, 200, 150);

                                reviewimgVO.setReviewno(foreign);
                                reviewimgVO.setImagefile(fileSaved);
                                reviewimgVO.setThumbfile(thumb);

                                int saved = this.reviewimgproc.create(reviewimgVO);
                                if (saved != 1) {
                                    return "redirect:/review/create";
                                }
                            } else {
                                return "redirect:/review/create";
                            }
                        } else {
                            return "redirect:/review/create";
                        }
                    } else {
                        ra.addFlashAttribute("cnt", 0);
                        ra.addFlashAttribute("code", "check_upload_file_fail");
                        ra.addFlashAttribute("url", "/review/msg");
                        return "redirect:/review/msg";
                    }
                }
            }

            // 여기서 식당의 평균 평점을 업데이트하는 로직을 추가
            Float averageRate = reviewProc.avg_Rate(restno);
            if (averageRate != 0) {
                restaurantProc.updateRate(restno, averageRate);
            }

            return "redirect:/review/list_paging";
        } else {
            return "redirect:/review/create";
        }
    }

    @GetMapping("/list_paging")
    public String list_paging(HttpSession session, Model model,
                              @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
        if (now_page < 1) {
            now_page = 1;
        }

        ArrayList<ReviewDTO> list = this.reviewProc.list_paging(now_page, Review.RECORD_PER_PAGE);
        model.addAttribute("list", list);

        int count = this.reviewProc.list_count();
        model.addAttribute("count", count);

        String paging = this.reviewProc.pagingBox(now_page, "/review/list_paging", count, Review.RECORD_PER_PAGE, Review.PAGE_PER_BLOCK);
        model.addAttribute("paging", paging);

        model.addAttribute("now_page", now_page);

        return "review/list_paging";
    }

    @GetMapping("/read")
    public String read(Model model, int reviewno) {
        ReviewVO reviewVO = this.reviewProc.read(reviewno);
        model.addAttribute("reviewVO", reviewVO);
        return "review/read";
    }

    @GetMapping("/update")
    public String update(Model model, @RequestParam("reviewno") int reviewno, String word) {
        ReviewVO reviewVO = this.reviewProc.read(reviewno);
        model.addAttribute("reviewVO", reviewVO);
        return "review/update";
    }

    @PostMapping("/update")
    public String update_process(Model model, ReviewVO reviewVO) {
        int cnt = this.reviewProc.update_review(reviewVO);
        if (cnt == 1) {
            return "redirect:/review/list_paging";
        } else {
            model.addAttribute("code", "update_fail");
            return "review/msg";
        }
    }

    @PostMapping("/delete")
    public String review_delete(Model model, @RequestParam("reviewno") int reviewno) {
        ReviewVO reviewVO = this.reviewProc.read(reviewno);
        int restno = reviewVO.getRestno();
        
        int cnt = this.reviewProc.delete_review(reviewno);
        if (cnt == 1) {
            // 리뷰 삭제 후 식당의 평균 평점을 갱신하는 로직 추가
            Float averageRate = reviewProc.avg_Rate(restno);
            restaurantProc.updateRate(restno, averageRate);
            return "redirect:/review/list_paging";
        }
        return "review/list_paging";
    }
    
    @PostMapping("/delete_mobile")
    public String review_delete_mobile(Model model, @RequestParam("reviewno") int reviewno, @RequestParam("redirect") String redirect) {
        ReviewVO reviewVO = this.reviewProc.read(reviewno);
        int restno = reviewVO.getRestno();
        
        int cnt = this.reviewProc.delete_review(reviewno);
        if (cnt == 1) {
            // 리뷰 삭제 후 식당의 평균 평점을 갱신하는 로직 추가
            Float averageRate = reviewProc.avg_Rate(restno);
            restaurantProc.updateRate(restno, averageRate);
            return "redirect:" + redirect;
        }
        return "review/review_my_page";
    }
    
    @GetMapping("/reviewAllList")
    public String reviewAllList(HttpSession session, Model model,
                                @RequestParam(name = "restno") int restno,
                                @RequestParam(name = "person") int person,
                                @RequestParam(name = "date") String date) {
        List<ReviewDTO> list = reviewProc.list_by_restno(restno);

        // 좋아요 수 및 사용자의 좋아요 여부를 설정
        CustomerVO customerVO = (CustomerVO) session.getAttribute("customerVO");
        for (ReviewDTO review : list) {
            int likesCount = review_likeProc.likes_count(review.getReviewno());
            review.setLikes_count(likesCount);

            if (customerVO != null) {
                int myLike = review_likeProc.mylikes(review.getReviewno(), customerVO.getCustno());
                review.setMylike(myLike);
            }
        }

        model.addAttribute("list", list);
        model.addAttribute("restno", restno);
        model.addAttribute("person", person);
        model.addAttribute("date", date);

        return "review/review_list";
    }
    
    @GetMapping("/review_my_page")
    public String reviewMyPage(HttpSession session, Model model) {
        Integer custno = (Integer) session.getAttribute("custno");
        if (custno == null) {
            return "redirect:/customer/login";
        }

        List<ReviewDTO> reviews = reviewProc.list_by_custno(custno);
        model.addAttribute("reviews", reviews);
        return "review/review_my_page";
    }
    
    @PostMapping("/like")
    @ResponseBody
    public Map<String, Object> likeReview(HttpSession session, @RequestBody Map<String, String> map) {
        Integer custno = (Integer) session.getAttribute("custno");
        Map<String, Object> response = new HashMap<>();
        int reviewno = Integer.parseInt(map.get("reviewno"));
        if (custno != null) {
            System.out.println("Customer number: " + custno);
            if (Boolean.parseBoolean(map.get("liked"))) {
                review_likeProc.decreased_likes(reviewno, custno);
                response.put("success", "decreased");
            } else {
                review_likeProc.increased_likes(reviewno, custno);
                response.put("success", "increased");
            }
            int likes_count = review_likeProc.likes_count(reviewno);
            System.out.println("Likes count: " + likes_count);
            response.put("likes_count", likes_count);
        } else {
            response.put("fail", "login");
        }
        return response;
    }
    
    @GetMapping("/update_mobile")
    public String update_mobile(Model model, @RequestParam("reviewno") int reviewno) {
        ReviewVO reviewVO = this.reviewProc.read(reviewno);
        model.addAttribute("reviewVO", reviewVO);
        return "review/update_mobile";
    }

    @PostMapping("/update_mobile")
    public String update_mobile_process(Model model, @Valid ReviewVO reviewVO, BindingResult bindingResult, 
                                        ReviewimgVO reviewimgVO, RedirectAttributes ra, 
                                        MultipartFile file1MF, MultipartFile file2MF, MultipartFile file3MF,
                                        HttpSession session) {
        if (session.getAttribute("custno") == null) {
            return "redirect:/customer/login";
        }
        int custno = (int) session.getAttribute("custno");
        reviewVO.setCustno(custno);

        int cnt = this.reviewProc.update_review(reviewVO);
        if (cnt == 1) {
            return "redirect:/review/review_my_page";
        } else {
            model.addAttribute("code", "update_fail");
            return "review/msg";
        }
    }



    
    
}
