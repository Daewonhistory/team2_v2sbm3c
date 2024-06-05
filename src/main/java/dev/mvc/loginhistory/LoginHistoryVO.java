package dev.mvc.loginhistory;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginHistoryVO {
  /**
   * CREATE TABLE LOGINHISTORY(
   * 		LOGHISTORYNO                  		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
   * 		CUSTNO                        		NUMBER(10)		 NOT NULL,
   * 		LOGINTIME                     		DATE		 NOT NULL,
   * 		IP                            		VARCHAR2(30)		 NOT NULL,
   *         LOGININFO                           VARCHAR2(100) NOT NULL,
   *
   *   FOREIGN KEY (CUSTNO) REFERENCES CUSTOMER (CUSTNO),
   *   CONSTRAINT SYS_C007048 UNIQUE (CUSTNO)
   * );
   */
  private  int loginhistoryno;
  private  int custno;
  private String ip ="";
  private String logininfo="";
  private String city = "";
  private String logintime="";



}
