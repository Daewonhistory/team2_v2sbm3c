package dev.mvc.ownerhistory;

import dev.mvc.dto.HistoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service("dev.mvc.ownerhistory.OwnerHistoryProc")
public class OwnerHistoryProc implements OwnerHistoryProcInter {

  @Autowired
  OwnerHistoryDAOInter historyDAO;

  @Override
  public int create(OwnerHistoryVO historyVO) {
    int cnt = this.historyDAO.create(historyVO);
    return cnt;
  }

  @Override
  public ArrayList<HistoryDTO> selecthistory(int ownerno) {
    return this.historyDAO.selecthistory(ownerno);
  }


}
