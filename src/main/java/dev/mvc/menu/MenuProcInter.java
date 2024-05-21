package dev.mvc.menu;

import java.util.ArrayList;
import java.util.HashMap;

public interface MenuProcInter {
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
	   * SPAN태그를 이용한 박스 모델의 지원, 1 페이지부터 시작 
	   * 현재 페이지: 11 / 22   [이전] 11 12 13 14 15 16 17 18 19 20 [다음] 
	   *
	   * @param cateno          카테고리번호 
	   * @param now_page      현재 페이지
	   * @param word 검색어
	   * @param list_file 목록 파일명
	   * @param search_count 검색 레코드수   
	   * @return 페이징 생성 문자열
	   */ 
	  public String pagingBox(int now_page, String word, String list_file, int search_count, int record_per_page,
	      int page_per_block);
	  
}
