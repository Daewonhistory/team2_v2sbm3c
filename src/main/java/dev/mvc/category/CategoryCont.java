package dev.mvc.category;

import java.io.UnsupportedEncodingException;


import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@RequestMapping("/category")
@Controller
public class CategoryCont {
  @Autowired
  @Qualifier("dev.mvc.category.CategoryProc")
  private CategoryProcInter categoryProc;

  /**
   * 페이지당 출력할 레코드 개수
   */
  public int record_per_page = 10;

  ;
  /**
   * 블록당 페이지 수, 하나의 블럭은 10개의 페이지로 구성됨  nowPage는 1부터 시작
   */
  public int page_per_blcok = 10;

  // 객체 생성
  public CategoryCont() {
    System.out.println("-> CateCont created.");
  }

//  @GetMapping(value="/list_all") // 오버랜딩cate/list_all
//  @ResponseBody
//  public String create() {
//    return "<h2>Create test.</h2>";
//  }

  // 폼 출력
//  @GetMapping(value = "/list_all") // 오버랜딩cate/list_all
//  public String list(CategoryVO categoryVO, Model model) {
//    categoryVO.setNamesub("-");
//    ArrayList<CategoryVO> list_all_name_y = this.categoryProc.list_all_name_y();
//
//
//    ArrayList<CategoryVO> list = this.categoryProc.list();
//
//
//    model.addAttribute("list", list);
//    return "/category/list_all"; // /templates/category/list_all.html
//  }

  @GetMapping(value = "/list_y") // 오버랜딩cate/list_all
  public String list_y(CategoryVO categoryVO, Model model) {
    ArrayList<CategoryVO> list = this.categoryProc.list_all_name_y();


    model.addAttribute("catelist", list);
    return "cate/list_y"; // /templates/category/list_all.html
  }

  // 폼 데이터 처리


//  @GetMapping("/list_all")
//     public String list(Model model) {
//      ArrayList<CategoryVO> list = this.categoryProc.list();
//
//
//      model.addAttribute("catelist",list);
//      return "cate/list_all";
//    }

  // 단일 조회
  @GetMapping("/read/{cateno}")
  public String read(Model model, @PathVariable int cateno, @RequestParam(name = "word", defaultValue = "") String word, @RequestParam(name = "type") String type, @RequestParam(defaultValue = "1") int now_page, HttpSession session) {
    String id = (String) session.getAttribute("id");
    String grade = (String) session.getAttribute("grade");


    Optional<CategoryVO> read = this.categoryProc.read(cateno);

    type = Tool.typecheckNull(type);

    int count = this.categoryProc.list_search_count(word, type);
    int num = count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("num", num);
    ArrayList<CategoryVO> catelist = this.categoryProc.list_search_paging(word, type, now_page, this.record_per_page);
    String paging = this.categoryProc.pagingBox(now_page, word, type, "/category/list", count, record_per_page, page_per_blcok);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("searchlist", catelist);

    if (read.isPresent()) {
      model.addAttribute("word", word);
      model.addAttribute("type", type);
      model.addAttribute("categoryVO", read.get());
      return "category/read";
    } else {
      return "redirect:/category/list_all";
    }


  }

  @GetMapping("/read_y/{cateno}")
  public String read_y(Model model, @PathVariable int cateno) {

    Optional<CategoryVO> read = this.categoryProc.read(cateno);

    ArrayList<CategoryVO> list = this.categoryProc.list();


    model.addAttribute("catelist", list);

    if (read.isPresent()) {

      model.addAttribute("categoryVO", read.get());
      return "cate/read_y"; // 적절한 뷰 이름을 반환합니다.
    } else {
      return "redirect:/category/list_y";
    }


  }

