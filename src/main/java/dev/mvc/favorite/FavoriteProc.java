package dev.mvc.favorite;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mvc.reserve.ReserveVO;


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
    // TODO Auto-generated method stub
    return null;
  }




	  
}
