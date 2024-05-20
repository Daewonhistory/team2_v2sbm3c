package dev.mvc.owner;

import java.util.ArrayList;
import java.util.HashMap;

public interface OwnerDAOInter {

  /**
   * 회원 중복아이디 체크
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
   * @param customerVO
   * @return 추가한 레코드 갯수
   */
  public int create(OwnerVO customerVO);

  /**
   * 회원 전체 목록
   * @return
   */
  public ArrayList<OwnerVO> list();

  public int update_grade(HashMap<String,Object> map);

  /**
   * memberno로 회원 정보 조회
   * @param memberno
   * @return
   */
  public OwnerVO read(int memberno);

  /**
   * id로 회원 정보 조회
   * @param id
   * @return
   */
  public OwnerVO readById(String id);

  /**
   * 수정 처리
   * @param customerVO
   * @return
   */
  public int update(OwnerVO customerVO);
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




}
