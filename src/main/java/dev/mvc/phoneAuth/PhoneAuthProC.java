package dev.mvc.phoneAuth;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("dev.mvc.phoneAuth.PhoneAuthProc")
public class PhoneAuthProC implements PhoneAuthProInter {
  @Autowired
  private PhoneAuthDAOInter phoneDAO;


  /**
   *  인증 번호 생성 메서드
   * @param phone 폰 번호
   * @param auth  인증번호
   * @return
   */
  @Override
  public int insertPhoneAuth(String phone, String auth) {
    return this.phoneDAO.insertPhoneAuth(phone, auth);
  }


  /**
   *  인증 번호 만료 메서드
   * @param phone 폰 번호
   * @param auth  인증번호
   * @return
   */

  @Override
  public int deleteExpiredAuthCodes(String phone, String auth) {
    return this.phoneDAO.deleteExpiredAuthCodes(phone,auth);
  }


  /**
   * 폰 인증 번호 개수 출력 메서드
   * @param phone
   * @return
   */

  @Override
  public int phonecount(String phone) {
    return this.phoneDAO.phonecount(phone);
  }



  /**
   *  폰 번호 지우는 메서드
   * @param phone
   * @return
   */
  @Override
  public int deleteauth(String phone) {
    return this.phoneDAO.deleteauth(phone);
  }



  /**
   * 인증 로직 메소드
   * @param map 폰 번호  , 인증 번호 맵
   * @return
   */
  @Override
  public int findAuthCodeByEmail(HashMap<String, Object> map) {
    return this.phoneDAO.findAuthCodeByEmail(map);
  }
}
