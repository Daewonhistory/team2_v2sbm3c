package dev.mvc.review;

import java.util.ArrayList;
import java.util.HashMap;

import dev.mvc.review.ReviewVO;

public interface ReviewDAOInter {
  
  /**
   * 리뷰 등록
   * insert id="create" parameterType="dev.mvc.review.reviewVO"
   * @param reviewVO
   * @return 등록한 개수
   */
  public int create(ReviewVO reviewVO);
  
  /**
   * 리뷰 조회
   * select id="read" resultType="dev.mvc.review.ReviewVO" parameterType="int"
   * @param reviewno
   * @return
   */
  public ReviewVO read(int reviewno);
  
  /**
   * delete id="delete" parameterType="int"
   * @param reviewno
   * @return 삭제된 레코드 갯수
   */
  public int delete_review(int reviewno);

  /**
   * 리뷰(글) 수정
   * update id="update_contents" parameterType="dev.mvc.review.ReviewVO"
   * @param reviewVO
   * @return
   */
  public int update_review(ReviewVO reviewVO);
  
  /**
   * 전체 목록
   * select id="list_all" resultType="dev.mvc.review.ReviewVO"     
   * @return 레코드 목록
   */
  public ArrayList<ReviewVO> list_all();
  
  /**
   * 고객의 리뷰 갯수
   * @param custno
   * @return
   */
  public int list_by_custno_count(int custno);
  
  /**
   * 식당 리뷰 갯수
   * @param custno
   * @return
   */
  public int list_by_restno_count(int restno);
  
  
  
  




}
