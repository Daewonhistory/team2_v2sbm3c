package dev.mvc.customer;

import dev.mvc.dto.RestDTO;
import dev.mvc.tool.Security;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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

  @Override
  public int checkNamePhone(String cname, String phone) {

    HashMap<String,Object> map = new HashMap<String,Object>();
    map.put("cname", cname);
    map.put("phone", phone);
    return this.customerDAO.checkNamePhone(map);
  }

  @Override
  public int checkNameEmail(String cname, String id) {

    HashMap<String,Object> map = new HashMap<String,Object>();
    System.out.println(id);
    map.put("cname", cname);
    map.put("id", id);
    return this.customerDAO.checkNameEmail(map);
  }


  @Override
  public int list_search_count(String word, String type) {

    HashMap<String, Object> search = new HashMap<String, Object>();
    search.put("word", word);
    search.put("type", type);

    int count = this.customerDAO.list_search_count(search);
    return count;
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

  @Override
  public ArrayList<CustomerVO> list_search_paging(String word, String type, int now_page, int record_per_page) {

    /*
    예) 페이지당 10개의 레코드 출력
    1 page: WHERE r >= 1 AND r <= 10
    2 page: WHERE r >= 11 AND r <= 20
    3 page: WHERE r >= 21 AND r <= 30

     예) 페이지당 3개의 레코드 출력
    1 page: WHERE r >= 1 AND r <= 3
    2 page: WHERE r >= 4 AND r <= 6
    3 page: WHERE r >= 7 AND r <= 9

    페이지에서 출력할 시작 레코드 번호 계산 기준값, nowPage는 1부터 시작
    1 페이지 시작 rownum: now_page = 1, (1 - 1) * 10 --> 0
    2 페이지 시작 rownum: now_page = 2, (2 - 1) * 10 --> 10
    3 페이지 시작 rownum: now_page = 3, (3 - 1) * 10 --> 20
    */
    int begin_of_page = (now_page - 1) * record_per_page;

    // 시작 rownum 결정
    // 1 페이지 = 0 + 1: 1
    // 2 페이지 = 10 + 1: 11
    // 3 페이지 = 20 + 1: 21
    int start_num = begin_of_page + 1;

    //  종료 rownum
    // 1 페이지 = 0 + 10: 10
    // 2 페이지 = 10 + 10: 20
    // 3 페이지 = 20 + 10: 30
    int end_num = begin_of_page + record_per_page;
    /*
    1 페이지: WHERE r >= 1 AND r <= 10
    2 페이지: WHERE r >= 11 AND r <= 20
    3 페이지: WHERE r >= 21 AND r <= 30
    */

    // System.out.println("begin_of_page: " + begin_of_page);
    // System.out.println("WHERE r >= "+start_num+" AND r <= " + end_num);
    Map<String, Object> map = new HashMap<String, Object>();

    map.put("word", word);
    map.put("type", type);
    map.put("start_num", start_num);
    map.put("end_num", end_num);
    ArrayList<CustomerVO> list = this.customerDAO.list_search_paging(map);
    return list;
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

  @Override
  public int updateProfile(CustomerVO customerVO) {
    return this.customerDAO.updateProfile(customerVO);
  }


  public String pagingBox(int now_page, String word, String type, String list_file, int search_count, int record_per_page, int page_per_block) {
    int total_page = (int) (Math.ceil((double) search_count / record_per_page));
    // 전체 그룹  수: (double)1/10 -> 0.1 -> 1 그룹, (double)12/10 -> 1.2 그룹-> 2 그룹
    int total_grp = (int) (Math.ceil((double) total_page / page_per_block));
    // 현재 그룹 번호: (double)13/10 -> 1.3 -> 2 그룹
    int now_grp = (int) (Math.ceil((double) now_page / page_per_block));

    // 1 group: 1, 2, 3 ... 9, 10
    // 2 group: 11, 12 ... 19, 20
    // 3 group: 21, 22 ... 29, 30
    int start_page = ((now_grp - 1) * page_per_block) + 1; // 특정 그룹의 시작  페이지
    int end_page = (now_grp * page_per_block);               // 특정 그룹의 마지막 페이지

    StringBuffer str = new StringBuffer(); // String class 보다 문자열 추가등의 편집시 속도가 빠름

    // style이 java 파일에 명시되는 경우는 로직에 따라 css가 영향을 많이 받는 경우에 사용하는 방법
    str.append("<nav aria-label='...'>");
    str.append("<ul class='pagination justify-content-center'>");
//      str.append("현재 페이지: " + nowPage + " / " + totalPage + "  ");

    // 이전 10개 페이지로 이동
    // now_grp: 1 (1 ~ 10 page)
    // now_grp: 2 (11 ~ 20 page)
    // now_grp: 3 (21 ~ 30 page)
    // 현재 2그룹일 경우: (2 - 1) * 10 = 1그룹의 마지막 페이지 10
    // 현재 3그룹일 경우: (3 - 1) * 10 = 2그룹의 마지막 페이지 20
    int _now_page = (now_grp - 1) * page_per_block;
    if (now_grp >= 2) { // 현재 그룹번호가 2이상이면 페이지수가 11페이지 이상임으로 이전 그룹으로 갈수 있는 링크 생성
      str.append("<li class='page-item'><A class='page-link' href='" + list_file + "?&word=" + word + "&now_page=" + _now_page + "&type='" + type + "'>이전</A></span>");
    }

    // 중앙의 페이지 목록
    for (int i = start_page; i <= end_page; i++) {
      if (i > total_page) { // 마지막 페이지를 넘어갔다면 페이 출력 종료
        break;
      }

      if (now_page == i) { // 목록에 출력하는 페이지가 현재페이지와 같다면 CSS 강조(차별을 둠)
        str.append("<li class='page-item active'><A class='page-link'>" + i + "</li>"); // 현재 페이지, 강조
      } else {
        // 현재 페이지가 아닌 페이지는 이동이 가능하도록 링크를 설정
        str.append("<li class='page-item'><a class='page-link' href='" + list_file + "?word=" + word + "&type=" + type + "&now_page=" + i + "'>" + i + "</a></li>");
      }
    }

    // 10개 다음 페이지로 이동
    // nowGrp: 1 (1 ~ 10 page),  nowGrp: 2 (11 ~ 20 page),  nowGrp: 3 (21 ~ 30 page)
    // 현재 페이지 5일경우 -> 현재 1그룹: (1 * 10) + 1 = 2그룹의 시작페이지 11
    // 현재 페이지 15일경우 -> 현재 2그룹: (2 * 10) + 1 = 3그룹의 시작페이지 21
    // 현재 페이지 25일경우 -> 현재 3그룹: (3 * 10) + 1 = 4그룹의 시작페이지 31
    _now_page = (now_grp * page_per_block) + 1; //  최대 페이지수 + 1
    if (now_grp < total_grp) {
      str.append("<li class='page-item'><A class='page-link' href='" + list_file + "?&word=" + word + "&now_page=" + _now_page + "'>다음</A></span>");
    }
    str.append("</ul>");
    str.append("</nav>");
    return str.toString();
  }

}
