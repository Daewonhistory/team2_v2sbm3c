package dev.mvc.restimg;

import dev.mvc.dto.RestFullData;
import dev.mvc.restaurant.RestaurantVO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface RestImgDAOInter {


  public int create(RestimgVO restimgVO);


  public int updateImage(RestFullData restFullData);

  public List<RestFullData> getImagesByRestno(Integer restno);

  public int delete(int rest_imgno);

  public List<RestimgVO> findByFileName(String fileName);
  
  public List<RestFullData> favorite_img(int restno);
}
