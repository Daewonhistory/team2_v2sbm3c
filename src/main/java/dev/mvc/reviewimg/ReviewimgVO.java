package dev.mvc.reviewimg;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
public class ReviewimgVO {



  /*
CREATE TABLE REVIEWIMAGE(
		review_imgno                    		NUMBER(10)		 NULL 		 PRIMARY KEY,
		imagefile                     		VARCHAR2(30)		 NULL ,
		thumbfile                     		VARCHAR2(30)		 NULL ,
		reviewno                        		NUMBER(10)		 NULL ,
  FOREIGN KEY (no) REFERENCES REVIEW (reviewno)
);

  */

    /** 리뷰 번호 */
    private int review_imgno;

    /** 리뷰 이미지 */
    private String imagefile = "";

    /** 리뷰 썸네일 이미지  */
    private String thumbfile = "";


    /** 리뷰 번호 */
    private int reviewno;


    private MultipartFile file1MF;
    private MultipartFile file2MF;
    private MultipartFile file3MF;

  }



