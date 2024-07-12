package dev.mvc.review_like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("dev.mvc.review_like.Review_likeProc")
public class Review_likeProc implements Review_likeProcInter{
  
  @Autowired
  private Review_likeDAOInter review_likeDAO;

  @Override
  public int increased_likes(int reviewno, int custno) {
    Map<String, Object> map = new HashMap<>();
    map.put("reviewno", reviewno);
    map.put("custno", custno);
    System.out.println("reviewno" + reviewno);
    System.out.println("custno" + custno);
    int cnt = this.review_likeDAO.increased_likes(map);
    return cnt;
  }


  @Override
  public int decreased_likes(int reviewno, int custno) {
    Map<String, Object> map = new HashMap<>();
    map.put("reviewno", reviewno);
    map.put("custno", custno);
    
    int cnt = this.review_likeDAO.decreased_likes(map);
    return cnt;
  }
  
  @Override
  public int delete(int reviewno) {
    int cnt = this.review_likeDAO.delete(reviewno);
    return cnt;
  }


  @Override
  public int likes_count(int reviewno) {
    int cnt = this.review_likeDAO.likes_count(reviewno);
    return cnt;
  }


  @Override
  public int mylikes(int reviewno, int custno) {
    Map<String, Object> map = new HashMap<>();
    map.put("reviewno", reviewno);
    map.put("custno", custno);
    
    int cnt = this.review_likeDAO.mylikes(map);
    return cnt;
  }
  

  @Override
  public int delete_by_reviewno(int reviewno) {
      return review_likeDAO.delete(reviewno);
  }



}
