package dev.mvc.category;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

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
  @NotEmpty(message = "중분류명은 필수 입력 항목입니다.")
  @Size(min = 2, max = 10, message = "중분류명의 입력 글자 수는 최소 2자에서 10자 이어야합니다.")
  private String name;




}