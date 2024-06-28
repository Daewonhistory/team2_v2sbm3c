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
	 * 
	 * @param now_page
	 * @param word
	 * @param list_file
	 * @param search_count
	 * @param record_per_page
	 * @param page_per_block
	 * @return
	 */
	public String pagingBox(int now_page, String word, String list_file, int search_count, int record_per_page,
	      int page_per_block);
	public int list_search_count(String word);
}
