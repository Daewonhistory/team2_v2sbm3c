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

  @Override
  public int checkID(String id) {
    int cnt = this.customerDAO.checkID(id);
    return cnt;
  }

  @Override
  public int create(CustomerVO customerVO) {

    customerVO.setPasswd(this.security.aesEncode(customerVO.getPasswd()));
    int cnt = this.customerDAO.create(customerVO);
    return cnt;
  }

  @Override
  public ArrayList<CustomerVO> list() {
    ArrayList<CustomerVO> list = this.customerDAO.list();
    return list;
  }

  @Override
  public CustomerVO read(int customerno) {
    CustomerVO customerVO = this.customerDAO.read(customerno);
    return customerVO;
  }

  @Override
  public CustomerVO readById(String id) {
    CustomerVO customerVO = this.customerDAO.readById(id);
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
  public int update(CustomerVO customerVO) {
    int cnt = this.customerDAO.update(customerVO);
    return cnt;
  }

  @Override
  public int delete(int customerno) {
    int cnt = this.customerDAO.delete(customerno);
    return cnt;
  }

  @Override
  public int passwd_check(HashMap<String, Object> map) {
    int cnt = this.customerDAO.passwd_check(map);
    return cnt;
  }

  @Override
  public int passwd_update(HashMap<String, Object> map) {
    int cnt = this.customerDAO.passwd_update(map);
    return cnt;
  }

  @Override
  public int login(HashMap<String, Object> map) {
    int cnt = this.customerDAO.login(map);
    return cnt;
  }

  @Override
  public int update_grade(HashMap<String, Object> map) {
    return this.customerDAO.update_grade(map);
  }


}
