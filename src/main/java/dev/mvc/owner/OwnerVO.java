package dev.mvc.owner;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OwnerVO {



  /*
CREATE TABLE OWNER(
		OWNERNO                       		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		ID                            		VARCHAR2(30)		 NOT NULL,
		PASSWD                        		VARCHAR2(200)		 NOT NULL,
		NAME                          		VARCHAR2(30)		 NOT NULL,
		PHONE                         		VARCHAR2(14)		 NOT NULL,
		TEL                           		VARCHAR2(14)		 NOT NULL,
		ADDRESS1                      		VARCHAR2(14)		 NOT NULL,
		ADDRESS2                      		VARCHAR2(14)		 NOT NULL,
		GRADE                         		NUMBER(2)		 NOT NULL,
		REG_DATE                      		DATE		 NOT NULL,
		image                         		VARCHAR2(30)		 NOT NULL,
  */

    /** 회원 번호 */
    private int ownerno;
    /** 아이디(이메일) */

    private String id = "";

    /** 패스워드 */

    private String passwd = "";
    /** 회원 성명 */
    private String oname = "";

    /** 회원 닉네임 */
    private String nickname = "";
    /** 전화 번호 */
    private String phone = "";

    private String tel = "";

    /** 주소 1 */
    private String address1 = " ";
    /** 주소 2 */
    private String address2 = " ";
    /** SNS 가입여부 */


    private int grade;


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



