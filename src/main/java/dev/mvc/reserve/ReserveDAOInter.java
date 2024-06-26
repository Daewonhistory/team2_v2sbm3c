package dev.mvc.reserve;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.dto.ReserveDTO;

public interface ReserveDAOInter {
  
  /**
   * 예약 등록
   * @param reserve
   * @return
   */
  int create(ReserveVO reserve);
  
  /**
   * 예약 조회
   * @return
   */
  public ArrayList<ReserveVO> list_all();
  
  /**
   * 전체 예약 수 조회
   * @return
   */
  int count_all();
  
  /**
   * 페이징을 적용한 예약 조회
   * @param params
   * @return
   */
  public ArrayList<ReserveDTO> list_reserve_paging(Map<String, Object> map);
  
  
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
   * @param reserve
   * @return
   */
  public int delete(int reserveno);
}

