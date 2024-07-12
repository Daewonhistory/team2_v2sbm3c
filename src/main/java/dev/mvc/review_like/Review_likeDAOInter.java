package dev.mvc.review_like;

import java.util.Map;

public interface Review_likeDAOInter {
  
  /**
   * 좋아요 추가
   * insert id="increased_likes" parameterType="Map"
   * @param reviewno, memberno
   * @return 증가 여부
   */
  public int increased_likes(Map<String, Object> map);

  /**
   * 좋아요 감소
   * update id="decreased_likes" parameterType="Map"
   * @param reviewno
   * @return 감소 여부
   */
  public int decreased_likes(Map<String, Object> map);
  
  /**
   * review의 likes 삭제 (review 삭제 전)
   * update id="delete" parameterType="Integer"
   * @param reviewno
   * @return
   */
  public int delete(int reviewno);

  /**
   * 총 좋아요 수
   * select id="likes_count" parameterType="Integer"
   * @param reviewno
   * @return 총 좋아요 수
   */
  public int likes_count(int reviewno);
  
  /**
   * 좋아요 개수
   * insert id="mylikes" parameterType="Map"
   * @param reviewno, memberno
   * @return 한 리뷰의 내 좋아요 개수 
   */
  public int mylikes(Map<String, Object> map);
  
  public int delete_by_reviewno(int reviewno);

}
