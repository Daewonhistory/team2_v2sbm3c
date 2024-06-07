package dev.mvc.owner;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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

    /** 사업자 닉네임 */
    private String nickname = "";
    /** 휴대폰 번호 */
    private String phone = "";
    /** 회사번호 */
    private String tel = "";

    /** 우편번호 번호 */
    private String zipcode = "";
    /** 주소 1 */
    private String address1 = " ";
    /** 주소 2 */
    private String address2 = " ";
    /** 등급 */
    private int grade;

    /** 프로필 이미지 */
    private String image ="";
    /** 가입일 */
    private String reg_date = "";
    /** 사업자등록증 번호 */
    private String businessno = "";

    /** 사업자 등록증 이미지 */
    private String certifi_image = "";
    /** 신분증  이미지 */
    private String identi_card_image = "";
    /** 사업자 등록한 날짜 */

    private String business_reg_date = "";
    /** 등록된 패스워드 */
    private String old_passwd = "";
    /** id 저장 여부 */
    private String id_save = "";
    /** passwd 저장 여부 */
    private String passwd_save = "";
    /** 이동할 주소 저장 */
    private String url_address = "";

    private MultipartFile file1MF;
    private MultipartFile file2MF;

    
  }



