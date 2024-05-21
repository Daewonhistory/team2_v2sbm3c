package dev.mvc.certifi;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CertifiVO {




  /*
CREATE TABLE CERTIFI(
		CERTIFINO                     		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
		OWNERNO                       		NUMBER(10)		 NOT NULL,
        BUSINESSNO                    		VARCHAR2(30)		 NOT NULL,
		REG_DATE                      		DATE		 NOT NULL,
		certifi_image                 		VARCHAR2(30)		 NOT NULL,
		identi_card_image             		VARCHAR2(30)		 NOT NULL,
  FOREIGN KEY (OWNERNO) REFERENCES OWNER (OWNERNO),
  CONSTRAINT SYS_C007030 UNIQUE (BUSINESSNO)
);                     		VARCHAR2(30)		 NOT NULL,
  */

  /**
   * 사업자 기본 번호
   */
  private int certifino;
  /**
   * 사업자 번호
   */
  private int ownerno;


  /**
   * 사업자 등록번호
   */
  private String businessno = "";

  /**
   * 사업자 등록증 이미지
   */

  private String certifi_image = "";

  /**
   * 신분증  이미지
   */
  private String identi_card_image = "";


  /**
   * 사업자 등록증 등록날짜
   */
  private String reg_date = "";
  /** 등급 */




}