  // 수정 폼
  @GetMapping("/update/{cateno}")
  public String updateForm(Model model, @PathVariable int cateno, @RequestParam(name = "word", defaultValue = "") String word, @PathVariable(required = false) @RequestParam(name = "type", required = false, defaultValue = "") String type, @RequestParam(defaultValue = "1") int now_page, HttpSession session) {
    String id = (String) session.getAttribute("id");
    String grade = (String) session.getAttribute("grade");

    Optional<CategoryVO> read = this.categoryProc.read(cateno);


    type = Tool.typecheckNull(type);

    if (word == null || word.equals("null")) {
      word = read.get().getName();

    }


    int count = this.categoryProc.list_search_count(word, type);
    ArrayList<CategoryVO> catelist = this.categoryProc.list_search_paging(word, type, now_page, this.record_per_page);
    int num = count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("num", num);
    String paging = this.categoryProc.pagingBox(now_page, word, type, "/category/list", count, record_per_page, page_per_blcok);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("word", word);
    model.addAttribute("type", type);


    model.addAttribute("searchlist", catelist);


    if (read.isPresent()) {
      model.addAttribute("categoryVO", read.get());


      return "category/update"; // 적절한 뷰 이름을 반환합니다
    } else {
      return "redirect:cate/search";
    }


  }


  @PostMapping("/update/{cateno}")
  public String update(Model model, @Valid CategoryVO categoryVO, BindingResult bindingResult, @PathVariable("cateno") int cateno, @RequestParam(name = "word", defaultValue = "") String word, @RequestParam(name = "type", defaultValue = "100") String type, RedirectAttributes rttr, @RequestParam(defaultValue = "1") int now_page, HttpSession session) throws UnsupportedEncodingException {


    int count = this.categoryProc.list_search_count(word, type);
    ArrayList<CategoryVO> catelist = this.categoryProc.list_search_paging(word, type, now_page, this.record_per_page);
    int num = count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("num", num);
    String paging = this.categoryProc.pagingBox(now_page, word, type, "/category/list", count, record_per_page, page_per_blcok);
    model.addAttribute("paging", paging);
    model.addAttribute("searchlist", catelist);
    model.addAttribute("now_page", now_page);

    if (bindingResult.hasErrors()) {
      return "category/update"; // 유효성 검사 에러가 있을 경우 수정 폼으로 이동
    }

    if (word != null && type != null) {
      model.addAttribute("word", word);
      model.addAttribute("type", type);
    }


    int cnt = this.categoryProc.update(categoryVO);
    model.addAttribute("cnt", cnt);

    if (cnt == 1) {
      rttr.addFlashAttribute("m", cateno + "번이 수정되었습니다");

      String redirectUrl = String.format("/category/update/%d?word=%s&type=%s&now_page=%s", cateno, Tool.encode(word), Tool.encode(type), now_page);
      return "redirect:" + redirectUrl;
    } else {
      model.addAttribute("code", "update_fail");
    }

    model.addAttribute("categoryVO", categoryVO);
    return "cate/updatemsg"; // 수정 결과 메시지를 표시하는 뷰로 이동


  }

  /***
   *
   *  출력 순서 높임: seqno 10 ->1
   * @param model
   * @param cateno 조회할 카테고리 번호
   */


  @PostMapping("/upforward/{cateno}")
  public String update_forward(Model model, @PathVariable("cateno") int cateno, @RequestParam(name = "type", defaultValue = "100") String type, @RequestParam(name = "word", defaultValue = "") String word, HttpServletRequest request, @RequestParam(name = "now_page", defaultValue = "1") int now_page, @RequestParam(name = "num") int num, @RequestParam(name = "name") String name, HttpSession session, RedirectAttributes rttr) {

    int cnt = this.categoryProc.update_forward(cateno);
    model.addAttribute("cnt", cnt);
    model.addAttribute("now_page", now_page);
    if (cnt == 1) {
      String referer = request.getHeader("Referer");
      // Referer 헤더를 통해 이전 페이지의 주소를 가져옴
      if (referer != null && referer.contains("/category/read")) {
        // 이전 페이지가 read 페이지인 경우
        rttr.addFlashAttribute("up", num + "번의 순서가 1 증가 되었습니다. 중분류 이름: " + name);
        return "redirect:/category/read/" + cateno + "?word=" + Tool.encode(word) + "&type=" + Tool.encode(type) + "&now_page=" + now_page;
      } else {
        // 이전 페이지가 list_all 페이지인 경우
        rttr.addFlashAttribute("up", num + "번의 순서가 1 증가 되었습니다. 중분류 이름: " + name);
        return "redirect:/category/search?word=" + Tool.encode(word) + "&type=" + Tool.encode(type) + "&now_page=" + now_page;
      }
    } else {
      model.addAttribute("code", "update_fail");
      return "redirect:/category/list_all/";
    }

  }


