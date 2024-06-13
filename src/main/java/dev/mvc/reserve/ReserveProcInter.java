package dev.mvc.reserve;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dev.mvc.admitperson.AdmitPersonVO;

public interface ReserveProcInter {
  
  /**
   * 예약 등록
   * @param reserve
   * @return
   */
  int create(ReserveVO reserve);
  
  
  /**
   * 모든 예약 조회
   * @return
   */
  public ArrayList<ReserveVO> list_all();
  
  /**
   * 고객마다 예약 조회
   * @return
   */
  public ArrayList<ReserveVO> list_search_by_custno(int custno);
  
  /**
   * 예약 날짜 조회
   * @param custno
   * @param reserveDate
   * @return
   */
  ArrayList<ReserveVO> list_search_by_reserve_date(Map<String, Object> params);
  

  /**
   * 예약 취소
   * @param reserveno
   * @return
   */
  public int delete(int reserveno);


  
}

