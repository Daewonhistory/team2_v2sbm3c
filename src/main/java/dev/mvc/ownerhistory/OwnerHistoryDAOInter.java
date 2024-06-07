package dev.mvc.ownerhistory;

import dev.mvc.dto.HistoryDTO;

import java.util.ArrayList;

public interface OwnerHistoryDAOInter {

  /**
   * 로그인 기록 생성
   *
   * @param historyVO
   * @return
   */
  public int create(OwnerHistoryVO historyVO);


  /**
   * 로그인 내역 조회
   * @param ownerno
   * @return
   */
  public ArrayList<HistoryDTO> selecthistory (int ownerno);

}
