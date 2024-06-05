package dev.mvc.loginhistory;

import dev.mvc.menu.MenuVO;

import java.util.ArrayList;
import java.util.HashMap;

public interface LoginHistoryProcInter {
	/**
	 * 로그인 기록 생성
	 * @param menuVO
	 * @return
	 */
	public int create(LoginHistoryVO historyVO);
	


}
