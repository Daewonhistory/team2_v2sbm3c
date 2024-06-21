package dev.mvc.review_like;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class Review_likeVO {



  /*
CREATE TABLE review_like (
  review_likeno          NUMBER(10)            NOT NULL  PRIMARY KEY, -- 리뷰 좋아요 번호, 레코드를 구분하는 컬럼 
  reviewno        NUMBER(10)            NOT NULL, -- 리뷰 번호 --FK
  custno   NUMBER(10)            NOT NULL, -- 고객 번호 --FK
  
  FOREIGN KEY (reviewno) REFERENCES REVIEW (reviewno), 
  FOREIGN KEY (custno) REFERENCES CUSTOMER (custno)

);

  */

    /** 리뷰 좋아요번호 */
    private int review_like;

    /** 리뷰 번호 */
    private int reviewno;

    /** 고객 번호  */
    private int custno;



  }



