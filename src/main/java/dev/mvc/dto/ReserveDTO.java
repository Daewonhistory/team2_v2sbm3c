package dev.mvc.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReserveDTO {

  /** 예약 번호 */
  private int reserveno;
  
  /** 작성 날짜 */
  private String sub_date;

  /** 인원 */
  private int person;
  
  /** 고객 번호 */
  private int custno;

  /** 식당 번호 */
  private int restno;
  
  /** 예약 가능한 인원*/
  private int admitpersonno;
  
  /** 식당 이름 */
  private String restname;
  
  /** 유저 이름*/
  private String nickname;
  
  /** 예약 날짜 */
  private String reserveDate;  // 추가된 필드
  
  /** 예약 시간 */
  private String reserveTime;  // 추가된 필드
  
  

  

}