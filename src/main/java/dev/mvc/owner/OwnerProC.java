package dev.mvc.owner;

import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service("dev.mvc.owner.OwnerProc")
public class OwnerProC implements OwnerProInter {

  @Autowired
  private OwnerDAOInter onwerDAO;


  @Autowired
  private Security security;

  public OwnerProC() {
//    System.out.println("customerProc created");
  }

  @Override
  public int checkNickName(String nickname) {
    int cnt = this.onwerDAO.checkNickName(nickname);
    return cnt;
  }

  @Override
  public int checkID(String id) {
    int cnt = this.onwerDAO.checkID(id);
    return cnt;
  }

  @Override
  public int create(OwnerVO customerVO) {

    customerVO.setPasswd(this.security.aesEncode(customerVO.getPasswd()));
    int cnt = this.onwerDAO.create(customerVO);
    return cnt;
  }

  @Override
  public ArrayList<OwnerVO> list() {
    ArrayList<OwnerVO> list = this.onwerDAO.list();
    return list;
  }

  @Override
  public OwnerVO read(int customerno) {
    OwnerVO customerVO = this.onwerDAO.read(customerno);
    return customerVO;
  }

  @Override
  public OwnerVO readById(String id) {
    OwnerVO customerVO = this.onwerDAO.readById(id);
    return customerVO;
  }


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

  @Override
  public int update(OwnerVO customerVO) {
    int cnt = this.onwerDAO.update(customerVO);
    return cnt;
  }

  @Override
  public int delete(int customerno) {
    int cnt = this.onwerDAO.delete(customerno);
    return cnt;
  }

  @Override
  public int passwd_check(HashMap<String, Object> map) {
    int cnt = this.onwerDAO.passwd_check(map);
    return cnt;
  }

  @Override
  public int passwd_update(HashMap<String, Object> map) {
    int cnt = this.onwerDAO.passwd_update(map);
    return cnt;
  }

  @Override
  public int login(HashMap<String, Object> map) {
    int cnt = this.onwerDAO.login(map);
    return cnt;
  }

  @Override
  public int update_grade(HashMap<String, Object> map) {
    return this.onwerDAO.update_grade(map);
  }


}
