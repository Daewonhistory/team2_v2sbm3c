package dev.mvc.reviewimg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.reviewimg.ReviewImgProc")
public class ReviewImgProc implements ReviewImgProcInter {

  @Autowired
  private ReviewImgDAOInter reviewimgDAO;

  public ReviewImgProc() {
    // System.out.println("ReviewImgProc created");
  }

  @Override
  public int create(ReviewimgVO reviewimgVO) {
    return this.reviewimgDAO.create(reviewimgVO);
  }

  @Override
  public int delete_by_reviewno(int reviewno) {
      return this.reviewimgDAO.delete_by_reviewno(reviewno);
  }

}
