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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.dto.ReviewDTO;
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

    public ReviewCont() {
        System.out.println("-> ReviewCont created.");
    }

    @GetMapping("/create")
    public String create(ReviewVO reviewVO) {
        return "/review/create";
    }

    @PostMapping(value = "/create")
    public String create(Model model, @Valid ReviewVO reviewVO, BindingResult bindingResult,ReviewimgVO reviewimgVO,
                                RedirectAttributes ra,
                                 MultipartFile file1MF,
                                 MultipartFile file2MF,
                                 MultipartFile file3MF) {
      
      reviewVO.setCustno(16); // 예시로 설정, 실제로는 로그인된 사용자의 ID를 설정
      reviewVO.setRestno(4); // 예시로 설정, 실제로는 선택된 식당의 ID를 설정

      int cnt = this.reviewProc.create(reviewVO); 
      
      int restno = reviewVO.getRestno();
      int custno = reviewVO.getCustno();
      
      int foreign = reviewProc.foreign(restno,custno);
      
      System.out.println("-> cnt: " + cnt);
      System.out.println("-> Generated reviewno: " + reviewVO.getReviewno());  // 추가된 로그 확인 부분

      if (cnt == 1) {
          
        int reviewno = reviewVO.getReviewno(); // 생성된 리뷰의 번호를 가져옴
        String upDir = Review.getUploadDir(); // 파일을 업로드할 폴더 준비
        System.out.println("-> upDir: " + upDir);

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
                            reviewimgVO.setImagefile(fileSaved); // 저장된 파일명 설정
                            reviewimgVO.setThumbfile(thumb); // 저장된 썸네일 파일명 설정

                            int saved = this.reviewimgproc.create(reviewimgVO);
                            if (saved != 1) {
                                return "redirect:/review/create";
                            }
                        } else {
                            return "redirect:/review/create"; // 파일이 이미지가 아닐 경우 리다이렉트
                        }
                    } else {
                        return "redirect:/review/create"; // 파일 크기가 0일 경우 리다이렉트
                    }
                } else {
                    ra.addFlashAttribute("cnt", 0);
                    ra.addFlashAttribute("code", "check_upload_file_fail");
                    ra.addFlashAttribute("url", "/review/msg"); // 메시지 페이지 URL 설정
                    return "redirect:/review/msg";
                }
            }
        }
        return "redirect:/review/list_paging";
      } else {
          return "redirect:/review/create";
      }
    }


    @GetMapping("/list_paging")
    public String list_paging(HttpSession session, Model model, @RequestParam(name = "now_page", defaultValue = "1") int now_page) {
        if (now_page < 1) {
            now_page = 1;
        }

        // 페이징된 리뷰 목록 가져오기
        ArrayList<ReviewDTO> list = this.reviewProc.list_paging(now_page, Review.RECORD_PER_PAGE);
        model.addAttribute("list", list);
        
        System.out.println(list.size());

        // 총 리뷰 개수 가져오기
        int count = this.reviewProc.list_count(); 
        model.addAttribute("count", count);
        
        System.out.println(count);

        // 페이징 박스 생성
        String paging = this.reviewProc.pagingBox(now_page, "/review/list_paging", count, Review.RECORD_PER_PAGE, Review.PAGE_PER_BLOCK);
        model.addAttribute("paging", paging);

        model.addAttribute("now_page", now_page);
        
        System.out.println(paging);

        return "review/list_paging"; // 페이징 결과를 출력할 view
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
        int cnt = this.reviewProc.delete_review(reviewno);
        if (cnt == 1) {
            return "redirect:/review/list_all";
        }
        return "review/list_all";
    }
}
