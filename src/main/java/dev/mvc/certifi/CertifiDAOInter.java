package dev.mvc.certifi;

import dev.mvc.owner.OwnerVO;

import java.util.ArrayList;
import java.util.HashMap;

public interface CertifiDAOInter {

   public int createCertifi(CertifiVO certifiVO);

   public ArrayList<CertifiVO> findByOnwer(int ownerno);
}
