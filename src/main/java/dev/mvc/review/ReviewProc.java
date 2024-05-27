package dev.mvc.review;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.mvc.review.ReviewVO;


@Service("dev.mvc.review.ReviewProc")
public class ReviewProc implements ReviewProcInter {
  
	@Autowired
	ReviewDAOInter reviewDAO;
	
	@Override
	public int create(ReviewVO reviewVO) {
		int cnt = this.reviewDAO.create(reviewVO);
		return cnt;
	}
	
	@Override
	public ReviewVO read(int reviewno) {
		ReviewVO reviewVO = this.reviewDAO.read(reviewno);
		return reviewVO;
	}
	
  @Override
  public int delete_review(int reviewno) {
    int cnt = this.reviewDAO.delete_review(reviewno);
    return cnt;
  }

  @Override
  public int update_review(ReviewVO reviewVO) {
    int cnt = this.reviewDAO.update_review(reviewVO);
    return cnt;
  }

  @Override
  public ArrayList<ReviewVO> list_all() {
    ArrayList<ReviewVO> list = this.reviewDAO.list_all();
    return list;
  }



  @Override
  public int list_by_custno_count(int custno) {
    int cnt = this.reviewDAO.list_by_custno_count(custno);
    return cnt;
  }

  @Override
  public int list_by_restno_count(int restno) {
    int cnt = this.reviewDAO.list_by_restno_count(restno);
    return cnt;
  }


  
}


	  

