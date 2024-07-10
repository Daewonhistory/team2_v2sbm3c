package dev.mvc.category;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

//list_all TABLE CATE(
//    CATENO                            NUMBER(10)     NOT NULL    PRIMARY KEY,
//    NAME                              VARCHAR2(30)     NOT NULL,
//    CNT                               NUMBER(7)    DEFAULT 0     NOT NULL,
//    RDATE                             DATE     NOT NULL,
//    SEQNO                             NUMBER(5)    DEFAULT 0     NOT NULL,
//    VISIBLE                           CHAR(1)    DEFAULT 'N'     NOT NULL
//);

@Setter
@Getter
public class CategoryVO {


  /**
   *   카테고리 번호
   */
  private int categoryno;

  /**
   * 카테고리 이름
   */

  private String name ="";

  private String image = "";
  private String thumb = "";
  private Integer seq;

  private String visible = "";
  private String reg_date = "";


  private MultipartFile file1MF;


}