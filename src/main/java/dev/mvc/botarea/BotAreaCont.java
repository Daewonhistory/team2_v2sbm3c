package dev.mvc.botarea;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.midarea.MidAreaProcInter;
import dev.mvc.midarea.MidAreaVO;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/botarea")
public class BotAreaCont{
	@Autowired
	@Qualifier("dev.mvc.botarea.BotAreProc")
	BotAreaProcInter botAreaProc;
	
	@Autowired
	@Qualifier("dev.mvc.midarea.MidAreaProc")
	MidAreaProcInter midAreaProc;
	
	public BotAreaCont() {
		System.out.println("-> BotAreaCont Created.");
	}
	
	@PostMapping("/create")
	public String create (HttpSession session, BotAreaVO botAreaVO) {
		String accessType = (String) session.getAttribute("type");
		
		// 관리자 확인
		if(accessType != null) {
			return "redirect:/manager";
		}else {
			int cnt = this.botAreaProc.create(botAreaVO);
			return "redirect:/botarea/list";
			
		}
	}
	
	@GetMapping("/update")
	public String update (Model model, HttpSession session, int botareano) {
		String accessType = (String) session.getAttribute("type");
		
		// 관리자 확인
		if(accessType != null) {
			return "redirect:/manager";
		}else {
			System.out.println("bot:"+botareano);
			BotAreaVO botAreaVO = this.botAreaProc.read(botareano);
			System.out.println("midareano" + botAreaVO.getMidareano());
			MidAreaVO midAreaVO = this.midAreaProc.read(botAreaVO.getMidareano());
			String midareaname = midAreaVO.getName(); 
			ArrayList<MidAreaVO> midAreaList = this.midAreaProc.list_all();
			model.addAttribute("midAreaList", midAreaList);
			model.addAttribute("botAreaVO", botAreaVO);
			model.addAttribute("midareaname", midareaname);
			return "botarea/update";
		}
	}
	
	@PostMapping("/update")
	public String update (HttpSession session, BotAreaVO botAreaVO) {
		String accessType = (String) session.getAttribute("type");
		
		// 관리자 확인
		if(accessType != null) {
			return "redirect:/manager";
		}else {
			int cnt = this.botAreaProc.update(botAreaVO);
			return "redirect:/botarea/list";
		}
	}
	
	@PostMapping("delete")
	public String delete (HttpSession session, int botareano) {
		String accessType = (String) session.getAttribute("type");
		
		// 관리자 확인
		if(accessType != null) {
			return "redirect:/manager";
		}else {
			int cnt = this.botAreaProc.delete(botareano);
			return "redirect:/botarea/list";
		}
	}
	@GetMapping("/list")
	public String list(Model model, 
					HttpSession session, 
					@RequestParam(defaultValue = "") String word,
					@RequestParam(defaultValue = "1") int now_page) {
		String accessType = (String) session.getAttribute("type");
		
		// 관리자 확인
		if(accessType != null) {
			return "redierect:/";
		}else{
			ArrayList<MidAreaVO> midAreaList = this.midAreaProc.list_all();
			model.addAttribute("midAreaList", midAreaList);
			model.addAttribute("type", accessType);
			model.addAttribute("word", word);
			model.addAttribute("now_page", now_page);
			return "/botarea/list_search_paging";
		}
		
	}
	@PostMapping("/list")
	@ResponseBody
	public ResponseEntity<BotAreaResponse> list(@RequestBody Map<String, Object> requestBody) {
		String word = ((String) requestBody.get("word")).trim();
		System.out.println(word);
		int now_page =  (int) requestBody.get("now_page");
		System.out.println("-> word: " + word);
		System.out.println("-> now_page: " + now_page);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("word", word);
		map.put("now_page", now_page);
		ArrayList<BotAreaVO> list = this.botAreaProc.list_search_paging(map);
		System.out.println(list.size());
		int search_count = this.botAreaProc.list_search_count(map);

		BotAreaResponse response = new BotAreaResponse();
		String paging = this.botAreaProc.pagingBox(now_page, word, word, search_count, BotArea.RECORD_PER_PAGE,
				BotArea.PAGE_PER_BLOCK);
		response.setBotAreaList(list);
		response.setPaging(paging);
		System.out.println("paging:" + paging);
		return new ResponseEntity<BotAreaResponse>(response, HttpStatus.OK);
	}
	
	
	@PostMapping("/botarea_list")
	@ResponseBody
	public ResponseEntity<ArrayList<BotAreaVO>> botAreaList(@RequestBody Map<String, Object> requestBody){
		String midAreano = (String) requestBody.get("midareano");
		System.out.println(midAreano);
		ArrayList<BotAreaVO> list = this.botAreaProc.search_by_midareano(Integer.parseInt(midAreano));
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}
