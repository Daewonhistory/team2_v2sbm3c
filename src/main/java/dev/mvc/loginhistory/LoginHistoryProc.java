package dev.mvc.loginhistory;

import dev.mvc.menu.Menu;
import dev.mvc.menu.MenuDAOInter;
import dev.mvc.menu.MenuProcInter;
import dev.mvc.menu.MenuVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service("dev.mvc.loginhistory.LoginHistoryProc")
public class LoginHistoryProc implements LoginHistoryProcInter {

	@Autowired
  LoginHistoryDAOInter historyDAO;
	
	@Override
	public int create(LoginHistoryVO historyVO) {
		int cnt = this.historyDAO.create(historyVO);
		return cnt;
	}


}
