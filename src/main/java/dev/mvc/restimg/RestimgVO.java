package dev.mvc.restimg;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestimgVO {



  /*
CREATE TABLE RESTIMAGE(
		rest_imgno                    		NUMBER(10)		 NULL 		 PRIMARY KEY,
		imagefile                     		VARCHAR2(30)		 NULL ,
		thumbfile                     		VARCHAR2(30)		 NULL ,
		restno                        		NUMBER(10)		 NULL ,
  FOREIGN KEY (restno) REFERENCES RESTAURANT (restno)
);

  */

    /** 식당 번호 */
    private int rest_imgno;

    /** 식당 이미지 */
    private String imagefile = "";

    /** 식당 썸네일 이미지  */
    private String thumbfile = "";


    /** 식당 번호 */
    private int restno;
    /** 카테고리 번호 번호 */
    private int cateno;




  }



