package dev.mvc.ingredient;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

public class IngredientProc implements IngredientProcInter {
	@Autowired
	private IngredientDAOInter ingredientDAO;
	
	@Override
	public int create(IngredientVO ingredientVO) {
		int cnt = this.ingredientDAO.create(ingredientVO);
		return cnt;
	}

	@Override
	public ArrayList<IngredientVO> list_all() {
		ArrayList<IngredientVO> list = this.ingredientDAO.list_all();
		return list;
	}

	@Override
	public ArrayList<IngredientVO> list_search(String word) {
		ArrayList<IngredientVO> list = this.ingredientDAO.list_search(word);
		return list;
	}

}
