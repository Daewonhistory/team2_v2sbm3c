package dev.mvc.favorite;

import java.util.ArrayList;
import java.util.List;


public interface FavoriteProcInter {

  /**
   * 즐겨찾기 추가
   * @param favoriteVO
   * @return
   */
  public int create(FavoriteVO favoriteVO);
  
  /**
   * 즐겨찾기 삭제
   * @param favoriteVO
   * @return
   */
  public int delete(FavoriteVO favoriteVO);
  
  /**
   * 모든 즐겨찾기 조회
   * @return
   */
  public ArrayList<FavoriteVO> favorite_list();
  
  /**
   * 특정 사용자가 특정 식당을 이미 즐겨찾기했는지 확인
   * @param custno
   * @param restno
   * @return
   */
  boolean isFavorited(int custno, int restno);
}


