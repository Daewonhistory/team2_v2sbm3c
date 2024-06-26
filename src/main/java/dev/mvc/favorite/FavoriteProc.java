package dev.mvc.favorite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.favorite.FavoriteProc")
public class FavoriteProc implements FavoriteProcInter {

  @Autowired
  private FavoriteDAOInter favoriteDAO;

  @Override
  public int create(FavoriteVO favoriteVO) {
    return this.favoriteDAO.create(favoriteVO);
  }

  @Override
  public int delete(FavoriteVO favoriteVO) {
    return this.favoriteDAO.delete(favoriteVO);
  }

  @Override
  public ArrayList<FavoriteVO> favorite_list() {
    ArrayList<FavoriteVO> list = this.favoriteDAO.favorite_list();
    System.out.println("DAO returned favorite list: " + list);
    return list;
  }
  
  @Override
  public ArrayList<FavoriteVO> list_by_custno(int custno) {
      return favoriteDAO.list_by_custno(custno);
  }
  
  @Override
  public boolean isFavorited(int custno, int restno) {
      Map<String, Object> params = new HashMap<>();
      params.put("custno", custno);
      params.put("restno", restno);
      return favoriteDAO.isFavorited(params) > 0; // 여기서 int를 boolean으로 변환
  }
}
