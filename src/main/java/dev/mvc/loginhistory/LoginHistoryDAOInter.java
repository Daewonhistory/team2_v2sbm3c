package dev.mvc.loginhistory;

import dev.mvc.menu.MenuVO;

import java.util.ArrayList;
import java.util.HashMap;

public interface LoginHistoryDAOInter {
	
	/**
	 * 로그인 기록 생성
	 * @param historyVO
	 * @return
	 */
	public int create(LoginHistoryVO historyVO);
	

}
