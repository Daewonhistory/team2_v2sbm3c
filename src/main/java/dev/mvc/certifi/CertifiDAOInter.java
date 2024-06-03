package dev.mvc.certifi;

import dev.mvc.category.CategoryVO;
import dev.mvc.owner.OwnerVO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface CertifiDAOInter {

   /**
    *  사업자 인증 정보 생성
    * @param certifiVO
    * @return
    */
   public int createCertifi(CertifiVO certifiVO);

   /**
    * 사업자 신분증 조회
    * @param ownerno
    * @return
    */
   public ArrayList<CertifiVO> findByOnwer(int ownerno);


   /**
    * 기능 : 검색
    * 매개변수 search 넣을 내용, start_num ,end_num ,
    * @return 표시된 카테고리 객체의 목록  s
    */
   public ArrayList<CategoryVO> list_search_paging(Map<String,Object> map);


//  public int list_search_count(String word);

   public int list_search_count(Map<String, Object> search);

   public ArrayList<CertifiVO>  businessnoList(int ownerno);
}
