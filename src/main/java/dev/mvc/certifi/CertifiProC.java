package dev.mvc.certifi;

import dev.mvc.owner.OwnerDAOInter;
import dev.mvc.owner.OwnerProInter;
import dev.mvc.owner.OwnerVO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service("dev.mvc.certifi.CertifiProc")

public class CertifiProC implements CertifiProInter {

  @Autowired
  private CertifiDAOInter certifiDAO;


  @Override
  public int createCertifi(CertifiVO certifiVO) {
    return this.certifiDAO.createCertifi(certifiVO);
  }

  @Override
  public ArrayList<CertifiVO> findByOnwer(int ownerno) {
    return this.certifiDAO.findByOnwer(ownerno);
  }
}
