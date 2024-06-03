package dev.mvc.certifi;

import dev.mvc.owner.OwnerVO;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

public interface CertifiProInter {

  public int createCertifi(CertifiVO certifiVO);

  public ArrayList<CertifiVO> findByOnwer(int ownerno);

  public ArrayList<CertifiVO>  businessnoList(int ownerno);

}



