package dev.mvc.customer;

import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service("dev.mvc.customer.CustomerProc")
public class CustomerProC implements CustomerProInter {

  @Autowired
  private CustomerDAOInter customerDAO;

  @Autowired
  private Security security;

  public CustomerProC() {
//    System.out.println("customerProc created");
  }
  /**
   * 중복 닉네임 체크
   * @param nickname
   * @return 추가한 레코드 갯수
   */

  @Override
  public int checkNickName(String nickname) {
    int cnt = this.customerDAO.checkNickName(nickname);
    return cnt;
  }
  /**
   * 중복 아이디 체크
   * @param id
   * @return 추가한 레코드 갯수
   */
  @Override
  public int checkID(String id) {
    int cnt = this.customerDAO.checkID(id);
    return cnt;
  }

  /**
   * 회원 가입
   * @param customerVO
   * @return 추가한 레코드 갯수
   */
  @Override
  public int create(CustomerVO customerVO) {

    customerVO.setPasswd(this.security.aesEncode(customerVO.getPasswd()));
    int cnt = this.customerDAO.create(customerVO);
    return cnt;
  }


  /**
   * 회원 전체 목록
   * @return
   */
  @Override
  public ArrayList<CustomerVO> list() {
    ArrayList<CustomerVO> list = this.customerDAO.list();
    return list;
  }


  /**
   * custno로 회원 정보 조회
   * @param custno
   * @return
   */
  @Override
  public CustomerVO read(int custno) {
    CustomerVO customerVO = this.customerDAO.read(custno);
    return customerVO;
  }


  /**
   * id로 회원 정보 조회
   * @param id
   * @return
   */
  @Override
  public CustomerVO readById(String id) {
    CustomerVO customerVO = this.customerDAO.readById(id);
    return customerVO;
  }


  /**
   * 회원 인지 체크
   * @param session
   * @return
   */

  @Override
  public boolean isCustomer(HttpSession session) {
    boolean sw = false; // 로그인하지 않은 것으로 초기화
    String grade = (String) session.getAttribute("grade");
//    System.out.println(grade);

    if (grade != null) {
      if (grade.equals("admin") || grade.equals("customer")) {
        sw = true;  // 로그인 한 경우
      }
    }
    return sw;
  }

  @Override
  public boolean isCustomerAdmin(HttpSession session) {
    boolean sw = false; // 로그인하지 않은 것으로 초기화
    String grade = (String) session.getAttribute("grade");
    if (grade != null) {
      if (grade.equals("admin")) {
        sw = true;  // 로그인 한 경우
      }
    }
    return sw;
  }


  /**
   * 수정 처리
   * @param customerVO
   * @return
   */
  @Override
  public int update(CustomerVO customerVO) {
    int cnt = this.customerDAO.update(customerVO);
    return cnt;
  }


  /**
   * 회원 삭제 처리
   * @param custno
   * @return
   */
  @Override
  public int delete(int custno) {
    int cnt = this.customerDAO.delete(custno);
    return cnt;
  }



  /**
   * 현재 패스워드 검사
   * @param map
   * @return 0: 일치하지 않음, 1: 일치함
   */
  @Override
  public int passwd_check(HashMap<String, Object> map) {
    int cnt = this.customerDAO.passwd_check(map);
    return cnt;
  }


  /**
   * 패스워드 변경
   * @param map
   * @return 변경된 패스워드 갯수
   */
  @Override
  public int passwd_update(HashMap<String, Object> map) {
    int cnt = this.customerDAO.passwd_update(map);
    return cnt;
  }

  /**
   * 로그인 처리
   */
  @Override
  public int login(HashMap<String, Object> map) {
    int cnt = this.customerDAO.login(map);
    return cnt;
  }


  /**
   * 등급 변경
   * @param map
   * @return
   */
  @Override
  public int update_grade(HashMap<String, Object> map) {
    return this.customerDAO.update_grade(map);
  }


}
