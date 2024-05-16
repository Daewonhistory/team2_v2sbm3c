package dev.mvc.customer;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerVO {



  /*
 CREATE TABLE CUSTOMER(
		CUSTNO                        		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(30)		 NOT NULL,
		PASSWD                        		VARCHAR2(200)		 NOT NULL,
		CNAME                          		VARCHAR2(30)		 NOT NULL,
		NICKNAME                      		VARCHAR2(50)		 NOT NULL,
		PHONE                         		VARCHAR2(14)		 NOT NULL,
		ADDRESS1                      		VARCHAR2(50)		 NULL ,
		ADDRESS2                      		VARCHAR2(50)		 NOT NULL,
		SNS                           		NUMBER(1)		 NULL , -- 1 이면 기본 회원 2면 카카오 3번 구글 4번 애플 5번 네이버
		GRADE                         		NUMBER(2)		 NOT NULL, -- 10이면 일반 회원 20 이면 정지 회원  99면 탈퇴 회원

		gender                        		CHAR(1)		 NOT NULL,
		image                         		VARCHAR2(30)		 NOT NULL,
		REG_DATE                      		DATE		 NOT NULL

  */

    /** 회원 번호 */
    private int custno;
    /** 아이디(이메일) */

    private String id = "";

    /** 패스워드 */

    private String passwd = "";
    /** 회원 성명 */
    private String cname = "";

    /** 회원 닉네임 */
    private String nickname = "";
    /** 전화 번호 */
    private String phone = "";
    /** 우편 번호 */

    /** 주소 1 */
    private String address1 = " ";
    /** 주소 2 */
    private String address2 = " ";
    /** SNS 가입여부 */
    private int sns;

    private int grade;

    private String gender="";
    private String image ="";
    /** 가입일 */
    private String reg_date = "";
    /** 등급 */


    /** 등록된 패스워드 */
    private String old_passwd = "";
    /** id 저장 여부 */
    private String id_save = "";
    /** passwd 저장 여부 */
    private String passwd_save = "";
    /** 이동할 주소 저장 */
    private String url_address = "";


  }



