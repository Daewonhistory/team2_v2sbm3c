package dev.mvc.menu;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dev.mvc.ingredient.Ingredient;
import dev.mvc.ingredient.IngredientProcInter;
import dev.mvc.ingredient.IngredientVO;
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

		String user_type = (String) session.getAttribute("type");
		// 관리자, 사업자 구분처리(사업자는 본인 식당만 생성, 관리자는 모두 생성가능)
		// model.addAttribute(user_type)
		// if(user_type.equals("owner")) {
		// session.getAtadfa("ownerno")
		// }else{}
		
		ArrayList<IngredientVO> list = this.ingredientProc.list_all();
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
			RedirectAttributes ra) {

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
		}

		// ------------------------------------------------------------------------------
		// PK의 return
		// ------------------------------------------------------------------------------
		// System.out.println("--> contentsno: " + contentsVO.getContentsno());
		// mav.addObject("contentsno", contentsVO.getContentsno()); // redirect
		// parameter 적용
		// ------------------------------------------------------------------------------

		// return "redirect:/contents/list_by_cateno?cateno=" + contentsVO.getCateno();
		// // 직접 변수 선언
		ra.addAttribute("word", word);
		ra.addAttribute("now_page", now_page);
		return "redirect:/menu/list_search_paging"; // ra.addAttribute 사용시 url에 직접 작성 안함

	}

	@GetMapping("read")
	public String read(Model model, String word, int now_page, int menuno) {
		MenuVO menuVO = this.menuProc.read(menuno);
		model.addAttribute("menuVO", menuVO);

		model.addAttribute(word, word);
		model.addAttribute("now_page", now_page);
		return "menu/read";
	}

	// http://localhost:9093/menu/list_search_paging
	@GetMapping("list_search_paging")
	public String list_search_paging(Model model,
			@RequestParam(defaultValue = "") String word,
			@RequestParam(defaultValue = "1") int now_page) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("word", word);
		map.put("now_page", now_page);
		map.put("restno", 0);

		ArrayList<MenuVO> list = this.menuProc.list_search_paging(map);
		model.addAttribute("list", list);
		int search_count = this.menuProc.list_search_count(word);
		model.addAttribute("search_count", search_count);
		String paging = this.menuProc.pagingBox(now_page, word, "/menu/list_search_paging", search_count,
				Menu.RECORD_PER_PAGE, Menu.PAGE_PER_BLOCK);
		model.addAttribute("paging", paging);

		model.addAttribute("word", word);
		model.addAttribute("now_page", now_page);
		model.addAttribute("search_count", search_count);

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
	public String update(Model model, int menuno, String word, int now_page) {
		MenuVO menuVO = this.menuProc.read(menuno);
		model.addAttribute(menuVO);

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
	public String update(Model model, RedirectAttributes ra, String word, int now_page, MenuVO menuVO) {
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
			model.addAttribute("code", "update_fail");
			return "content/msg";
		}

		// ------------------------------------------------------------------------------
		// PK의 return
		// ------------------------------------------------------------------------------
		// System.out.println("--> contentsno: " + contentsVO.getContentsno());
		// mav.addObject("contentsno", contentsVO.getContentsno()); // redirect
		// parameter 적용
		// ------------------------------------------------------------------------------

		// return "redirect:/contents/list_by_cateno?cateno=" + contentsVO.getCateno();
		// // 직접 변수 선언
		ra.addAttribute("word", word);
		ra.addAttribute("now_page", now_page);
		return "redirect:/menu/list_search_paging"; // ra.addAttribute 사용시 url에 직접 작성 안함
	}

	@PostMapping("delete")
	public String delete(Model model, RedirectAttributes ra, int menuno, String word, int now_page) {
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
}
