package dev.mvc.schedule;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dev.mvc.restaurant.RestaurantProInter;
import dev.mvc.restaurant.RestaurantVO;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/schedule")
@Controller
public class ScheduleCont {
	@Autowired
	@Qualifier("dev.mvc.schedule.ScheduleProc")
	private ScheduleProcInter scheduleProc;
	
	@Autowired
	@Qualifier("dev.mvc.restaurant.RestaurantProc")
	private RestaurantProInter restaurantProc;
	
	public ScheduleCont() {
		System.out.println("-> ScheduleCont Created.");
	}
	
	@GetMapping("/create")
	public String create(Model model, HttpSession session, int restno) {
		String accessType = (String) session.getAttribute("type");
		if(accessType == null) { // 관리자 접속
			RestaurantVO restaurantVO = this.restaurantProc.read(restno);
			model.addAttribute("restaurantVO", restaurantVO);
			
			return "/schedule/create";
			
		}else if(accessType.equals("owner")) { // 사장 접속
			int ownernoOfRestaurant = this.restaurantProc.read(restno).getOwnerno();
			
			// 현재 식당의 사장이 아니라면 되돌려보냄
			if((int) session.getAttribute("ownerno") != ownernoOfRestaurant) {
				return "redirect:/manager";
			}
			
			RestaurantVO restaurantVO = this.restaurantProc.read(restno);
			model.addAttribute("restaurantVO", restaurantVO);
			
			return "/schedule/create";
		}else {
			return "redirect:/manager";
		}
	}
	
	@PostMapping("/create")
	public String createProc(HttpSession session, int restno, @RequestParam("admit_persons")int[] admit_persons, @RequestParam("times")int[] times) {
		String accessType = (String) session.getAttribute("type");
		System.out.println("허용인원 갯수:" + admit_persons.length);
		System.out.println("시각 갯수:" + times.length);
		if(accessType == null) { // 관리자 접속
			int cnt = this.scheduleProc.createFullSchedule(admit_persons, times ,restno);
			
			
			return "redirect:/schedule/update?restno=" + restno;
			
		}else if(accessType.equals("owner")) { // 사장 접속
			int ownernoOfRestaurant = this.restaurantProc.read(restno).getOwnerno();
			
			// 현재 식당의 사장이 아니라면 되돌려보냄
			if((int) session.getAttribute("ownerno") != ownernoOfRestaurant) {
				return "redirect:/manager";
			}
			
			int cnt = this.scheduleProc.createFullSchedule(admit_persons, times, restno);
			
			return "redirect:/schedule/update?restno=" + restno;
		}else {
			return "redirect:/manager";
		}
		
	}
}
