package dev.mvc.allergy;

import java.util.ArrayList;
import java.util.Map;

public interface AllergyDAOInter {
  /**
   * 알러지 재료 생성
   * @param allergyVO
   * @return
   */
  public int create(AllergyVO allergyVO);
  
  /**
   * 고객의 번호로 재료 목록
   * @param custno
   * @return
   */
  public ArrayList<AllergyVO> list_by_custno(int custno);
  
  
  /**
   * 알러지 재료 목록
   * @param ingredno
   * @return
   */
  public ArrayList<AllergyVO> list_by_ingredno(int ingredno);
  
  /**
   * 알러지재료번호로 삭제
   * @param allerno
   * @return
   */
  public int delete_by_allerno(int allerno);
  
  /**
   * 
   * @param custno
   * @return
   */
  public int delete_by_custno(int custno);
  
  /**
   * 고객번호와 재료번호로 조회
   * @param map
   * @return
   */
  public AllergyVO search_by_ingredno_custno(Map<String, Object> map);
  
  /**
   * 손님의 알러지 재료들 전부 삭제
   * @param ingredno
   * @return
   */
  public int delete_by_ingredno(int ingredno);
}
