package dev.mvc.customerhistory;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerHistoryVO {
  /**
   * CREATE TABLE CUSTOMERHISTORY(
   * CUSTHISTORYNO                  		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
   * CUSTNO                        		NUMBER(10)		 NOT NULL,
   * IP                            		VARCHAR2(100)		 NOT NULL,
   * LOGININFO                           VARCHAR2(500) NOT NULL,
   * CITY                                VARCHAR2(100) NOT NULL,
   * LOGINTIME                     		DATE		 NOT NULL,
   * <p>
   * FOREIGN KEY (CUSTNO) REFERENCES CUSTOMER (CUSTNO)
   * <p>
   * );
   */
  private int custhistoryno;
  private int custno;
  private String ip = "";
  private String logininfo = "";
  private String city = "";
  private String logintime = "";


}