  @PostMapping("/upbackward/{cateno}")
  public String upbackward(Model model, @PathVariable("cateno") int cateno, @RequestParam(name = "type", defaultValue = "100") String type, @RequestParam(name = "word", defaultValue = "") String word, @RequestParam(name = "now_page", defaultValue = "1") int now_page, @RequestParam(name = "num") int num, @RequestParam(name = "name") String name, HttpServletRequest request, RedirectAttributes rttr, HttpSession session) {

      int cnt = this.categoryProc.update_backward(cateno);

      model.addAttribute("cnt", cnt);
      model.addAttribute("now_page", now_page);
      if (cnt == 1) {
        String referer = request.getHeader("Referer");
        // Referer 헤더를 통해 이전 페이지의 주소를 가져옴
        if (referer != null && referer.contains("/category/read")) {
          // 이전 페이지가 read 페이지인 경우
          rttr.addFlashAttribute("down", num + "번의 순서가 1 감소 되었습니다. 중분류 이름: " + name);
          return "redirect:/category/read/" + cateno + "?word" + Tool.encode(word) + "&type=" + Tool.encode(type) + "&now_page=" + now_page;
        } else {
          // 이전 페이지가 list_all 페이지인 경우
          rttr.addFlashAttribute("down", num + "번의 순서가 1 감소 되었습니다. 중분류 이름: " + name);
          return "redirect:/category/search?word=" + Tool.encode(word) + "&type=" + Tool.encode(type) + "&now_page=" + now_page;
        }
      } else {
        model.addAttribute("code", "update_fail");
        return "redirect:/category/search";
      }


  }


  /***
   *
   *  삭제: cateno 기준으로 공개 처리
   * @param model
   * @param cateno 수정할 카테고리 번호
   */

  @PostMapping("/show/{cateno}")
  public String show(Model model, @PathVariable("cateno") int cateno, @RequestParam(name = "type", defaultValue = "100") String type, @RequestParam(name = "word", defaultValue = "") String word, @RequestParam(name = "now_page", defaultValue = "1") int now_page, @RequestParam(name = "num") int num, @RequestParam(name = "name") String name, HttpServletRequest request, RedirectAttributes rttr, HttpSession session) {


    int cnt = this.categoryProc.show(cateno);
    model.addAttribute("cnt", cnt);
    model.addAttribute("now_page", now_page);
    if (cnt == 1) {
      String referer = request.getHeader("Referer");
      // Referer 헤더를 통해 이전 페이지의 주소를 가져옴
      if (referer != null && referer.contains("/category/read")) {
        // 이전 페이지가 read 페이지인 경우
        rttr.addFlashAttribute("show", num + "번이 공개 처리 되었습니다. 중분류 이름: " + name);
        return "redirect:/category/read/" + cateno + "?word" + Tool.encode(word) + "&type=" + Tool.encode(type) + "&now_page=" + now_page;
      } else {
        // 이전 페이지가 list_all 페이지인 경우
        rttr.addFlashAttribute("show", num + "번이 공개 처리 되었습니다. 중분류 이름: " + name);
        return "redirect:/category/search?type=" + Tool.encode(word) + "&type=" + Tool.encode(type) + "&now_page=" + now_page;
      }
    } else {
      model.addAttribute("code", "update_fail");
      return "redirect:/category/search";
    }


  }

  /***
   *
   *  삭제: cateno 기준으로 비공개 처리
   * @param model
   * @param cateno 수정할 카테고리 번호
   */


