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
   * 모든 예약 조회
   * @return
   */
  public ArrayList<FavoriteVO> favorite_list();
}


