package dev.mvc.menu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.allergy.AllergyProcInter;
import dev.mvc.allergy.AllergyVO;
import dev.mvc.ingredient.IngredientProcInter;
import dev.mvc.ingredient.IngredientVO;
import dev.mvc.menuingred.MenuIngredDTO;
import dev.mvc.menuingred.MenuIngredProcInter;
import dev.mvc.menuingred.MenuIngredVO;
import dev.mvc.restaurant.RestaurantProInter;
import dev.mvc.restaurant.RestaurantVO;
import dev.mvc.tool.Tool;
import dev.mvc.tool.Upload;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping(value = "/menu")
public class MenuCont {
	@Autowired
	@Qualifier("dev.mvc.menu.MenuProc")
	private MenuProcInter menuProc;
	@Autowired
	@Qualifier("dev.mvc.ingredient.IngredientProc")
	private IngredientProcInter ingredientProc;
	@Autowired
	@Qualifier("dev.mvc.menuingred.MenuIngredProc")
	private MenuIngredProcInter menuIngredProc;
	@Autowired
	@Qualifier("dev.mvc.restaurant.RestaurantProc")
	private RestaurantProInter restaurantProc;
	@Autowired
	@Qualifier("dev.mvc.allergy.AllergyProc")
	private AllergyProcInter AllergyProc;

	public MenuCont() {
		System.out.println("-> MenuCont Created.");
	}

	/**
	 * 메뉴 생성 GET
	 *
	 * @param session
	 * @param model
	 * @param restno
	 * @param word
	 * @param now_page
	 * @return
	 */
	@GetMapping("/create")
	public String create(HttpSession session,
			Model model,
			@RequestParam(defaultValue = "") String word,
			@RequestParam(defaultValue = "1") int now_page) {
		
		String accessType = (String) session.getAttribute("type");
		System.out.println("=>accessType:" + accessType);
		int restno = 0;
		if(accessType == "Master" || accessType == "Admin" || accessType == "Manager") {
			ArrayList<RestaurantVO> restaurantList = this.restaurantProc.list_all();
			model.addAttribute("restaurantList", restaurantList);
		} else if (accessType.equals("owner")) {

			int ownerno = (int) session.getAttribute("ownerno");
			ArrayList<RestaurantVO> ownerRestList = this.restaurantProc.findByOwnerR(ownerno);
			model.addAttribute("restaurantList", ownerRestList);
		}

		ArrayList<IngredientVO> list = this.ingredientProc.list_all();
		model.addAttribute("accessType", accessType);
		model.addAttribute("list", list);
		model.addAttribute("restno", 4);
		model.addAttribute("word", word);
		model.addAttribute("now_page", now_page);

		return "menu/create";
	}

	/**
	 * 메뉴 생성 POST
	 *
	 * @param model
	 * @param request
	 * @param session
	 * @param menuVO
	 * @param ra
	 * @return
	 */
	@PostMapping(value = "/create")
	public String create(Model model,
			HttpServletRequest request,
			HttpSession session,
			@Valid MenuVO menuVO,
			String word,
			int now_page,
			@RequestParam("ingredno[]") int[] ingredno,
			RedirectAttributes ra) {
		for (int no : ingredno) {
			System.out.println(no);
		}
		// ------------------------------------------------------------------------------
		// 파일 전송 코드 시작
		// ------------------------------------------------------------------------------
		String file1 = ""; // 원본 파일명 image
		String file1saved = ""; // 저장된 파일명, image

		String upDir = Menu.getUploadDir(); // 파일을 업로드할 폴더 준비
		System.out.println("-> upDir: " + upDir);
		System.out.println("파일명" + menuVO.getFile1MF());
		// 전송 파일이 없어도 file1MF 객체가 생성됨.
		// <input type='file' class="form-control" name='file1MF' id='file1MF'
		// value='' placeholder="파일 선택">
		MultipartFile mf = menuVO.getFile1MF();

		file1 = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
		System.out.println("-> 원본 파일명 산출 file1: " + file1);

		if (!file1.isEmpty()) {
			if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사
				long size1 = mf.getSize(); // 파일 크기

				if (size1 > 0) { // 파일 크기 체크
					// 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
					int menu_cnt = this.menuProc.list_by_restno_count(menuVO.getRestno());
					System.out.println("-> 원본 파일명 산출 file1: " + file1);
					String exe = file1.split("\\.")[1];

					String new_file_name = "rest" + menuVO.getRestno() + "_" + (menu_cnt + 1) + "." + exe;
					file1saved = Upload.saveFileSpring(mf, upDir, new_file_name);

				}
				System.out.println("file1saved" + file1saved);
				menuVO.setImage(file1saved); // 저장본 파일명
				// ------------------------------------------------------------------------------
				// 파일 전송 코드 종료
				// ------------------------------------------------------------------------------
			} else {
				ra.addFlashAttribute("cnt", 0);
				ra.addFlashAttribute("code", "check_upload_file_fail");
				ra.addFlashAttribute("url", "/contents/msg"); // msg.jsp, redirect parameter 적용
				return "redirect:/contents/msg";
			}

		}
		int cnt = this.menuProc.create(menuVO);

