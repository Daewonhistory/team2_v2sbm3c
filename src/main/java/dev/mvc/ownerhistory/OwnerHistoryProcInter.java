package dev.mvc.ownerhistory;

public interface OwnerHistoryProcInter {
  /**
   * 로그인 기록 생성
   *
   * @param historyVO
   * @return
   */
  public int create(OwnerHistoryVO historyVO);


}
