package dev.mvc.restaurant;

import dev.mvc.category.CategoryVO;
import dev.mvc.dto.RestDTO;
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
}



