package dev.mvc.menu;

import java.util.ArrayList;
import java.util.HashMap;

public interface MenuDAOInter {
	
	/**
	 * 메뉴 생성
	 * @param menuVO
	 * @return
	 */
	public int create(MenuVO menuVO);
	
	/**
	 * 메뉴 정보 
	 * @param menuno
	 * @return
	 */
	public MenuVO read(int menuno);
	
	/**
	 * 메뉴 삭제
	 * @param menuno
	 * @return
	 */
	public int delete_by_menuno(int menuno);
	
	/**
	 * 메뉴 수정
	 * @param menuVO
	 * @return
	 */
	public int update_by_menuno(MenuVO menuVO);
	
	/**
	 * 메뉴 이름 검색 + 페이징
	 * @param word
	 * @return
	 */
	public ArrayList<MenuVO> list_search_paging(HashMap<String, Object> map);
	
	/**
	 * 메뉴 검색 시 출력 수
	 * @param word
	 * @return
	 */
	public int list_search_count(String word);
	
	/**
	 * 식당의 메뉴 갯수
	 * @param restno
	 * @return
	 */
	public int list_by_restno_count(int restno);
	
	/**
	 * 식당의 마지막에 추가된 메뉴 조회
	 * @param restno
	 * @return
	 */
	public MenuVO last_menu_by_restno(int restno);
	
}
