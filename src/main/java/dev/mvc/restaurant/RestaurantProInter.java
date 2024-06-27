package dev.mvc.restaurant;

import dev.mvc.category.CategoryVO;
import dev.mvc.dto.RestDTO;
import dev.mvc.dto.RestFullData;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface RestaurantProInter {

  /**
   * 식당 생성
   * @param restaurantVO
   * @return
   */
  public int create(RestaurantVO restaurantVO);


  /**
   * 기능 : 사업자 번호로 검색
   * 매개변수 search 넣을 내용, start_num ,end_num ,
   * @return 표시된 카테고리 객체의 목록  s
   */
  public ArrayList<RestDTO> list_search_paging(String word, String type, int now_page, int record_per_page);



  public int update_map(RestFullData restFullData);


  public int update(RestFullData restFullData);

  /**
   * 페이징 필요한 개수 반환
   * @param search
   * @return
   */

  public int list_search_count(String word, String type);

  /**
   *
   * 페이징 메서드
   * @param now_page
   * @param word
   * @param type
   * @param list_file
   * @param search_count
   * @param record_per_page
   * @param page_per_block
   * @return
   */
  public String pagingBox(int now_page, String word,String type, String list_file, int search_count,int record_per_page, int page_per_block);


  public Integer foreign(int ownerno);

  public int next(String businessno);

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
  
  /**
   * 조건을 통한 식당 검색 이미지 포함
   * @param map
   * @return
   */
  public ArrayList<RestFullData> SearchRestaurantWithImg(Map<String, Object> map);
  

  /**
   * 레스토랑의 모든 데이터 조회
   * @param restno
   * @return
   */
  public RestFullData readFullData(int restno);
  
  
  public String test(String date);
  
  /**
   * 맵 화면 내의 식당 조회
   * @param southWest
   * @param NorthEast
   * @return
   */
  public ArrayList<RestFullData> coordinateSearchList(double westLat, double eastLat, double southLng, double northLng);

}



