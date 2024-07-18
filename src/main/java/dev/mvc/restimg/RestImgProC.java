package dev.mvc.restimg;

import dev.mvc.dto.RestFullData;
import dev.mvc.restaurant.RestaurantVO;
import dev.mvc.tool.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

  @Override
  public int updateImage(RestFullData restFullData) {
    return this.restimgDAO.updateImage(restFullData);
  }

  @Override
  public List<RestFullData> getImagesByRestno(Integer restno) {
    return this.restimgDAO.getImagesByRestno(restno);
  }

  @Override
  public int delete(int rest_imgno) {
    return this.restimgDAO.delete(rest_imgno);
  }

  @Override
  public List<RestimgVO> findByFileName(String fileName) {
    return this.restimgDAO.findByFileName(fileName);
  }
  
  @Override
  public List<RestFullData> favorite_img(int restno) {
    return this.restimgDAO.favorite_img(restno);
  }


}


