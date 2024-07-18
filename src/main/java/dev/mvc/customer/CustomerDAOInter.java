package dev.mvc.customer;

import dev.mvc.dto.RestDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface CustomerDAOInter {

  /**
   * 중복 닉네임 체크
   * @param nickname
   * @return 추가한 레코드 갯수
   */
  public int checkNickName(String nickname);


  /**
   * 아이디 찾기 시 회원 조회
   * @param cname
   * @param phone
   * @return 추가한 레코드 갯수
   */

  public String findNamePhone(HashMap<String , Object> map) ;

  public String findNameEmail(HashMap<String , Object> map) ;

  public int passwd_updates(HashMap<String, Object> map);

  public int checkNamePhone(HashMap<String , Object> map) ;

  public int checkNameEmail(HashMap<String , Object> map) ;

  /**
   * 중복 아이디 체크
   * @param id
   * @return 추가한 레코드 갯수
   */
  public int checkID(String id);

  /**
   * 회원 가입
   * @param customerVO
   * @return 추가한 레코드 갯수
   */
  public int create(CustomerVO customerVO);

  /**
   * 회원 전체 목록
   * @return
   */
  public ArrayList<CustomerVO> list();

  /**
   * 등급 변경
   * @param map
   * @return
   */
  public int update_grade(HashMap<String,Object> map);

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
   * 수정 처리
   * @param customerVO
   * @return
   */
  public int update(CustomerVO customerVO);
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

  public int updateProfile(CustomerVO customerVO);


  /**
   * 기능 : 사업자 번호로 검색
   * 매개변수 search 넣을 내용, start_num ,end_num ,
   * @return 표시된 카테고리 객체의 목록  s
   */
  public ArrayList<CustomerVO> list_search_paging(Map<String,Object> map);

  public int list_search_count(HashMap<String,Object> map);


  public int currval();

}