  @PostMapping("/hide/{cateno}")
  public String hide(Model model, @PathVariable("cateno") int cateno, @RequestParam(name = "type", defaultValue = "100") String type, @RequestParam(name = "word", defaultValue = "") String word, @RequestParam(name = "now_page", defaultValue = "1") int now_page, @RequestParam(name = "num") int num, @RequestParam(name = "name") String name, HttpServletRequest request, RedirectAttributes rttr, HttpSession session) {


    int cnt = this.categoryProc.hide(cateno);
    model.addAttribute("cnt", cnt);
    model.addAttribute("now_page", now_page);
    if (cnt == 1) {
      String referer = request.getHeader("Referer");
      // Referer 헤더를 통해 이전 페이지의 주소를 가져옴
      if (referer != null && referer.contains("/category/read")) {
        // 이전 페이지가 read 페이지인 경우
        rttr.addFlashAttribute("hide", num + "번이 숨김 처리 되었습니다. 중분류 이름: " + name);
        return "redirect:/category/read/" + cateno + "?word=" + Tool.encode(word) + "&type=" + Tool.encode(type) + "&now_page=" + now_page;
      } else {
        // 이전 페이지가 list_all 페이지인 경우
        rttr.addFlashAttribute("hide", num + "번이 숨김 처리 되었습니다. 중분류 이름: " + name);
        return "redirect:/category/search?word=" + Tool.encode(word) + "&type=" + Tool.encode(type) + "&now_page=" + now_page;
      }
    } else {
      model.addAttribute("code", "update_fail");
      return "redirect:/category/search";
    }

  }


  /***
   *
   *  삭제: cateno 기준으로 삭제
   * @param model
   * @param cateno 조회할 카테고리 번호
   */

  @PostMapping("/delete/{cateno}")
  public String delete(Model model, @PathVariable("cateno") int cateno, @RequestParam("name") String name, @RequestParam("word") String word, @RequestParam(value = "type", defaultValue = "100") String type, @RequestParam(name = "now_page", defaultValue = "1") int now_page, @RequestParam(value = "num") int num, RedirectAttributes rttr, HttpSession session) {


    int count = this.categoryProc.delete(cateno);
    int search_cnt = this.categoryProc.list_search_count(word, type);
    if (search_cnt % this.record_per_page == 0) {
      now_page = now_page - 1;
      if (now_page < 1) {
        now_page = 1;
      }
    }

    model.addAttribute("word", word);
    model.addAttribute("now_page", now_page);
    model.addAttribute("type", type);

    if (count == 1 && (word == null || word.isEmpty() || "null".equals(word))) {
      rttr.addFlashAttribute("delete", num + "번 중분류 '" + name + "'이 삭제되었습니다.");
      return "redirect:/category/search?now_page=" + now_page;
    } else if (count == 1) {
      rttr.addFlashAttribute("delete", num + "번 중분류 '" + name + "'이 삭제되었습니다.");
      return "redirect:/category/search?word=" + Tool.encode(word) + "&type=" + Tool.encode(type) + "&now_page" + now_page;
    } else {
      rttr.addFlashAttribute("delete", "삭제 실패");
      return "redirect:/category/search";
    }

  }


  @PostMapping("/multidelete")
  public String multidelete(@RequestParam("catenoList") List<Integer> catenoList, RedirectAttributes rttr, HttpSession session) {


    int count = this.categoryProc.multiple_delete(catenoList);
    if (count > 0) {
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < catenoList.size(); i++) {
        Integer cateno = catenoList.get(i);
        sb.append(cateno).append("번");
        if (i < catenoList.size() - 1) { // 현재 항목이 리스트의 마지막이 아닌 경우에만 쉼표와 공백 추가
          sb.append(", ");
        }
      }
      String catenoString = sb.toString(); // 문자열로 변환
      rttr.addFlashAttribute("multi", "선택한 " + catenoString + " 카테고리가 삭제되었습니다. " + "삭제된 개수: " + count + "개");
    } else {
      rttr.addFlashAttribute("multi", "선택한 카테고리 삭제에 실패했습니다.");
    }
    return "redirect:/category/list_all";


  }

