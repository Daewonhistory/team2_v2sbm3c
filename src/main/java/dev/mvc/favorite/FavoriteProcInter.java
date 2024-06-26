package dev.mvc.favorite;

import java.util.ArrayList;
import java.util.Map;


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
   * 즐겨찾기 삭제(모바일)
   * @param favoriteVO
   * @return
   */
  int delete_favorite_rest(FavoriteVO favoriteVO);
  
  /**
   * 모든 즐겨찾기 조회
   * @return
   */
  public ArrayList<FavoriteVO> favorite_list();
  
  /**
   * 사용자에 따른 즐겨찾기 조회
   * @param custno
   * @return
   */
  public ArrayList<FavoriteVO> list_by_custno(int custno); 
  
  /**
   * 특정 사용자가 특정 식당을 이미 즐겨찾기했는지 확인
   * @param custno
   * @param restno
   * @return
   */
  public boolean isFavorited(int custno, int restno);

  
  
  
}


