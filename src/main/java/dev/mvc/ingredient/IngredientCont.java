package dev.mvc.ingredient;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.tool.Tool;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@RequestMapping(value="/ingredient")
public class IngredientCont {
	@Autowired
	@Qualifier("dev.mvc.ingredient.IngredientProc")
	private IngredientProcInter ingredientProc;
	
	public IngredientCont() {
		System.out.println("-> IngredeintCont created.");
	}
	
	@GetMapping(value="/list_search")
	public String list_search(Model model, @Valid IngredientVO ingredientVO,@RequestParam(name="word", defaultValue="") String word) {
		ArrayList<IngredientVO> list = this.ingredientProc.list_search(word);
		model.addAttribute("list", list);
		model.addAttribute("word", word);
		return  "ingredient/list_search";
	}
	
	@GetMapping(value="/list_search_paging")
	public String list_search_paging(Model model, @Valid IngredientVO ingredientVO, 
			@RequestParam(name="word", defaultValue="") String word, 
			@RequestParam(name="now_page", defaultValue="1") int now_page) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
	    hashMap.put("word", word);
	    hashMap.put("now_page", now_page);
	    
	    ArrayList<IngredientVO> list = this.ingredientProc.list_search_paging(hashMap);
	    model.addAttribute("list", list);
	    
	    int search_count = this.ingredientProc.list_search_count(word);
	    model.addAttribute("search_count", search_count);
	    String paging = this.ingredientProc.pagingBox(now_page, word, "/ingredient/list_search_paging", search_count, Ingredient.RECORD_PER_PAGE, Ingredient.PAGE_PER_BLOCK);
	    model.addAttribute("paging", paging);
	    System.out.println(paging);
	    
	    
	    model.addAttribute("word",word);
	    model.addAttribute("now_page", now_page);
		return  "ingredient/list_search_paging";
	}
	
	// create
	// https://localhost/ingredient/list_serarch
	@PostMapping(value="/list_search_paging")
	public String create(Model model, @Valid IngredientVO ingredientVO, 
			@RequestParam(name="word", defaultValue="") String word) {
		System.out.println("check");
		
		int cnt = this.ingredientProc.create(ingredientVO);
		System.out.println("check3");
		// 페이징 목록
		ArrayList<IngredientVO> list = this.ingredientProc.list_search(word);    
		model.addAttribute("list", list);
		System.out.println("check2");
		
		
		if (cnt == 1) { // 등록 성공
		 
		  return "redirect:/ingredient/list_search_paging?word=" + Tool.encode(word) + "&now_page=1"; // /cate/list_all.html
		 
		} else { // 실패
		  model.addAttribute("code", "create_fail");
		  return "ingredient/msg"; // /templates/cate/msg.html
		}
	}
	
	@GetMapping(value="update")
	public String update(Model model, @RequestParam(name="ingredno") int ingredno, 
			@RequestParam(name="word", defaultValue="") String word, 
			@RequestParam(name="now_page", defaultValue="1") int now_page) {
		ArrayList<IngredientVO> list = this.ingredientProc.list_search(word);
		model.addAttribute("list", list);
		
		IngredientVO ingredientVo = this.ingredientProc.read(ingredno);
		model.addAttribute("word", word);
		model.addAttribute("now_page", now_page);
		model.addAttribute("ingredientVO", ingredientVo);
		return "ingredient/update";
	}
	
	@PostMapping(value="update")
	public String update(Model model, IngredientVO ingredientVO, 
			@RequestParam(name="word", defaultValue="") String word,
			int now_page) {
		System.out.println(ingredientVO.getIngredno() + ingredientVO.getName());
		int cnt = this.ingredientProc.update_by_ingredno(ingredientVO);
		if(cnt == 1) {
			return "redirect:/ingredient/list_search_paging?word=" + Tool.encode(word) + "&now_page=" + now_page;
		}else {
			model.addAttribute("code", "update_fail");
			return "ingredient/msg"; // /templates/cate/msg.html
		}
	}
	
	/**
	   * Delete form
	   * http://localhost:9091/cate/delete/1
	   * @param model
	   * @param cateno Category number to delete.
	   * @return
	   */
	@PostMapping(value="/delete")
	public String delete(Model model, @RequestParam(name = "ingredno", defaultValue = "0") int ingredno, 
			@RequestParam(name = "word", defaultValue = "") String word,
			int now_page) {
 
		int cnt = this.ingredientProc.delete_by_ingredno(ingredno); // 삭제
		System.out.println("-> cnt: " + cnt);
		System.out.println(now_page);
		if (cnt == 1) {
			if (this.ingredientProc.list_search_count(word) % Ingredient.RECORD_PER_PAGE == 0) {
			     now_page = now_page - 1; // 삭제시 DBMS는 바로 적용되나 크롬은 새로고침등의 필요로 단계가 작동 해야함.
			     if (now_page < 1) {
			       now_page = 1; // 시작 페이지
			     }
			   }
			return "redirect:/ingredient/list_search_paging?word=" + Tool.encode(word) + "&now_page=" + now_page;
		} else {
			model.addAttribute("code", "delete_fail");
			return "ingredient/msg"; // /templates/cate/msg.html
		}
	}
}
