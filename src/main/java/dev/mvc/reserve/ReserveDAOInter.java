package dev.mvc.reserve;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.dto.ReserveDTO;

public interface ReserveDAOInter {
  
    /**
     * 예약 등록
     * @param reserve
     * @return 생성된 예약 수
     */
    int create(ReserveVO reserve);
    
    /**
     * 모든 예약 조회
     * @return 예약 목록
     */
    ArrayList<ReserveVO> list_all();
    
    /**
     * 특정 날짜의 예약 조회
     * @param params
     * @return 예약 목록
     */
    ArrayList<ReserveVO> list_search_by_reserve_date(Map<String, Object> params);
    
    
    /**
     * 특정 고객의 예약 조회
     * @param custno
     * @return 예약 목록
     */
    ArrayList<ReserveVO> list_search_by_custno(int custno);
    
    /**
     * 예약 취소
     * @param reserveno
     * @return 삭제된 예약 수
     */
    int delete(int reserveno);
    
    /**
     * 페이징을 적용한 예약 조회
     * @param params
     * @return 예약 목록
     */
    ArrayList<ReserveDTO> list_reserve_paging(Map<String, Object> params);
    

    ArrayList<ReserveDTO> list_owner_paging(Map<String, Object> map);

    
    /**
     * 특정 사업자의 예약 수 조회
     * @param params
     * @return 예약 수
     */
    int count_by_owner(Map<String, Object> map);


    
    /**
     * 전체 예약 수 조회
     * @return 예약 수
     */
    int count_all();
    
    
}
