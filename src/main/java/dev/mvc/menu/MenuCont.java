package dev.mvc.menu;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.ingredient.Ingredient;
import dev.mvc.tool.Tool;

@Controller
@RequestMapping(value="/menu")
public class MenuCont {
	@Autowired
	@Qualifier("dev.mvc.menu.MenuProc")
	private MenuProcInter menuProc;
	
	public MenuCont() {
		System.out.println("-> MenuCont Created.");
	}
	
	@GetMapping("/create")
	public String create(Model model,
			int menuno,
			@RequestParam(defaultValue = "") String word,
			@RequestParam(defaultValue = "1") int now_page){
		
		MenuVO menuVO = this.menuProc.read(menuno);
		
		model.addAttribute("menuVO", menuVO);
		
		return "menu/create";
	}
	
	//http://localhost:9093/menu/list_search_paging
	@GetMapping("list_search_paging")
	public String list_search_paging(Model model, 
			@RequestParam(defaultValue = "") String word,
			@RequestParam(defaultValue = "1") int now_page){
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("word", word);
		map.put("now_page", now_page);
		map.put("restno", 0);
		
		System.out.println("check1");
		ArrayList<MenuVO> list = this.menuProc.list_search_paging(map);
		model.addAttribute("list", list);
		System.out.println(list.size());
		int search_count = this.menuProc.list_search_count(word);
	    model.addAttribute("search_count", search_count);
	    String paging = this.menuProc.pagingBox(now_page, word, "/menu/list_search_paging", search_count, Menu.RECORD_PER_PAGE, Menu.PAGE_PER_BLOCK);
	    model.addAttribute("paging", paging);
		
		model.addAttribute("word", word);
		model.addAttribute("now_page", now_page);
		model.addAttribute("search_count", search_count);
			
		return "menu/list_search_paging";
	}
}
