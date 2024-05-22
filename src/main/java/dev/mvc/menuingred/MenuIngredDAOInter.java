package dev.mvc.menuingred;

import java.util.ArrayList;
import java.util.Map;

public interface MenuIngredDAOInter {
	/**
	 * 메뉴의 재료 생성
	 * @param menuIngredVO
	 * @return
	 */
	public int create(MenuIngredVO menuIngredVO);
	
	/**
	 * 메뉴의 재료 목록
	 * @param menuno
	 * @return
	 */
	public ArrayList<MenuIngredVO> list_by_menuno(int menuno);
	
	/**
	 * 메뉴재료번호로 삭제
	 * @param menuingredno
	 * @return
	 */
	public int delete_by_menuingredno(int menuingredno);
	
	/**
	 * 메뉴번호와 재료번호로 조회
	 * @param map
	 * @return
	 */
	public MenuIngredVO search_by_ingredno_menuno(Map<String, Object> map);
	
	/**
	 * 메뉴의 재료들 전부 삭제
	 * @param menuno
	 * @return
	 */
	public int delete_by_menuno(int menuno);
}
