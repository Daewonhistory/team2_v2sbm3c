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
//    System.out.println("ownerProc created");
  }


  /**
   * 사업자 중복이름 체크
   * @param nickname
   * @return 추가한 레코드 갯수
   */
  @Override
  public int checkNickName(String nickname) {
    int cnt = this.onwerDAO.checkNickName(nickname);
    return cnt;
  }

  /**
   * 회원 중복아이디 체크
   * @param id
   * @return 추가한 레코드 갯수
   */
  @Override
  public int checkID(String id) {
    int cnt = this.onwerDAO.checkID(id);
    return cnt;
  }


  /**
   * 회원 가입
   * @param ownerVO
   * @return 추가한 레코드 갯수
   */
  @Override
  public int create(OwnerVO ownerVO) {

    ownerVO.setPasswd(this.security.aesEncode(ownerVO.getPasswd()));
    int cnt = this.onwerDAO.create(ownerVO);
    return cnt;
  }

  /**
   * 사업자 전체 목록
   * @return
   */
  @Override
  public ArrayList<OwnerVO> list() {
    ArrayList<OwnerVO> list = this.onwerDAO.list();
    return list;
  }


  /**
   * ownerno로 사업자 정보 조회
   * @param ownerno
   * @return
   */
  @Override
  public OwnerVO read(int ownerno) {
    OwnerVO ownerVO = this.onwerDAO.read(ownerno);
    return ownerVO;
  }

  /**
   * id로 사업자 정보 조회
   * @param id
   * @return
   */
  @Override
  public OwnerVO readById(String id) {
    OwnerVO ownerVO = this.onwerDAO.readById(id);
    return ownerVO;
  }


  @Override
  public boolean isOwner(HttpSession session) {
    boolean sw = false; // 로그인하지 않은 것으로 초기화
    String grade = (String) session.getAttribute("grade");
//    System.out.println(grade);

    if (grade != null) {
      if (grade.equals("admin") || grade.equals("owner")) {
        sw = true;  // 로그인 한 경우
      }
    }
    return sw;
  }

  @Override
  public boolean isOwnerAdmin(HttpSession session) {
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
   * @param ownerVO
   * @return
   */
  @Override
  public int update(OwnerVO ownerVO) {
    int cnt = this.onwerDAO.update(ownerVO);
    return cnt;
  }


  /**
   * 사업자 삭제 처리
   * @param ownerno
   * @return
   */
  @Override
  public int delete(int ownerno) {
    int cnt = this.onwerDAO.delete(ownerno);
    return cnt;
  }


  /**
   * 현재 패스워드 검사
   * @param map
   * @return 0: 일치하지 않음, 1: 일치함
   */
  @Override
  public int passwd_check(HashMap<String, Object> map) {
    int cnt = this.onwerDAO.passwd_check(map);
    return cnt;
  }

  /**
   * 패스워드 변경
   * @param map
   * @return 변경된 패스워드 갯수
   */
  @Override
  public int passwd_update(HashMap<String, Object> map) {
    int cnt = this.onwerDAO.passwd_update(map);
    return cnt;
  }

  /**
   * 로그인 처리
   */
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
