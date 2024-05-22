package dev.mvc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class restDTO {

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

  private String thumbfile;
}