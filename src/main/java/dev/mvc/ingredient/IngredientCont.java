package dev.mvc.ingredient;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import dev.mvc.tool.Tool;


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
	public String list_search(Model model, @RequestParam(name = "word", defaultValue = "") String word) {
		ArrayList<IngredientVO> list = this.ingredientProc.list_search(word);
		return  "ingredient/list_search";
	}
	
	@PostMapping(value="/list_search")
	public String create(Model model, String word) {

		// 페이징 목록
		ArrayList<IngredientVO> list = this.ingredientProc.list_search(word);    
		model.addAttribute("list", list);
		
		IngredientVO ingredientVO = new IngredientVO();
		ingredientVO.setName(word);
		int cnt = this.ingredientProc.create(ingredientVO);
		
		if (cnt == 1) { // 등록 성공
		 
		  return "redirect:/ingredient/list_search?word=" + Tool.encode(word); // /cate/list_all.html
		 
		} else { // 실패
		  model.addAttribute("code", "create_fail");
		  return "ingredient/msg"; // /templates/cate/msg.html
		}
	}
}
