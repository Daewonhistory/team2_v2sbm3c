package dev.mvc.customer;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

public interface CustomerProInter {
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
   * memberno로 회원 정보 조회
   * @param memberno
   * @return
   */
  public CustomerVO read(int memberno);

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
   * @param memberno
   * @return
   */
  public int delete(int memberno);

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
}



