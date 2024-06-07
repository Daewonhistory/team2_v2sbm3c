package dev.mvc.notice;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import dev.mvc.restaurant.RestaurantProInter;
import dev.mvc.restaurant.RestaurantVO;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@RequestMapping("/notice")
@Controller
public class NoticeController {
	@Qualifier("dev.mvc.notice.NoticeProc")
	@Autowired
	private NoticeProcInter noticeProc;
	
	@Qualifier("dev.mvc.restaurant.RestaurantProc")
	@Autowired
	private RestaurantProInter restaurantPro;
	
	@GetMapping("/create")
	public String create(Model model, HttpSession session) {
		String accessType = (String) session.getAttribute("type");
		if(accessType.equals("owner")) {	// 사업자 접속
			int ownerno = Integer.parseInt((String) session.getAttribute("ownerno"));
			ArrayList<RestaurantVO> restList = this.restaurantPro.findByOwnerR(ownerno);
			int restno = 0;
			if(restList.size() >= 1) {
				restno = restList.get(0).getRestno();
				model.addAttribute("restno", restno);
			}else {
				model.addAttribute("code", "not_created_restaurant");
				return "/notice/msg";
			}
		}else {	//관리자 접속
			
		}
		return "/notice/create";
	}
	
	@PostMapping("/create")
	public String createProc(Model model, NoticeVO noticeVO) {
		int cnt = this.noticeProc.create(noticeVO);
		if(cnt > 0) {
			return "redirect:/notice/list";
		}else {
			return "redirect:/notice/msg";
		}
		
	}
	
	@GetMapping("/list")
	public String list(Model model, HttpSession session) {
		String accessType = (String) session.getAttribute("type");
		ArrayList<NoticeVO> noticeList;
		if(accessType!=null && accessType.equals("owner")) {
			int ownerno = Integer.parseInt((String) session.getAttribute("ownerno"));
			ArrayList<RestaurantVO> restList = this.restaurantPro.findByOwnerR(ownerno);
			if(restList.size() >= 1) {
				int restno = restList.get(0).getRestno();
				noticeList = this.noticeProc.list_by_restno(restno);
				model.addAttribute("restVO", restList.get(0));
			}else {
				model.addAttribute("code", "not_created_restaurant");
				return "/notice/msg";
			}
		}else {
			noticeList = this.noticeProc.list_all();
		}
		
		model.addAttribute("noticeList", noticeList);
		
		return "/notice/list";
	}
	
	@GetMapping("/update")
	public String update(Model model, int noticeno) {
		
		return "/notice/update";
	}
}
