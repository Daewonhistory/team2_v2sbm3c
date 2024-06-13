package dev.mvc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestFullData {

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

  private int reserve_range;
  /** 사업자 번호 */
  private int ownerno;
  /** 카테고리 번호 번호 */
  private int categoryno;
  
  private int botareano;
  
  private double rate;

  private String image1;

  private String image2;

  private String image3;

}