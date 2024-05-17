package dev.mvc.ingredient;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.ingredient.IngredientProc")
public class IngredientProc implements IngredientProcInter {
	@Autowired
	private IngredientDAOInter ingredientDAO;
	
	@Override
	public int create(IngredientVO ingredientVO) {
		int cnt = this.ingredientDAO.create(ingredientVO);
		return cnt;
	}
	
	@Override
	public IngredientVO read(int ingredno) {
		IngredientVO ingredientVO = this.ingredientDAO.read(ingredno);
		return ingredientVO;
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

	@Override
	public int delete_by_ingredno(int ingredno) {
		int cnt = this.ingredientDAO.delete_by_ingredno(ingredno);
		return cnt;
	}

	@Override
	public int update_by_ingredno(IngredientVO ingredientVO) {
		int cnt = this.ingredientDAO.update_by_ingredno(ingredientVO);
		return cnt;
	}

	

}
