package dev.mvc.restimg;

import dev.mvc.dto.RestFullData;
import dev.mvc.restaurant.RestaurantVO;

import java.util.ArrayList;
import java.util.List;

public interface RestImgProInter {

  /**
   * 식당 생성
   * @param restimgVO
   * @return
   */
  public int create(RestimgVO restimgVO);

  public int updateImage(RestFullData restFullData);

  public List<RestFullData> getImagesByRestno(Integer restno);

}



