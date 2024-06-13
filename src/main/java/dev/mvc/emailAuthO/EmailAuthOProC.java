package dev.mvc.emailAuthO;


import dev.mvc.emailAuth.EmailAuthDAOInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("dev.mvc.emailAuthO.EmailAuthOProc")
public class EmailAuthOProC implements EmailAuthOProInter {
  @Autowired
  private EmailAuthODAOInter emailDAO;


  @Override
  public int insertEmailAuth(String email, String auth) {
    return this.emailDAO.insertEmailAuth(email,auth);
  }

  @Override
  public int deleteExpiredAuthCodes(String email, String auth) {
    return this.emailDAO.deleteExpiredAuthCodes(email,auth);
  }

  @Override
  public int emailcount(String email) {
    return this.emailDAO.emailcount(email);
  }

  @Override
  public int deleteauth(String email) {
    return this.emailDAO.deleteauth(email);
  }

  @Override
  public int findAuthCodeByEmail(HashMap<String, Object> map) {
    return this.emailDAO.findAuthCodeByEmail(map);
  }
}
