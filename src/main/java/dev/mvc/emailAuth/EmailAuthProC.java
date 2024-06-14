package dev.mvc.emailAuth;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service("dev.mvc.emailAuth.EmailAuthProc")
public class EmailAuthProC implements EmailAuthProInter {


  @Autowired
  private EmailAuthDAOInter emailDAO;



  @Override
  public int insertEmailAuth(String id, String auth) {
    return this.emailDAO.insertEmailAuth(id,auth);
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
