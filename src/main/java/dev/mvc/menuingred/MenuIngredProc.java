package dev.mvc.menuingred;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.menuingred.MenuIngredProc")
public class MenuIngredProc implements MenuIngredProcInter {
	@Autowired
	MenuIngredDAOInter menuIngredDAO;
	
	@Override
	public int create(MenuIngredVO menuIngredVO) {
		int cnt = this.menuIngredDAO.create(menuIngredVO);
		return cnt;
	}

	@Override
	public ArrayList<MenuIngredVO> list_by_menuno(int menuno) {
		ArrayList<MenuIngredVO> list = this.menuIngredDAO.list_by_menuno(menuno);
		return list;
	}

	@Override
	public int delete_by_menuingredno(int menuingredno) {
		int cnt = this.menuIngredDAO.delete_by_menuingredno(menuingredno);
		return cnt;
	}

	@Override
	public MenuIngredVO search_by_ingredno_menuno(Map<String, Object> map) {
		MenuIngredVO menuIngredVO = this.menuIngredDAO.search_by_ingredno_menuno(map);
		return menuIngredVO;
	}

	@Override
	public int delete_by_menuno(int menuno) {
		int cnt = this.menuIngredDAO.delete_by_menuno(menuno);
		return cnt;
	}
	
	

}
