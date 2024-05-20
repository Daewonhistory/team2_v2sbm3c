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
	    	    
	    String paging = this.ingredientProc.pagingBox(now_page, word, "/ingredient/list_search_paging", list.size(), Ingredient.RECORD_PER_PAGE, Ingredient.PAGE_PER_BLOCK);
	    model.addAttribute("paging", paging);
	    
	    
	    
	    model.addAttribute("word",word);
	    model.addAttribute("now_page", now_page);
		return  "ingredient/list_search";
	}
	
	// create
	// https://localhost/ingredient/list_serarch
	@PostMapping(value="/list_search")
	public String create(Model model, @Valid IngredientVO ingredientVO, @RequestParam(name="word", defaultValue="") String word) {
		System.out.println("check");
		// 페이징 목록
		ArrayList<IngredientVO> list = this.ingredientProc.list_search(word);    
		model.addAttribute("list", list);
		
		int cnt = this.ingredientProc.create(ingredientVO);
		
		if (cnt == 1) { // 등록 성공
		 
		  return "redirect:/ingredient/list_search?word=" + Tool.encode(word); // /cate/list_all.html
		 
		} else { // 실패
		  model.addAttribute("code", "create_fail");
		  return "ingredient/msg"; // /templates/cate/msg.html
		}
	}
	
	@GetMapping(value="update")
	public String update(Model model, @RequestParam(name="ingredno") int ingredno, @RequestParam(name="word", defaultValue="") String word, @RequestParam(name="now_page", defaultValue="1") int now_page) {
		ArrayList<IngredientVO> list = this.ingredientProc.list_search(word);
		model.addAttribute("list", list);
		
		IngredientVO ingredientVo = this.ingredientProc.read(ingredno);
		model.addAttribute("word", word);
		model.addAttribute("now_page", now_page);
		model.addAttribute("ingredientVO", ingredientVo);
		return "ingredient/update";
	}
	
	@PostMapping(value="update")
	public String update(Model model, IngredientVO ingredientVO, @RequestParam(name="word", defaultValue="") String word) {
		System.out.println(ingredientVO.getIngredno() + ingredientVO.getName());
		int cnt = this.ingredientProc.update_by_ingredno(ingredientVO);
		if(cnt == 1) {
			return "redirect:/ingredient/list_search?word=" + Tool.encode(word);
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
	public String delete(Model model, @RequestParam(name = "ingredno", defaultValue = "0") int ingredno, @RequestParam(name = "word", defaultValue = "") String word) {
 
		int cnt = this.ingredientProc.delete_by_ingredno(ingredno); // 삭제
		System.out.println("-> cnt: " + cnt);

		if (cnt == 1) {
			return "redirect:/ingredient/list_search?word=" + Tool.encode(word);
		} else {
			model.addAttribute("code", "delete_fail");
			return "ingredient/msg"; // /templates/cate/msg.html
		}
	}
}
