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

import dev.mvc.admitperson.AdmitPersonProcInter;
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
	
	@Autowired
	@Qualifier("dev.mvc.admitperson.AdmitPersonProc")
	private AdmitPersonProcInter admitPersonProc;
	
	public ScheduleCont() {
		System.out.println("-> ScheduleCont Created.");
	}
	
	@GetMapping("/create")
	public String create(Model model, HttpSession session, int restno) {
		String accessType = (String) session.getAttribute("type");
		if(accessType == null) { // 관리자 접속
			RestaurantVO restaurantVO = this.restaurantProc.read(restno);
			model.addAttribute("restaurantVO", restaurantVO);
			model.addAttribute("accessType", accessType);
			return "/schedule/create";
			
		}else if(accessType.equals("owner")) { // 사장 접속
			int ownernoOfRestaurant = this.restaurantProc.read(restno).getOwnerno();
			
			// 현재 식당의 사장이 아니라면 되돌려보냄
			if((int) session.getAttribute("ownerno") != ownernoOfRestaurant) {
				return "redirect:/manager";
			}
			
			RestaurantVO restaurantVO = this.restaurantProc.read(restno);
			model.addAttribute("restaurantVO", restaurantVO);
			model.addAttribute("accessType", accessType);
			return "/schedule/create";
		}else {
			System.out.println("다른사람");
			return "redirect:/manager";
		}
	}
	
	@PostMapping("/create")
	public String createProc(HttpSession session, int restno, @RequestParam("admit_persons")int[] admit_persons, @RequestParam("times")int[] times) {
		String accessType = (String) session.getAttribute("type");
		System.out.println("허용인원 갯수:" + admit_persons.length);
		System.out.println("시각 갯수:" + times.length);
		RestaurantVO restaurantVO = this.restaurantProc.read(restno);
		if(accessType == null) { // 관리자 접속
			int cnt = this.scheduleProc.createFullSchedule(admit_persons, times ,restno);

			System.out.println(cnt);
			if(cnt == 1) {
				System.out.println("허용인원 생성");
				ArrayList<ScheduleVO> scheduleList = this.scheduleProc.list_by_restno(restno);
				this.admitPersonProc.createBeginning(restno, restaurantVO.getReserve_range(), scheduleList);
			}
			return "redirect:/schedule/update?restno=" + restno;
			
		}else if(accessType.equals("owner")) { // 사장 접속
			RestaurantVO RestaurantVO = this.restaurantProc.read(restno);
			
			// 현재 수정할 식당의 사장이 아니라면 되돌려보냄
			if((int) session.getAttribute("ownerno") != RestaurantVO.getOwnerno()) {
				return "redirect:/manager";
			}
			
			int cnt = this.scheduleProc.createFullSchedule(admit_persons, times, restno);
			System.out.println(cnt);
			if(cnt == 1) {
				System.out.println("허용인원 생성");
				ArrayList<ScheduleVO> scheduleList = this.scheduleProc.list_by_restno(restno);
				this.admitPersonProc.createBeginning(restno, restaurantVO.getReserve_range(), scheduleList);
			}
			return "redirect:/schedule/update?restno=" + restno;
		}else {
			

			return "redirect:/manager";
		}
		
	}
	
	@GetMapping("/update")
	public String update(Model model, HttpSession session, int restno) {
		ArrayList<ScheduleVO> scheduleList = this.scheduleProc.list_by_restno(restno);
		
		if(scheduleList.size() == 0) {	//스케줄이 생성되지 않은 경우 스케줄 등록페이지로
			System.out.println("다른사람");

			return "redirect:/schedule/create?restno=" + restno;
		}else {
			String accessType = (String) session.getAttribute("type");
			if(accessType == null) { // 관리자 접속
				RestaurantVO restaurantVO = this.restaurantProc.read(restno);
				model.addAttribute("restaurantVO", restaurantVO);
				model.addAttribute("scheduleList", scheduleList);
				return "/schedule/update";
				
			}else if(accessType.equals("owner")) { // 사장 접속
				int ownernoOfRestaurant = this.restaurantProc.read(restno).getOwnerno();
				
				// 현재 식당의 사장이 아니라면 되돌려보냄
				if((int) session.getAttribute("ownerno") != ownernoOfRestaurant) {
					System.out.println("not same owner");
					return "redirect:/manager";
				}
				
				RestaurantVO restaurantVO = this.restaurantProc.read(restno);
				model.addAttribute("accessType", accessType);
				model.addAttribute("restaurantVO", restaurantVO);
				model.addAttribute("scheduleList", scheduleList);
				return "/schedule/update";
			}else {
				System.out.println("다른사람");
				
				return "redirect:/manager";
			}
		}
		
	}
	
	@PostMapping("/update")
	public String updateProc(HttpSession session, int restno, @RequestParam("admit_persons")int[] admit_persons, @RequestParam("schedulenos")int[] schedulenos) {
		String accessType = (String) session.getAttribute("type");
		if(accessType == null) { // 관리자 접속
			int cnt = this.scheduleProc.updateFullSchedule(admit_persons, schedulenos);
			
			
			return "redirect:/schedule/update?restno=" + restno;
			
		}else if(accessType.equals("owner")) { // 사장 접속
			int ownernoOfRestaurant = this.restaurantProc.read(restno).getOwnerno();
			
			// 현재 식당의 사장이 아니라면 되돌려보냄
			if((int) session.getAttribute("ownerno") != ownernoOfRestaurant) {
				return "redirect:/manager";
			}
			
			int cnt = this.scheduleProc.updateFullSchedule(admit_persons, schedulenos);
			
			return "redirect:/schedule/update?restno=" + restno;
		}else {
			return "redirect:/manager";
		}
		
	}
}