		if (cnt == 0) {
			model.addAttribute("cnt", cnt);
			model.addAttribute("code", "create_fail");
			return "content/msg";
		} else {
			int menuIngredCnt = 0;
			// 메뉴 재료 추가
			for (int ingredientno : ingredno) {
				MenuVO lastMenuVO = this.menuProc.last_menu_by_restno(menuVO.getRestno());
				MenuIngredVO menuIngredVO = new MenuIngredVO();
				menuIngredVO.setIngredno(ingredientno);
				menuIngredVO.setMenuno(lastMenuVO.getMenuno());
				menuIngredCnt = this.menuIngredProc.create(menuIngredVO);
				if (menuIngredCnt == 0) {
					ra.addAttribute("word", word);
					ra.addAttribute("now_page", now_page);
					ra.addFlashAttribute("code", "create_menuingred_fail");
					return "redirect:/menu/msg"; // ra.addAttribute 사용시 url에 직접 작성 안함
				}

			}
			ra.addFlashAttribute("word", word);
			ra.addFlashAttribute("now_page", now_page);
			return "redirect:/menu/list"; // ra.addAttribute 사용시 url에 직접 작성 안함

		}

	}

	@GetMapping("read")
	public String read(Model model, HttpSession session, String word, int now_page, int menuno) {
		String accessType = (String) session.getAttribute("type");
		
		if((accessType == "Master" || accessType == "Admin" || accessType == "Manager") && !accessType.equals("owner")) {
			return "redirect:/owner";
		}
		
		MenuVO menuVO = this.menuProc.read(menuno);
		model.addAttribute("menuVO", menuVO);
		// 메뉴의 재료 목록
		ArrayList<MenuIngredVO> menuIngredList = this.menuIngredProc.list_by_menuno(menuno);
		ArrayList<IngredientVO> IngredList = new ArrayList<IngredientVO>();
		for (MenuIngredVO menuIngredVO : menuIngredList) {
			IngredientVO ingredientVO = this.ingredientProc.read(menuIngredVO.getIngredno());
			IngredList.add(ingredientVO);
		}
		model.addAttribute("IngredNameList", IngredList);
		model.addAttribute("accessType", accessType);
		model.addAttribute("word", word);
		model.addAttribute("now_page", now_page);
		
		return "menu/read";
	}

	// http://localhost:9093/menu/list_search_paging
	@GetMapping("list")
	public String list_search_paging(Model model,
			HttpSession session,
			@RequestParam(defaultValue = "") String word,
			@RequestParam(defaultValue = "1") int now_page) {
		String accessType = (String) session.getAttribute("type");
		
		int restno = 0;
		ArrayList<RestaurantVO> RestList = null;
		int ownerno = 0;
		if(accessType == "Master" || accessType == "Admin" || accessType == "Manager") { // 관리자 접속
			System.out.println("admin");
			RestList = this.restaurantProc.list_all();
			ownerno = 0;
			
		}else if(accessType.equals("owner")) {	// 사업자 접속
			ownerno = (int)session.getAttribute("ownerno");
			System.out.println("Owner" + ownerno);
			RestList = this.restaurantProc.findByOwnerR(ownerno);
			System.out.println("ownerRestList"+ RestList.size());
		}else {
			return "redirect:/";
		}
		model.addAttribute("accessType", accessType);
		model.addAttribute("RestList", RestList);
		
		return "menu/list_search_paging";
	}

	/**
	 * 메뉴 수정 GET
	 *
	 * @param model
	 * @param menuno
	 * @param word
	 * @param now_page
	 * @return
	 */
	@GetMapping("update")
	public String update(Model model, HttpSession session, int menuno, String word, int now_page) {
	  String accessType = (String) session.getAttribute("type");
	  MenuVO menuVO = this.menuProc.read(menuno);
	  model.addAttribute(menuVO);

	  ArrayList<IngredientVO> list = this.ingredientProc.list_all();
	  model.addAttribute("list", list);
		
	  if((accessType == "Master" || accessType == "Admin" || accessType == "Manager") || accessType == "owner") {
	    // 메뉴의 재료 목록
	    ArrayList<MenuIngredVO> menuIngredList = this.menuIngredProc.list_by_menuno(menuno);
		ArrayList<IngredientVO> IngredList = new ArrayList<IngredientVO>();
		for (MenuIngredVO menuIngredVO : menuIngredList) {
		  IngredientVO ingredientVO = this.ingredientProc.read(menuIngredVO.getIngredno());
		  IngredList.add(ingredientVO);
		}
		model.addAttribute("IngredNameList", IngredList);
	  }else {
	    return "redirect:/owner";
	  }
	  model.addAttribute("word", word);
      model.addAttribute("now_page", now_page);
      return "menu/update";
	}
	/**
	 * 메뉴 수정 (내용, 이미지)POST
	 *
	 * @param model
	 * @param ra
	 * @param word
	 * @param now_page
	 * @param menuVO
	 * @return
	 */
	@PostMapping("update")
	public String update(Model model, RedirectAttributes ra,
			@RequestParam(defaultValue = "") String word,
			int now_page,
			MenuVO menuVO,
			@RequestParam(name = "ingredno[]", defaultValue = "") int[] ingredno,
			@RequestParam(name = "deleteIngredno[]", defaultValue = "") int[] deleteIngredno) {
		System.out.println("가격:" + menuVO.getPrice());
		MenuVO menuVO_old = this.menuProc.read(menuVO.getMenuno());
		// -------------------------------------------------------------------
		// 파일 삭제 시작
		// -------------------------------------------------------------------
		String file1saved = menuVO_old.getImage(); // 실제 저장된 파일명
		long size1 = 0;

		String upDir = Menu.getUploadDir(); // C:/kd/deploy/resort_v2sbm3c/contents/storage/
		Tool.deleteFile(upDir, file1saved); // 실제 저장된 파일삭제
		// -------------------------------------------------------------------
		// 파일 삭제 종료
		// -------------------------------------------------------------------
		// ------------------------------------------------------------------------------
		// 파일 전송 코드 시작
		// ------------------------------------------------------------------------------
		System.out.println("-> upDir: " + upDir);
		System.out.println("파일명" + menuVO.getFile1MF());
		// 전송 파일이 없어도 file1MF 객체가 생성됨.
		// <input type='file' class="form-control" name='file1MF' id='file1MF'
		// value='' placeholder="파일 선택">
		MultipartFile mf = menuVO.getFile1MF();

		String file1 = mf.getOriginalFilename(); // 원본 파일명 산출, 01.jpg
		size1 = mf.getSize();
		System.out.println("-> 원본 파일명 산출 file1: " + file1);

		if (!file1.isEmpty()) {
			if (Tool.checkUploadFile(file1) == true) { // 업로드 가능한 파일인지 검사

				if (size1 > 0) { // 파일 크기 체크
					// 파일 저장 후 업로드된 파일명이 리턴됨, spring.jsp, spring_1.jpg...
					file1saved = Upload.saveFileSpring(mf, upDir, file1saved);
				}
				System.out.println("file1saved" + file1saved);
				menuVO.setImage(file1saved); // 저장본 파일명
				// ------------------------------------------------------------------------------
				// 파일 전송 코드 종료
				// ------------------------------------------------------------------------------
			} else {
				ra.addFlashAttribute("cnt", 0);
				ra.addFlashAttribute("code", "check_upload_file_fail");
				ra.addFlashAttribute("url", "/contents/msg"); // msg.jsp, redirect parameter 적용
				return "redirect:/contents/msg";
			}

		}
		int cnt = this.menuProc.update_by_menuno(menuVO);

		if (cnt == 0) {
			model.addAttribute("cnt", cnt);
			model.addAttribute("code", "create_fail");
			return "content/msg";
		} else {
			for (int deleteno : deleteIngredno) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ingredno", deleteno);
				map.put("menuno", menuVO.getMenuno());
				MenuIngredVO deleteMenuIngredVO = this.menuIngredProc.search_by_ingredno_menuno(map);
				if (deleteMenuIngredVO != null) {
					int deletCnt = this.menuIngredProc.delete_by_menuingredno(deleteMenuIngredVO.getMenuingredno());
				}

			}
			int menuIngredCnt = 0;
			// 메뉴 재료 추가
			for (int ingredientno : ingredno) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("ingredno", ingredientno);
				map.put("menuno", menuVO.getMenuno());
				if (this.menuIngredProc.search_by_ingredno_menuno(map) == null) {
					MenuIngredVO menuIngredVO = new MenuIngredVO();
					menuIngredVO.setIngredno(ingredientno);
					menuIngredVO.setMenuno(menuVO.getMenuno());
					menuIngredCnt = this.menuIngredProc.create(menuIngredVO);
					if (menuIngredCnt == 0) {
						ra.addAttribute("word", word);
						ra.addAttribute("now_page", now_page);
						ra.addFlashAttribute("code", "update_menuingred_fail");
						return "redirect:/menu/msg"; // ra.addAttribute 사용시 url에 직접 작성 안함
					}
				}
			}
			ra.addAttribute("word", word);
			ra.addAttribute("now_page", now_page);
			return "redirect:/menu/list"; // ra.addAttribute 사용시 url에 직접 작성 안함
		}
	}

	@PostMapping("delete")
	public String delete(Model model, RedirectAttributes ra, int menuno, String word, int now_page) {
		// 메뉴 재료 삭제
		this.menuIngredProc.delete_by_menuno(menuno);
		// 메뉴 삭제
		int cnt = this.menuProc.delete_by_menuno(menuno);
		if (cnt == 1) {

			ra.addAttribute("word", word);
			ra.addAttribute("now_page", now_page);
			return "redirect:/menu/list_search_paging";
		} else {
			ra.addAttribute("code", "delete_fail");
			return "redirect:/menu/msg";
		}
	}

	@PostMapping(value = "/menulist")
	@ResponseBody
	public ResponseEntity<MenuResponse> menulist(@RequestBody Map<String,Object> requestBody) {
		String word = ((String) requestBody.get("word")).trim();
		System.out.println("-> searchword:" + word);
		String strRestno = (String) requestBody.get("restno");
		int restno = Integer.parseInt(strRestno);
		System.out.println("-> restno:" + restno);
		int nowPage = (int)requestBody.get("now_page");
		System.out.println("-> nowPage:" + nowPage);
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("word", word);
		map.put("restno", restno);
		map.put("now_page", nowPage);
		ArrayList<MenuVO> list = this.menuProc.list_search_paging(map);

		int search_count = this.menuProc.list_by_restno_search_count(map);
		System.out.println("search_count:" + search_count);
		MenuResponse response = new MenuResponse();
		String paging = this.menuProc.pagingBox(nowPage, word, word, search_count, Menu.RECORD_PER_PAGE,
				Menu.PAGE_PER_BLOCK);
		response.setMenuList(list);
		response.setPaging(paging);
		System.out.println(search_count);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/menuAllList")
	public String menuAllList(Model model, HttpSession session, int restno, int person, String date) {
		String userType = (String)session.getAttribute("type");
		ArrayList<MenuIngredDTO> list = null;
		if(userType!=null && userType.equals("customer")) {
			int custno = (int) session.getAttribute("custno");
			list = new ArrayList<MenuIngredDTO>();
			ArrayList<MenuVO> menuList = this.menuProc.list_by_restno(restno);
			for(MenuVO menuVO : menuList) {
				MenuIngredDTO menuIngredDTO = new MenuIngredDTO();
				ArrayList<MenuIngredVO> menuIngredList = this.menuIngredProc.list_by_menuno(menuVO.getMenuno());
				
				ArrayList<IngredientVO> ingredList = this.menuIngredProc.allergy_check_ingredient(menuVO.getMenuno(), custno);
				System.out.println("재료리스트 사이즈: " + ingredList.size());
				for(IngredientVO ingredientVO : ingredList) {
					System.out.println("재료 확인"+ingredientVO.getName() + ": " + ingredientVO.getHas_allergy());
				}
				menuIngredDTO.setMenuVO(menuVO);
				menuIngredDTO.setIngredList(ingredList);
				list.add(menuIngredDTO);
			}
			
		}else {
			list = new ArrayList<MenuIngredDTO>();
			ArrayList<MenuVO> menuList = this.menuProc.list_by_restno(restno);
			for(MenuVO menuVO : menuList) {
				MenuIngredDTO menuIngredDTO = new MenuIngredDTO();
				ArrayList<MenuIngredVO> menuIngredList = this.menuIngredProc.list_by_menuno(menuVO.getMenuno());
				ArrayList<IngredientVO> ingredList = new ArrayList<IngredientVO>();
				for(MenuIngredVO menuIngredVO : menuIngredList) {
					IngredientVO ingredientVO = this.ingredientProc.read(menuIngredVO.getIngredno());
					ingredList.add(ingredientVO);
				}
				menuIngredDTO.setMenuVO(menuVO);
				menuIngredDTO.setIngredList(ingredList);
				list.add(menuIngredDTO);
			}
		}
		
		model.addAttribute("list", list);
		model.addAttribute("restno", restno);
		model.addAttribute("person", person);
		model.addAttribute("date", date);
		
		return "/menu/menu_list";
	}
}
