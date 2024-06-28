package dev.mvc.admitperson;

import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.botarea.BotAreaVO;
import dev.mvc.midarea.MidAreaVO;
import dev.mvc.restaurant.RestaurantProInter;
import dev.mvc.restaurant.RestaurantVO;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admitperson")
public class AdmitPersonCont {
	@Autowired
	@Qualifier("dev.mvc.admitperson.AdmitPersonProc")
	private AdmitPersonProcInter admitPersonProc;
	
	@Autowired
	@Qualifier("dev.mvc.restaurant.RestaurantProc")
	private RestaurantProInter restaurantProc;
	
	@GetMapping("/create")
	public String create(Model model, HttpSession session) {
		String accessType = (String) session.getAttribute("type");
		ArrayList<RestaurantVO> restaurantList = null;
		if(accessType != null && accessType.equals("owner")) {
			int accessOwnerno = (int) session.getAttribute("ownerno");
			restaurantList = this.restaurantProc.findByOwnerR(accessOwnerno);
			
		}else if(accessType == null) {
			restaurantList = this.restaurantProc.list_all();
			
		}
		model.addAttribute("restaurantList", restaurantList);
		return "/admitperson/create";
	}
	
	@PostMapping("/create")
	public String createProc(HttpSession session, AdmitPersonVO admitPersonVO) {
		int cnt = this.admitPersonProc.create(admitPersonVO);
		
		return "redirect:/admitperson/list";
	}
	
	@GetMapping("/list")
	public String list(Model model, HttpSession session) {
		String accessType = (String) session.getAttribute("type");
		System.out.println(accessType + "aaa");
		ArrayList<RestaurantVO> restaurantList = null;
		if(accessType != null && accessType.equals("owner")) {
			System.out.println("owner access");
			int accessOwnerno = (int) session.getAttribute("ownerno");
			restaurantList = this.restaurantProc.findByOwnerR(accessOwnerno);
			
		}else if(accessType == null) {
			System.out.println("admin access");
			accessType = "admin";
			restaurantList = this.restaurantProc.list_all();
		}
		else {
			return "redirect:/";
		}
		model.addAttribute("type", accessType);
		model.addAttribute("restaurantList", restaurantList);
		return "admitperson/list_search_paging";
	}
	
	@PostMapping("/list_search")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> listSearch(@RequestBody Map<String, Object> requestBody){
		int restno = (int) requestBody.get("restno");
		String start_date = (String) requestBody.get("start_date");
		String end_date = (String) requestBody.get("end_date");
		int now_page = (int) requestBody.get("now_page");
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("restno", restno);
		map.put("start_date", start_date);
		map.put("end_date", end_date);
		map.put("now_page", now_page);
		ArrayList<AdmitPersonVO> admitPersonList = this.admitPersonProc.list_search_paging(map);
		
		int searchCount = this.admitPersonProc.list_search_count(map);
		
		String paging = this.admitPersonProc.pagingBox(now_page, searchCount, AdmitPerson.RECORD_PER_PAGE, AdmitPerson.PAGE_PER_BLOCK);
		
		Map<String,Object> response = new HashMap<String, Object>();
		response.put("admitPersonList", admitPersonList);
		response.put("paging", paging);
		
		return new ResponseEntity<Map<String,Object>> (response, HttpStatus.OK); 
	}
	
	@GetMapping("/update")
	public String update(Model model, HttpSession session, int admitpersonno) {
		String accessType = (String) session.getAttribute("type");
		
		// 관리자 확인
		if(accessType == null) {
			AdmitPersonVO admitPersonVO = this.admitPersonProc.read(admitpersonno);
			RestaurantVO restaurantVO = this.restaurantProc.read(admitPersonVO.getRestno());
			model.addAttribute("admitPersonVO", admitPersonVO);
			model.addAttribute("restname", restaurantVO.getName());
			return "admitperson/update";
		}else {
			return "redirect:/manager";
		}
	}
	
	@PostMapping("/update")
	public String updateProc(AdmitPersonVO admitPersonVO) {
		int cnt = this.admitPersonProc.update_admit_person(admitPersonVO);
		
		return "redirect:/admitperson/list";
	}
	
	@PostMapping("delete")
	public String deleteProc(int admitpersonno) {
		int cnt = this.admitPersonProc.delete(admitpersonno);
		return "redirect:/admitperson/list";
	}
	
	@PostMapping("/searchPossibleTime")
	@ResponseBody
	public ResponseEntity<ArrayList<AdmitPersonVO>> searchPossibleTime(@RequestBody Map<String, Object> requestBody){
		
		String date = ((String) requestBody.get("date")) + " 00:00:00";
		System.out.println("date:"+ date);
		int personnel = Integer.parseInt((String) requestBody.get("personnel"));
		requestBody.replace("date", date);
		requestBody.replace("personnel", personnel);
		ArrayList<AdmitPersonVO> list = this.admitPersonProc.admit_list(requestBody);
		System.out.println("리스트사이즈:" + list.size());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}	
	
	
}
