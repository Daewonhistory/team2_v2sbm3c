package dev.mvc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDTO {

  /** 리뷰 번호 */
  private int reviewno;

  /** 제목 */


  /** 내용 */
  private String content = "";

  /** 평점 */
  private int rate;

  /** 작성 날짜 */
  private String rdate;
  
  /** 고객 번호 */
  private int custno;

  /** 식당 번호 */
  private int restno;

  /** 이미지 파일들 */
  private String image1;
  private String image2;
  private String image3;
  
  /** 식당 이름 */
  private String restname;
  
  /** 유저 이름*/
  private String nickname;
  
  /** 좋아요 여부 추가*/
  private boolean myLike; 
  
  private int likes_count;  // 좋아요 개수 필드 추가
  

}