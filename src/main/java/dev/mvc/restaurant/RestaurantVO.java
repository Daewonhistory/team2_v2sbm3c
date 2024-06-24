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
                           grade                                NUMBER(3)           DEFAULT 1 NOT NULL ,
                           address                              VARCHAR(100)     DEFAULT '-' NOT NULL,
                           lat                                  NUMBER(10,7)    DEFAULT 0 NOT NULL,
                           lng                                  NUMBER(10,7)    DEFAULT 0 NOT NULL,
                           reserverange                         NUMBER(3)       DEFAULT 1 NOT NULL,
                           ownerno                       		NUMBER(10)		 NOT NULL,
                           categoryno                        	NUMBER(10)		 NULL ,
                           botareano                            NUMBER(3)       NULL,
                           FOREIGN KEY (ownerno) REFERENCES OWNER (ownerno),
                           FOREIGN KEY (categoryno) REFERENCES CATEGORY (categoryno),
                           FOREIGN KEY (botareano) REFERENCES BOTAREANO (botareano)
);
  */

    /** 식당 번호 */
    private int restno;

    /** 식당 이름 */
    private String name = "";

    /** 식당 전화 번호 */
    private String tel = "";
    
    private int grade;
    
    private String address;
    
    private double lat;
    
    private double lng;

    private int botareano;
    
    private int reserve_range;
    /** 사업자 번호 */
    private int ownerno;
    /** 카테고리 번호 번호 */
    private int categoryno;




  }



