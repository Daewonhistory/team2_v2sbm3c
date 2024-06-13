package dev.mvc.favorite;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FavoriteVO {



  /*CREATE TABLE favorite (
  favoriteno       NUMBER(10)            NOT NULL  PRIMARY KEY, -- 즐겨찾기 번호, 레코드를 구분하는 컬럼 
  restno           NUMBER(10)            NOT NULL, -- 식당 번호 --FK
  custno           NUMBER(10)            NOT NULL, -- 고객 번호 --FK
  resturl          VARCHAR(255)          NOT NULL, -- 식당 url
  
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno), 
  FOREIGN KEY (custno) REFERENCES CUSTOMER (custno)
);

  */
  
  /** 좋아요 번호 */
  private int favoriteno;
  
  /** 식당 번호 */
  private int restno;
  
  /** 고객 번호 */
  private int custno;
  
  /** 식당url */
  private String resturl;
}