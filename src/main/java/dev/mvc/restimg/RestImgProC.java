package dev.mvc.restimg;

import dev.mvc.restaurant.RestaurantVO;
import dev.mvc.tool.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.restimg.RestImgProC")
public class RestImgProC implements RestImgProInter {

  @Autowired
  private RestImgDAOInter restimgDAO;


  @Autowired
  private Security security;

  public RestImgProC() {
//    System.out.println("ownerProc created");
  }

  /**
   * 식당 생성
   *
   * @param restimgVO
   * @return
   */
  @Override
  public int create(RestimgVO restimgVO) {
    return this.restimgDAO.create(restimgVO);
  }


}


