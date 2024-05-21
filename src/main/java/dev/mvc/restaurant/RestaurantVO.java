package dev.mvc.restaurant;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestaurantVO {



  /*
CREATE TABLE RESTAURANT(
		restno                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		name                          		VARCHAR2(50)		 NOT NULL,
		tel                           		VARCHAR2(14)		 NOT NULL,
		ownerno                       		NUMBER(10)		 NOT NULL,
		cateno                        		NUMBER(10)		 NULL ,
  FOREIGN KEY (ownerno) REFERENCES OWNER (ownerno),
  FOREIGN KEY (cateno) REFERENCES CATEGORY (cateno)
);
  */

    /** 식당 번호 */
    private int restno;

    /** 식당 이름 */
    private String name = "";

    /** 식당 전화 번호 */
    private String tel = "";


    /** 사업자 번호 */
    private int ownerno;
    /** 카테고리 번호 번호 */
    private int categoryno;




  }



