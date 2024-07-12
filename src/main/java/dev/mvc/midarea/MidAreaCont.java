package dev.mvc.midarea;

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

import jakarta.servlet.http.HttpSession;

@RequestMapping("/midarea")
@Controller
public class MidAreaCont {
	@Autowired
	@Qualifier("dev.mvc.midarea.MidAreaProc")
	private MidAreaProcInter midAreaProc;
	
	@GetMapping("/create")
	public String create(Model model, HttpSession session) {
		String accessType = (String) session.getAttribute("type");
		
		if(accessType == null) {
			model.addAttribute("accessType", accessType);
			return "/midarea/create";
		}else {
			
			return "redirect:/manager";
		}
		
	}
	@PostMapping("/create")
	public String createProc(MidAreaVO midAreaVO) {
		int cnt = this.midAreaProc.create(midAreaVO);
		
		return "redirect:/midarea/list";
	}
	
	@GetMapping("/list")
	public String list(Model model, 
					HttpSession session, 
					@RequestParam(defaultValue = "") String word,
					@RequestParam(defaultValue = "1") int now_page) {
		String accessType = (String) session.getAttribute("type");
		
		// 관리자 확인
		if(accessType == null) {
			model.addAttribute("accessType", accessType);
			model.addAttribute("now_page", now_page);
			return "/midarea/list";
		}else{
			return "redirect:/manager";
		}
		
	}
	
	@PostMapping("/list")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> list(@RequestBody Map<String, Object> requestBody){
		int now_page =  (int) requestBody.get("now_page");
		HashMap<String, Object> map = new HashMap<String, Object>(); 
		System.out.println(now_page);
		map.put("now_page", now_page);
		ArrayList<MidAreaVO> midAreaList = this.midAreaProc.list_paging(map);
		int listCount = this.midAreaProc.list_count();
		System.out.println(listCount);
		String paging = this.midAreaProc.pagingBox(now_page, listCount, MidArea.RECORD_PER_PAGE, MidArea.PAGE_PER_BLOCK);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("midAreaList", midAreaList);
		response.put("paging", paging);
		System.out.println(paging);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	
	@PostMapping("update")
	public String updateProc(MidAreaVO midAreaVO) {
		int cnt = this.midAreaProc.update(midAreaVO);
		return "redirect:/midarea/list";
	}
	
	@PostMapping("delete")
	public String deleteProc(int midareano) {
		int cnt = this.midAreaProc.delete(midareano);
		return "redirect:/midarea/list";
	}
	
}
