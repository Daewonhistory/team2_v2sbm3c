package dev.mvc.ownerhistory;

import dev.mvc.dto.HistoryDTO;

import java.util.ArrayList;

public interface OwnerHistoryProcInter {
  /**
   * 로그인 기록 생성
   *
   * @param historyVO
   * @return
   */
  public int create(OwnerHistoryVO historyVO);

  public ArrayList<HistoryDTO> selecthistory (int ownerno);
}
