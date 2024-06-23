package dev.mvc.review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import dev.mvc.dto.ReviewDTO;
import dev.mvc.menu.MenuVO;

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
   * select id="list_paging" resultType="dev.mvc.review.ReviewVO"     
   * @return 레코드 목록
   */
  public ArrayList<ReviewDTO> list_paging(Map<String, Object> map);
  
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
  public int list_count();
  
  /**
   * delete id="delete" parameterType="int"
   * @param reviewno
   * @return 삭제된 레코드 갯수
   */
  public int foreign(@Param("restno") int restno, @Param("custno") int custno);
  
  /**
   * 식당에 따라 리뷰 조회
   * @param restno
   * @return
   */
  public ArrayList<ReviewDTO> list_by_restno(int restno);
  
  /**
   * 고객에 따른 리뷰 조회
   * @param custno
   * @return
   */
  public ArrayList<ReviewDTO> list_by_custno(int custno);
  

  

  
  
  
  
  




}
