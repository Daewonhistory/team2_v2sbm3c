package dev.mvc.restaurant;

import dev.mvc.category.CategoryVO;
import dev.mvc.dto.RestDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface RestaurantDAOInter {


  public int create(RestaurantVO restaurantVO);


  /**
   * 기능 : 사업자 번호로 검색
   * 매개변수 search 넣을 내용, start_num ,end_num ,
   * @return 표시된 카테고리 객체의 목록  s
   */
  public ArrayList<RestDTO> list_search_paging(Map<String,Object> map);


  /**
   * 페이징 필요한 개수 반환
   * @param search
   * @return
   */

  public int list_search_count(Map<String, Object> search);

  public int nextval();


  /**
   * 사업자 세션 으로 식당 리스트 출력
   * @param onwerno
   * @return
   */
  public ArrayList<RestaurantVO> findByOwnerR(int onwerno);
  
  /**
   * 모든 식당 리스트 출력
   * @return
   */
  public ArrayList<RestaurantVO> list_all();
  
  /**
   * 식당 정보 조회
   * @param restno
   * @return
   */
  public RestaurantVO read(int restno);
  
  /**
   * 조건을 통한 식당 검색
   * @param map
   * @return
   */
  public ArrayList<RestaurantVO> condition_search_list(Map<String, Object> map);
}
