package dev.mvc.phoneAuthO;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public interface PhoneAuthOProInter {

  /**
   *  인증 번호 생성 메서드
   * @param phone 폰 번호
   * @param auth  인증번호
   * @return
   */
  public int insertPhoneAuth(@Param("phone") String phone, @Param("auth") String auth);
  /**
   *  인증 번호 만료 메서드
   * @param phone 폰 번호
   * @param auth  인증번호
   * @return
   */
  public int deleteExpiredAuthCodes(@Param("phone") String phone, @Param("auth") String auth);

  /**
   * 폰 인증 번호 개수 출력 메서드
   * @param phone
   * @return
   */
  public int phonecount(@Param("phone") String phone);

  /**
   *  폰 번호 지우는 메서드
   * @param phone
   * @return
   */
  public int deleteauth(@Param("phone") String phone);


  /**
   * 인증 로직 메소드
   * @param map 폰 번호  , 인증 번호 맵
   * @return
   */
  public int findAuthCodeByEmail(HashMap<String,Object> map);


}



