package dev.mvc.review;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import dev.mvc.dto.ReviewDTO;

public interface ReviewProcInter {
  
  /**
   * 리뷰 등록
   * @param reviewVO
   * @return 등록한 개수
   */
  public int create(ReviewVO reviewVO);

  /**
   * 리뷰 정보
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
   * 페이징된 리뷰 목록
   * @param now_page 현재 페이지 번호
   * @param record_per_page 페이지당 레코드 수
   * @return 레코드 목록
   */
  public ArrayList<ReviewDTO> list_paging(int now_page, int record_per_page);
  
  /**
   * 손님 리뷰 갯수
   * @param custno
   * @return
   */
  public int list_by_custno_count(int custno);
  
  /**
   * 식당 리뷰 갯수
   * @param restno
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
   * 식당 리뷰 조회
   * @param restno
   * @return
   */
  public ArrayList<ReviewDTO> list_by_restno(int restno);
  

  
  /**
   * 페이징 박스 생성
   * @param now_page 현재 페이지 번호
   * @param list_file 목록 파일명
   * @param total_count 전체 레코드 수
   * @param record_per_page 페이지당 레코드 수
   * @param page_per_block 블록당 페이지 수
   * @return 페이징 박스 HTML
   */
  public String pagingBox(int now_page, String list_file, int total_count, int record_per_page, int page_per_block);
  
  
  


}
