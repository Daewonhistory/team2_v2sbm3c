package dev.mvc.reserve;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.dto.ReserveDTO;

public interface ReserveProcInter {
  
  /**
   * 예약 등록
   * @param reserve
   * @return
   */
  int create(ReserveVO reserve);
  
  
  /**
   *  예약 조회
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
  
  /**
   * 페이징을 적용한 예약 조회
   * @param 
   * @param 
   * @return
   */
  public ArrayList<ReserveDTO> list_reserve_paging(int now_page, int record_per_page);

  /**
   * 전체 예약 수 조회
   * @return
   */
  public int count_all();

  /**
   * 페이징 박스 생성
   * @param now_page 현재 페이지 번호
   * @param list_file 목록 파일명
   * @param total_count 전체 레코드 수
   * @param record_per_page 페이지당 레코드 수
   * @param page_per_block 블록당 페이지 수
   * @return 페이징 박스 HTML
   */
  String pagingBox(int now_page, String list_file, int total_count, int record_per_page, int page_per_block);
}


  


