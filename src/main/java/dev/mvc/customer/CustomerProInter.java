package dev.mvc.customer;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface CustomerProInter {

  /**
   * 중복닉네임 체크
   * @param nickname
   * @return 추가한 레코드 갯수
   */
  public int checkNickName(String nickname);

  public ArrayList<CustomerVO> list_search_paging(String word, String type, int now_page, int record_per_page);


  public int list_search_count(String word, String type);

  /**
   * 중복아이디 체크
   * @param id
   * @return 추가한 레코드 갯수
   */
  
  public int checkID(String id);

  /**
   * 회원 가입
   * @param memberVO
   * @return
   */
  public int create(CustomerVO memberVO);

  /**
   * 회원 전체 목록
   * @return
   */
  public ArrayList<CustomerVO> list();

  /**
   * custno로 회원 정보 조회
   * @param custno
   * @return
   */
  public CustomerVO read(int custno);

  /**
   * id로 회원 정보 조회
   * @param id
   * @return
   */
  public CustomerVO readById(String id);

  /**
   * 로그인된 회원 계정인지 검사합니다.
   * @param session
   * @return true: 사용자
   */
  public boolean isCustomer(HttpSession session);

  /**
   * 로그인된 회원 관리자 계정인지 검사합니다.
   * @param session
   * @return true: 사용자
   */
  public boolean isCustomerAdmin(HttpSession session);

  /**
   * 수정 처리
   * @param memberVO
   * @return
   */
  public int update(CustomerVO memberVO);

  /**
   * 회원 삭제 처리
   * @param custno
   * @return
   */
  public int delete(int custno);

  /**
   * 현재 패스워드 검사
   * @param map
   * @return 0: 일치하지 않음, 1: 일치함
   */
  public int passwd_check(HashMap<String, Object> map);

  /**
   * 패스워드 변경
   * @param map
   * @return 변경된 패스워드 갯수
   */
  public int passwd_update(HashMap<String, Object> map);

  /**
   * 로그인 처리
   */
  public int login(HashMap<String, Object> map);

  public int update_grade(HashMap<String,Object> map);

  public int updateProfile(CustomerVO customerVO);

  public String pagingBox(int now_page, String word,String type, String list_file, int search_count,int record_per_page, int page_per_block);


}



