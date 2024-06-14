package dev.mvc.favorite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;

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
}
