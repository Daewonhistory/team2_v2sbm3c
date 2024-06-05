package dev.mvc.ownerhistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.ownerhistory.OwnerHistoryProc")
public class OwnerHistoryProc implements OwnerHistoryProcInter {

	@Autowired
  OwnerHistoryDAOInter historyDAO;
	
	@Override
	public int create(OwnerHistoryVO historyVO) {
		int cnt = this.historyDAO.create(historyVO);
		return cnt;
	}


}
