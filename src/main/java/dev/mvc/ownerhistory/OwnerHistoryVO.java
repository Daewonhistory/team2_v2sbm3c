package dev.mvc.ownerhistory;


import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OwnerHistoryVO {
  /**
   *CREATE TABLE OWNERHISTORY(
   *                            OWNERHISTORYNO                  		NUMBER(10)		 NOT NULL		 PRIMARY KEY,
   *                            OWNERNO                        		NUMBER(10)		 NOT NULL,
   *                            IP                            		VARCHAR2(30)		 NOT NULL,
   *                            LOGININFO                           VARCHAR2(500) NOT NULL,
   *                            LOGINTIME                     		DATE		 NOT NULL,
   *
   *                            FOREIGN KEY (OWNERNO) REFERENCES OWNER (OWNERNO)
   *
   *
   * );
   */
  private  int ownerhistoryno;
  private  int ownerno;
  private String ip ="";
  private String logininfo="";
  private String city = "";
  private String logintime="";

}