//  /**
//   * 등록폼 + 검색번호
//   * @param model
//   * @param type
//   * @param word
//   * @param categoryVO
//   * @param now_page 현재 페이지 번호
//   * @return dw
//   */

  @GetMapping("/list")
  public String searchForm(HttpSession session, Model model, @RequestParam(name = "type", defaultValue = "100") String type, String word, CategoryVO categoryVO, @RequestParam(name = "now_page", defaultValue = "1") int now_page) {

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


    int count = this.categoryProc.list_search_count(word, type);
    // 일련 번호 생성
    int num = count - ((now_page - 1) * this.record_per_page);
    ArrayList<CategoryVO> catelist = this.categoryProc.list_search_paging(word, type, now_page, this.record_per_page);
    String paging = this.categoryProc.pagingBox(now_page, word, type, "/category/list", count, record_per_page, page_per_blcok);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("count", count);

    model.addAttribute("num", num);
    if (catelist.isEmpty()) {
      model.addAttribute("word", word);
      model.addAttribute("type", type);
      model.addAttribute("nulllist", "결과가 없습니다.");
    } else if (!catelist.isEmpty() && !word.equals("")) {
      model.addAttribute("word", word);
      model.addAttribute("type", type);
      model.addAttribute("search", types + ":" + word + "에  대한 검색 결과 : 총 " + catelist.size() + "건");
    }

    model.addAttribute("word", word);
    model.addAttribute("type", type);
    model.addAttribute("searchlist", catelist);


    return "category/search_all"; // Assuming "search_result" is the name of the view to display the search results


  }

  /**
   * 등록 처리 메서드
   * @param model
   * @param response
   * @param categoryVO
   * @param bindingResult
   * @param rttr
   * @param type
   * @param session
   * @param word
   * @param now_page
   * @return
   */
  @PostMapping(value = "/create") //
  public String create(Model model, HttpServletResponse response, @Valid CategoryVO categoryVO, BindingResult bindingResult, RedirectAttributes rttr, @RequestParam(name = "type", defaultValue = "100") String type, HttpSession session, @RequestParam(name = "word", defaultValue = "") String word, @RequestParam(defaultValue = "1") int now_page) {


    int count = this.categoryProc.list_search_count(word, type);
    model.addAttribute("type", type);
    model.addAttribute("word", word);
    ArrayList<CategoryVO> list = this.categoryProc.list_search_paging(word, type, now_page, this.record_per_page);

    int num = count - ((now_page - 1) * this.record_per_page);
    model.addAttribute("num", num);
    model.addAttribute("searchlist", list);
    String paging = this.categoryProc.pagingBox(now_page, word, type, "/category/list", count, record_per_page, page_per_blcok);
    model.addAttribute("paging", paging);
    model.addAttribute("now_page", now_page);
    model.addAttribute("count", count);

    model.addAttribute("num", num);
    if (bindingResult.hasErrors()) {
      response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
      response.setHeader("Pragma", "no-cache");
      response.setHeader("Expires", "0");
      rttr.addFlashAttribute("nameErrors", bindingResult.getFieldErrors("name"));

      return "redirect:/category/search?word=" + Tool.encode(word) + "&type=" + Tool.encode(type) + "&now_page=" + now_page;

    }


    int cnt = this.categoryProc.create(categoryVO);
    model.addAttribute("cnt", cnt);
    System.out.println("-> cnt: " + cnt);

    if (cnt == 1) {
      rttr.addFlashAttribute("create", "중분류: " + categoryVO.getName() + "" );
//      model.addAttribute("code", "create_success");
//      model.addAttribute("name", categoryVO.getName());
//      model.addAttribute("namesub", categoryVO.getNamesub());
      return "redirect:/category/search?word=" + Tool.encode(word) + "&type=" + Tool.encode(type) + "&now_page=" + now_page;

    } else {
      model.addAttribute("code", "create_fail");
      return "cate/msg"; // /templates/category/msg.html
    }


  }

  @GetMapping("/catelist")

  public ResponseEntity<ArrayList<CategoryVO>> catelist() {
    ArrayList<CategoryVO> list = this.categoryProc.list();
    return new ResponseEntity<>(list, HttpStatus.OK);
  }
  @GetMapping("/catelist_no")

  public ResponseEntity<ArrayList<CategoryVO>> catelist(@RequestParam("categoryno")  Integer categoryno) {
    ArrayList<CategoryVO> list = this.categoryProc.list_by_categoryno(categoryno);
    return new ResponseEntity<>(list, HttpStatus.OK);
  }


}






