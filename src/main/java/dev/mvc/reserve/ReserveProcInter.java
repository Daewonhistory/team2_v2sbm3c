package dev.mvc.reserve;

import java.util.ArrayList;
import java.util.Map;

import dev.mvc.dto.ReserveDTO;
import dev.mvc.restaurant.RestaurantVO;

public interface ReserveProcInter {
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
    ArrayList<ReserveDTO> list_reserve_paging(int now_page, int record_per_page);
    
    /**
     * 사업자의 식당 예약 
     * @param ownerno
     * @param now_page
     * @param record_per_page
     * @return
     */
    ArrayList<ReserveDTO> list_owner_paging(int ownerno, String reserve_date,int now_page, int record_per_page);
    
    int count_by_owner(int ownerno,String reserve_date);


 
    /**
     * 전체 예약 수 조회
     * @return 예약 수
     */
    int count_all();

    /**
     * 페이징 박스 생성
     * @param now_page
     * @param list_file
     * @param total_count
     * @param record_per_page
     * @param page_per_block
     * @return 페이징 HTML
     */
    String pagingBox(int now_page, String list_file, int total_count, int record_per_page, int page_per_block);

    
}
