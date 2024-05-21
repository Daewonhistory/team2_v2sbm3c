package dev.mvc.owner;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashMap;

public interface OwnerProInter {

  /**
   * 중복닉네임 체크
   * @param nickname
   * @return 추가한 레코드 갯수
   */
  public int checkNickName(String nickname);


  /**
   * 중복아이디 체크
   * @param id
   * @return 추가한 레코드 갯수
   */
  
  public int checkID(String id);

  /**
   * 회원 가입
   * @param onwerVO
   * @return
   */
  public int create(OwnerVO onwerVO);

  /**
   * 회원 전체 목록
   * @return
   */
  public ArrayList<OwnerVO> list();

  /**
   * onwerno로 회원 정보 조회
   * @param onwerno
   * @return
   */
  public OwnerVO read(int onwerno);

  /**
   * id로 회원 정보 조회
   * @param id
   * @return
   */
  public OwnerVO readById(String id);

  /**
   * 로그인된 회원 계정인지 검사합니다.
   * @param session
   * @return true: 사용자
   */
  public boolean isOwner(HttpSession session);

  /**
   * 로그인된 회원 관리자 계정인지 검사합니다.
   * @param session
   * @return true: 사용자
   */
  public boolean isOwnerAdmin(HttpSession session);

  /**
   * 수정 처리
   * @param onwerVO
   * @return
   */
  public int update(OwnerVO onwerVO);

  /**
   * 회원 삭제 처리
   * @param onwerno
   * @return
   */
  public int delete(int onwerno);

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

  /**
   *
   * 등급 수정
   * @param map
   * @return
   */
  public int update_grade(HashMap<String,Object> map);
}



