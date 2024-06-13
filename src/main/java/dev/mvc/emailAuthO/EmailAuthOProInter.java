package dev.mvc.emailAuthO;


import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
@Component
public interface EmailAuthOProInter {

  public int insertEmailAuth(@Param("email") String email, @Param("auth") String auth);

  public int deleteExpiredAuthCodes(@Param("email") String email, @Param("auth") String auth);


  public int emailcount(@Param("email") String email);


  public int deleteauth(@Param("email") String email);

  public int findAuthCodeByEmail(HashMap<String,Object> map);


}



