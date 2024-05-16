package dev.mvc.ingredient;

import java.util.ArrayList;

public interface IngredientProcInter {
	/**
	 * 재료 추가
	 * @param name
	 * @return
	 */
	public int create(IngredientVO ingredientVO);
	
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
}
