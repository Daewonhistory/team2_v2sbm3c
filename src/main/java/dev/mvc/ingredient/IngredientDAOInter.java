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
