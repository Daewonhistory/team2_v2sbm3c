package dev.mvc.ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dev.mvc.ingredient.IngredientVO;

public interface IngredientDAOInter {
	/**
	 * 재료 추가
	 * @param ingredientVO
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
}
