package dev.mvc.ingredient;

import java.util.ArrayList;
import java.util.HashMap;

public interface IngredientProcInter {
	/**
	 * 재료 추가
	 * @param name
	 * @return
	 */
	public int create(IngredientVO ingredientVO);
	
	/**
	 * 재료 읽기
	 * @param ingredno
	 * @return
	 */
	public IngredientVO read(int ingredno);
	
	/**
	 * 모든 재료 출력
	 * @return
	 */
	public ArrayList<IngredientVO> list_all();
	
	
	/**
	 * 재료 검색
	 * @param word 검색어
	 * @return
	 */
	public ArrayList<IngredientVO> list_search(String word);
	
	/**
	 * 재료 삭제
	 * @param ingredno
	 * @return
	 */
	public int delete_by_ingredno(int ingredno);
	
	/**
	 * 재료 수정
	 * @param ingredientVO
	 * @return
	 */
	public int update_by_ingredno(IngredientVO ingredientVO);
	
	public ArrayList<IngredientVO> list_search_paging(HashMap<String, Object> map);
	
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
