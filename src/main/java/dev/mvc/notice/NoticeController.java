package dev.mvc.notice;

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
import org.springframework.web.bind.annotation.ResponseBody;

import dev.mvc.menu.Menu;
import dev.mvc.menu.MenuResponse;
import dev.mvc.menu.MenuVO;
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
	private RestaurantProInter restaurantProc;
	
	@GetMapping("/create")
	public String create(Model model, HttpSession session) {
		String accessType = (String) session.getAttribute("type");
		System.out.println("type:" + accessType);
		if(accessType!=null && accessType.equals("owner")) {	// 사업자 접속
			int ownerno = (int) session.getAttribute("ownerno");
			ArrayList<RestaurantVO> restList = this.restaurantProc.findByOwnerR(ownerno);
			int restno = 0;
			System.out.println("size=>" + restList.size());
			if(restList.size() >= 1) {
				restno = restList.get(0).getRestno();
				model.addAttribute("restno", restno);
			}else {
				model.addAttribute("code", "not_created_restaurant");
				return "/notice/msg";
			}
		}else {	//관리자 접속
		  ArrayList<RestaurantVO> restList = this.restaurantProc.list_all();
		  model.addAttribute("restList", restList);
		}
		model.addAttribute("accessType", accessType);
		return "/notice/create";
	}
	
	@PostMapping("/create")
	public String createProc(Model model, NoticeVO noticeVO) {
	  System.out.println(noticeVO.getRestno());
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
      
      int restno = 0;
      ArrayList<RestaurantVO> RestList = null;
      int ownerno = 0;
      if (accessType == null) { // 관리자 접속
          System.out.println("admin");
          RestList = this.restaurantProc.list_all();
          ownerno = 0;
          
      }else if(accessType.equals("owner")) {  // 사업자 접속
          ownerno = (int)session.getAttribute("ownerno");
          System.out.println("Owner" + ownerno);
          RestList = this.restaurantProc.findByOwnerR(ownerno);
          System.out.println("ownerRestList"+ RestList.size());
      }else {
          return "redirect:/";
      }
      model.addAttribute("accessType", accessType);
      model.addAttribute("RestList", RestList);

      return "/notice/list";
	}
	
//	@PostMapping("/list")
//	@ResponseBody
//	public ResponseEntity<Map<String, Object>> list(@RequestBody Map<String, Object> requestBody){
//	  String word = ((String) requestBody.get("word")).trim();
//      System.out.println("-> searchword:" + word);
//      String strRestno = (String) requestBody.get("restno");
//      int restno = Integer.parseInt(strRestno);
//      System.out.println("-> restno:" + restno);
//      int nowPage = (int)requestBody.get("now_page");
//      System.out.println("-> nowPage:" + nowPage);
//      
//      HashMap<String, Object> map = new HashMap<String, Object>();
//      map.put("word", word);
//      map.put("restno", restno);
//      map.put("now_page", nowPage);
//      ArrayList<NoticeVO> list = this.noticeProc.list_search_paging(map);
//
//      int search_count = this.noticeProc.list_by_restno_search_count(map);
//      System.out.println("search_count:" + search_count);
//      Map<String,Object> response = new HashMap<String, Object>();
//      String paging = this.noticeProc.pagingBox(nowPage, word, search_count, Notice.RECORD_PER_PAGE, Notice.PAGE_PER_BLOCK);
//      response.put("noticeList", list);
//      response.put("paging", paging);
//
//	  
//	  return new ResponseEntity<>(response, HttpStatus.OK);
//	}
	
	@GetMapping("/read")
	public String read(Model model, int noticeno) {
		NoticeVO noticeVO = this.noticeProc.read(noticeno);
		model.addAttribute("noticeVO", noticeVO);
		return "/notice/read";
	}
	
	@GetMapping("/update")
	public String update(Model model, int noticeno) {
		System.out.println(noticeno);
		NoticeVO noticeVO = this.noticeProc.read(noticeno);
		model.addAttribute("noticeVO", noticeVO);
		return "/notice/update";
	}
	
	
	@PostMapping("/update")
	public String updateProc(Model model, NoticeVO noticeVO){
		
		int cnt = this.noticeProc.update(noticeVO);
		return "redirect:/notice/list";
	}
	@PostMapping("/delete")
	public String delete(Model model, int noticeno) {
		int cnt = this.noticeProc.delete(noticeno);
		return "redirect:/notice/list";
	}
}
