package dev.mvc.reviewimg;

import dev.mvc.review.ReviewVO;

import java.util.ArrayList;
import java.util.Map;

public interface ReviewImgDAOInter {
  


  public int create(ReviewimgVO reviewimgVO);
  
  public int delete_by_reviewno(int reviewno);




}
